package hr.fer.zemris.java.p12.servlets;

import java.awt.BasicStroke;
import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.utility.PollOption;

/**
 * Servlet that creates chart of voting statistics.
 * 
 * @author Jelić, Nikola
 */
@WebServlet("/servleti/glasanje-grafika")
public class GlasanjeGrafikaServlet extends HttpServlet {
	
	/**
	 * Serial version ID.
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("image/png");
		Long pollID = null;
		try {
			pollID = Long.parseLong(req.getParameter("pollID"));
		} catch(NumberFormatException e) {
		}
		
		if(pollID == null) {
			return;
		}
		OutputStream outputStream = resp.getOutputStream();

		JFreeChart chart = getChart(DAOProvider.getDao().getOptionsByID(pollID));
		int width = 400;
		int height = 400;
		ChartUtils.writeChartAsPNG(outputStream, chart, width, height);
	}

	/**
	 * Creates and returns chart.
	 *  
	 * @param options all data needed for making chart
	 * @throws IOException if exception occurs while reading voting results.
	 */
	public JFreeChart getChart(List<PollOption> options) throws IOException {
		
		DefaultPieDataset dataset = new DefaultPieDataset();
		
		for(PollOption o:options) {
			dataset.setValue(o.getTitle(), o.getVotesCount());
		}
		
		boolean legend = true;
		boolean tooltips = true;
		boolean urls = false;

		JFreeChart chart = ChartFactory.createPieChart("Grafički prikaz rezultata", dataset, legend, tooltips, urls);

		chart.setBorderPaint(Color.BLACK);
		chart.setBorderStroke(new BasicStroke(5.0f));
		chart.setBorderVisible(true);

		return chart;
	}
}
