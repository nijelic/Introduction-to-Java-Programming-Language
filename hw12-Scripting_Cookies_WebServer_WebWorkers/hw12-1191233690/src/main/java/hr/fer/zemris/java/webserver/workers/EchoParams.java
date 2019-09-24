package hr.fer.zemris.java.webserver.workers;

import java.io.IOException;
import java.util.Map;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * WebWorker that prints parameters in a table form.
 * @author Jelić, Nikola
 */
public class EchoParams implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {
		Map<String, String> params = context.getParameters();
		StringBuilder sb = new StringBuilder();
		sb.append("<table>");
		sb.append("<tr><th>Key</th><th>Value</th></tr>");
		for (Map.Entry<String, String> pair : params.entrySet()) {
			sb.append(" <tr><td>" + pair.getKey() + "</td><td>" + pair.getValue() + "</td></tr>\n");
		}
		sb.append(" </table>");
		try {
			context.write(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
