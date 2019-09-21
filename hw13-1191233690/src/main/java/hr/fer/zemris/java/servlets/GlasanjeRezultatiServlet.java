package hr.fer.zemris.java.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.GlasanjeUtil.Util;

/**
 * This servlet creates html page of voting results.
 * 
 * @author Jelić, Nikola
 */
public class GlasanjeRezultatiServlet extends HttpServlet {

	/**
	 * Serial version ID.
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String[] array = Util.readResults(req);
		req.setAttribute("length", (Integer)array.length);
		for(Integer i = 0; i<array.length; i++) {
			String[] vote = array[i].split("\t");
			req.setAttribute(i.toString() + "ID", vote[0]);
			req.setAttribute(i.toString(), vote[1]);
		}
		
		array = Util.readDefinition(req);
		
		for(Integer i = 0; i<array.length; i++) {
			String[] song = array[i].split("\t");
			req.setAttribute(song[0] + "Author", song[1]);
			req.setAttribute(song[0] + "url", song[2]);
		}
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}

}
