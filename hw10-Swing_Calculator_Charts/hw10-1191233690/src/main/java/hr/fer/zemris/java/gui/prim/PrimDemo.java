package hr.fer.zemris.java.gui.prim;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.ListDataListener;
import java.util.List;
import java.util.ArrayList;
import java.awt.Container;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.event.ListDataEvent;

/**
 * This program opens window that has button. Every time u click it u get new
 * prim number.
 * 
 * @author Jelić, Nikola
 */
public class PrimDemo extends JFrame {

	/**
	 * Serial version ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor sets start position of window.
	 */
	public PrimDemo() {
		super();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("PrimDemo");
		setLocation(20, 20);
		setSize(500, 200);
		initGUI();
	}

	/**
	 * Method sets initial state.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());

		PrimListModel model = new PrimListModel();
		JList<Integer> list1 = new JList<>(model);
		JList<Integer> list2 = new JList<>(model);

		JButton button = new JButton("sljedeći");

		button.addActionListener(e -> {
			model.next();
		});

		JPanel central = new JPanel(new GridLayout(1, 0));
		central.add(new JScrollPane(list1));
		central.add(new JScrollPane(list2));

		cp.add(central, BorderLayout.CENTER);
		cp.add(button, BorderLayout.PAGE_END);
	}

	/**
	 * Implementation of {@link ListModel} which calculates prim numbers.
	 */
	static class PrimListModel implements ListModel<Integer> {

		/**
		 * List of prim numbers.
		 */
		private List<Integer> elements = new ArrayList<>();
		/**
		 * List of listeners.
		 */
		private List<ListDataListener> listeners = new ArrayList<>();

		/**
		 * This method calculates prim numbers and adds it to elements. Than calls
		 * listeners.
		 */
		public void next() {
			int prim = elements.get(getSize() - 1);
			int size = getSize();

			if (prim == 1) {
				elements.add(2);
			} else if (prim == 2) {
				elements.add(3);
			} else {
				boolean flag = true;
				while (flag) {
					prim += 2;
					int i = 1;
					for (; i < size; ++i) {
						if (prim % elements.get(i) == 0) {
							break;
						}
					}
					if (i == size) {
						flag = false;
					}
				}
				elements.add(prim);
			}

			ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, size, size);
			for (ListDataListener l : listeners) {
				l.intervalAdded(event);
			}
		}

		/**
		 * Constructor sets number 1 to elements.
		 */
		public PrimListModel() {
			elements.add(1);
		}

		@Override
		public int getSize() {
			return elements.size();
		}

		@Override
		public Integer getElementAt(int index) {
			return elements.get(index);
		}

		@Override
		public void addListDataListener(ListDataListener l) {
			listeners.add(l);
		}

		@Override
		public void removeListDataListener(ListDataListener l) {
			listeners.remove(l);
		}
	}

	/**
	 * Main method invokes window.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new PrimDemo().setVisible(true);
		});
	}
}
