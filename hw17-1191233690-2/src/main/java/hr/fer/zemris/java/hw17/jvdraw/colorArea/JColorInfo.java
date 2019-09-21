package hr.fer.zemris.java.hw17.jvdraw.colorArea;

import java.awt.Color;

import javax.swing.JLabel;

/**
 * Label that writes current background and foreground selected colors.
 * 
 * @author Jelić, Nikola
 */
public class JColorInfo extends JLabel {

	/**
	 * Serial version identifier.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Foreground color.
	 */
	private Color fgColor;
	/**
	 * Background color.
	 */
	private Color bgColor;
	
	/**
	 * Constructor that sets {@link ColorChangeListener}s to fgColorProvider and bgColorProvider.
	 * 
	 * @param fgColorProvider the provider of foreground color.
	 * @param bgColorProvider the provider of background color.
	 */
	public JColorInfo(IColorProvider fgColorProvider, IColorProvider bgColorProvider) {
		super();
		fgColor = fgColorProvider.getCurrentColor();
		bgColor = bgColorProvider.getCurrentColor();
		
		fgColorProvider.addColorChangeListener(new ColorChangeListener() {
			
			@Override
			public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
				JColorInfo.this.setFgColor(newColor);
			}
		});
		
		bgColorProvider.addColorChangeListener(new ColorChangeListener() {
			
			@Override
			public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
				JColorInfo.this.setBgColor(newColor);
			}
		});
		
	}

	/**
	 * FgColor getter.
	 * 
	 * @return foreground color.
	 */
	public Color getFgColor() {
		return fgColor;
	}
	
	/**
	 * FgColor setter.
	 * 
	 * @param fgColor the foreground color.
	 */
	public void setFgColor(Color fgColor) {
		this.fgColor = fgColor;
		this.setText(this.toString());
	}
	
	/**
	 * BgColor getter.
	 * 
	 * @return background color.
	 */
	public Color getBgColor() {
		return bgColor;
	}
	
	/**
	 * BgColor setter.
	 * 
	 * @param bgColor the background color.
	 */
	public void setBgColor(Color bgColor) {
		this.bgColor = bgColor;
		this.setText(this.toString());
	}
	
	@Override
	public String toString() {
		return "Foreground color: ("+fgColor.getRed()+", "+fgColor.getGreen()+", "+fgColor.getBlue()+"), background color: ("+bgColor.getRed()+", "+bgColor.getGreen()+", "+bgColor.getBlue()+").";
	}

}
