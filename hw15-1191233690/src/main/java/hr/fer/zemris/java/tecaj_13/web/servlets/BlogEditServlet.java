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
 * This servlet starts editing by sending form with filled data if permission is allowed.
 * 
 * @author Jelić, Nikola
 *
 */
@WebServlet("/servleti/editBlog/*")
public class BlogEditServlet extends HttpServlet {

	/**
	 * Serial version ID.
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String[] pathSplit = req.getPathInfo().split("/");

		if(pathSplit.length != 2) {
			req.getSession().setAttribute("err", "Wrong url.");
			resp.sendRedirect(req.getContextPath() + "/servleti/main");
			return;
		}
		
		Long id;
		try {
			id = Long.parseLong(pathSplit[1]);
		} catch (NumberFormatException e) {
			req.getSession().setAttribute("err", "Wrong url.");
			resp.sendRedirect(req.getContextPath() + "/servleti/main");
			return;
		}
		
		BlogEntry be = BlogUtil.entriesByID(id).get(0);
		String nick = (String)req.getSession().getAttribute("current.user.nick");
		
		if(!be.getCreator().getNick().equals(nick)) {
			req.getSession().setAttribute("err", "You are not allowed to edit.");
			resp.sendRedirect(req.getContextPath() + "/servleti/main");
			return;
		}
		
		req.getSession().setAttribute("edit", id);
		req.getSession().setAttribute("title", be.getTitle());
		req.getSession().setAttribute("text", be.getText());
		req.getRequestDispatcher("/WEB-INF/pages/formBlogEntry.jsp").forward(req, resp);
	}
}
