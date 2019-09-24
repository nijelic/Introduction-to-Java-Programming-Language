package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.ResourceBundle;

/**
 * This is interface whose objects will be able to give translations
 * for given keys.
 * 
 * @author Jelić, Nikola
 */
public interface ILocalizationProvider {
	/**
	 * Adds listeners to this.
	 * 
	 * @param l listener to be added.
	 * */
	void addLocalizationListener(ILocalizationListener l);

	/**
	 * Removes listener from this.
	 * 
	 * @param l listener to be removed.
	 * */
	void removeLocalizationListener(ILocalizationListener l);

	/**
	 * For given key returns translation.
	 * 
	 * @param key whose translation will be returned
	 * @return the translation of key
	 * */
	String getString(String key);
	
	/**
	 * Returns cashed current language.
	 * 
	 * @return currentLanguge the cashed language.
	 * */
	ResourceBundle  getCurrentLanguage();
}
