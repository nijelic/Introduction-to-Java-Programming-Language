package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.List;

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
 * Processes login of user.
 *  
 * @author Jelić, Nikola
 */
@WebServlet("/servleti/login")
public class LoginServlet extends HttpServlet {
	/**
	 * Serial version ID.
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		processLogin(req);
		resp.sendRedirect(req.getContextPath() + "/servleti/main");
	}
	
	private void processLogin(HttpServletRequest req) {
		String nick = (String)req.getParameter("nick");
		
		
		String pass = BlogUtil.sha1((String)req.getParameter("pass"));
		
		List<BlogUser> user = JPAEMProvider.getEntityManager().createNamedQuery("BlogUser.user", BlogUser.class)
					.setParameter("ni", nick)
					.getResultList();
		HttpSession sess = req.getSession();
		if(user.size() >1) {
			System.out.println(user.toString());
		} else if(user.size() == 0) {
			sess.setAttribute("err","User doesn't exist.");
		} else {
			sess.setAttribute("current.user.nick", user.get(0).getNick());
			if(pass.equals(user.get(0).getPasswordHash())) {
				sess.setAttribute("current.user.fn", user.get(0).getFirstName());
				sess.setAttribute("current.user.ln", user.get(0).getLastName());
				sess.setAttribute("current.user.id", user.get(0).getId());
			} else {
				sess.setAttribute("err", "Wrong password.");
			}
		}
	}
}
