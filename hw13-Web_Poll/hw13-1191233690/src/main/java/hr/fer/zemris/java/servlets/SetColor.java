package hr.fer.zemris.java.servlets;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Sets background color for session.
 * 
 * @author Jelić, Nikola
 */
public class SetColor extends HttpServlet {
	
	/**
	 * Serial version ID.
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getSession().setAttribute("pickedBgCol", req.getParameter("pickedBgCol"));
		req.getRequestDispatcher("colors.jsp").forward(req, resp);
	}
}
