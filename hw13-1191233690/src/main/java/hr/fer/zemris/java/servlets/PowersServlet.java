package hr.fer.zemris.java.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFRow;

/**
 * This servlet creates and reuturns "table.xls" with powers of selected numbers
 * in the [-100, 100] segment.
 * 
 * @author Jelić, Nikola
 */
public class PowersServlet extends HttpServlet {

	/**
	 * Serial version ID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Integer a;
		Integer b;
		Integer n;
		try {
			a = Integer.parseInt(req.getParameter("a"));
		} catch (NumberFormatException e) {
			req.setAttribute("error", "Parameter 'a' is not parsable to integer.");
			req.getRequestDispatcher("WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		try {
			b = Integer.parseInt(req.getParameter("b"));
		} catch (NumberFormatException e) {
			req.setAttribute("error", "Parameter 'b' is not parsable to integer.");
			req.getRequestDispatcher("WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		try {
			n = Integer.parseInt(req.getParameter("n"));
		} catch (NumberFormatException e) {
			req.setAttribute("error", "Parameter 'n' is not parsable to integer.");
			req.getRequestDispatcher("WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		if (a < -100 || 100 < a) {
			req.setAttribute("error", "Parameter 'a' is out of bounds. Should be element of [-100, 100].");
			req.getRequestDispatcher("WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		if (b < -100 || 100 < b) {
			req.setAttribute("error", "Parameter 'b' is out of bounds. Should be element of [-100, 100].");
			req.getRequestDispatcher("WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		if (n < 1 || 5 < n) {
			req.setAttribute("error", "Parameter 'n' is out of bounds. Should be element of [1, 5].");
			req.getRequestDispatcher("WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}
		if (a > b) {
			req.setAttribute("error", "Parameter 'a' should be less or equal to parameter 'b'.");
			req.getRequestDispatcher("WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}

		resp.setHeader("Content-Disposition", "attachment; filename=\"table.xls\"");
		createXls(a, b, n).write(resp.getOutputStream());
	}

	/**
	 * Returns created xls file.
	 * 
	 * @param a number of lower bound
	 * @param b number of upper bound
	 * @param n number of powers
	 * @return created xls file
	 */
	private HSSFWorkbook createXls(Integer a, Integer b, Integer n) {

		HSSFWorkbook hwb = new HSSFWorkbook();
		for (int i = 1; i <= n; i++) {
			HSSFSheet sheet = hwb.createSheet("Sheet " + i);

			HSSFRow rowhead = sheet.createRow((short) 0);
			rowhead.createCell((short) 0).setCellValue("Number");
			rowhead.createCell((short) 1).setCellValue("Power of " + i);

			short rowNum = 1;
			for (Integer j = a; j <= b; j++) {
				HSSFRow row = sheet.createRow(rowNum);
				row.createCell((short) 0).setCellValue(j.toString());
				row.createCell((short) 1).setCellValue(String.format("%.0f", Double.valueOf(Math.pow(j, i))));
				rowNum++;
			}
		}

		return hwb;
	}
}
