package hr.fer.zemris.java.webserver;

/**
 * This is used for dispatching request given by client.
 * @author Jelić, Nikola
 */
public interface IDispatcher {
	/**
	 * Method that dispatches request given by client throughout urlPath.
	 * 
	 * @param urlPath used for understanding request.
	 * @throws Exception if error occurs while working on request.
	 */
	void dispatchRequest(String urlPath) throws Exception;
}