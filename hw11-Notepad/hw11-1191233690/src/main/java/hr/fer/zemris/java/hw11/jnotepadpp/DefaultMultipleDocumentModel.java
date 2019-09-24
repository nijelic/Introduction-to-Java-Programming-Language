package hr.fer.zemris.java.hw11.jnotepadpp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.io.InputStream;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.Icon;
import javax.swing.JOptionPane;

/**
 * This class inherits from JTabbedPane and implements MultipleDocumentModel.
 * This class have a collection of SingleDocumentModel objects, a reference to
 * current SingleDocumentModel and a support for listeners management.
 * @author Jelić, Nikola
 */
public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {

	private static final long serialVersionUID = 1L;

	/**
	 * List of documents as {@link SingleDocumentModel}.
	 */
	private List<SingleDocumentModel> documents;
	/**
	 * Current document that is showed to user.
	 */
	private SingleDocumentModel currentDocument;
	/**
	 * Listeners of this object.
	 */
	private List<MultipleDocumentListener> listeners;
	/**
	 * Red icon is used when document is modified.
	 */
	private Icon redIcon;
	/**
	 * Green icon is used when document is unmodified.
	 */
	private Icon greenIcon;
	/**
	 * 
	 * */
	private SingleDocumentListener listener;

	/**
	 * Default constructor
	 */
	public DefaultMultipleDocumentModel() {
		documents = new ArrayList<>();
		listeners = new ArrayList<>();
		setSelectedIndex(-1);
		redIcon = readIcon("icons/red_icon.png");
		greenIcon = readIcon("icons/green_icon.png");

		addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				SingleDocumentModel priorDocument = currentDocument;
				if (getSelectedIndex() != -1) {
					currentDocument = documents.get(getSelectedIndex());
				} else {
					currentDocument = null;
				}
				listeners.forEach((f) -> f.currentDocumentChanged(priorDocument, currentDocument));
			}
		});
	}

	/**
	 * Reads icon from path.
	 * 
	 * @param path from which reads icon.
	 */
	private ImageIcon readIcon(String path) {
		try {
			InputStream is = this.getClass().getResourceAsStream(path);
			if (is == null) {
				System.err.print("Couldn't input icon");
			}
			byte[] bytes = is.readAllBytes();
			is.close();
			return new ImageIcon(bytes);
		} catch (IOException e) {
			System.err.print("Couldn't read icon");
		}
		return null;
	}

	@Override
	public void closeDocument(SingleDocumentModel model) {
		int index = documents.indexOf(model);

		notifyAndChangeCurrentDocument(index);
		remove(index);
		SingleDocumentModel doc = documents.remove(index);

		if (index < documents.size()) {
			notifyAndChangeCurrentDocument(index);
		} else {
			notifyAndChangeCurrentDocument(index - 1);
		}

		listeners.forEach((e) -> {
			e.documentRemoved(doc);
		});
	}

	@Override
	public SingleDocumentModel createNewDocument() {
		SingleDocumentModel newDoc = new DefaultSingleDocumentModel(null, "");
		newDoc.setModified(true);
		documents.add(newDoc);
		addTab("(unnamed)", redIcon, wrapToJPanel(newDoc.getTextComponent()), "(unnamed)");

		notifyAndChangeCurrentDocument(documents.size() - 1);

		listeners.forEach((e) -> {
			e.documentAdded(newDoc);
		});

		return newDoc;
	}

	@Override
	public SingleDocumentModel getCurrentDocument() {
		return currentDocument;
	}

	@Override
	public SingleDocumentModel loadDocument(Path path) {
		Objects.requireNonNull(path);

		// if already opened
		for (int i = 0, len = documents.size(); i < len; i++) {
			if (path.equals(documents.get(i).getFilePath())) {
				notifyAndChangeCurrentDocument(i);
				return documents.get(i);
			}
		}

		if (!Files.isReadable(path)) {
			JOptionPane.showMessageDialog(this, "Given path is not readable file!", "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}

		// loads new
		try {
			SingleDocumentModel newDoc = new DefaultSingleDocumentModel(path, Files.readString(path));
			newDoc.setModified(false);
			documents.add(newDoc);
			addTab(path.getFileName().toString(), greenIcon, wrapToJPanel(newDoc.getTextComponent()), path.toString());

			notifyAndChangeCurrentDocument(documents.size() - 1);

			listeners.forEach((e) -> {
				e.documentAdded(newDoc);
			});

			return newDoc;
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Error while reading file!", "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}

	@Override
	public int getNumberOfDocuments() {
		return documents.size();
	}

	@Override
	public SingleDocumentModel getDocument(int index) {
		return documents.get(index);
	}

	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {
		if (newPath == null) {
			try {
				if (model.getFilePath() == null) {
					JOptionPane.showMessageDialog(this, "Path is set to null so it is not possible to save file.",
							"Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				Files.writeString(model.getFilePath(), model.getTextComponent().getText());
				model.setModified(false);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this, "Error occured while saving file!", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
			return;
		}

		if (!newPath.equals(model.getFilePath())) {
			// check if is already opened
			for (SingleDocumentModel m : documents) {
				if (newPath.equals(m.getFilePath())) {
					JOptionPane.showMessageDialog(this, "The specified file of given path is already opened!", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
		}

		try {
			Files.writeString(newPath, model.getTextComponent().getText());
			model.setModified(false);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Error while saving file!", "Error", JOptionPane.ERROR_MESSAGE);
		}
		model.setFilePath(newPath);
		JOptionPane.showMessageDialog(this, "Document has been saved!", "Information", JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Returns text wrapped to JPanel as JScrollPane.
	 * 
	 * @param text text to be wrapped
	 */
	private JPanel wrapToJPanel(JTextArea text) {
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());
		p.add(new JScrollPane(text), BorderLayout.CENTER);
		return p;
	}

	/**
	 * Changes currentDocument to value of documents at index. If index == -1
	 * currentDocument is set to null. Also, notifies all listeners that
	 * currentDocument have changed.
	 * 
	 * @param index index of an element of documents to which will currentDocument
	 *              be changed
	 */
	private void notifyAndChangeCurrentDocument(int index) {
		SingleDocumentModel priorDocument = currentDocument;
		setSelectedIndex(index);
		if (index != -1) {
			currentDocument = documents.get(index);
		} else {
			currentDocument = null;
		}
		if (priorDocument != null) {
			priorDocument.removeSingleDocumentListener(listener);
		}
		if (currentDocument != null) {
			addListener(currentDocument);
		}
		listeners.forEach((e) -> {
			e.currentDocumentChanged(priorDocument, currentDocument);
		});
	}

	/**
	 * Adds {@link SingleDocumentListener} to new document.
	 * 
	 * @param newDoc New document made by createNewDocument or loadDocument method.
	 */
	private void addListener(SingleDocumentModel newDoc) {
		listener = new SingleDocumentListener() {

			@Override
			public void documentModifyStatusUpdated(SingleDocumentModel model) {
				if (model.isModified()) {
					setIconAt(getSelectedIndex(), redIcon);
				} else {
					setIconAt(getSelectedIndex(), greenIcon);
				}
			}

			@Override
			public void documentFilePathUpdated(SingleDocumentModel model) {

				int index = documents.indexOf(model);
				if (model.getFilePath() == null) {
					setToolTipTextAt(index, "(unnamed)");
				} else {
					setToolTipTextAt(index, model.getFilePath().toString());
					setTitleAt(index, model.getFilePath().getFileName().toString());
				}

				// used for changing window title
				if (model.equals(currentDocument)) {
					listeners.forEach((e) -> e.currentDocumentChanged(currentDocument, currentDocument));
				}
			}
		};
		newDoc.addSingleDocumentListener(listener);
	}

	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return documents.iterator();
	}

	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.add(l);
	}

	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.remove(l);
	}
}
