package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

/**
 * Kinda proxy to {@link LocalizationProviderBridge}.
 * 
 * @author Jelić, Nikola
 * */
public class FormLocalizationProvider extends LocalizationProviderBridge {

	/**
	 * Constructor sets windowListener to the frame.
	 * 
	 * @param provider used for setting {@link ILocalizationProvider}
	 * @param frame sets {@link WindowListener}.
	 * */
	public FormLocalizationProvider(ILocalizationProvider provider, JFrame frame) {
		super(provider);
		frame.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				connect();
			}
			
			@Override
			public void windowClosing(WindowEvent e) {}
			@Override
			public void windowDeactivated(WindowEvent e) {}
			@Override
			public void windowDeiconified(WindowEvent e) {}
			@Override
			public void windowIconified(WindowEvent e) {}
			
			@Override
			public void windowClosed(WindowEvent e) {
				disconnect();
			}
			
			@Override
			public void windowActivated(WindowEvent e) {}
		});
	}
}
