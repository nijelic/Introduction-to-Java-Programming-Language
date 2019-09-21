package hr.fer.zemris.java.hw17.jvdraw;

import javax.swing.JComponent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;
import java.awt.Color;
import java.awt.Dimension;

import hr.fer.zemris.java.hw17.jvdraw.colorArea.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.drawingModel.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.drawingModel.DrawingModelListener;
import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObjectImpl;
import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObjectPainter;
import hr.fer.zemris.java.hw17.jvdraw.shapeButtons.Circle;
import hr.fer.zemris.java.hw17.jvdraw.shapeButtons.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.shapeButtons.ToolImpl;
import hr.fer.zemris.java.hw17.jvdraw.shapeButtons.Line;

/**
 * Canvas that provides drawing.
 * 
 * @author Jelić, Nikola
 */
public class JDrawingCanvas extends JComponent {
	/**
	 * Serial version identifier.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Model of drawing.
	 */
	private DrawingModel dm;
	/**
	 * Type of currently drawing object. Starts with line.
	 */
	private DrawingType type = DrawingType.LINE;
	/**
	 * Tool used for drawing specific object by user.
	 */
	private ToolImpl tool = null;
	/**
	 * Foreground color provider.
	 */
	private IColorProvider fgProvider;
	/**
	 * Background color provider.
	 */
	private IColorProvider bgProvider;
	
	/**
	 * Constructor sets {@link DrawingModel}, foreground and background color provider.
	 * 
	 * @param dm Model of drawing.
	 * @param fgProvider Foreground color provider.
	 * @param bgProvider Background color provider.
	 */
	public JDrawingCanvas(DrawingModel dm, IColorProvider fgProvider, IColorProvider bgProvider) {
		this.dm = Objects.requireNonNull(dm);
		this.fgProvider = fgProvider;
		this.bgProvider = bgProvider;
		initCanvas();
	}
	
	/**
	 * Initializes canvas.
	 */
	private void initCanvas() {
		
		// when DrawinModel changes image will update. 
		dm.addDrawingModelListener(new DrawingModelListener() {

			@Override
			public void objectsRemoved(DrawingModel source, int index0, int index1) {
				if (source.isModified()) {
					JDrawingCanvas.this.update(getGraphics());
				}
			}

			@Override
			public void objectsChanged(DrawingModel source, int index0, int index1) {
				if (source.isModified()) {
					JDrawingCanvas.this.update(getGraphics());
				}
			}

			@Override
			public void objectsAdded(DrawingModel source, int index0, int index1) {
				if (source.isModified()) {
					JDrawingCanvas.this.update(getGraphics());
				}
			}
		});
		
		// when user click on the Canvas image will update
		this.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				if(tool == null) {
					tool = createTool();
					tool.mousePressed(e);
				} else {
					tool.mousePressed(e);
					update(getGraphics());
					tool.paint((Graphics2D)getGraphics());
				}
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				if(tool != null) {
					tool.mouseReleased(e);
					if(tool.getEndX() != null) {
						dm.add(new GeometricalObjectImpl(tool));
						tool = null;
					}
				}
			}
			
		});
		
		// when user move mouse on the Canvas image will update
		this.addMouseMotionListener(new MouseAdapter() {
			
			@Override
			public void mouseDragged(MouseEvent e) {
				super.mouseDragged(e);
				if(tool != null) {
					tool.mouseDragged(e);
					update(getGraphics());
					tool.paint((Graphics2D)getGraphics());
				}
			}
			
			@Override
			public void mouseMoved(MouseEvent e) {
				super.mouseMoved(e);
				if(tool != null) {
					tool.mouseMoved(e);
					update(getGraphics());
					tool.paint((Graphics2D)getGraphics());
				}
			}
			
		});
		
	}
	
	/**
	 * Creates tool depending on type.
	 * 
	 * @return created tool.
	 */
	private ToolImpl createTool() {
		if(type == DrawingType.CIRCLE) {
			return new Circle(fgProvider);
		}
		else if(type == DrawingType.FILLEDCIRCLE) {
			return new FilledCircle(fgProvider, bgProvider);
		}
		else {
			return new Line(fgProvider);
		}
	}
	
	/**
	 * Type setter.
	 * 
	 * @param type to be set.
	 */
	public void setType(DrawingType type) {
		this.type = type;
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(400, 400);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		GeometricalObjectPainter painter = new GeometricalObjectPainter(g);
		Dimension dim = getSize();

		int height = dim.height;
		int width = dim.width;

		g.setColor(Color.white);
		g.fillRect(0, 0, width, height);

		int size = dm.getSize();
		for (int index = 0; index < size; index++) {
			dm.getObject(index).accept(painter);
		}
	}
}
