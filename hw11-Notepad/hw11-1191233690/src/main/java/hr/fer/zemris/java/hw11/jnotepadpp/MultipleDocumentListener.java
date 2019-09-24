package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * Interface used for listeners of {@link MultipleDocumentModel}.
 * 
 * @author Jelić, Nikola
 * */
public interface MultipleDocumentListener {
	
	/**
	 * Notifies listener that currentDocument has changed from previous to current. Can't be both null.
	 * 
	 * @param previousModel model before change
	 * @param currentModel model after change
	 * */
	void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel);

	/**
	 * Notifies listener that new document has been added.
	 * 
	 * @param model of added document.
	 * */
	void documentAdded(SingleDocumentModel model);

	/**
	 * Notifies listener that document has been removed.
	 * 
	 * @param model which has been removed.
	 * */
	void documentRemoved(SingleDocumentModel model);
}
