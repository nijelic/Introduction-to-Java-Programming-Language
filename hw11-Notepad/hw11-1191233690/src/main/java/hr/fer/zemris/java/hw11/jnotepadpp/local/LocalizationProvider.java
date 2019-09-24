package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.ArrayList;
import java.util.List;

/**
 * This is singleton class. So this class is unique. It extends
 * {@link AbstractLocalizationProvider}.
 * 
 * @author Jelić, Nikola
 */
public class LocalizationProvider extends AbstractLocalizationProvider {

	/**
	 * Default language is english.
	 */
	private String language = new String("en");
	/**
	 * bundle contains all of the translations.
	 */
	private ResourceBundle bundle;
	/**
	 * Listeners interested in translating.
	 */
	private List<ILocalizationListener> listeners = new ArrayList<>();
	/**
	 * Unique instance of this.
	 */
	private static final LocalizationProvider instance = new LocalizationProvider();
	/**
	 * Location of all translations.
	 */
	private static final String LOCATION = "hr.fer.zemris.java.hw11.jnotepadpp.local.prijevodi";

	/**
	 * Default constructor sets default language.
	 */
	private LocalizationProvider() {
		Locale locale = Locale.forLanguageTag(language);
		bundle = ResourceBundle.getBundle(LOCATION, locale);
	}

	/**
	 * Getter of instance.
	 * 
	 * @return unique instance of this class.
	 */
	public static LocalizationProvider getInstance() {
		return instance;
	}

	/**
	 * Setter of language.
	 * 
	 * @param language to be set.
	 */
	public void setLanguage(String language) {
		Locale locale = Locale.forLanguageTag(language);
		bundle = ResourceBundle.getBundle(LOCATION, locale);
		this.language = language;
		fire();
	}

	/**
	 * Getter of translation of specific key.
	 * 
	 * @param key of translation
	 * @return translation of specific key.
	 */
	public String getString(String key) {
		return bundle.getString(key);
	}

	@Override
	public void addLocalizationListener(ILocalizationListener l) {
		listeners.add(l);
	}

	@Override
	public void removeLocalizationListener(ILocalizationListener l) {
		listeners.remove(l);
	}

	@Override
	public void fire() {
		listeners.forEach(e -> e.localizationChaneged());
	}
	
	@Override
	public ResourceBundle getCurrentLanguage() {
		return bundle;
	}

}
