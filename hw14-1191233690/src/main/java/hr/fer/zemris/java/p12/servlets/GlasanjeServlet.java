package hr.fer.zemris.java.p12.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.utility.Poll;
import hr.fer.zemris.java.p12.utility.PollOption;

/**
 * This servlet creates html that enables voting.
 * 
 * @author Jelić, Nikola
 */
@WebServlet("/servleti/glasanje")
public class GlasanjeServlet extends HttpServlet {

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
		Poll poll = DAOProvider.getDao().getPollByID(pollID);
		
		// used for safty
		if(options.isEmpty() || poll==null){
			resp.sendRedirect(req.getContextPath() + "/servleti/index.html");
			return;
		}
		req.setAttribute("options", options);
		req.setAttribute("poll", poll);
		
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}
}
