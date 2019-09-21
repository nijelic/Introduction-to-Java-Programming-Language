package hr.fer.zemris.java.gui.layouts;

/**
 * Used for saving position of component for {@link CalcLayout}.
 * 
 * @author Jelić, Nikola
 */
public class RCPosition {

	/**
	 * number of row of the position
	 */
	private int row;
	/**
	 * number of column of the position
	 */
	private int column;

	/**
	 * Constructor sets private fields.
	 * 
	 * @param row    number of row of the position
	 * @param column number of column of the position
	 */
	public RCPosition(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}

	/**
	 * Getter of row.
	 * 
	 * @return row as int.
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Getter of column.
	 * 
	 * @return column as int.
	 */
	public int getColumn() {
		return column;
	}
}
