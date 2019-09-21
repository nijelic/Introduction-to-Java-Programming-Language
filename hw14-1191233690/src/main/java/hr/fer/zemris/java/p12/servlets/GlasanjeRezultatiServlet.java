package hr.fer.zemris.java.p12.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.utility.PollOption;

import java.util.List;

/**
 * This servlet creates html page of voting results.
 * 
 * @author Jelić, Nikola
 */
@WebServlet("/servleti/glasanje-rezultati")
public class GlasanjeRezultatiServlet extends HttpServlet {

	/**
	 * Serial version ID.
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long pollID = null;
		try {
			pollID = Long.parseLong(req.getParameter("pollID"));
		} catch(NumberFormatException e) {
		}
		
		if(pollID == null) {
			resp.sendRedirect(req.getContextPath() + "/servleti/index.html");
			return;
		}
		
		List<PollOption> options = DAOProvider.getDao().getOptionsByID(pollID);
		// used for safty
		if(options.isEmpty()){
			resp.sendRedirect(req.getContextPath() + "/servleti/index.html");
			return;
		}
		req.setAttribute("options", options);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}

}
