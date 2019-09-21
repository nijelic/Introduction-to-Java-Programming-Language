package hr.fer.zemris.java.p12.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.dao.sql.SQLDAO;


/**
 * Servlet used for voting. Accepts vote and renders results.
 * 
 * @author Jelić, Nikola
 */
@WebServlet("/servleti/glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet {

	/**
	 * Serial version ID.
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long id = null;
		
		try {
			id = Long.parseLong(req.getParameter("id"));
		} catch(NumberFormatException e) {
		}
		
		if(id == null) {
			resp.sendRedirect(req.getContextPath() + "/servleti/index.html");
			return;
		}
		
		SQLDAO.vote(id);
		Long realId = DAOProvider.getDao().convertToPollID(id);
		
		// used for safty
		if(realId == null){
			resp.sendRedirect(req.getContextPath() + "/servleti/index.html");
			return;
		}
		
		resp.sendRedirect(req.getContextPath() + "/servleti/glasanje-rezultati?pollID="+realId);
	}

}
