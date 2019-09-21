package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Sets background if was changed and sets html code given by "home.smscr".
 * @author Jelić, Nikola
 */
public class Home implements IWebWorker{

	@Override
	public void processRequest(RequestContext context) throws Exception {
		String bgColor = context.getPersistentParameter("bgcolor");
		if(bgColor == null) {
			context.setTemporaryParameter("background", "7F7F7F");
		} else {
			context.setTemporaryParameter("background", bgColor);
		}
		context.getDispatcher().dispatchRequest("/private/pages/home.smscr");
	}
}
