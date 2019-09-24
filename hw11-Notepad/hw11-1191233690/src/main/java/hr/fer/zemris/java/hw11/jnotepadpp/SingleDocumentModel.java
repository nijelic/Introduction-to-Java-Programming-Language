package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;
import javax.swing.JTextArea;

/**
 * SingleDocumentModel represents a model of single document, having information
 * about file path from which document was loaded (can be null for new
 * document), document modification status and reference to Swing component
 * which is used for editing (each document has its own editor component)
 * 
 * @author Jelić, Nikola
 */
public interface SingleDocumentModel {
	
	/**
	 * Getter of textComponent.
	 * 
	 * @return textComponent, the JTextArea 
	 * */
	JTextArea getTextComponent();

	/**
	 * Getter of filePath, where file is saved or will be saved.
	 * 
	 * @return filePath, where file is saved or will be saved.
	 * */
	Path getFilePath();

	/**
	 * Setter of filePath, where file will be saved.
	 * 
	 * @param path, where file will be saved.
	 * */
	void setFilePath(Path path);

	/**
	 * Returns true if file was modified, else false.
	 * 
	 * @return true if file was modified, else false.
	 * */
	boolean isModified();

	/**
	 * Setter of modified.
	 * 
	 * @param modified, true if file was modified, else false
	 * */
	void setModified(boolean modified);

	/**
	 * Adds listener to object.
	 * 
	 * @param listener to be added
	 * */
	void addSingleDocumentListener(SingleDocumentListener l);

	/**
	 * Removes listener from object.
	 * 
	 * @param listener to be removed.
	 * */
	void removeSingleDocumentListener(SingleDocumentListener l);
}
