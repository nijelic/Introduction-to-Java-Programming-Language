package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.jpa.JPAEMProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.utility.BlogUtil;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import java.sql.Date;
import java.util.ArrayList;

/**
 * Processes update of BlogEntry got by form.
 * 
 * @author Jelić, Nikola
 */
@WebServlet("/servleti/updateBlog")
public class BlogUpdateServlet extends HttpServlet {

	/**
	 * Serial version ID.
	 */
	private static final long serialVersionUID = 1L;

	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		if(req.getParameter("title")==null || req.getParameter("text") == null) {
			resp.sendRedirect(req.getContextPath() + "/servleti/main");
			return;
		}
			
		if(req.getParameter("title").trim().isEmpty()) {
			req.getSession().setAttribute("msg", "Title must not be empty.");
			req.getSession().setAttribute("text", req.getParameter("text"));
			req.getRequestDispatcher("/WEB-INF/pages/formBlogEntry.jsp").forward(req, resp);
			return;
		} else if(req.getParameter("title").length() > 100) {
			req.getSession().setAttribute("msg", "Title must not have more than 100 letters.");
			req.getSession().setAttribute("title", req.getParameter("title"));
			req.getSession().setAttribute("text", req.getParameter("text"));
			req.getRequestDispatcher("/WEB-INF/pages/formBlogEntry.jsp").forward(req, resp);
			return;
		} if(req.getParameter("text").length() > 4000) {
			req.getSession().setAttribute("msg", "Text must not have more than 4000 letters.");
			req.getSession().setAttribute("title", req.getParameter("title"));
			req.getSession().setAttribute("text", req.getParameter("text"));
			req.getRequestDispatcher("/WEB-INF/pages/formBlogEntry.jsp").forward(req, resp);
			return;
		}
		
		BlogEntry be;
				
		if(req.getSession().getAttribute("edit") != null) {
			be = BlogUtil.entriesByID((Long)req.getSession().getAttribute("edit")).get(0);
			be.setTitle(req.getParameter("title"));
			be.setText(req.getParameter("text"));
			be.setLastModifiedAt(new Date(System.currentTimeMillis()));
			
		} else {
			be = new BlogEntry();
			be.setTitle(req.getParameter("title"));
			be.setText(req.getParameter("text"));
			be.setLastModifiedAt(new Date(System.currentTimeMillis()));
			be.setCreator(BlogUtil.userByNick((String)req.getSession().getAttribute("current.user.nick")));
			be.setComments(new ArrayList<BlogComment>());
			be.setCreatedAt(new Date(System.currentTimeMillis()));
			EntityManager em = JPAEMProvider.getEntityManager();
			em.persist(be);
		}
		req.getSession().setAttribute("edit", null);
		resp.sendRedirect(req.getContextPath() + "/servleti/author/"+ (String)req.getSession().getAttribute("current.user.nick"));
	}
}
