package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.utility.BlogUtil;

/**
 * Shows specific BlogEntry with its comments.
 * 
 * @author Jelić, Nikola
 */
@WebServlet("/servleti/showBlog/*")
public class BlogShowServlet extends HttpServlet {

	/**
	 * Serial version ID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String[] pathSplit = req.getPathInfo().split("/");
		
		if(pathSplit.length != 2) {
			resp.sendRedirect(req.getContextPath() + "/servleti/main");
			return;
		}
		Long id;
		try {
			id = Long.parseLong(pathSplit[1]);
		} catch(Exception e) {
			resp.sendRedirect(req.getContextPath() + "/servleti/main");
			return;
		}
		
		BlogEntry entry = BlogUtil.entriesByID(id).get(0);
		req.setAttribute("entry", entry);
		req.setAttribute("comments", entry.getComments());
		req.getRequestDispatcher("/WEB-INF/pages/showBlog.jsp").forward(req, resp);
	}
}
