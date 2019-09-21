package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.utility.BlogUtil;

/**
 * Lists all possible BlogEntries that can be edit by specific user.
 * 
 * @author Jelić, Nikola
 */
@WebServlet("/servleti/pickToEditBlog")
public class PickToEditServlet extends HttpServlet {

	/**
	 * Serial version ID.
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("entries", BlogUtil.entriesByNick((String)req.getSession().getAttribute("current.user.nick")));
		req.getRequestDispatcher("/WEB-INF/pages/pickToEdit.jsp").forward(req, resp);
	}
}
