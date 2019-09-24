package hr.fer.zemris.java.gui.charts;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import java.awt.Container;
import java.nio.file.Paths;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.nio.file.Path;
import java.nio.file.Files;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.io.InputStreamReader;
import java.io.BufferedInputStream;
import javax.swing.SwingConstants;

/**
 * Demonstration of {@link BarChart}. Gets path from first args and reads data from file.
 * Than models barChart.
 * 
 * @author Jelić, Nikola
 */
public class BarChartDemo extends JFrame {

	/**
	 * Unique ID of serial version
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor takes {@link BarChart} and {@link Path} to data.
	 * 
	 * @param chart data of {@link BarChart}
	 * @param p     {@link Path} to data
	 */
	public BarChartDemo(BarChart chart, Path p) {
		super();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("BarChart");
		setLocation(100, 100);
		setSize(500, 200);
		initGUI(chart, p);

	}

	/**
	 * Used for initialization.
	 * 
	 * @param chart data of {@link BarChart}
	 * @param p     {@link Path} to data
	 */
	private void initGUI(BarChart chart, Path p) {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());

		JComponent comp = new BarChartComponent(chart);
		cp.add(comp, BorderLayout.CENTER);

		JLabel label = new JLabel(p.toString());
		label.setHorizontalAlignment(SwingConstants.CENTER);
		cp.add(label, BorderLayout.PAGE_START);
	}

	/**
	 * Main method starts program.
	 * @param in first argument should be path to file.
	 * */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Expected only one path to the txt file.");
		}
		try {
			Path p = Paths.get(args[0]);
			BufferedReader br = new BufferedReader(
					new InputStreamReader(new BufferedInputStream(Files.newInputStream(p)), "UTF-8"));

			String xName = br.readLine();
			String yName = br.readLine();
			List<XYValue> data = parseData(br.readLine());
			int yMin = Integer.parseInt(br.readLine().replaceAll(" ", ""));
			int yMax = Integer.parseInt(br.readLine().replaceAll(" ", ""));
			int ySpace = Integer.parseInt(br.readLine().replaceAll(" ", ""));
			BarChart chart = new BarChart(data, xName, yName, yMin, yMax, ySpace);
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					BarChartDemo prozor = new BarChartDemo(chart, p);
					prozor.setVisible(true);
				}
			});
		} catch (IOException e) {
			System.out.println("Couldn't read file properly.");
		} catch (Exception e) {
			System.out.println("Your file is invalid: " + e.getMessage());
		}
	}

	/**
	 * This method parses {@link XYValue}s of data.
	 * 
	 * @param line of input String
	 * 
	 * @return list of {@link XYValue}s
	 * */
	private static List<XYValue> parseData(String line) {
		int x;
		int y;
		StringBuilder sb = new StringBuilder();
		int index = 0;
		List<XYValue> data = new ArrayList<XYValue>();

		while (index < line.length()) {
			index = trim(index, line);
			while (index < line.length() && line.charAt(index) != ',') {
				sb.append(line.charAt(index++));
			}
			x = Integer.parseInt(sb.toString());
			sb = new StringBuilder();
			++index;
			while (index < line.length() && line.charAt(index) != ' ') {
				sb.append(line.charAt(index++));
			}
			y = Integer.parseInt(sb.toString());
			sb = new StringBuilder();
			data.add(new XYValue(x, y));
		}
		return data;
	}

	/**
	 * This method skips white spaces.
	 * 
	 * @param index of current char
	 * @param line  of string
	 * @return index of next not whitespace
	 */
	private static int trim(int index, String line) {
		while (index < line.length() && line.charAt(index) == ' ')
			++index;
		return index;
	}
}
