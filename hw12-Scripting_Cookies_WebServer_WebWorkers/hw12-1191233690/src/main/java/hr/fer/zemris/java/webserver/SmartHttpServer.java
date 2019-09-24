package hr.fer.zemris.java.webserver;

import java.util.Map;
import java.util.Properties;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.Random;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.net.Socket;
import java.net.ServerSocket;
import java.net.InetSocketAddress;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.io.PushbackInputStream;
import java.lang.reflect.InvocationTargetException;
import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * This class is simple http server.
 * 
 * @author Jelić, Nikola
 */
public class SmartHttpServer {

	/**
	 * Address of server
	 */
	private String address;
	/**
	 * Domain name of server
	 */
	private String domainName;
	/**
	 * Server's port
	 */
	private int port;
	/**
	 * Number of workers in threadPool
	 */
	private int workerThreads;
	/**
	 * Timeout of one session
	 */
	private int sessionTimeout;
	/**
	 * Mime types
	 */
	private Map<String, String> mimeTypes = new HashMap<String, String>();
	/**
	 * Thread of server
	 */
	private ServerThread serverThread;
	/**
	 * ThreadPool of client workers
	 */
	private ExecutorService threadPool;
	/**
	 * Path of document root
	 */
	private Path documentRoot;
	/**
	 * Key: path; Values: Workers
	 */
	private Map<String, IWebWorker> workersMap = new HashMap<>();
	/**
	 * Key: SIDs; Values: {@link SessionMapEntry}
	 */
	private Map<String, SessionMapEntry> sessions = new HashMap<String, SmartHttpServer.SessionMapEntry>();
	/**
	 * Used for SID randomization
	 */
	private Random sessionRandom = new Random();

	/**
	 * Session map entry is used for tracking session of clients.
	 * 
	 * @author Jelić, Nikola
	 */
	private static class SessionMapEntry {
		/**
		 * Session id
		 */
		String sid;
		/**
		 * Hostname without port
		 */
		String host;
		/**
		 * Time of session validation in milliseconds since 1.1.1970.
		 */
		long validUntil;
		/**
		 * Map of important parameters 
		 */
		Map<String, String> map;
	}

	/**
	 * Basic constructor
	 * 
	 * @param configFileName file of server configuration
	 */
	public SmartHttpServer(String configFileName) {

		try {
			Properties serverProp = getProperties(configFileName);

			address = serverProp.getProperty("server.address");
			domainName = serverProp.getProperty("server.domainName");
			port = Integer.parseInt(serverProp.getProperty("server.port"));
			workerThreads = Integer.parseInt(serverProp.getProperty("server.workerThreads"));
			sessionTimeout = Integer.parseInt(serverProp.getProperty("session.timeout"));
			documentRoot = Paths.get(serverProp.getProperty("server.documentRoot")).toAbsolutePath().normalize();

			Properties propMime = getProperties(serverProp.getProperty("server.mimeConfig"));
			Set<String> keys = propMime.stringPropertyNames();
			for (String key : keys) {
				mimeTypes.put(key, propMime.getProperty(key));
			}

			loadWorkers(getProperties(serverProp.getProperty("server.workers")));
		} catch (IOException e) {
			System.out.println("Couldn't read config file.");
			return;
		}

	}

	/**
	 * Loads {@link IWebWorker}s given by {@link Properties}.
	 * 
	 * @param prop Properties of workers
	 */
	private void loadWorkers(Properties prop) {
		Set<String> keys = prop.stringPropertyNames();
		for (String key : keys) {

			String path = documentRoot.resolve(key.substring(1)).toAbsolutePath().normalize().toString();
			String fqcn = prop.getProperty(key);
			
			if (workersMap.get(path) != null) {
				throw new RuntimeException("There are multiple lines with same path: " + path);
			}

			IWebWorker iww = jvmLoadClass(fqcn);
			if (iww != null) {
				workersMap.put(path, iww);
			}
		}
	}

	/**
	 * JVM loads {@link IWebWorker} by using class and FQCN(fully qualified class name).
	 * @param fqcn (fully qualified class name)
	 * @return returns {@link IWebWorker} or null if couldn't create.
	 */
	private IWebWorker jvmLoadClass(String fqcn) {
		try {
			Class<?> referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);
			Object newObject = referenceToClass.getDeclaredConstructor().newInstance();
			IWebWorker iww = (IWebWorker) newObject;
			return iww;
		} catch (ClassNotFoundException | IllegalAccessException | InstantiationException | InvocationTargetException
				| NoSuchMethodException e) {
			return null;
		}
	}

	/**
	 * Creates and returns Properties from path.
	 * 
	 * @param path String path of Properties
	 * @return properties of given path
	 * @throws IOException if exception occurs while creating properties.
	 */
	private Properties getProperties(String path) throws IOException {
		InputStream is;
		is = Files.newInputStream(Paths.get(path));
		Properties prop = new Properties();
		prop.load(is);
		return prop;
	}

	/**
	 * Starts server thread if not already running by initializing threadPool.
	 */
	protected synchronized void start() {
		if (serverThread != null) {
			return;
		}
		System.out.println("Server started...\n");
		threadPool = Executors.newFixedThreadPool(workerThreads, new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				Thread t = new Thread(r);
				t.setDaemon(true);
				return t;
			}
		});
		serverThread = new ServerThread();
		serverThread.run();
	}

	/**
	 * Signals to server thread to stop running and shutdown threadPool.
	 */
	protected synchronized void stop() {
		serverThread.interrupt();
		threadPool.shutdownNow();
		serverThread = null;
	}

	/**
	 * Class used as server thread.
	 * 
	 * @author Jelić, Nikola
	 */
	protected class ServerThread extends Thread {
		@Override
		public void run() {
			try (ServerSocket serverSocket = new ServerSocket()) {
				serverSocket.bind(new InetSocketAddress(address, port));

				System.out.println(serverSocket.getLocalSocketAddress());
				System.out.println(serverSocket.getLocalPort());
				while (true) {
					Socket client = serverSocket.accept();
					ClientWorker cw = new ClientWorker(client);
					threadPool.submit(cw);
				}

			} catch (IOException e) {
				System.out.println("IOException occured while starting server: " + e.getMessage());
			}
		}
	}

	/**
	 * ClientWorker is used to create communication between server and client.
	 * 
	 * @author Jelić, Nikola
	 */
	private class ClientWorker implements Runnable, IDispatcher {
		/**
		 * Socket used for communication with client.
		 */
		private Socket csocket;
		/**
		 * Input stream
		 */
		private PushbackInputStream istream;
		/**
		 * Output stream
		 */
		private OutputStream ostream;
		/**
		 * version of HTTP
		 */
		private String version;
		/**
		 * method mostly GET
		 */
		private String method;
		/**
		 * Pure host name - without socket
		 */
		private String host;
		/**
		 * Parameters.
		 */
		private Map<String, String> params = new HashMap<String, String>();
		/**
		 * Temporary parameters.
		 */
		private Map<String, String> tempParams = new HashMap<String, String>();
		/**
		 * Permanent parameters given from {@link SessionMapEntry}.map
		 */
		private Map<String, String> permPrams;
		/**
		 * List of output cookies.
		 */
		private List<RCCookie> outputCookies = new ArrayList<RequestContext.RCCookie>();
		/**
		 * Session ID
		 */
		private String SID;
		/**
		 * Through which server communicates with client.
		 */
		private RequestContext context = null;

		/**
		 * Basic constructor sets {@link Socket}
		 * @param csocket socket used for communication
		 */
		public ClientWorker(Socket csocket) {
			super();
			this.csocket = csocket;
		}

		@Override
		public void run() {
			try {
				// input & output streams of socket
				istream = new PushbackInputStream(csocket.getInputStream());
				ostream = csocket.getOutputStream();

				// Then read complete request header from your client in separate method...
				List<String> request = readRequest();

				// If header is invalid (less then a line at least) returns response status 400
				if (request == null) {
					sendError(400, "Bad request");
					return;
				}

				// prints header
				for (String line : request) {
					System.out.print(line);
				}

				String firstLine = request.get(0);
				Path requestedPath = extractMethodAndVersion(firstLine);
				if (requestedPath == null) {
					return;
				}

				setHost(request);

				checkSession(request);
				// sets HttpOnly
				outputCookies.add(new RCCookie("sid", SID, null, host, "/; HttpOnly"));

				
				String path;
				String paramString;
				
				// (path, paramString) = split requestedPath to path and parameterString
				int index = requestedPath.toString().indexOf('?');
				if (index == -1) {
					path = requestedPath.toString();
				} else {
					path = requestedPath.toString().substring(0, index);
					paramString = requestedPath.toString().substring(index + 1);
					if (parseParameters(paramString)) {
						return;
					}
				}

				// extreme case calls workers directly
				if (path.startsWith("/ext")) {
					IWebWorker iww = jvmLoadClass(
							"hr.fer.zemris.java.webserver.workers." + path.substring(path.lastIndexOf('/') + 1));
					if (iww != null) {
						try {
							createContext();
							iww.processRequest(context);
						} catch (Exception e) {
							sendError(400, "Bad request");
						}
					} else {
						sendError(400, "Bad request");
					}
					return;
				}

				// else needs to be dispatched
				try {
					internalDispatchRequest(path, true);
				} catch (Exception e) {
					sendError(400, "Bad request");
					return;
				}

			} catch (IOException e) {
			} finally {
				try {
					csocket.close();
				} catch (IOException e) {
				}
			}
		}

		/**
		 * Checks if Cookie was sent and has valid sessionID (SID).
		 * 
		 * @param request list of header lines.
		 */
		private void checkSession(List<String> request) {
			for (String line : request) {
				if (line.startsWith("Cookie:")) {
					int index = line.indexOf("sid=\"") + "sid=\"".length();
					int quote = line.indexOf('\"', index);
					SID = line.substring(index, quote);
					break;
				}
			}

			// checks if already SID exist in sessions
			if (SID == null || sessions.get(SID) == null) {
				permPrams = createSession();
				return;
			} else {
				SessionMapEntry entry = sessions.get(SID);
				// wrong host
				if (!entry.host.equals(host)) {
					permPrams = createSession();
					return;
				}

				// time passed
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				if (entry.validUntil < timestamp.getTime()) {
					sessions.remove(SID);
					permPrams = createSession();
					return;
				} else {
					// valid SID exists
					entry.validUntil = timestamp.getTime() + sessionTimeout * 1000;
					permPrams = entry.map;
				}
			}
		}

		/**
		 * Creates new {@link SessionMapEntry} and returns map.
		 * 
		 * @return map of entry
		 */
		private Map<String, String> createSession() {
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			SessionMapEntry entry = new SessionMapEntry();
			generateSID();
			entry.sid = SID;
			entry.map = new ConcurrentHashMap<>();
			entry.validUntil = timestamp.getTime() + sessionTimeout * 1000;
			entry.host = host;
			sessions.put(SID, entry);
			System.out.println("sid: " + SID);
			return entry.map;
		}

		/**
		 * Generates session ID by randomizing 20 Upper chars.
		 */
		private void generateSID() {
			int bound = 26;
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < 20; i++) {
				sb.append(Character.toChars(65 + sessionRandom.nextInt(bound)));
			}
			SID = sb.toString();
		}

		@Override
		public void dispatchRequest(String urlPath) throws Exception {
			internalDispatchRequest(urlPath, false);
		}

		/**
		 * Extracts method(GET) and version(HTTP/1.1) from header, returns
		 * path(www.locallhost.com/...)
		 * 
		 * @param firstLine
		 * @return
		 * @throws IOException
		 */
		private Path extractMethodAndVersion(String firstLine) throws IOException {
			String[] words = firstLine.split("\\s");
			if (words.length < 3 || !words[0].equals("GET")
					|| (!words[2].equals("HTTP/1.0") && !words[2].equals("HTTP/1.1"))) {
				sendError(400, "Bad request");
				return null;
			}

			method = words[0];
			version = words[2];
			return Paths.get(words[1]);
		}

		/**
		 * Dispatches request. If directCall is true and urlPath starts with '/private'
		 * sends error 404.
		 * 
		 * @param urlPath    Path used for determination what client wants
		 * @param directCall if path was called with 'private' part
		 * @throws Exception throws forward
		 */
		public void internalDispatchRequest(String urlPath, boolean directCall) throws Exception {
			// security
			if (directCall && (urlPath.startsWith("/private/") || urlPath.equals("/private"))) {
				sendError(404, "Not Found");
				return;
			}

			// if requestedPath is not below documentRoot, return response status 403
			// forbidden
			Path requestedPath = documentRoot.resolve(urlPath.substring(1));
			if (!requestedPath.startsWith(documentRoot)) {
				sendError(403, "Forbidden");
				return;
			}

			// if worker is called with path
			if (workersMap.get(requestedPath.toString()) != null) {
				createContext();
				workersMap.get(requestedPath.toString()).processRequest(context);
			}

			// check if requestedPath exists, is file and is readable; if not, return status
			// 404 else extract file extension
			if (!Files.exists(requestedPath) || Files.isDirectory(requestedPath) || !Files.isReadable(requestedPath)) {
				sendError(404, "File not found");
				return;
			}

			String extension = "";
			int i = requestedPath.toString().lastIndexOf('.');
			if (i > 0) {
				extension = requestedPath.toString().substring(i + 1);
			}

			// find in mimeTypes map appropriate mimeType for current file extension
			// map was filled during the construction of SmartHttpServer from
			// mime.properties
			String mime = mimeTypes.get(extension);
			if (mime == null) {
				mime = new String("application/octet-stream");
			}

			// if special request / 'smscr' file
			if (extension.equals("smscr")) {
				processSmscr(requestedPath, mime);
			} else {
				// reads from file
				createContext();
				context.setMimeType(mime);
				context.setStatusCode(200);
				byte[] bytes = Files.readAllBytes(requestedPath);
				context.setContentLength((long) bytes.length);
				context.write(bytes);
			}
		}

		/**
		 * Creates context if is null.
		 */
		private void createContext() {
			if (context == null) {
				context = new RequestContext(ostream, params, permPrams, outputCookies, tempParams, this, SID);
			}
		}

		/**
		 * Processes 'smscr' files.
		 * 
		 * @param requestedPath path of file.
		 * @param mime          type of mime.
		 * @throws Exception if occurs while reading string or executing
		 *                   {@link SmartScriptEngine}.
		 */
		private void processSmscr(Path requestedPath, String mime) throws Exception {
			String documentBody = Files.readString(requestedPath);
			createContext();

			// create engine and execute it
			new SmartScriptEngine(new SmartScriptParser(documentBody).getDocumentNode(), context).execute();
		}

		/**
		 * Parses parameters. Delimiter is '&'. Every parameter has name and value.
		 * [name=value&name2=...]
		 * 
		 * @param paramString contains name-value pairs
		 * @return true if error occurs, else false
		 * @throws IOException if sendError throws {@link IOException}
		 */
		private boolean parseParameters(String paramString) throws IOException {
			String[] words = paramString.split("&");
			for (String word : words) {
				String[] keyValue = word.split("=");
				if (keyValue.length != 2) {
					sendError(400, "Bad request");
					return true;
				}
				params.put(keyValue[0], keyValue[1]);
			}
			return false;
		}

		/**
		 * Sets host. Goes through headers, and if there is header “Host: xxx”, assign
		 * host property. Or, sets domainName if "xxx" is not valid.
		 * 
		 * @param request is list of lines.
		 */
		private void setHost(List<String> request) {
			for (String line : request) {
				int index = line.indexOf("Host:");
				if (index != -1) {
					host = line.split("\\s+")[1];
					index = host.indexOf(':');
					if (index != -1) {
						host = host.substring(0, index);
					}
					return;
				}
			}
			host = domainName;
		}

		/**
		 * Reads request and returns as list of lines.
		 * 
		 * @return list of lines of request.
		 * @throws IOException possible occurs while reading istream
		 */
		private List<String> readRequest() throws IOException {
			StringBuilder sb = new StringBuilder();
			List<String> list = new ArrayList<>();
			int state = 0;
			l: while (true) {
				int b = istream.read();
				if (b == -1) {
					return null;
				}
				if (b != '\r') {
					sb.append((char) b);
				}
				switch (state) {
				case 0:
					if (b == '\r') {
						state = 1;
					} else if (b == '\n') {
						list.add(sb.toString());
						sb = new StringBuilder();
						state = 4;
					}
					break;
				case 1:
					if (b == '\n') {
						list.add(sb.toString());
						sb = new StringBuilder();
						state = 2;
					} else {
						state = 0;
					}
					break;
				case 2:
					if (b == '\r') {
						state = 3;
					} else {
						state = 0;
					}
					break;
				case 3:
					if (b == '\n') {
						list.add(sb.toString());
						sb = new StringBuilder();
						break l;
					} else {
						state = 0;
					}
					break;
				case 4:
					if (b == '\n') {
						list.add(sb.toString());
						sb = new StringBuilder();
						break l;
					} else {
						state = 0;
					}
					break;
				}
			}
			return list;
		}

		/**
		 * Sends error message to client.
		 * 
		 * @param statusCode status number
		 * @param statusText describing text
		 * @throws IOException if
		 */
		private void sendError(int statusCode, String statusText) throws IOException {
			createContext();
			context.setStatusCode(statusCode);
			context.setStatusText(statusText);
			context.setMimeType("text/plain");
			context.setEncoding("UTF-8");
			context.setContentLength(0l);
			context.write("");
		}
	}

	/**
	 * Main method.
	 * 
	 * @param args first argument is path to configurations.
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("Expected only one argument: path to configurations 'server.properties'.");
			return;
		}
		SmartHttpServer shs = new SmartHttpServer(args[0]);
		shs.start();
	}
}