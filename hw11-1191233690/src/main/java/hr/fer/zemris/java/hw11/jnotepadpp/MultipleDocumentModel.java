package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;

/**
 * MultipleDocumentModel represents a model capable of holding zero, one or more
 * documents, where each document and having a concept of current document – the
 * one which is shown to the user and on which user works.
 * 
 * @author Jelić, Nikola
 */
public interface MultipleDocumentModel extends Iterable<SingleDocumentModel> {

	/**
	 * Creates new document whose text is empty and filePath is null.
	 * 
	 * @return new document as {@link SingleDocumentModel}
	 */
	SingleDocumentModel createNewDocument();

	/**
	 * Getter of currentDocument.
	 * 
	 * @return current document as {@link SingleDocumentModel}
	 */
	SingleDocumentModel getCurrentDocument();

	/**
	 * Loads document (if exists and isn't already opened) or creates new whose text
	 * is empty and filePath is path.
	 * 
	 * @return loaded document
	 */
	SingleDocumentModel loadDocument(Path path);

	/**
	 * Saves document to newPath. If newPath == null saves to model's filePath. If
	 * model's filePath is also null than gets proper message to user.
	 * 
	 * @param model   to be saved
	 * @param newPath the path where to be saved
	 */
	void saveDocument(SingleDocumentModel model, Path newPath);

	/**
	 * Closes document and notifies listeners.
	 * 
	 * @param model to be closed
	 */
	void closeDocument(SingleDocumentModel model);

	/**
	 * Adds listener.
	 * 
	 * @param l listener to be added.
	 */
	void addMultipleDocumentListener(MultipleDocumentListener l);

	/**
	 * Removes listener.
	 * 
	 * @param l listener to be removed.
	 * */
	void removeMultipleDocumentListener(MultipleDocumentListener l);

	/**
	 * Returns number of documents.
	 * 
	 * @return number of documents.
	 * */
	int getNumberOfDocuments();

	/**
	 * Returns document at the specific index.
	 * 
	 * @param index whose elements will be returned.
	 * @return specific document at index position.
	 * */
	SingleDocumentModel getDocument(int index);
}
