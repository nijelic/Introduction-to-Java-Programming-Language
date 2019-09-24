package hr.fer.zemris.java.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.java.GlasanjeUtil.Util;

/**
 * This servlet creates "table.xls" file with results of voting.
 * 
 * @author Jelić, Nikola
 */
public class GlasanjeXlsServlet extends HttpServlet {

	/**
	 * Serial version ID.
	 */
	private static final long serialVersionUID = 1L;
		
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setHeader("Content-Disposition", "attachment; filename=\"table.xls\"");
		createXls(req).write(resp.getOutputStream());
	}

	/**
	 * Creates and returns xls-file.
	 * 
	 * @param req used for finding path
	 * @return created xls file
	 * @throws IOException if exception occurs while reading file.
	 */
	private HSSFWorkbook createXls(HttpServletRequest req) throws IOException {
		List<Util.Pair<String, Integer>> list = Util.authorsAndVotes(req);

		HSSFWorkbook hwb = new HSSFWorkbook();
		HSSFSheet sheet = hwb.createSheet("Sheet 1");

		HSSFRow rowhead = sheet.createRow((short) 0);
		rowhead.createCell((short) 0).setCellValue("Bend");
		rowhead.createCell((short) 1).setCellValue("Broj glasova");
		
		short rowNum = 1;
		for(Util.Pair<String, Integer> pair:list) {
			HSSFRow row = sheet.createRow(rowNum);
			row.createCell((short) 0).setCellValue(pair.key);
			row.createCell((short) 1).setCellValue(pair.value.toString());
			rowNum++;
		}

		return hwb;
	}
}
