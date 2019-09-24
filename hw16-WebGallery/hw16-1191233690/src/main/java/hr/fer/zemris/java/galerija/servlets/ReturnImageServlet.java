package hr.fer.zemris.java.galerija.servlets;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet which returns small image. If there is no image in
 * "WEB-INF/thumbnails/" than first resizes image to 150x150px and saves it to
 * that path.
 * 
 * @author Jelić, Nikola
 *
 */
@WebServlet("/servlets/image/*")
public class ReturnImageServlet extends HttpServlet {

	/**
	 * Serial version ID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String[] pathSplit = req.getPathInfo().split("/");
		
		if (pathSplit.length != 2) {
			return;
		}

		String image = pathSplit[1];
		Path dir = Paths.get(req.getServletContext().getRealPath("WEB-INF/thumbnails"));
		if (!Files.exists(dir)) {
			Files.createDirectories(dir);
		}
		
		Path path = Paths.get(req.getServletContext().getRealPath("WEB-INF/thumbnails/" + image));

		if (!Files.exists(path)) {
			if (createImage(image, req)) {
				return;
			}
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

	/**
	 * Creates image if exists in original size or Returns true as there is no image.
	 * 
	 * @param image name
	 * @param req HttpServletRequest is used for getting real path.
	 * @return true if there is no image or false if image is resized and saved.
	 */
	private boolean createImage(String image, HttpServletRequest req) {
		Path input = Paths.get(req.getServletContext().getRealPath("WEB-INF/slike/" + image));
		Path output = Paths.get(req.getServletContext().getRealPath("WEB-INF/thumbnails/" + image));

		if (!Files.exists(input)) {
			return true;
		}
		resizeImage(input, output);
		return false;
	}

	/**
	 * Resizes image if original exists.
	 * 
	 * @param input path to the original image.
	 * @param output path to the resized/new image.
	 */
	private void resizeImage(Path input, Path output) {
		try {
			// reads input image
			BufferedImage inputImage = ImageIO.read(Files.newInputStream(input));

			// creates output image
			BufferedImage outputImage = new BufferedImage(150, 150, inputImage.getType());

			// scales the input image to the output image
			Graphics2D g2d = outputImage.createGraphics();
			g2d.drawImage(inputImage, 0, 0, 150, 150, null);
			g2d.dispose();

			// extracts extension of output file
			String formatName = output.toString().substring(output.toString().lastIndexOf(".") + 1);

			// writes to output file
			ImageIO.write(outputImage, formatName, Files.newOutputStream(output));
		} catch (IOException e) {
		}
	}

}
