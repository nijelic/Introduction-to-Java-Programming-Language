package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Starts creation of new blog by calling "formBlogEntry.jsp" with no data if permission is allowed.
 * 
 * @author Jelić, Nikola
 */
@WebServlet("/servleti/newBlog")
public class BlogNewServlet extends HttpServlet {

	/**
	 * Serial version ID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(req.getSession().getAttribute("current.user.nick")==null) {
			req.getSession().setAttribute("err", "You are not allowed to create.");
			resp.sendRedirect(req.getContextPath() + "/servleti/main");
			return;
		}
		req.getSession().setAttribute("edit", null);
		req.getRequestDispatcher("/WEB-INF/pages/formBlogEntry.jsp").forward(req, resp);
	}
}
