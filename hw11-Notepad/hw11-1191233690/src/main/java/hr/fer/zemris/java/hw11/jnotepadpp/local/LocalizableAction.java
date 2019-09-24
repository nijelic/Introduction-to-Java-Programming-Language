package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;

/**
 * Extends {@link AbstractAction} used for easier building of {@link JComponent}s.
 * 
 * @author Jelić, Nikola
 */
public class LocalizableAction extends AbstractAction {

	/**
	 * Serial version ID.
	 * */
	private static final long serialVersionUID = 1L;
	/**
	 * Key of translate.
	 * */
	private String key;

	/**
	 * Constructor that sets key and automatic translating.
	 * 
	 * @param key used for searching of specific translate.
	 * @param flp provider to which is class connected.
	 * */
	public LocalizableAction(String key, ILocalizationProvider flp) {
		this.key = key;
		
		putValue(Action.NAME, flp.getString(key));
		flp.addLocalizationListener(new ILocalizationListener() {

			@Override
			public void localizationChaneged() {
				putValue(Action.NAME, flp.getString(key));
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	}
}
