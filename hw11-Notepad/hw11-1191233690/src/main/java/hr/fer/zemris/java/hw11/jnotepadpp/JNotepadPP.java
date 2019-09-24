package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Color;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.text.BadLocationException;

import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.BorderFactory;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.KeyStroke;
import javax.swing.JToolBar;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Function;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.text.Document;
import java.util.Locale;
import java.text.Collator;

import java.util.List;

import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;

/**
 * This is Notepad++ application.
 * 
 * @author Jelić, Nikola
 */
public class JNotepadPP extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * This is model of tabbedPane that holds all text areas.
	 */
	private MultipleDocumentModel tabbedPane;
	/**
	 * String that remembers what needs to be copied.
	 */
	private String saveSelected;
	/**
	 * Left {@link JLabel} used for status bar. Contains length of text.
	 */
	private JLabel leftLabel;
	/**
	 * Right {@link JLabel} used for status bar. Contains Ln, Col and Sel.
	 */
	private JLabel rightLabel;
	/**
	 * Listener used for changing caret over text.
	 */
	private ChangeListener caretListener;
	/**
	 * Timer used for easier implementation of date and time.
	 */
	private Timer timer;
	/**
	 * Used for localization of translation.
	 * */
	private FormLocalizationProvider flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);

	/**
	 * Default constructor.
	 */
	public JNotepadPP() {
		super();
		
		LocalizationProvider.getInstance().setLanguage("en");
		
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setTitle("JNotepad++");
		setLocation(100, 100);
		setSize(1100, 400);
		setMinimumSize(new Dimension(900, 350));

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				if (startClosingProcess()) {
					dispose();
					timer.stop();
				}
			}
		});

		initGUI();
	}

	/**
	 * Initializes GUI.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());

		tabbedPane = new DefaultMultipleDocumentModel();
		addListener();
		cp.add((DefaultMultipleDocumentModel) tabbedPane, BorderLayout.CENTER);

		createActions();
		createMenus();
		JPanel jp = new JPanel();

		jp.add(createToolbar1());
		jp.add(createToolbar2());
		jp.add(createToolbar3());
		jp.add(createToolbar4());
		cp.add(jp, BorderLayout.PAGE_START);

		cp.add(statusBar(), BorderLayout.PAGE_END);
	}

	/**
	 * List of all actions. This method sets up them.
	 */
	private void createActions() {

		// creating and saving
		newDocument.putValue(Action.NAME, "Create new document");
		newDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
		newDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
		newDocument.putValue(Action.SHORT_DESCRIPTION, "Creates empty document.");

		openDocument.putValue(Action.NAME, "Open");
		openDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		openDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		openDocument.putValue(Action.SHORT_DESCRIPTION, "Open document from disk.");

		saveDocument.putValue(Action.NAME, "Save");
		saveDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		saveDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		saveDocument.putValue(Action.SHORT_DESCRIPTION, "Save document on disk.");

		saveAsDocument.putValue(Action.NAME, "Save As");
		saveAsDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift control S"));
		saveAsDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
		saveAsDocument.putValue(Action.SHORT_DESCRIPTION, "Save document on disk to new file.");

		// closing and exiting

		closeCurrentTab.putValue(Action.NAME, "Close tab");
		closeCurrentTab.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control W"));
		closeCurrentTab.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
		closeCurrentTab.putValue(Action.SHORT_DESCRIPTION, "Closes current tab.");

		exitApplication.putValue(Action.NAME, "Exit");
		exitApplication.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control Q"));
		exitApplication.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
		exitApplication.putValue(Action.SHORT_DESCRIPTION, "Exits application.");

		// editing

		copy.putValue(Action.NAME, "Copy");
		copy.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
		copy.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		copy.putValue(Action.SHORT_DESCRIPTION, "Copies selected");

		cut.putValue(Action.NAME, "Cut");
		cut.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		cut.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);
		cut.putValue(Action.SHORT_DESCRIPTION, "Cuts selected");

		paste.putValue(Action.NAME, "Paste");
		paste.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
		paste.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_P);
		paste.putValue(Action.SHORT_DESCRIPTION, "Paste copied/cut text");

		// statistical info
		statistics.putValue(Action.NAME, "Statisical Info");
		statistics.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control i"));
		statistics.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_I);
		statistics.putValue(Action.SHORT_DESCRIPTION, "Writes number of: lines, characters & non-blanks.");

		// Tools - Change case
		toUppercase.putValue(Action.NAME, "To uppercase");
		toUppercase.putValue(Action.SHORT_DESCRIPTION, "Transforms selected text to uppercase");

		toLowercase.putValue(Action.NAME, "To lowercase");
		toLowercase.putValue(Action.SHORT_DESCRIPTION, "Transforms selected text to lowercase");

		invertcase.putValue(Action.NAME, "Inver case");
		invertcase.putValue(Action.SHORT_DESCRIPTION, "Inverts case of selected text");

		// Tools - sort
		ascending.putValue(Action.NAME, "Ascending");
		ascending.putValue(Action.SHORT_DESCRIPTION, "Ascending sort of selected lines.");

		descending.putValue(Action.NAME, "Descending");
		descending.putValue(Action.SHORT_DESCRIPTION, "Descending sort of selected lines.");

		// Tools - unique
		unique.putValue(Action.NAME, "Unique");
		unique.putValue(Action.SHORT_DESCRIPTION, "Removes repetition of selected lines.");
		
		// Change of langualge
		hr.putValue(Action.NAME, flp.getString("hr"));
		hr.putValue(Action.SHORT_DESCRIPTION, "Set to croatian language");
		
		en.putValue(Action.NAME, flp.getString("en"));
		en.putValue(Action.SHORT_DESCRIPTION, "Set to english language");
		
		de.putValue(Action.NAME, flp.getString("de"));
		de.putValue(Action.SHORT_DESCRIPTION, "Set to german language");
		
		// disabeles all tools
		setEnabledSelected(false);
	}

	/**
	 * Creating {@link JMenuBar}: File, Edit and Tools.
	 */
	private void createMenus() {
		JMenuBar mb = new JMenuBar();

		JMenu file = new JMenu(new LocalizableAction("File", flp));
		mb.add(file);
		file.add(new JMenuItem(newDocument));
		file.add(new JMenuItem(openDocument));
		file.add(new JMenuItem(saveDocument));
		file.add(new JMenuItem(saveAsDocument));
		file.addSeparator();
		file.add(new JMenuItem(closeCurrentTab));
		file.addSeparator();
		file.add(new JMenuItem(exitApplication));

		JMenu edit = new JMenu(new LocalizableAction("Edit", flp));
		mb.add(edit);
		edit.add(new JMenuItem(copy));
		edit.add(new JMenuItem(cut));
		edit.add(new JMenuItem(paste));
		edit.addSeparator();
		edit.add(new JMenuItem(statistics));

		JMenu tools = new JMenu(new LocalizableAction("Tools", flp));
		mb.add(tools);

		JMenu changeCase = new JMenu(new LocalizableAction("Change_case", flp));
		tools.add(changeCase);
		changeCase.add(toUppercase);
		changeCase.add(toLowercase);
		changeCase.add(invertcase);

		JMenu sortLines = new JMenu(new LocalizableAction("Sort", flp));
		tools.add(sortLines);
		sortLines.add(ascending);
		sortLines.add(descending);

		tools.addSeparator();
		tools.add(unique);
		
		JMenu languages = new JMenu(new LocalizableAction("Languages", flp));
		mb.add(languages);
		languages.add(new JMenuItem(hr));
		languages.add(new JMenuItem(en));
		languages.add(new JMenuItem(de));

		setJMenuBar(mb);
	}

	/**
	 * First {@link JToolBar} contains: newDocument, openDocument, saveDocument and
	 * saveAsDocument.
	 */
	private JToolBar createToolbar1() {
		JToolBar tb = new JToolBar();
		tb.setFloatable(true);

		tb.add(new JButton(newDocument));
		tb.add(new JButton(openDocument));
		tb.add(new JButton(saveDocument));
		tb.add(new JButton(saveAsDocument));

		return tb;
	}

	/**
	 * Second {@link JToolBar} contains: close current tab and exit application.
	 */
	private JToolBar createToolbar2() {
		JToolBar tb = new JToolBar();
		tb.setFloatable(true);

		tb.add(new JButton(closeCurrentTab));
		tb.add(new JButton(exitApplication));

		return tb;
	}

	/**
	 * Third {@link JToolBar} contains: copy, cut and paste.
	 */
	private JToolBar createToolbar3() {
		JToolBar tb = new JToolBar();
		tb.setFloatable(true);

		tb.add(new JButton(copy));
		tb.add(new JButton(cut));
		tb.add(new JButton(paste));

		return tb;
	}

	/**
	 * Fourth {@link JToolBar} contains only statistics info.
	 * */
	private JToolBar createToolbar4() {
		JToolBar tb = new JToolBar();
		tb.setFloatable(true);

		tb.add(new JButton(statistics));

		return tb;
	}

	/**
	 * Status bar contains: leftLabel and rightPanel.
	 * leftLabel contains length of current text.
	 * rightPanel contains: rightLabel and DateAndTime.
	 * rightLabel contains: Ln, Col and Sel.
	 * */
	private JPanel statusBar() {
		JPanel statusBar = new JPanel(new GridLayout(1, 0));
		
		leftLabel = new JLabel();
		JPanel rightPanel = new JPanel(new BorderLayout());
		
		statusBar.add(leftLabel);
		statusBar.add(rightPanel);

		Border blackline = BorderFactory.createLineBorder(Color.black);
		statusBar.setBorder(blackline);

		leftLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		rightPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

		leftLabel.setText(" length:");
		
		rightLabel = new JLabel();
		rightLabel.setText(" Ln:  Col:  Sel:");
		rightPanel.add(rightLabel, BorderLayout.WEST);

		JLabel timeLabel = new JLabel();
		rightPanel.add(timeLabel, BorderLayout.EAST);
		timer = new Timer(500, new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss  ");
				Date date = new Date();
				timeLabel.setText(dateFormat.format(date));
			}
		});
		
		timer.start();
		return statusBar;
	}
	
	/**
	 * Opens file.
	 * */
	private final Action openDocument = new LocalizableAction("open", flp) {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser jfc = new JFileChooser();
			jfc.setDialogTitle("Open file");
			if (jfc.showOpenDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}
			
			Path filePath = jfc.getSelectedFile().toPath();
			
			if (!Files.isReadable(filePath)) {
				JOptionPane.showMessageDialog(JNotepadPP.this, "Could not read " + filePath + ".", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			tabbedPane.loadDocument(filePath);
		}
	};

	/**
	 * Creates new file.
	 * */
	private final Action newDocument = new LocalizableAction("new", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			tabbedPane.createNewDocument();
		}
	};


	/**
	 * Saves file.
	 * */
	private final Action saveDocument = new LocalizableAction("save", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (checkCurrentNull((String) this.getValue(Action.NAME))) {
				return;
			}
			save();
		}
	};

	/**
	 * Save As file.
	 * */
	private final Action saveAsDocument = new LocalizableAction("saveAs", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (checkCurrentNull((String) this.getValue(Action.NAME))) {
				return;
			}
			saveAs();
		}
	};

	/**
	 * Save file if path already exists or does saving As.
	 */
	private void save() {
		Path path = tabbedPane.getCurrentDocument().getFilePath();
		if (path == null) {
			saveAs();
			return;
		}
		tabbedPane.saveDocument(tabbedPane.getCurrentDocument(), path);
	}

	/**
	 * Save As to chosen file.
	 */
	private void saveAs() {
		Path path = askForPath();
		if (path == null) {
			return;
		}
		tabbedPane.saveDocument(tabbedPane.getCurrentDocument(), path);
	}

	/**
	 * Asks for path and check if already exists. If exists asks for approval.
	 * 
	 * @return path if needs to be saved or null if shouldn't be saved.
	 */
	private Path askForPath() {
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle("Save document");
		if (jfc.showSaveDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(JNotepadPP.this, "Not saved.", "Information",
					JOptionPane.INFORMATION_MESSAGE);
			return null;
		}
		Path path = jfc.getSelectedFile().toPath();
		if (Files.exists(path)) {
			int ans = JOptionPane.showConfirmDialog(JNotepadPP.this,
					path.getFileName().toString() + " exists. Would you like to override it?", "Confirmation.",
					JOptionPane.YES_NO_OPTION);
			if (ans == JOptionPane.NO_OPTION) {
				return null;
			}
		}
		return path;
	}

	/**
	 * Closes current tab.
	 * */
	private final Action closeCurrentTab = new LocalizableAction("close", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			SingleDocumentModel currentDocument = tabbedPane.getCurrentDocument();
			if (currentDocument == null) {
				return;
			}
			if (closeTab(currentDocument)) {
				tabbedPane.closeDocument(currentDocument);
			}
		}
	};

	/**
	 * Asks questions while closing tab.
	 * */
	private boolean closeTab(SingleDocumentModel document) {
		if (document == null) {
			int ans = JOptionPane.showConfirmDialog(JNotepadPP.this, "Are you sure to exit?", "Confirmation.",
					JOptionPane.YES_NO_OPTION);
			if (ans == JOptionPane.YES_OPTION) {
				return true;
			}
			return false;
		}
		if (!document.isModified()) {
			int ans = JOptionPane.showConfirmDialog(JNotepadPP.this,
					"Are you sure to exit from " + document.getFilePath().getFileName().toString() + "?",
					"Confirmation.", JOptionPane.YES_NO_OPTION);
			if (ans == JOptionPane.YES_OPTION) {
				return true;
			}
			return false;
		}
		
		// would save?
		int ans = JOptionPane.showOptionDialog(JNotepadPP.this, "Would you like to save changes?", "Save info",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

		if (ans == JOptionPane.CANCEL_OPTION) {
			return false;
		} else if (ans == JOptionPane.YES_OPTION) {
			save();
			return true;
		} else if (ans == JOptionPane.NO_OPTION) {
			return true;
		}
		return false;
	}

	/**
	 * Cuts selected text.
	 * */
	private final Action cut = new LocalizableAction("cut", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (checkCurrentNull((String) this.getValue(Action.NAME))) {
				return;
			}
			rememeberString();
			deleteString();
		}
	};

	/**
	 * Copies selected text.
	 * */
	private final Action copy = new LocalizableAction("copy", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (checkCurrentNull((String) this.getValue(Action.NAME))) {
				return;
			}
			rememeberString();
		}
	};

	/**
	 * Saves selected text. Used by cut or copy.
	 * */
	private void rememeberString() {
		JTextArea editor = tabbedPane.getCurrentDocument().getTextComponent();
		int start = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());
		int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());

		if (len < 1) {
			saveSelected = new String("");
			return;
		}

		try {
			saveSelected = editor.getDocument().getText(start, len);
		} catch (BadLocationException e1) {
		}
	}

	/**
	 * Deletes selected text. Used by cut.
	 * */
	private void deleteString() {
		JTextArea editor = tabbedPane.getCurrentDocument().getTextComponent();
		int start = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());
		int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());

		if (len < 1) {
			return;
		}

		try {
			editor.getDocument().remove(start, len);
		} catch (BadLocationException e1) {
		}
	}

	/**
	 * Paste action
	 * */
	private final Action paste = new LocalizableAction("paste", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				if (checkCurrentNull((String) this.getValue(Action.NAME))) {
					return;
				}
				JTextArea editor = tabbedPane.getCurrentDocument().getTextComponent();
				editor.getDocument().insertString(editor.getCaretPosition(), saveSelected, null);
			} catch (BadLocationException e1) {
			}
		}
	};

	/**
	 * Exit form application action.
	 * */
	private final Action exitApplication = new LocalizableAction("exit", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (startClosingProcess()) {
				dispose();
				timer.stop();
			}
		}
	};

	/**
	 * Statistical info action.
	 * */
	private final Action statistics = new LocalizableAction("stat", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (checkCurrentNull((String) this.getValue(Action.NAME))) {
				return;
			}
			String text = tabbedPane.getCurrentDocument().getTextComponent().getText();
			int numberOfCharacters = text.length();
			int numberOfLines = numberOfCharacters - text.replace("\n", "").length() + 1;
			int numberOfNonBlank = text.replaceAll("\\s+", "").length();
			JOptionPane.showMessageDialog(JNotepadPP.this,
					"Your document has " + numberOfCharacters + " characters, " + numberOfNonBlank
							+ " non-blank characters and " + numberOfLines + " lines.",
					"Statistical info", JOptionPane.INFORMATION_MESSAGE);
		}
	};

	/**
	 * Converts selected text to upper case.
	 * */
	private final Action toUppercase = new LocalizableAction("upper", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			transformCase(text -> {
				char[] chars = text.toCharArray();
				for (int i = 0; i < chars.length; i++) {
					chars[i] = Character.toUpperCase(chars[i]);
				}
				return new String(chars);
			});
		}
	};

	/**
	 * Converts selected text to lower case.
	 * */
	private final Action toLowercase = new LocalizableAction("lower", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			transformCase(text -> {
				char[] chars = text.toCharArray();
				for (int i = 0; i < chars.length; i++) {
					chars[i] = Character.toLowerCase(chars[i]);
				}
				return new String(chars);
			});
		}
	};

	/**
	 * Invert selected text to upper or lower case.
	 * */
	private final Action invertcase = new LocalizableAction("inv", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			transformCase(text -> {
				char[] chars = text.toCharArray();
				for (int i = 0; i < chars.length; i++) {
					if (Character.isUpperCase(chars[i])) {
						chars[i] = Character.toLowerCase(chars[i]);
					} else if (Character.isLowerCase(chars[i])) {
						chars[i] = Character.toUpperCase(chars[i]);
					}
				}
				return new String(chars);
			});
		}
	};

	/**
	 * Transform text depending on func. Used by toUppercase, toLowercase and inverseCase.
	 * 
	 * @param func Depends on caller.
	 * */
	private void transformCase(Function<String, String> func) {
		JTextArea editor = tabbedPane.getCurrentDocument().getTextComponent();
		int start = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());
		int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());

		if (len < 1)
			return;

		Document doc = editor.getDocument();

		try {
			String text = doc.getText(start, len);
			text = func.apply(text);

			doc.remove(start, len);
			doc.insertString(start, text, null);
		} catch (BadLocationException e1) {
		}
	}

	/**
	 * Sort ascending action.
	 * */
	private final Action ascending = new LocalizableAction("asc", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			sortSelected((s1, s2) -> {
				Locale hrLocale = new Locale("hr");
				Collator hrCollator = Collator.getInstance(hrLocale);
				return hrCollator.compare(s1, s2);
			});
		}
	};

	/**
	 * Sort descending action.
	 * */
	private final Action descending = new LocalizableAction("des", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			sortSelected((s1, s2) -> {
				Locale hrLocale = new Locale("hr");
				Collator hrCollator = Collator.getInstance(hrLocale);
				return hrCollator.compare(s2, s1);
			});
		}
	};

	/**
	 * Sorts selected lines by comparator comp.
	 * 
	 * @param comp The comparator that controls comparation.
	 * */
	private void sortSelected(Comparator<String> comp) {
		transformLines((text) -> {

			int numberOfLines = text.length() - text.replaceAll("\n", "").length() + 1;
			String[] lines = text.split("\n");
			List<String> linesSorted = new ArrayList<>();
			for (int i = 0; i < lines.length; i++) {
				linesSorted.add(lines[i]);
			}
			for (int i = lines.length; i < numberOfLines; i++) {
				linesSorted.add("");
			}
			linesSorted.sort(comp);
			StringBuilder sb = new StringBuilder();
			sb.append(linesSorted.get(0));
			for (int i = 1, len = linesSorted.size(); i < len; i++) {
				sb.append("\n");
				sb.append(linesSorted.get(i));
			}
			return sb.toString();
		});
	}

	/**
	 * Used for transforming lines. Depends on func.
	 * 
	 * @param func controls what will happened
	 * */
	private void transformLines(Function<String, String> func) {
		JTextArea editor = tabbedPane.getCurrentDocument().getTextComponent();
		int start = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());
		int end = Math.max(editor.getCaret().getDot(), editor.getCaret().getMark());

		try {
			start = editor.getLineStartOffset(editor.getLineOfOffset(start));
			end = editor.getLineEndOffset(editor.getLineOfOffset(end));

			Document doc = editor.getDocument();

			String text = doc.getText(start, end - start);
			text = func.apply(text);

			doc.remove(start, end - start);
			doc.insertString(start, text, null);

		} catch (BadLocationException e) {
		}
	}

	/**
	 * Unique action makes selected rows unique. Deletes all repeated lines.
	 * */
	private final Action unique = new LocalizableAction("unique", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			transformLines(text -> {
				String[] lines = text.split("\n");
				List<String> linesList = new ArrayList<>();
				for (int i = 0; i < lines.length; i++) {
					if (linesList.indexOf(lines[i]) == -1)
						linesList.add(lines[i]);
				}
				StringBuilder sb = new StringBuilder();
				sb.append(linesList.get(0));
				for (int i = 1, len = linesList.size(); i < len; i++) {
					sb.append("\n");
					sb.append(linesList.get(i));
				}
				return sb.toString();
			});
		}
	};
	
	/**
	 * Action that sets Croatian language
	 * */
	private final Action hr = new LocalizableAction("hr", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("hr");
		}
	};
	
	/**
	 * Action that sets English language
	 * */
	private final Action en = new LocalizableAction("en", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("en");
		}
	};
	
	/**
	 * Action that sets German language
	 * */
	private final Action de = new LocalizableAction("de", flp) {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("de");
		}
	};

	/**
	 * Adds listener to tabbedPane and updates caretListener.
	 * */
	private void addListener() {
		tabbedPane.addMultipleDocumentListener(new MultipleDocumentListener() {

			@Override
			public void documentRemoved(SingleDocumentModel model) {
			}

			@Override
			public void documentAdded(SingleDocumentModel model) {
			}

			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				if (previousModel != null) {
					previousModel.getTextComponent().getCaret().removeChangeListener(caretListener);
				}
				if (currentModel == null) {
					setTitle("JNotepad++");
					return;
				}
				setTitle(pathAsString(currentModel) + " - JNotepad++");

				caretListener = new ChangeListener() {

					@Override
					public void stateChanged(ChangeEvent e) {
						calculateStatusBar();
						JTextArea editor = tabbedPane.getCurrentDocument().getTextComponent();
						setEnabledSelected(editor.getCaret().getDot() != editor.getCaret().getMark());
					}
				};
				currentModel.getTextComponent().getCaret().addChangeListener(caretListener);
				calculateStatusBar();
				setEnabledSelected(false);
			}

		});
	}

	/**
	 * Sets enabled or disabled all parts that depends on selected text.
	 * */
	private void setEnabledSelected(boolean selected) {
		toUppercase.setEnabled(selected);
		toLowercase.setEnabled(selected);
		invertcase.setEnabled(selected);
		ascending.setEnabled(selected);
		descending.setEnabled(selected);
		unique.setEnabled(selected);
	}
	
	/**
	 * Calculates and updates statusBar.
	 * */
	private void calculateStatusBar() {
		if (tabbedPane.getCurrentDocument() == null) {
			return;
		}
		JTextArea text = tabbedPane.getCurrentDocument().getTextComponent();

		leftLabel.setText(" length:" + text.getText().length());

		int position = text.getCaret().getDot();

		String substring = text.getText().substring(0, position);

		int ln = substring.length() - substring.replaceAll("\n", "").length() + 1;
		int col = substring.length() - substring.lastIndexOf('\n');
		int sel = Math.abs(text.getCaret().getDot() - text.getCaret().getMark());

		rightLabel.setText(" Ln:" + ln + "  Col:" + col + "  Sel:" + sel);
	}

	/**
	 * Goes through all opened tabs and begins to closing them.
	 * 
	 * @return true if needs to finish closing application, or false to abort.
	 * */
	private boolean startClosingProcess() {
		for (SingleDocumentModel model : tabbedPane) {
			if (!closeTab(model)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns path as string. If path is null returns "(unnamed)".
	 * 
	 * @param doc document of searching path
	 * @return path as String or "(unnamed)"
	 * */
	private static String pathAsString(SingleDocumentModel doc) {
		if (doc.getFilePath() == null) {
			return "(unnamed)";
		}
		return doc.getFilePath().toString();
	}

	/**
	 * Checks if current document is null. Used by actions that mustn't have null JTextArea.
	 * 
	 * @param operation Name of operation.
	 * */
	private boolean checkCurrentNull(String operation) {
		if (tabbedPane.getCurrentDocument() == null) {
			JOptionPane.showMessageDialog(JNotepadPP.this,
					"JNotepad++ has no documents. Operation '" + operation + "' is not possible.", "Information",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}
		return false;
	}

	/**
	 * Main method starts program.
	 */
	public static void main(String[] args) {

		SwingUtilities.invokeLater(() -> {
			new JNotepadPP().setVisible(true);
		});
	}
}
