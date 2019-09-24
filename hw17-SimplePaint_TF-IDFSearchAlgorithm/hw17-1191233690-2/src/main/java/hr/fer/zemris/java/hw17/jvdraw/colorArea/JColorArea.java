package hr.fer.zemris.java.hw17.jvdraw.colorArea;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.Color;

import javax.swing.JColorChooser;
import javax.swing.JComponent;

import java.util.List;
import java.util.ArrayList;

import java.awt.event.MouseAdapter;

/**
 * 
 * 
 * @author Jelić, Nikola
 */
public class JColorArea extends JComponent implements IColorProvider {

	/**
	 * Serial version identifier.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Color that is currently selected.
	 */
	Color selectedColor;
	/**
	 * List of all listeners.
	 */
	List<ColorChangeListener> listeners = new ArrayList<>();

	/**
	 * Constructor that sets selectedColor.
	 * 
	 * @param selectedColor
	 */
	public JColorArea(Color selectedColor) {
		this.selectedColor = selectedColor;
		
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);

				// When mouse is clicked the color chooser will opened and selected color will be changed.
				Color newColor = JColorChooser.showDialog(JColorArea.this, "Choose background color", Color.white);
				if (newColor != null) {
					Color oldColor = selectedColor;
					JColorArea.this.selectedColor = newColor;
					update(getGraphics());
					for (ColorChangeListener l : listeners) {
						l.newColorSelected(JColorArea.this, oldColor, JColorArea.this.selectedColor);
					}

				}
			}
		});
	}
	
	/**
	 * Default constructor sets selectedColor to white.
	 */
	public JColorArea() {
		this(Color.WHITE);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(15, 15);
	}

	@Override
	public Color getCurrentColor() {
		return selectedColor;
	}

	@Override
	public void addColorChangeListener(ColorChangeListener l) {
		listeners.add(l);
		l.newColorSelected(this, selectedColor, selectedColor);
	}

	@Override
	public void removeColorChangeListener(ColorChangeListener l) {
		listeners.remove(l);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Dimension dim = getSize();
		int height = dim.height;
		int width = dim.width;

		g.setColor(selectedColor);
		g.fillRect(0, 0, width, height);
	}

}
