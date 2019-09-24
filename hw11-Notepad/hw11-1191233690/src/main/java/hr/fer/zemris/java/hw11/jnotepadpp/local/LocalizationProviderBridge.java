package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.List;

/**
 * This class is decorator for some other {@link ILocalizationProvider}. This
 * class offers two additional methods: connect() and disconnect().
 * 
 * @author Jelić, Nikola
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {

	/**
	 * Provider must not be null.
	 * */
	private ILocalizationProvider provider;
	/**
	 * Checks if someone is connected to this class.
	 * */
	private boolean connected = false;
	/**
	 * All interested listeners.
	 * */
	private List<ILocalizationListener> listeners = new ArrayList<>();
	/**
	 * Listener of provider.
	 * */
	private ILocalizationListener listener;
	/**
	 * Used for current language caching.
	 * */
	private ResourceBundle bundle;
	/**
	 * Used for checking did bridge change,
	 * */
	private boolean changed;
	
	/**
	 * Constructor sets provider.
	 * 
	 * @param provider that is set.
	 * */
	public LocalizationProviderBridge(ILocalizationProvider provider) {
		this.provider = Objects.requireNonNull(provider);
	}

	/**
	 * Used for user's connection.
	 * */
	public void connect() {
		if (!connected) {
			if(getCurrentLanguage() != null && getCurrentLanguage().equals(bundle)) {
				changed = true;
			} else {
				changed = false;
			}
			listener = new ILocalizationListener() {

				@Override
				public void localizationChaneged() {
					fire();
				}
			};
			provider.addLocalizationListener(listener);
			bundle = getCurrentLanguage();
			connected = true;
		}
	}
	
	/**
	 * Used when user want to disconnect.
	 * */
	public void disconnect() {
		if (connected) {
			provider.removeLocalizationListener(listener);
			connected = false;
		}
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
	public String getString(String s) {
		return provider.getString(s);
	}

	@Override
	public void fire() {
		if (!connected) {
			return;
		}
		listeners.forEach(e -> e.localizationChaneged());
	}
	
	@Override
	public ResourceBundle getCurrentLanguage() {
		return bundle;
	}
	
	/**
	 * Returns true if bundle has changed, else false.
	 * 
	 * @return true if bundle has changed, else false
	 * */
	public boolean isChanged() {
		return changed;
	}
}
