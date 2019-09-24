package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.ArrayList;
import java.util.Set;

import com.sun.javadoc.Parameter;

import hr.fer.zemris.java.webserver.IDispatcher;

/**
 * Used for communication with client. Has inner class {@link RCCookie} which is
 * used for cookies collection.
 * 
 * @author Jelić, Nikola
 */
public class RequestContext {

	/**
	 * This class models cookie.
	 * 
	 * @author Jelić, Nikola
	 */
	public static class RCCookie {

		/**
		 * Cookie name.
		 */
		private String name;
		/**
		 * Value of cookie
		 */
		private String value;
		/**
		 * Domain which client has sent
		 */
		private String domain;
		/**
		 * Path which client has sent
		 */
		private String path;
		/**
		 * Maximum age of cookie.
		 */
		private Integer maxAge;

		/**
		 * Constructor that sets all private fields.
		 * 
		 * @param name   Cookie name.
		 * @param value  Value of cookie
		 * @param maxAge Maximum age of cookie.
		 * @param domain Domain which client has sent
		 * @param path   Path which client has sent
		 */
		public RCCookie(String name, String value, Integer maxAge, String domain, String path) {
			super();
			this.name = name;
			this.value = value;
			this.domain = domain;
			this.path = path;
			this.maxAge = maxAge;
		}

		/**
		 * Getter of name
		 * 
		 * @return name
		 */
		public String getName() {
			return name;
		}

		/**
		 * Getter of value
		 * 
		 * @return value
		 */
		public String getValue() {
			return value;
		}

		/**
		 * Getter of domain
		 * 
		 * @return domain
		 */
		public String getDomain() {
			return domain;
		}

		/**
		 * Getter of path
		 * 
		 * @return path
		 */
		public String getPath() {
			return path;
		}

		/**
		 * Getter of maxAge
		 * 
		 * @return maxAge
		 */
		public Integer getMaxAge() {
			return maxAge;
		}
	}

	/**
	 * Output stream from server to client
	 */
	private OutputStream outputStream;
	/**
	 * Charset of encoding
	 */
	private Charset charset;
	/**
	 * Encoding in which text is sent
	 */
	private String encoding = new String("UTF-8");
	/**
	 * Status text of header
	 */
	private String statusText = new String("OK");
	/**
	 * Mime type, default is text/html.
	 */
	private String mimeType = new String("text/html");
	/**
	 * length of the content(after header)
	 */
	private Long contentLength = null;
	/**
	 * Status code of header
	 */
	private int statusCode = 200;
	/**
	 * Flag - is header generated
	 */
	private boolean headerGenerated = false;
	/**
	 * Parameters used for scripting
	 */
	private Map<String, String> parameters;
	/**
	 * Temporary parameters used for scripting
	 */
	private Map<String, String> temporaryParameters = new HashMap<>();
	/**
	 * Persistent parameters used for communication with client within session
	 */
	private Map<String, String> persistentParameters;
	/**
	 * Cookies which are sent to client
	 */
	private List<RCCookie> outputCookies;
	/**
	 * Dispatcher is mostly called by Workers
	 */
	private IDispatcher dispatcher;
	/**
	 * Session id
	 */
	private String sid;

	/**
	 * Basic constructor sets outputStream, parameters, persistentParameters and
	 * outputCookies.
	 * 
	 * @param outputStream         Output stream from server to client
	 * @param parameters           Parameters used for scripting
	 * @param persistentParameters Persistent parameters used for communication with
	 *                             client within session
	 * @param outputCookies        Cookies which are sent to client
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies) {
		this.outputStream = Objects.requireNonNull(outputStream);
		if (parameters == null) {
			this.parameters = new HashMap<>();
		} else {
			this.parameters = parameters;
		}
		if (persistentParameters == null) {
			this.persistentParameters = new HashMap<>();
		} else {
			this.persistentParameters = persistentParameters;
		}
		if (outputCookies == null) {
			this.outputCookies = new ArrayList<>();
		} else {
			this.outputCookies = outputCookies;
		}
	}

	/**
	 * This constructor is used in {@link SmartHttpServer}
	 * 
	 * @param outputStream         Output stream from server to client
	 * @param parameters           Parameters used for scripting
	 * @param persistentParameters Persistent parameters used for communication with
	 *                             client within session
	 * @param outputCookies        Cookies which are sent to client
	 * @param temporaryParameters  Temporary parameters used for scripting
	 * @param dispatcher           Dispatcher is mostly called by Workers
	 * @param sid                  Session id
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies,
			Map<String, String> temporaryParameters, IDispatcher dispatcher, String sid) {
		this(outputStream, parameters, persistentParameters, outputCookies);
		if (temporaryParameters == null) {
			this.temporaryParameters = new HashMap<>();
		} else {
			this.temporaryParameters = temporaryParameters;
		}
		this.dispatcher = dispatcher;
		this.sid = sid;
	}

	/**
	 * Setter of encoding
	 * 
	 * @param encoding
	 * @throws RuntimeException if tries to change while header was already written
	 */
	public void setEncoding(String encoding) {
		if (headerGenerated && !this.encoding.equals(encoding)) {
			throw new RuntimeException();
		}
		this.encoding = encoding;
	}

	/**
	 * Setter of statusText
	 * 
	 * @param statusText
	 * @throws RuntimeException if tries to change while header was already written
	 */
	public void setStatusText(String statusText) {
		if (headerGenerated && !this.statusText.equals(statusText)) {
			throw new RuntimeException();
		}
		this.statusText = statusText;
	}

	/**
	 * Setter of mimeType
	 * 
	 * @param mimeType
	 * @throws RuntimeException if tries to change while header was already written
	 */
	public void setMimeType(String mimeType) {
		if (headerGenerated && !this.mimeType.equals(mimeType)) {
			throw new RuntimeException();
		}
		this.mimeType = mimeType;
	}

	/**
	 * Setter of contentLength
	 * 
	 * @param contentLength
	 * @throws RuntimeException if tries to change while header was already written
	 */
	public void setContentLength(Long contentLength) {
		if (headerGenerated && !this.contentLength.equals(contentLength)) {
			throw new RuntimeException();
		}
		this.contentLength = contentLength;
	}

	/**
	 * Setter of statusCode
	 * 
	 * @param statusCode
	 * @throws RuntimeException if tries to change while header was already written
	 */
	public void setStatusCode(int statusCode) {
		if (headerGenerated && this.statusCode != statusCode) {
			throw new RuntimeException();
		}
		this.statusCode = statusCode;
	}

	/**
	 * Getter of {@link Parameter}s
	 * 
	 * @return parameters
	 */
	public Map<String, String> getParameters() {
		return parameters;
	}

	/**
	 * Getter of dispatcher
	 * 
	 * @return dispatcher mostly used by {@link IWebWorker}s
	 */
	public IDispatcher getDispatcher() {
		return dispatcher;
	}

	/**
	 * Getter of element of parameters with key of name.
	 * 
	 * @param name the key of parameter
	 * @return string of value
	 */
	public String getParameter(String name) {
		return parameters.get(name);
	}

	/**
	 * Returns unmodifiable set of parameters values.
	 * 
	 * @return unmodifiable set of parameters values.
	 */
	public Set<String> getParameterNames() {
		return Set.copyOf(parameters.values());
	}

	// * method that retrieves value from persistentParameters map (or null if no
	// association exists):
	/**
	 * Retrieves value from persistentParameters map (or null if no association
	 * exists)
	 * 
	 * @param name as key
	 * @return returns value of persistentParameter with key:name
	 */
	public String getPersistentParameter(String name) {
		return persistentParameters.get(name);
	}

	// * :
	/**
	 * Method that retrieves names of all parameters in persistent parameters map.
	 * 
	 * @return unmodifiable set of persistent parameters values.
	 */
	public Set<String> getPersistentParameterNames() {
		return Set.copyOf(persistentParameters.values());
	}

	/**
	 * Method that stores a value to persistentParameters map
	 * 
	 * @param name  used as key.
	 * @param value to be stored.
	 */
	public void setPersistentParameter(String name, String value) {
		persistentParameters.put(name, value);
	}

	/**
	 * Method that removes a value from persistentParameters map.
	 * 
	 * @param name whose value will be removed.
	 */
	public void removePersistentParameter(String name) {
		persistentParameters.remove(name);
	}

	/**
	 * method that retrieves value from temporaryParameters map (or null if no
	 * association exists)
	 * 
	 * @param name as key
	 * @return value which key is name.
	 */
	public String getTemporaryParameter(String name) {
		return temporaryParameters.get(name);
	}

	/**
	 * Method that retrieves names of all parameters in temporary parameters map.
	 * 
	 * @return unmodifiable set of values
	 */
	public Set<String> getTemporaryParameterNames() {
		return Set.copyOf(temporaryParameters.keySet());
	}

	/**
	 * Method that retrieves an identifier which is unique for current user session.
	 * 
	 * @return an identifier which is unique for current user session
	 */
	public String getSessionID() {
		return sid;
	}

	/**
	 * Method that stores a value to temporaryParameters map.
	 * 
	 * @param name  key to be stored
	 * @param value to be stored
	 */
	public void setTemporaryParameter(String name, String value) {
		temporaryParameters.put(name, value);
	}

	/**
	 * Method that removes a value from temporaryParameters map.
	 * 
	 * @param name of value to be removed
	 */
	public void removeTemporaryParameter(String name) {
		temporaryParameters.remove(name);
	}

	/**
	 * Adds {@link RCCookie} to this.
	 * @param cookie to be added. Requires non null.
	 */
	public void addRCCookie(RCCookie cookie) {
		outputCookies.add(Objects.requireNonNull(cookie));
	}

	/**
	 * Writes byte array of data to outputStream and returns this.
	 * 
	 * @param data to be written
	 * @return this
	 * @throws IOException if error occurs while writing.
	 */
	public RequestContext write(byte[] data) throws IOException {
		writeHeader();
		outputStream.write(data);
		outputStream.flush();
		return this;
	}

	/**
	 * Writes byte array of data to outputStream and returns this.
	 * 
	 * @param data to be written
	 * @param offset of start writing
	 * @param len length of data to be writed.
	 * @return this
	 * @throws IOException if error occurs while writing.
	 */
	public RequestContext write(byte[] data, int offset, int len) throws IOException {
		writeHeader();
		outputStream.write(data, offset, len);
		outputStream.flush();
		return this;
	}

	/**
	 * Writes String of data to outputStream and returns this.
	 * @param text of String to be written
	 * @return this
	 * @throws IOException if error occurs while writing.
	 */
	public RequestContext write(String text) throws IOException {
		writeHeader();
		outputStream.write(text.getBytes(charset));
		outputStream.flush();
		return this;
	}

	/**
	 * Method that writes header.
	 * 
	 * @throws IOException if error occurs while writing.
	 */
	private void writeHeader() throws IOException {
		if (headerGenerated) {
			return;
		}
		charset = Charset.forName(encoding);

		StringBuilder sb = new StringBuilder();
		sb.append("HTTP/1.1 " + statusCode + " " + statusText + "\r\n");
		sb.append("Content-Type: " + mimeType);
		if (mimeType.startsWith("text/")) {
			sb.append("; charset=" + encoding);
		}
		sb.append("\r\n");
		if (contentLength != null) {
			sb.append("Content-Length: " + contentLength + "\r\n");
		}
		if (!outputCookies.isEmpty()) {
			for (RCCookie c : outputCookies) {
				sb.append("Set-Cookie: " + c.name + "=\"" + c.value + "\"");
				if (c.domain != null) {
					sb.append("; Domain=" + c.domain);
				}
				if (c.path != null) {
					sb.append("; Path=" + c.path);
				}
				if (c.maxAge != null) {
					sb.append("; Max-Age=" + c.maxAge);
				}
				sb.append("\r\n");
			}
		}
		sb.append("\r\n");
		outputStream.write(sb.toString().getBytes(StandardCharsets.ISO_8859_1));
		headerGenerated = true;
	}
}
