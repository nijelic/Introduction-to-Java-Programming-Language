package hr.fer.zemris.java.galerija.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet which returns original image.
 * 
 * @author Jelić, Nikola
 */
@WebServlet("/servlets/normalImage")
public class NormalImageServlet extends HttpServlet {
	
	/**
	 * Serial version ID.
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String image= req.getParameter("name");
		
		Path path = Paths.get(req.getServletContext().getRealPath("WEB-INF/slike/" + image));
		
		if (!Files.exists(path)) {
				return;
		}
		
		ServletOutputStream out = resp.getOutputStream();
		String mime = req.getServletContext().getMimeType(image);
		
		if (mime == null) {
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
		resp.setContentType(mime);

		InputStream in = Files.newInputStream(path);
		
		// Copy the contents of the file to the output stream
		byte[] buf = new byte[1024];
		int count = 0;
		while ((count = in.read(buf)) >= 0) {
			out.write(buf, 0, count);
		}
		out.close();
		in.close();
	}

}
