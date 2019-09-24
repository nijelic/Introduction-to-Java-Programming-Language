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
 * Demo of brojPoziva.smscr file.
 * @author Jelić, Nikola
 */
public class NumberOfCalls {

	/**
	 * Main method
	 * @param args none
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException{
		String documentBody = Files.readString(Paths.get("webroot/scripts/brojPoziva.smscr"));
		Map<String,String> parameters = new HashMap<String, String>();
		Map<String,String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		
		persistentParameters.put("brojPoziva", "3");
		RequestContext rc = new RequestContext(System.out, parameters, persistentParameters,cookies);
		new SmartScriptEngine(
				new SmartScriptParser(documentBody).getDocumentNode(), 
				rc
		).execute();
		System.out.println("Vrijednost u mapi: "+rc.getPersistentParameter("brojPoziva"));
	}
}
