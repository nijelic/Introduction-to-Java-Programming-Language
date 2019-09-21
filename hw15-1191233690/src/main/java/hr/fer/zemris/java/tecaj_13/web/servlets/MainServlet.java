package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import hr.fer.zemris.java.tecaj_13.dao.jpa.JPAEMProvider;

/**
 * Creates landing page which is also used as main page to redirect all invalid calls.
 * 
 * @author Jelić, Nikola
 */
@WebServlet("/servleti/main")
public class MainServlet extends HttpServlet {
	/**
	 * Serial version ID.
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		@SuppressWarnings("unchecked")
		List<String> list = (List<String>)em.createNamedQuery("BlogUser.nicks").getResultList();
		req.setAttribute("nicks", list);
		req.getRequestDispatcher("/WEB-INF/pages/main.jsp").forward(req, resp);
	}
	
}
