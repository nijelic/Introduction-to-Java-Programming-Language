package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * Demo of calc.smscr script.
 * @author Jelić, Nikola
 */
public class CalcDemo {
	/**
	 * First image is image of earth.
	 */
	private static final String FIRST_IMG_NAME = "images/earth.gif";
	/**
	 * Second image is image of minions.
	 */
	private static final String SECOND_IMG_NAME = "images/minion.jpg";
	/**
	 * Main method
	 * @param args none
	 * @throws IOException if error while reading occurs.
	 */
	public static void main(String[] args) throws IOException{
		String documentBody = Files.readString(Paths.get("webroot/private/pages/calc.smscr"));
		Map<String,String> parameters = new HashMap<String, String>();
		Map<String,String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		RequestContext context = new RequestContext(System.out, parameters, persistentParameters, cookies);

		/*		
 		int statusCode = 404;
		String statusText = "Not found";
		context.setStatusCode(statusCode);
		context.setStatusText(statusText);
		context.setMimeType("text/plain");
		context.setEncoding("UTF-8");
		context.setContentLength(0l);
		context.write("\n");
		*/
		Integer a = 1;
		Integer b = 2;
		context.setTemporaryParameter("varA", a.toString());
		context.setTemporaryParameter("varB", b.toString());
		a += b;
		context.setTemporaryParameter("zbroj", a.toString());
		context.setTemporaryParameter(new String("imgName"), ((a%2==1)?FIRST_IMG_NAME: SECOND_IMG_NAME));
		// create engine and execute it
		new SmartScriptEngine(
			new SmartScriptParser(documentBody).getDocumentNode(),
			context
		).execute();
	}
}
