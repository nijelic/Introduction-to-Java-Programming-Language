package hr.fer.zemris.java.webserver;
/**
 * Does some work for server before sending data to client.
 * @author Jelić, Nikola
 */
public interface IWebWorker {
	/**
	 * Processes request given by client.
	 * @param context through which server communicates with client.
	 * @throws Exception if error occurs while processing
	 */
	public void processRequest(RequestContext context) throws Exception;
}