package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;

import javax.swing.WindowConstants;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.ButtonGroup;
import javax.swing.JToggleButton;
import javax.swing.JScrollPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import hr.fer.zemris.java.hw17.jvdraw.colorArea.JColorArea;
import hr.fer.zemris.java.hw17.jvdraw.colorArea.JColorInfo;
import hr.fer.zemris.java.hw17.jvdraw.drawingModel.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.drawingModel.DrawingModelImpl;
import hr.fer.zemris.java.hw17.jvdraw.menus.FileMenu;
import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObjectEditor;

/**
 * Starts simple application that is used for drawing lines, circles and filled circles.
 * 
 * @author Jelić, Nikola
 */
public class JVDraw extends JFrame {

	/**
	 * Serial version identifier.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * KeyCode for DELETE.
	 */
	private static final int DEL = 127;
	/**
	 * KeyCode for '-'.
	 */
	private static final int MINUS = 109;
	/**
	 * KeyCode for '+'.
	 */
	private static final int PLUS = 107;
	/**
	 * Model of drawing.
	 */
	private DrawingModel dm;
	
	/**
	 * Default constructor.
	 */
	public JVDraw() {
		super();

		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setTitle("JVDraw");
		setLocation(100, 100);
		setSize(700, 400);
		setMinimumSize(new Dimension(700, 350));

		initGUI();
	}

	/**
	 * Initializes GUI.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());

		JPanel jp = new JPanel();

		// --- ToolBar---
		JToolBar tb = new JToolBar();
		tb.setFloatable(false);

		// ToolBar: used for picking background and foreground color of geometric object
		JColorArea fgColorProvider = new JColorArea(Color.WHITE);
		JColorArea bgColorProvider = new JColorArea(Color.WHITE);
		tb.add(fgColorProvider);
		tb.add(bgColorProvider);

		// ToolBar: buttons for adding line, circle, filled circle
		Font font = new Font("Arial", Font.PLAIN, 10);
		Dimension dimension = new Dimension(110, 15);
		JToggleButton line = new JToggleButton("add line");
		line.setFont(font);
		line.setPreferredSize(dimension);
		JToggleButton circle = new JToggleButton("add circle");
		circle.setFont(font);
		circle.setPreferredSize(dimension);
		JToggleButton filledCircle = new JToggleButton("add filled circle");
		filledCircle.setFont(font);
		filledCircle.setPreferredSize(dimension);

		ButtonGroup group = new ButtonGroup();

		group.add(line);
		group.add(circle);
		group.add(filledCircle);
		tb.add(line);
		tb.add(circle);
		tb.add(filledCircle);
		jp.add(tb);
		cp.add(jp, BorderLayout.PAGE_START);

		// --- Canvas & List ---
		JPanel central = new JPanel(new GridLayout(1, 0));

		dm = new DrawingModelImpl();
		
		JDrawingCanvas canvas = new JDrawingCanvas(dm, fgColorProvider, bgColorProvider);
		central.add(canvas);

		DrawingObjectListModel drawings = new DrawingObjectListModel(dm);
		JList<String> list = new JList<>(drawings);
		listOperations(list);
		JScrollPane jsp = new JScrollPane(list);
		jsp.setMaximumSize(new Dimension(0, 80));
		central.add(jsp);

		// listeners
		line.addActionListener(l -> {
			canvas.setType(DrawingType.LINE);
		});
		circle.addActionListener(l -> {
			canvas.setType(DrawingType.CIRCLE);
		});
		filledCircle.addActionListener(l -> {
			canvas.setType(DrawingType.FILLEDCIRCLE);
		});
		line.setSelected(true);

		cp.add(central, BorderLayout.CENTER);

		// --- Colors Info
		JColorInfo colorsInfo = new JColorInfo(fgColorProvider, bgColorProvider);
		cp.add(colorsInfo, BorderLayout.PAGE_END);

		createMenus();
	}
	

	/**
	 * Sets listeners for list.
	 * 
	 * @param list
	 */
	private void listOperations(JList<String> list) {
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				if (e.getClickCount() == 2) {

					// Double-click detected
					int index = list.locationToIndex(e.getPoint());
					GeometricalObjectEditor editor = dm.getObject(index).createGeometricalObjectEditor();
					if (JOptionPane.showConfirmDialog(JVDraw.this, editor, "Edit if you want.",
							JOptionPane.OK_OPTION) == JOptionPane.OK_OPTION) {
						try {
							editor.checkEditing();
							editor.acceptEditing();
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(JVDraw.this, ex.getMessage());
						}
					}
				}
			}
		});
		list.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {

				if (list.isSelectionEmpty()) {
					return;
				}
				int index = list.getSelectedIndex();
				if (e.getKeyCode() == DEL) {
					dm.remove(dm.getObject(index));
				} else if (e.getKeyCode() == MINUS) {
					dm.changeOrder(dm.getObject(index), 1);
				} else if (e.getKeyCode() == PLUS) {
					dm.changeOrder(dm.getObject(index), -1);
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}
		});

	}

	/**
	 * Create "File" menu.
	 */
	private void createMenus() {
		JMenuBar mb = new JMenuBar();
		
		JMenu file = new JMenu("File");
		mb.add(file);
		
		FileMenu fm = new FileMenu(dm, JVDraw.this);
		file.add(new JMenuItem(fm.getOpen()));
		file.add(new JMenuItem(fm.getSave()));
		file.add(new JMenuItem(fm.getSaveAs()));
		file.addSeparator();
		file.add(new JMenuItem(fm.getExport()));
		file.addSeparator();
		file.add(new JMenuItem(fm.getExit()));
		
		setJMenuBar(mb);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				fm.getExit().actionPerformed(null);
			}
		});
	}
	
	/**
	 * Starts program.
	 * 
	 * @param args are not needed.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new JVDraw().setVisible(true);
		});
	}

}
