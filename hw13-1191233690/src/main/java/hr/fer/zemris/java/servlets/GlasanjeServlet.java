package hr.fer.zemris.java.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.GlasanjeUtil.Util;

/**
 * This servlet creates html that enables voting.
 * 
 * @author Jelić, Nikola
 */
public class GlasanjeServlet extends HttpServlet {

	/**
	 * Serial version ID.
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String[] array = Util.readDefinition(req);
		
		req.setAttribute("length", (Integer)array.length);
		for(Integer i = 0; i<array.length; i++) {
			String[] song = array[i].split("\t");

			req.setAttribute(i.toString() + "ID", song[0]);
			req.setAttribute(i.toString() + "Author", song[1]);
			req.setAttribute(i.toString() + "url", song[2]);
		}
	
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}
}
