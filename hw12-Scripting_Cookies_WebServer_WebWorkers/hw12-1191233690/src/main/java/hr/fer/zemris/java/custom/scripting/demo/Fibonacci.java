package hr.fer.zemris.java.custom.scripting.demo;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;
import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

/**
 * Demo of fibonacci.smscr script.
 * @author Jelić, Nikola
 */
public class Fibonacci {

	/***
	 * Main method.
	 * @param args none
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException{
		String documentBody = Files.readString(Paths.get("webroot/scripts/fibonacci.smscr"));
		Map<String,String> parameters = new HashMap<String, String>();
		Map<String,String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		
		// create engine and execute it
		new SmartScriptEngine(
			new SmartScriptParser(documentBody).getDocumentNode(),
			new RequestContext(System.out, parameters, persistentParameters, cookies)
		).execute();
		
	}
}