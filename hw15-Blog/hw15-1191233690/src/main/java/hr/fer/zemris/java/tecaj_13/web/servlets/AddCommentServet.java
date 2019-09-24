package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;

import java.sql.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.utility.BlogUtil;

/**
 * Servlet that adds comment to BlogEntry.
 * 
 * @author Jelić, Nikola
 */
@WebServlet("/servleti/showBlog/addComment/*")
public class AddCommentServet extends HttpServlet {

	/**
	 * Serial version ID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(req.getParameter("comment") == null) {
			resp.sendRedirect(req.getContextPath() + "/servleti/main");
			return;
		}
		String[] list = req.getPathInfo().split("/");
		if(list.length!=2) {
			resp.sendRedirect(req.getContextPath() + "/servleti/main");
			return;
		}
		Long id;
		try {
			id =Long.parseLong(list[1]);
		} catch(Exception e) {
			resp.sendRedirect(req.getContextPath() + "/servleti/main");
			return;
		}
		List<BlogEntry> entries = BlogUtil.entriesByID(id);
		BlogEntry entry = entries.get(0);
		BlogComment bc = new BlogComment();
		bc.setBlogEntry(entry);
		bc.setMessage(req.getParameter("comment"));
		bc.setPostedOn(new Date(System.currentTimeMillis()));
		bc.setUsersEMail(req.getParameter("email"));
		entry.getComments().add(bc);
		resp.sendRedirect(req.getContextPath() + "/servleti/showBlog/"+id);
	}
}
