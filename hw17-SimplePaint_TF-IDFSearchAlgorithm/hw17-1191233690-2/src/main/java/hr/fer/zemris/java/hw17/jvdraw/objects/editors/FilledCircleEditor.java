package hr.fer.zemris.java.hw17.jvdraw.objects.editors;

import java.awt.Color;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObjectListener;
import hr.fer.zemris.java.hw17.jvdraw.shapeButtons.FilledCircle;

/**
 * Editor implementation of filled circle.
 * 
 * @author Jelić, Nikola
 */
public class FilledCircleEditor extends GeometricalObjectEditor{

	/**
	 * Serial version identifier.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Has all info about drawing.
	 */
	private FilledCircle circle;
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
	private JTextArea txtR;
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
	 * Used to set and get info from Dialog.
	 */
	private JTextArea txtbgR;
	/**
	 * Used to set and get info from Dialog.
	 */
	private JTextArea txtbgG;
	/**
	 * Used to set and get info from Dialog.
	 */
	private JTextArea txtbgB;
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
	 * @param circle has all information of drawing.
	 * @param listeners that accepts editing.
	 * @param go object that will be edited.
	 */
	public FilledCircleEditor(FilledCircle circle, List<GeometricalObjectListener> listeners, GeometricalObject go) {
		this.circle = circle;
		this.listeners = listeners;
		this.go = go;
		JLabel stX = new JLabel("start X:");
		JLabel stY = new JLabel("start Y:");
		JLabel R = new JLabel("R:");
		JLabel fgRed = new JLabel("fgRed:");
		JLabel fgGreen = new JLabel("fgGreen:");
		JLabel fgBlue = new JLabel("fgBlue:");
		JLabel bgRed = new JLabel("bgRed:");
		JLabel bgGreen = new JLabel("bgGreen:");
		JLabel bgBlue = new JLabel("bgBlue:");
		
		txtStX = new JTextArea(circle.getStartX().toString());
		txtStY = new JTextArea(circle.getStartY().toString());
		txtR = new JTextArea(circle.getR().toString());
		txtfgR = new JTextArea("" + circle.getFgColor().getRed());
		txtfgG = new JTextArea("" + circle.getFgColor().getGreen());
		txtfgB = new JTextArea("" + circle.getFgColor().getBlue());
		txtbgR = new JTextArea("" + circle.getBgColor().getRed());
		txtbgG = new JTextArea("" + circle.getBgColor().getGreen());
		txtbgB = new JTextArea("" + circle.getBgColor().getBlue());
		this.add(stX);
		this.add(txtStX);
		this.add(stY);
		this.add(txtStY);
		this.add(R);
		this.add(txtR);
		this.add(fgRed);
		this.add(txtfgR);
		this.add(fgGreen);
		this.add(txtfgG);
		this.add(fgBlue);
		this.add(txtfgB);
		this.add(bgRed);
		this.add(txtbgR);
		this.add(bgGreen);
		this.add(txtbgG);
		this.add(bgBlue);
		this.add(txtbgB);
	}
	
	@Override
	public void acceptEditing() {
		if (!editable)
			return;
		circle.setStartX(Integer.parseInt(txtStX.getText()));
		circle.setStartY(Integer.parseInt(txtStY.getText()));
		circle.setR(Integer.parseInt(txtR.getText()));
		circle.setFgColor(new Color(Integer.parseInt(txtfgR.getText()), Integer.parseInt(txtfgG.getText()),
				Integer.parseInt(txtfgB.getText())));
		circle.setBgColor(new Color(Integer.parseInt(txtbgR.getText()), Integer.parseInt(txtbgG.getText()),
				Integer.parseInt(txtbgB.getText())));
		listeners.forEach(l->l.geometricalObjectChanged(go));
	}
	
	@Override
	public void checkEditing() {
		try {
			Integer.parseInt(txtStX.getText());
			Integer.parseInt(txtStY.getText());
			Integer.parseInt(txtR.getText());
			int fgR = Integer.parseInt(txtfgR.getText());
			int fgG = Integer.parseInt(txtfgG.getText());
			int fgB = Integer.parseInt(txtfgB.getText());
			int bgR = Integer.parseInt(txtfgR.getText());
			int bgG = Integer.parseInt(txtfgG.getText());
			int bgB = Integer.parseInt(txtfgB.getText());
			if (fgR < 0 || fgR > 255 || fgG < 0 || fgG > 255 || fgB < 0 || fgB > 255) {
				throw new RuntimeException("You picked wrong fgColors.");
			}
			if (bgR < 0 || bgR > 255 || bgG < 0 || bgG > 255 || bgB < 0 || bgB > 255) {
				throw new RuntimeException("You picked wrong bgColors.");
			}

			editable = true;
		} catch (NumberFormatException e) {
			throw new RuntimeException("You have entered unparseable points.");
		}
	}
	
}
