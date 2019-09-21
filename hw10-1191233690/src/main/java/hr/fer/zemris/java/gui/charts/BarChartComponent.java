package hr.fer.zemris.java.gui.charts;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.BasicStroke;

import javax.swing.JComponent;
import java.awt.Graphics2D;

import java.awt.geom.AffineTransform;

/**
 * Custom component used for {@link BarChart}.
 * 
 * @author Jelić, Nikola
 */
public class BarChartComponent extends JComponent {

	/**
	 * Serial version ID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Gap from left and bottom edge of window
	 */
	private static final int GAP = 14;
	/**
	 * Gap used for beautiful rendering.
	 */
	private static final int GAP2 = 8;
	/**
	 * Data for chart.
	 */
	private BarChart chart;

	/**
	 * Constructor that sets chart.
	 * 
	 * @param chart data for chart.
	 */
	public BarChartComponent(BarChart chart) {
		super();
		this.chart = chart;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		int origin = GAP + 2 * GAP2 + g.getFontMetrics().stringWidth(Integer.toString(chart.getyMax()));

		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(3));

		Dimension dim = getSize();
		int height = dim.height;
		int width = dim.width;

		// y-Name
		g.setColor(Color.BLACK);

		Graphics2D g2d = (Graphics2D) g;
		AffineTransform defaultAt = g2d.getTransform();
		AffineTransform at = new AffineTransform();
		at.rotate(-Math.PI / 2);
		g2d.setTransform(at);
		int y = height / 2 + g.getFontMetrics().stringWidth(chart.getyName()) / 2;
		g2d.drawString(chart.getyName(), -y, GAP);
		AffineTransform at2 = AffineTransform.getQuadrantRotateInstance(1);
		g2d.setTransform(at2);
		g2d.setTransform(defaultAt);

		// x Name
		int x = width / 2 - g.getFontMetrics().stringWidth(chart.getxName()) / 2;
		g2d.drawString(chart.getxName(), x, height - GAP);

		// y axis
		g.drawLine(origin, height - origin, origin, origin / 2);
		g.drawPolyline(new int[] { origin - GAP2 / 2, origin, origin + GAP2 / 2 },
				new int[] { origin / 2 + GAP2 / 2, origin / 2, origin / 2 + GAP2 / 2 }, 3);

		// x axis
		g.drawLine(origin, height - origin, width - origin / 2, height - origin);
		g.drawPolyline(new int[] { width - origin / 2 - GAP2 / 2, width - origin / 2, width - origin / 2 - GAP2 / 2 },
				new int[] { height - origin + GAP2 / 2, height - origin, height - origin - GAP2 / 2 }, 3);

		// preparation
		int nx = chart.getData().size();
		int xPixPerN = (width - 2 * origin) / nx;
		int ny = (chart.getyMax() - chart.getyMin()) / chart.getySpace();
		int yPixPerN = (height - 2 * origin) / ny;

		// y axis
		for (int i = 0; i <= ny; ++i) {
			int y1 = height - origin - i * yPixPerN;

			g.drawPolyline(new int[] { origin - GAP2 / 2, origin, origin + GAP2 / 2 }, new int[] { y1, y1, y1 }, 3);
			String text = Integer.toString((chart.getyMin() + i * chart.getySpace()));
			g2d.drawString(text, origin - GAP2 - g.getFontMetrics().stringWidth(text), y1);
		}

		// x axis
		for (int i = 0; i <= nx; ++i) {
			int x1 = origin + i * xPixPerN;
			g.setColor(Color.BLACK);
			g.drawPolyline(new int[] { x1, x1, x1 },
					new int[] { height - origin + GAP2 / 2, height - origin, height - origin - GAP2 / 2 }, 3);
			if (i < nx) {
				XYValue data = chart.getData().get(i);
				String text = Integer.toString(data.getX());
				g2d.drawString(text, x1 + xPixPerN / 2, height - origin + (int) (GAP2 * 2));
				g.setColor(Color.ORANGE);
				int heightY = (data.getY() - chart.getyMin()) * yPixPerN / chart.getySpace();
				g.fillRect(x1, height - origin - heightY, xPixPerN, heightY);
			}

		}

	}

}
