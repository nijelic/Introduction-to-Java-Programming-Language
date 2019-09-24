package hr.fer.zemris.java.p12.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.utility.PollOption;


/**
 * This servlet creates "table.xls" file with results of voting.
 * 
 * @author Jelić, Nikola
 */
@WebServlet("/servleti/glasanje-xls")
public class GlasanjeXlsServlet extends HttpServlet {

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
		resp.setHeader("Content-Disposition", "attachment; filename=\"table.xls\"");
		createXls(DAOProvider.getDao().getOptionsByID(pollID)).write(resp.getOutputStream());
	}

	/**
	 * Creates and returns xls-file.
	 * 
	 * @param options all data used for filling xls-file
	 * @throws IOException if exception occurs while reading file.
	 */
	private HSSFWorkbook createXls(List<PollOption> options) throws IOException {
		
		HSSFWorkbook hwb = new HSSFWorkbook();
		HSSFSheet sheet = hwb.createSheet("Sheet 1");

		HSSFRow rowhead = sheet.createRow((short) 0);
		rowhead.createCell((short) 0).setCellValue("Option");
		rowhead.createCell((short) 1).setCellValue("Votes");
		
		short rowNum = 1;
		for(PollOption o:options) {
			HSSFRow row = sheet.createRow(rowNum);
			row.createCell((short) 0).setCellValue(o.getTitle());
			row.createCell((short) 1).setCellValue(o.getVotesCount());
			rowNum++;
		}

		return hwb;
	}
}
