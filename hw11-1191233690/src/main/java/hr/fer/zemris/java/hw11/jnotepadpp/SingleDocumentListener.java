package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * Interface used for listeners of {@link SingleDocumentModel}.
 * 
 * @author Jelić, Nikola
 * */
public interface SingleDocumentListener {
	
	/**
	 * Activates if status of modified variable is updated/changed.
	 * 
	 * @param model whose listener was called. 
	 * */
	void documentModifyStatusUpdated(SingleDocumentModel model);

	/**
	 * Activates if filePath variable is updated/changed.
	 * 
	 * @param model whose listener was called. 
	 * */
	void documentFilePathUpdated(SingleDocumentModel model);
}
