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
 * Provides listing of blog titles, calling edit or creating new {@link BlogEntry} if permission is allowed.
 *  
 * @author Jelić, Nikola
 */
@WebServlet("/servleti/author/*")
public class AuthorServlet extends HttpServlet {

	/**
	 * Serial version ID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (req.getPathInfo() == null) {
			resp.sendRedirect(req.getContextPath() + "/servleti/main");
			return;
		}

		String[] pathSplit = req.getPathInfo().split("/");
		if (pathSplit.length == 0 || pathSplit.length > 3) {
			resp.sendRedirect(req.getContextPath() + "/servleti/main");
			return;
		}
		
		String nick = pathSplit[1];
		req.getSession().setAttribute("current.picked.nick", nick);
		if (pathSplit.length == 2) {
			processNick(nick, req, resp);
		} else if (pathSplit[2].equals("new")) {
		
			if (nick.equals(req.getSession().getAttribute("current.user.nick"))) {
				resp.sendRedirect(req.getContextPath() + "/servleti/newBlog");
				return;
			} else {
				req.getSession().setAttribute("err", "You are not allowed to create.");
				resp.sendRedirect(req.getContextPath() + "/servleti/main");
				return;
			}
		} else if (pathSplit[2].equals("edit")) {
			if (nick.equals(req.getSession().getAttribute("current.user.nick"))) {
				resp.sendRedirect(req.getContextPath() + "/servleti/pickToEditBlog");
				return;
			} else {
				req.getSession().setAttribute("err", "You are not allowed to edit.");
				resp.sendRedirect(req.getContextPath() + "/servleti/main");
				return;
			}
		} else {
			resp.sendRedirect(req.getContextPath() + "/servleti/showBlog/"+pathSplit[2]);
			return;
		}
	}

	/**
	 * If path contains ../author/NICK this function will list titles of BlogEntries of nick.
	 * 
	 * @param nick used to find list of titles.
	 * @param req HttpServletRequest
	 * @param resp HttpServletResponse
	 * @throws IOException if IO exception occurs while calling forward on dispatcher.
	 * @throws ServletException if exception occurs while calling forward on dispatcher.
	 */
	private void processNick(String nick, HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		
		req.setAttribute("entries", BlogUtil.entriesByNick(nick));
		req.setAttribute("editor", nick.equals(req.getSession().getAttribute("current.user.nick")));
		req.getRequestDispatcher("/WEB-INF/pages/listOfTitles.jsp").forward(req, resp);
	}

	
	
}
