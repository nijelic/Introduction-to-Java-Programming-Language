package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager2;
import java.util.Comparator;
import java.util.function.Function;

import hr.fer.zemris.java.gui.calc.Calculator;

import java.awt.Insets;

/**
 * Implementation of custom {@link LayoutManager2} used for {@link Calculator}.
 * 
 * @author Jelić, Nikola
 * */
public class CalcLayout implements LayoutManager2 {
	/**
	 * Space between two components.
	 * */
	private int spaceBetween;
	/**
	 * Number of rows.
	 * */
	private final static int ROWS = 5;
	/**
	 * Number of columns.
	 * */
	private final static int COLS = 7;
	/**
	 * 2D array of components
	 * */
	private Component[][] components = new Component[ROWS][COLS];
	/**
	 * This is 2D array of uniform distribution.
	 * It is added to iTh height if number of height pixels is not divisible by ROWS
	 * */
	private final static int[][] UNIFORM_HEIGHT = new int[][] {
		{0,0,0,0,0},
		{0,0,1,0,0},
		{0,1,0,1,0},
		{1,0,1,0,1},
		{1,1,1,0,1}		
	};
	/**
	 * This is 2D array of uniform distribution.
	 * It is added to iTh width if number of width pixels is not divisible by COLS
	 * */
	private final static int[][] UNIFORM_WIDTH = new int[][] {
		{0,0,0,0,0,0,0},
		{0,0,0,1,0,0,0},
		{0,1,0,0,0,1,0},
		{0,1,0,1,0,1,0},
		{1,0,1,0,1,0,1},
		{1,0,1,1,1,0,1},
		{1,1,1,1,1,0,1},
	};
	
	/**
	 * Default constructor sets spaceBetween to zero.
	 * */
	public CalcLayout() {
		this(0);
	}

	/**
	 * Constructor that sets spaceBetween.
	 * 
	 * @param spaceBetween 
	 * @throws CalcLayoutException if spaceBetween is negative
	 * */
	public CalcLayout(int spaceBetween) {
		super();
		if(spaceBetween<0) {
			throw new CalcLayoutException("Spaces between components must not be negative integer.");
		}
		this.spaceBetween = spaceBetween;
	}

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		if(constraints instanceof String) {
			String constrain = (String)constraints;
			constrain = constrain.replace(" ", "");
			int index = constrain.indexOf(',');
			if(index < 1) {
				throw new CalcLayoutException("Invalid string constraints.");
			}
			try {
				int row = Integer.parseInt(constrain.substring(0,index));
				int col = Integer.parseInt(constrain.substring(index+1));
				addComponent(comp, row, col);
			} catch(NumberFormatException e) {
				throw new CalcLayoutException("Unparsable integers");
			} catch(Exception e) {
				throw new CalcLayoutException(e);
			}
		} else if (constraints instanceof RCPosition) {
			RCPosition position = (RCPosition) constraints;
			addComponent(comp, position.getRow(), position.getColumn());
		}
	}

	/**
	 * This method makes validation and if possible adds component.
	 * 
	 * @param comp Component to be added
	 * @param row the row position
	 * @param col the column position 
	 * */
	private void addComponent(Component comp, int row, int col) {
		if(row < 1 || ROWS < row ) {
			throw new CalcLayoutException("Row is outside of bounds.");
		}
		if(col < 1 || COLS < col ) {
			throw new CalcLayoutException("Column is outside of bounds.");
		}
		if(row==1 && 1<col && col<6) {
			throw new CalcLayoutException("Column is outside of bounds. Because first row has only three elements.");
		}
		if(components[row-1][col-1] != null) {
			throw new CalcLayoutException("It is not possible to add component. Some component exist at position: "+ row+","+col);
		}
		components[row-1][col-1] = comp;
	}
	
	@Override
	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException();
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		// DOES NOTHING
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		// DOES NOTHING
		return 0;
	}

	@Override
	public void invalidateLayout(Container target) {
		// DOES NOTHING
	}

	@Override
	public void layoutContainer(Container parent) {
		synchronized (parent.getTreeLock()) {
			if(parent.getSize().height<0) {
				throw new CalcLayoutException("Height is negative");
			}
			if(parent.getSize().width<0) {
				throw new CalcLayoutException("Width is negative");
			}
	        Insets insets = parent.getInsets();
	        int top = insets.top;
	        int bottom = parent.getSize().height - insets.bottom;
	        int left = insets.left;
	        int right = parent.getSize().width - insets.right;
	        if(bottom<0 || right<0) {
				return;
	        	// throw new CalcLayoutException("It is impossible to calculate position of elements. Screen is too small.");
			}
	        int height = bottom - top;
	        int width = right - left;
	        int heightPerComponent = (height-(ROWS-1)*spaceBetween)/ROWS;
	        int widthPerComponent = (width-(COLS-1)*spaceBetween)/COLS;
	        int heightResidual = (height-(ROWS-1)*spaceBetween) - heightPerComponent*ROWS;
	        int widthResidual = (width-(COLS-1)*spaceBetween)- widthPerComponent*COLS;
	        if(height<0 || width<0 || heightPerComponent<0 || widthPerComponent<0 || heightResidual<0 || widthResidual<0) {
				return;
	        	// throw new CalcLayoutException("It is impossible to calculate position of elements. Screen is too small.");
			}
	        int x = left;
	        int y = top;
	        int widthPosition = x+4*spaceBetween+5*widthPerComponent;
	        for(int i=0; i < 5; ++i) {
	        	widthPosition += UNIFORM_WIDTH[widthResidual][i];
	        }
	        if(components[0][0]!=null) {
				components[0][0].setBounds(x, y, widthPosition,  heightPerComponent+UNIFORM_HEIGHT[heightResidual][0]);
			}
	        x = widthPosition + spaceBetween;
	        
			if(components[0][5]!=null) {
				components[0][5].setBounds(x, y, widthPerComponent + UNIFORM_WIDTH[widthResidual][5], heightPerComponent+UNIFORM_HEIGHT[heightResidual][0]);		
			}
			x += widthPerComponent + UNIFORM_WIDTH[widthResidual][5] + spaceBetween;
			if(components[0][6]!=null) {
				components[0][6].setBounds(x, y, widthPerComponent + UNIFORM_WIDTH[widthResidual][6], heightPerComponent+UNIFORM_HEIGHT[heightResidual][0]);
			}
			y += heightPerComponent+UNIFORM_HEIGHT[heightResidual][0]+spaceBetween;
			for(int i=1; i<ROWS; ++i) {
				x = left;
				for(int j = 0; j<COLS; ++j) {
					if(components[i][j]!=null) {
						components[i][j].setBounds(x, y, 
								widthPerComponent + UNIFORM_WIDTH[widthResidual][j], 
								heightPerComponent+UNIFORM_HEIGHT[heightResidual][i]);
					}
					x += widthPerComponent + UNIFORM_WIDTH[widthResidual][j] + spaceBetween;
				}
				y += heightPerComponent+UNIFORM_HEIGHT[heightResidual][i]+spaceBetween;
			}
	      }
	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		return layoutSize(target, (a, b) -> a-b, (a)->a.getMaximumSize());
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return layoutSize(parent, (a, b) -> b-a, (a)->a.getMinimumSize());
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return layoutSize(parent, (a, b) -> b-a, (a)->a.getPreferredSize());
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		if(components[0][0]==comp) {
			components[0][0] = null;
			return;
		}
		if(components[0][5]==comp) {
			components[0][5] = null;
			return;
		}
		if(components[0][6]==comp) {
			components[0][6] = null;
			return;
		}
		for(int i=1; i<ROWS; ++i) {
			for(int j = 0; j<COLS; ++j) {
				if(components[i][j] == comp) {
					components[i][j] = null;
					return;
				}
			}
		}
	}
	
	/**
	 * This method calculates minimum, maximum or preferred layoutSize depending on comparator and function.
	 * 
	 * @param target Container used for calculating insets.
	 * @param c Comparator depends on type of layoutSize
	 * @param f Function that depends on type of layoutSize
	 * 
	 * @return Dimension of layout
	 * */
	private Dimension layoutSize(Container target, Comparator<Integer> c, Function<Component, Dimension> f) {
		int width = 0;
		int height = 0;
		boolean first = true;
		// first row
		if(components[0][0]!=null && f.apply(components[0][0])!=null) {
			if(first) {
				width = (f.apply(components[0][0]).width - 4*spaceBetween)/5;
				height = f.apply(components[0][0]).height;
				first=false;
			}
		}
		if(components[0][5]!=null && f.apply(components[0][5])!=null) {
			if(first) {
				width = f.apply(components[0][5]).width;
				height = f.apply(components[0][5]).height;
				first=false;
			} else {
				if(c.compare(width, f.apply(components[0][5]).width) > 0) {
					width = f.apply(components[0][5]).width;
				}
				if(c.compare(height, f.apply(components[0][5]).height) > 0) {
					height = f.apply(components[0][5]).height;
				}
			}
		}
		if(components[0][6]!=null && f.apply(components[0][6])!=null) {
			if(first) {
				width = f.apply(components[0][6]).width;
				height = f.apply(components[0][6]).height;
				first=false;
			} else {
				if(c.compare(width, f.apply(components[0][6]).width) > 0) {
					width = f.apply(components[0][6]).width;
				}
				if(c.compare(height, f.apply(components[0][6]).height) > 0) {
					height = f.apply(components[0][6]).height;
				}
			}
		}
		
		// other rows
		for(int i=1; i<ROWS; ++i) {
			for(int j = 0; j<COLS; ++j) {
				if(components[i][j] != null && f.apply(components[i][j])!=null) {
					if(first) {
						width = f.apply(components[i][j]).width;
						height = f.apply(components[i][j]).height;
						first=false;
					} else {
						if(c.compare(width, f.apply(components[i][j]).width) > 0) {
							width = f.apply(components[i][j]).width;
						}
						if(c.compare(height, f.apply(components[i][j]).height) > 0) {
							height = f.apply(components[i][j]).height;
						}
					}
				}
			}
		}
		
		// final calculation
		Dimension dim = new Dimension(width*COLS+(COLS-1)*spaceBetween, height*ROWS+(ROWS-1)*spaceBetween);
		Insets insets = target.getInsets();
		dim.width += insets.left + insets.right;
		dim.height += insets.top + insets.bottom;
		return dim;
	}
}
