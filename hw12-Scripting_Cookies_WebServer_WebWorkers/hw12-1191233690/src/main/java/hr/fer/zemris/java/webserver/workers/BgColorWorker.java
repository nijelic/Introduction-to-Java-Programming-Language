package hr.fer.zemris.java.webserver.workers;

import java.io.IOException;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Tries to change the color of background.
 * @author Jelić, Nikola
 */
public class BgColorWorker implements IWebWorker{

	@Override
	public void processRequest(RequestContext context) throws Exception {
		String color = context.getParameter("bgcolor");
		if(color==null) {
			didMatch(context, false);
		} else {
			color = color.toUpperCase();
			if(color.length()!=6 || !color.matches("^[0-9A-F]+$")) {
				didMatch(context, false);
			} else {
				context.setPersistentParameter("bgcolor", color);
				didMatch(context, true);
			}
		}
	}
	
	/**
	 * Sends message that depends on if change of color succeed or don't.
 	 * @param context used for communication
	 * @param match true or false / succeed or failed.
	 */
	private void didMatch(RequestContext context, boolean match) {
		StringBuilder sb = new StringBuilder();
		sb.append("<!DOCTYPE html>\n<html>\n<head>\n");
		sb.append(" </head>\n <body>\n");
		if(match) {
			sb.append("<h1>Uspjela je obnova boje.</h1>");
		} else {
			sb.append("<h1>Nije uspjela je obnova boje.</h1>");
		}
		sb.append("<a href=\"http://www.localhost.com:5721/index2.html\">index2.html</a>\n"); 
		sb.append(" </body>\n</html>");
		try {
			context.write(sb.toString());
		} catch (IOException e) {
			System.out.println("BgColorWorker failed to send html.");
		}
	}
}
