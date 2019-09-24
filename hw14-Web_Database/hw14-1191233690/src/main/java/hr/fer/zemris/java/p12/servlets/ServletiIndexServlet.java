package hr.fer.zemris.java.p12.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.utility.Poll;

import java.util.List;

/**
 * This is "starting" servlet.
 * 
 * @author Jelić, Nikola
 */
@WebServlet("/servleti/index.html")
public class ServletiIndexServlet extends HttpServlet {

	/**
	 * Serial version ID.
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Poll> polls = DAOProvider.getDao().getPolls();
		
		req.setAttribute("polls", polls);
		req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);
	}
}
