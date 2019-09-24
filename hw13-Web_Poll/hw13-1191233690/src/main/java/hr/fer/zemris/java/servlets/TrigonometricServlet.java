package hr.fer.zemris.java.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Creates html with table of cos and sin values of integer segment.
 * 
 * @author Jelić, Nikola
 */
public class TrigonometricServlet extends HttpServlet {
	
	/**
	 * Serial version ID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Integer a = 0;
		Integer b = 360;
		
		try {
			a = Integer.parseInt(req.getParameter("a"));
		} catch(NumberFormatException e) {			
		}
		try {
			b = Integer.parseInt(req.getParameter("b"));
		} catch(NumberFormatException e) {			
		}
		if (a > b) {
			int tmp = b;
			b = a;
			a = tmp;
		}
		if(b > a + 720) {
			b = a + 720;
		}
		req.setAttribute("a", a);
		req.setAttribute("b", b);
		req.getRequestDispatcher("WEB-INF/pages/trigonometric.jsp").forward(req, resp);
	}	
}
