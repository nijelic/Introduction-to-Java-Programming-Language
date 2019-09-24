package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.GlasanjeUtil.Util;

/**
 * Servlet used for voting. Accepts vote and renders results.
 * 
 * @author Jelić, Nikola
 */
public class GlasanjeGlasajServlet extends HttpServlet {

	/**
	 * Serial version ID.
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String[] array = Util.readResults(req);
		String id = req.getParameter("id");
		
		List<Util.Pair<String, String>> list = new ArrayList<>();
		
		for (Integer i = 0; i < array.length; i++) {
			String[] votes = array[i].split("\t");
			if (votes[0].equals(id)) {
				try {
					Integer numberOfVotes = Integer.parseInt(votes[1]);
					numberOfVotes++;
					list.add(new Util.Pair<String, String>(votes[0], numberOfVotes.toString()));
					
				} catch (NumberFormatException e) {
					list.add(new Util.Pair<String, String>(votes[0], votes[1]));
				}
			} else {
				list.add(new Util.Pair<String, String>(votes[0], votes[1]));
			}
		}
		
		list.sort((e, f) -> f.value.compareTo(e.value));
		
		StringBuilder sb = new StringBuilder();
		for (Util.Pair<String, String> entry : list) {
			String votes = entry.value;
			id = entry.key;
			sb.append(id + "\t" + votes + "\n");
		}
		
		if (array.length > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		
		Files.writeString(
				Paths.get(req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt")),
				sb.toString());

		resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
	}

}
