package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * This class implements {@link ILocalizationProvider} and will add ability to
 * register, de-register and inform listeners.
 * 
 * @author Jelić, Nikola
 */
abstract public class AbstractLocalizationProvider implements ILocalizationProvider {

	/**
	 * Default constructor.
	 * */
	public AbstractLocalizationProvider() {
	}

	/**
	 * Adds {@link ILocalizationListener} to this.
	 * 
	 * @param l listener to be added
	 * */
	abstract public void addLocalizationListener(ILocalizationListener l);

	/**
	 * Removes {@link ILocalizationListener} form this.
	 * 
	 * @param l listener to be removed.
	 * */
	abstract public void removeLocalizationListener(ILocalizationListener l);

	/**
	 * Method that informs all of the listeners.
	 * */
	abstract public void fire();
}
