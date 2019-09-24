package hr.fer.zemris.java.hw17.jvdraw.objects.editors;

import java.awt.Color;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import hr.fer.zemris.java.hw17.jvdraw.shapeButtons.Line;
import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObjectListener;

/**
 * Editor implementation of line.
 * 
 * @author Jelić, Nikola
 */
public class LineEditor extends GeometricalObjectEditor {

	/**
	 * Serial version identifier.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Used to set and get info from Dialog.
	 */
	private JTextArea txtStX;
	/**
	 * Used to set and get info from Dialog.
	 */
	private JTextArea txtStY;
	/**
	 * Used to set and get info from Dialog.
	 */
	private JTextArea txtEndX;
	/**
	 * Used to set and get info from Dialog.
	 */
	private JTextArea txtEndY;
	/**
	 * Used to set and get info from Dialog.
	 */
	private JTextArea txtfgR;
	/**
	 * Used to set and get info from Dialog.
	 */
	private JTextArea txtfgG;
	/**
	 * Used to set and get info from Dialog.
	 */
	private JTextArea txtfgB;
	/**
	 * Has all info about drawing.
	 */
	private Line line;
	/**
	 * Checks editing.
	 */
	private boolean editable = false;
	/**
	 * Listeners that accepts editing.
	 */
	private List<GeometricalObjectListener> listeners;
	/**
	 * Object that is edited.
	 */
	private  GeometricalObject go;
	
		
	/**
	 * Constructor that sets all needed parameters so it can be used as {@link JPanel}.
	 * 
	 * @param line has all information of drawing.
	 * @param listeners that accepts editing.
	 * @param go object that will be edited.
	 */
	public LineEditor(Line line, List<GeometricalObjectListener> listeners, GeometricalObject go) {
		this.line = line;
		this.listeners = listeners;
		this.go = go;
		JLabel stX = new JLabel("start X:");
		JLabel stY = new JLabel("start Y:");
		JLabel endX = new JLabel("end X:");
		JLabel endY = new JLabel("end Y:");
		JLabel fgRed = new JLabel("Red:");
		JLabel fgGreen = new JLabel("Green:");
		JLabel fgBlue = new JLabel("Blue:");

		txtStX = new JTextArea(line.getStartX().toString());
		txtStY = new JTextArea(line.getStartY().toString());
		txtEndX = new JTextArea(line.getEndX().toString());
		txtEndY = new JTextArea(line.getEndY().toString());
		txtfgR = new JTextArea("" + line.getFgColor().getRed());
		txtfgG = new JTextArea("" + line.getFgColor().getGreen());
		txtfgB = new JTextArea("" + line.getFgColor().getBlue());
		this.add(stX);
		this.add(txtStX);
		this.add(stY);
		this.add(txtStY);
		this.add(endX);
		this.add(txtEndX);
		this.add(endY);
		this.add(txtEndY);
		this.add(fgRed);
		this.add(txtfgR);
		this.add(fgGreen);
		this.add(txtfgG);
		this.add(fgBlue);
		this.add(txtfgB);
	}

	@Override
	public void acceptEditing() {
		if (!editable)
			return;
		line.setStartX(Integer.parseInt(txtStX.getText()));
		line.setStartY(Integer.parseInt(txtStY.getText()));
		line.setEndX(Integer.parseInt(txtEndX.getText()));
		line.setEndY(Integer.parseInt(txtEndY.getText()));
		line.setFgColor(new Color(Integer.parseInt(txtfgR.getText()), Integer.parseInt(txtfgG.getText()),
				Integer.parseInt(txtfgB.getText())));
		listeners.forEach(l->l.geometricalObjectChanged(go));
	}

	@Override
	public void checkEditing() {
		try {
			Integer.parseInt(txtStX.getText());
			Integer.parseInt(txtStY.getText());
			Integer.parseInt(txtEndX.getText());
			Integer.parseInt(txtEndY.getText());
			int fgR = Integer.parseInt(txtfgR.getText());
			int fgG = Integer.parseInt(txtfgG.getText());
			int fgB = Integer.parseInt(txtfgB.getText());
			if (fgR < 0 || fgR > 255 || fgG < 0 || fgG > 255 || fgB < 0 || fgB > 255) {
				throw new RuntimeException("You picked wrong colors.");
			}

			editable = true;
		} catch (NumberFormatException e) {
			throw new RuntimeException("You have entered unparseable points.");
		}
	}

}
