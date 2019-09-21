package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Objects;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * This class is implementation of {@link SingleDocumentModel} interface. It
 * defines a constructor with two parameters: filePath and textComponent. The
 * constructor creates an instance of JTextArea and set its text content,
 * registers a listener on JTextArea component’s document model and use it to
 * track its modified status flag.
 * 
 * @author Jelić, Nikola
 */
public class DefaultSingleDocumentModel implements SingleDocumentModel {

	/**
	 * Used for text content.
	 */
	private JTextArea textComponent;
	/**
	 * Path of the file of this content.
	 */
	private Path filePath;
	/**
	 * Flag that tracks modifications.
	 */
	private boolean modified;
	/**
	 * Listeners of this object.
	 */
	private List<SingleDocumentListener> listeners;

	/**
	 * Constructor sets filePath and text. FilePath can be null, but text must not.
	 * 
	 * @param filePath Path of the file of this content.
	 * @param text     Used for content.
	 */
	public DefaultSingleDocumentModel(Path filePath, String text) {
		this.filePath = filePath;
		modified = true;
		listeners = new ArrayList<>();
		textComponent = new JTextArea(Objects.requireNonNull(text));
		setTextChangedListener();
	}
	
	/**
	 * This method is called by constructor. It adds listener to document of text.
	 */
	private void setTextChangedListener() {
		textComponent.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				changeHappened();
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				changeHappened();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				changeHappened();
			}
		});
	}
	
	/**
	 * When change of text happens, modified is set to true and listeners are
	 * called.
	 */
	private void changeHappened() {
		modified = true;
		listeners.forEach((l) -> {
			l.documentModifyStatusUpdated(this);
		});
	}

	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		listeners.add(l);
	}

	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		listeners.remove(l);
	}

	@Override
	public Path getFilePath() {
		return filePath;
	}

	@Override
	public void setFilePath(Path path) {
		this.filePath = Objects.requireNonNull(path);
		listeners.forEach((l) -> {
			l.documentFilePathUpdated(this);
		});
	}

	@Override
	public JTextArea getTextComponent() {
		return textComponent;
	}

	@Override
	public boolean isModified() {
		return modified;
	}

	@Override
	public void setModified(boolean modified) {
		if (this.modified != modified) {
			this.modified = modified;
			listeners.forEach((l) -> {
				l.documentModifyStatusUpdated(this);
			});
		}
	}
}
