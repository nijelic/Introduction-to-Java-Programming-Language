package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.data.general.DefaultPieDataset;
import java.awt.BasicStroke;
import java.awt.Color;

/**
 * Creates demo chart and returns it.
 * 
 * @author Jelić, Nikola
 */
public class ChartServlet extends HttpServlet {

	/**
	 * Serial version ID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("image/png");

		OutputStream outputStream = resp.getOutputStream();

		JFreeChart chart = getChart();
		int width = 500;
		int height = 350;
		ChartUtils.writeChartAsPNG(outputStream, chart, width, height);
	}

	/**
	 * Creates demo chart and returns it.
	 * 
	 * @return demo chart.
	 */
	public JFreeChart getChart() {
		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue("Linux", 29);
		dataset.setValue("Mac", 20);
		dataset.setValue("Windows", 51);

		boolean legend = true;
		boolean tooltips = true;
		boolean urls = false;

		JFreeChart chart = ChartFactory.createPieChart("Which operating system are you using?", dataset, legend,
				tooltips, urls);

		chart.setBorderPaint(Color.BLACK);
		chart.setBorderStroke(new BasicStroke(5.0f));
		chart.setBorderVisible(true);

		return chart;
	}
}
