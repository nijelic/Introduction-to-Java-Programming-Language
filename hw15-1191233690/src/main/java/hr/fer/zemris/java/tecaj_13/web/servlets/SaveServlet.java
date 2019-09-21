package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import hr.fer.zemris.java.tecaj_13.dao.jpa.JPAEMProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;
import hr.fer.zemris.java.tecaj_13.utility.BlogUtil;

/**
 * Processes registration of new user.
 * 
 * @author Jelić, Nikola
 */
@WebServlet("/servleti/save")
public class SaveServlet extends HttpServlet {
	/**
	 * Serial version ID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		processSave(req);
		resp.sendRedirect(req.getContextPath() + "/servleti/register");
	}

	private void processSave(HttpServletRequest req) {
		String nick = (String) req.getParameter("nick");
		String pass = (String) req.getParameter("pass");
		String fn = (String) req.getParameter("fn");
		String ln = (String) req.getParameter("ln");
		String email = (String) req.getParameter("email");
		boolean error = false;

		HttpSession sess = req.getSession();
		sess.setAttribute("current.user.nick", nick);
		sess.setAttribute("current.user.fn", fn);
		sess.setAttribute("current.user.ln", ln);
		sess.setAttribute("current.user.email", email);

		if (nick.length() == 0) {
			sess.setAttribute("err.nick", "Nick must not be empty.");
			error = true;
		} else if (nick.length() >= 30) {
			sess.setAttribute("err.nick", "Nick must not have length that is more than 30 chars.");
			error = true;
		}
		if (fn.length() == 0) {
			sess.setAttribute("err.fn", "First name must not be empty.");
			error = true;
		} else if (fn.length() >= 30) {
			sess.setAttribute("err.fn", "First name must not have length that is more than 30 chars.");
			error = true;
		}
		if (ln.length() == 0) {
			sess.setAttribute("err.ln", "Last name must not be empty.");
			error = true;
		} else if (ln.length() >= 30) {
			sess.setAttribute("err.ln", "Last name must not have length that is more than 30 chars.");
			error = true;
		}
		if (email.length() == 0) {
			sess.setAttribute("err.email", "Email must not be empty.");
			error = true;
		} else if (email.length() >= 50) {
			sess.setAttribute("err.email", "Email must not have length that is more than 50 chars.");
			error = true;
		} else if(!email.contains("@")) {
			sess.setAttribute("err.email", "Your email is not valid.");
			error = true;
		}
		if (pass.length() < 8) {
			sess.setAttribute("err.pass", "Password must have minimal 8 characters.");
			error = true;
		}
		
		List<BlogUser> user = JPAEMProvider.getEntityManager().createNamedQuery("BlogUser.user",BlogUser.class)
				.setParameter("ni", nick)
				.getResultList();
		if(user.size() != 0) {
			sess.setAttribute("err.nick", "User already exists.");
			error = true;
		}
		if(error) {
			return;
		}
		
		pass = BlogUtil.sha1(pass);
		
		BlogUser bu = new BlogUser();
		bu.setEmail(email);
		bu.setFirstName(fn);
		bu.setLastName(ln);
		bu.setNick(nick);
		bu.setPasswordHash(pass);
		
		EntityManager em = JPAEMProvider.getEntityManager();
		em.persist(bu);
		
		sess.setAttribute("current.user.id", bu.getId());
	}
}
