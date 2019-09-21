package hr.fer.zemris.java.servlets;

import java.awt.BasicStroke;
import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import hr.fer.zemris.java.GlasanjeUtil.Util;

/**
 * Servlet that creates chart of voting statistics.
 * 
 * @author Jelić, Nikola
 */
public class GlasanjeGrafikaServlet extends HttpServlet {
	
	/**
	 * Serial version ID.
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("image/png");

		OutputStream outputStream = resp.getOutputStream();

		JFreeChart chart = getChart(req);
		int width = 400;
		int height = 400;
		ChartUtils.writeChartAsPNG(outputStream, chart, width, height);
	}

	/**
	 * Creates and returns chart.
	 *  
	 * @param req used for path determination.
	 * @return created chart
	 * @throws IOException if exception occurs while reading voting results.
	 */
	public JFreeChart getChart(HttpServletRequest req) throws IOException {
		
		DefaultPieDataset dataset = new DefaultPieDataset();
		
		List<Util.Pair<String, Integer>> list = Util.authorsAndVotes(req);
		for(Util.Pair<String, Integer> value:list) {
			dataset.setValue(value.key, value.value);
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
