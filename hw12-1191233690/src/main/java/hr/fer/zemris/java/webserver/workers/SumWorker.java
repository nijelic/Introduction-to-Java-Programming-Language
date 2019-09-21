package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Sums two numbers and writes them as table.
 * @author Jelić, Nikola
 */
public class SumWorker implements IWebWorker {

	private static final String FIRST_IMG_NAME = "images/earth.gif";
	private static final String SECOND_IMG_NAME = "images/minion.jpg";
	@Override
	public void processRequest(RequestContext context) throws Exception {
		Integer a;
		Integer b;
		try {
			a = Integer.parseInt(context.getParameter("a"));
		} catch (NumberFormatException e) {
			a = 1;
		}
		try {
			b = Integer.parseInt(context.getParameter("b"));
		} catch (NumberFormatException e) {
			b = 2;
		}
		context.setTemporaryParameter("varA", a.toString());
		context.setTemporaryParameter("varB", b.toString());
		a += b;
		context.setTemporaryParameter("zbroj", a.toString());
		context.setTemporaryParameter(new String("imgName"), ((a%2==1)?FIRST_IMG_NAME: SECOND_IMG_NAME));
		context.getDispatcher().dispatchRequest("/private/pages/calc.smscr");
	}
}
