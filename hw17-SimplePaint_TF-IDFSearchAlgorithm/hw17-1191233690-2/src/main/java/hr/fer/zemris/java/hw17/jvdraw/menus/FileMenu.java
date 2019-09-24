package hr.fer.zemris.java.hw17.jvdraw.menus;

import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.imageio.ImageIO;
import java.nio.file.Path;
import java.nio.file.Files;
import java.io.File;
import java.io.IOException;
import java.util.List;

import hr.fer.zemris.java.hw17.jvdraw.JVDraw;
import hr.fer.zemris.java.hw17.jvdraw.drawingModel.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObjectImpl;
import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObjectPainter;
import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObjectVisitor;
import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObjectBBCalculator;
import hr.fer.zemris.java.hw17.jvdraw.shapeButtons.Line;
import hr.fer.zemris.java.hw17.jvdraw.shapeButtons.Circle;
import hr.fer.zemris.java.hw17.jvdraw.shapeButtons.FilledCircle;

/**
 * This class creates all actions within "file".
 * 
 * @author Jelić, Nikola
 */
public class FileMenu {

	/**
	 * Save action.
	 */
	private Action save;
	/**
	 * SaveAs action.
	 */
	private Action saveAs;
	/**
	 * Open action.
	 */
	private Action open;
	/**
	 * Exit action.
	 */
	private Action exit;
	/**
	 * Export action.
	 */
	private Action export;
	/**
	 * Path of last saving.
	 */
	private Path filepath;
	/**
	 * Used as JFrame.
	 */
	private JVDraw jvd;
	/**
	 * Drawing model.
	 */
	private DrawingModel dm;
	
	
	/**
	 * Constructor sets {@link DrawingModel} and {@link JVDraw}.
	 * 
	 * @param dm DrawingModel
	 * @param jvd JVDraw
	 */
	public FileMenu(DrawingModel dm, JVDraw jvd) {
		super();
		this.dm = dm;
		this.jvd = jvd;

		initActions();
	}
	
	/**
	 * Getter of save action.
	 * 
	 * @return save action.
	 */
	public Action getSave() {
		return save;
	}


	/**
	 * Getter of saveAs action.
	 * 
	 * @return saveAs action.
	 */
	public Action getSaveAs() {
		return saveAs;
	}


	/**
	 * Getter of open action.
	 * 
	 * @return open action.
	 */
	public Action getOpen() {
		return open;
	}


	/**
	 * Getter of exit action.
	 * 
	 * @return exit action.
	 */
	public Action getExit() {
		return exit;
	}


	/**
	 * Getter of export action.
	 * 
	 * @return export action.
	 */
	public Action getExport() {
		return export;
	}

	/**
	 * Initialization of all Actions.
	 */
	private void initActions() {

		save = new AbstractAction() {
			/**
			 * Serial version identifier.
			 */
			private static final long serialVersionUID = 1L;
			
			@Override
			public void actionPerformed(ActionEvent e) {
				save();
				dm.clearModifiedFlag();
			}
		};
		save.putValue(Action.NAME, "save");
		save.putValue(Action.SHORT_DESCRIPTION, "Saves file on disk.");
		
		saveAs = new AbstractAction() {
			/**
			 * Serial version identifier.
			 */
			private static final long serialVersionUID = 1L;
			
			@Override
			public void actionPerformed(ActionEvent e) {
				saveAs();
				dm.clearModifiedFlag();
			}
		};
		saveAs.putValue(Action.NAME, "saveAs...");
		saveAs.putValue(Action.SHORT_DESCRIPTION, "Saves file on disk. Asks user where to save.");

		open = new AbstractAction() {
			/**
			 * Serial version identifier.
			 */
			private static final long serialVersionUID = 1L;
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setDialogTitle("Open file");
				if (jfc.showOpenDialog(jvd) != JFileChooser.APPROVE_OPTION) {
					return;
				}

				Path path = jfc.getSelectedFile().toPath();
				if (!Files.isReadable(path)) {
					JOptionPane.showMessageDialog(jvd, "File " + path + " is not readable.", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				if(checkExtension(path)) {
					return;
				}
				

				List<String> allLines = null;
				try {
					allLines = Files.readAllLines(path);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(jvd, "Error occured while reading file " + path + ".", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				filepath = path;

				updateDrawingModel(allLines);
			}
		};
		open.putValue(Action.NAME, "open");
		open.putValue(Action.SHORT_DESCRIPTION, "Opens file from disk.");
		
		exit = new AbstractAction() {
			/**
			 * Serial version identifier.
			 */
			private static final long serialVersionUID = 1L;
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(dm.isModified()) {
					int ans = JOptionPane.showConfirmDialog(jvd, "Do you want to save before exiting?", "Confirmation.",
							JOptionPane.YES_NO_CANCEL_OPTION);
					
					if (ans == JOptionPane.YES_OPTION) {
						save();
					} else if(ans == JOptionPane.NO_OPTION) {
					} else {
						return;
					}
					jvd.dispose();
				} else {
					jvd.dispose();
				}
			}
		};
		exit.putValue(Action.NAME, "exit");
		exit.putValue(Action.SHORT_DESCRIPTION, "Exits file.");
		
		export = new AbstractAction() {
			/**
			 * Serial version identifier.
			 */
			private static final long serialVersionUID = 1L;
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setDialogTitle("Export document");
				jfc.setFileFilter(new FileFilter() {
					
					@Override
					public String getDescription() {
						return "JPG, PNG and GIF";
					}
					
					@Override
					public boolean accept(File file) {
						return file.getName().toUpperCase().equals(".JPG")
								|| file.getName().toUpperCase().equals(".PNG")
								|| file.getName().toUpperCase().equals(".GIF");
					}
				});
				
				if (jfc.showSaveDialog(jvd) != JFileChooser.APPROVE_OPTION) {
					JOptionPane.showMessageDialog(jvd, "Not saved.", "Information",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				
				Path path = jfc.getSelectedFile().toPath();
				String extension = getValidExtension(path);
				if(extension == null) {
					return;
				}
				
				if (Files.exists(path)) {
					int ans = JOptionPane.showConfirmDialog(jvd,
							path.getFileName().toString() + " exists. Would you like to override it?", "Confirmation.",
							JOptionPane.YES_NO_OPTION);
					if (ans == JOptionPane.NO_OPTION) {
						return;
					}
				}
				exportImage(path);
			}
			/**
			 * Returns null if extension is invalid, else return extension.
			 * 
			 * @param path whose extension is searched.
			 * @return extension.
			 */
			private String getValidExtension(Path path) {
				String extension = path.toString();
				extension = extension.substring(extension.lastIndexOf(".")+1);
				if(!extension.toUpperCase().equals("JPG") 
						&& !extension.toUpperCase().equals("PNG") 
						&& !extension.toUpperCase().equals("GIF"))
				{
					return null;
				}
				return extension;
			}
			
			/**
			 * Exports image to given path.
			 * 
			 * @param path where to export image.
			 */
			private void exportImage(Path path) {
				int size = dm.getSize();
				GeometricalObjectBBCalculator bbcalc = new GeometricalObjectBBCalculator();
				for(int index = 0; index < size; index++) {
				 dm.getObject(index).accept(bbcalc);
				}
				
				Rectangle boundingBox = bbcalc.getBoundingBox();
				BufferedImage image = new BufferedImage(
						boundingBox.width, boundingBox.height, BufferedImage.TYPE_3BYTE_BGR
						);
				
				Graphics2D g = image.createGraphics();
				g.translate(-boundingBox.x, -boundingBox.y);
				g.fillRect(boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
				
				GeometricalObjectPainter painter = new GeometricalObjectPainter(g);
				for(int index = 0; index < size; index++) {
					 dm.getObject(index).accept(painter);
					}
				g.dispose();
				
				try {
				File file = new File(path.toString());
				ImageIO.write(image, getValidExtension(path), file);
				JOptionPane.showMessageDialog(jvd,
						"Image is exported.", "Information.",
						JOptionPane.INFORMATION_MESSAGE);
				} catch(IOException e) {
					JOptionPane.showMessageDialog(jvd,
							"Error occured. Image is not exported.", "Confirmation.",
							JOptionPane.OK_OPTION);
				}
			}
		};
		export.putValue(Action.NAME, "export");
		export.putValue(Action.SHORT_DESCRIPTION, "Exports image as 'png', 'gif' or 'jpg'.");
	}

	
	// ----- OPENING ------
	
	/**
	 * Updates drawing model from lines.
	 * 
	 * @param allLines that defines drawing model.
	 */
	private void updateDrawingModel(List<String> allLines) {
		dm.clear();
		try {
			for (String row : allLines) {
				String[] list = row.split(" ");

				if (list[0].equals("LINE") && list.length == 8) {
					parseLine(list);
				} else if (list[0].equals("CIRCLE") && list.length == 7) {
					parseCircle(list);
				} else if (list[0].equals("FCIRCLE") && list.length == 10) {
					parseFCircle(list);
				} else {
					throw new RuntimeException("Wrong object.");
				}
			}
		} catch (RuntimeException e) {
			JOptionPane.showMessageDialog(jvd, "File is broken. "+e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			dm.clear();
			return;
		}

	}

	/**
	 * Parses data, type of line.
	 * 
	 * @param list of data.
	 */
	private void parseLine(String[] list) {
		int startX = Integer.parseInt(list[1]);
		int startY = Integer.parseInt(list[2]);
		int endX = Integer.parseInt(list[3]);
		int endY = Integer.parseInt(list[4]);

		dm.add(
				new GeometricalObjectImpl(
						new Line(
								parseColor(list[5], list[6], list[7]), 
								startX, 
								startY, 
								endX, 
								endY
								)
						)
				);
	}

	/**
	 * Parses data, type of circle.
	 * 
	 * @param list of data.
	 */
	private void parseCircle(String[] list) {
		int startX = Integer.parseInt(list[1]);
		int startY = Integer.parseInt(list[2]);
		int R = Integer.parseInt(list[3]);

		dm.add(
				new GeometricalObjectImpl(
						new Circle(
								parseColor(list[4], list[5], list[6]), 
								startX, 
								startY, 
								startX-R,
								startY
								)
						)
				);
	}
	
	/**
	 * Parses data, type of filled circle.
	 * 
	 * @param list of data.
	 */
	private void parseFCircle(String[] list) {
		int startX = Integer.parseInt(list[1]);
		int startY = Integer.parseInt(list[2]);
		int R = Integer.parseInt(list[3]);

		dm.add(
				new GeometricalObjectImpl(
						new FilledCircle(
								parseColor(list[4], list[5], list[6]), 
								parseColor(list[7], list[8], list[9]),
								startX, 
								startY, 
								startX-R,
								startY
								)
						)
				);
	}
	
	/**
	 * Parses color from strings.
	 * 
	 * @param colorR string of integer of Red.
	 * @param colorG string of integer of Green.
	 * @param colorB string of integer of Blue.
	 * @return Color.
	 */
	private Color parseColor(String colorR, String colorG, String colorB) {
		int R = Integer.parseInt(colorR);
		int G = Integer.parseInt(colorG);
		int B = Integer.parseInt(colorB);

		if (R < 0 || R > 255 || G < 0 || G > 255 || B < 0 || B > 255) {
			throw new RuntimeException("Wrong color.");
		}
		return new Color(R, G, B);
	}
	
	
	// ----- SAVING -----
	
	/**
	 * Saves data to filepath.
	 */
	private void save() {
		if (filepath == null) {
			saveAs();
			return;
		}
		saveDocument();
	}
	
	/**
	 * SaveAs to chosen file.
	 */
	private void saveAs() {
		askForPath();
		if (filepath == null) {
			return;
		}
		saveDocument();
	}

	/**
	 * Asks for path and check if already exists. If exists asks for approval.
	 */
	private void askForPath() {
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle("Save document");
		if (jfc.showSaveDialog(jvd) != JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(jvd, "Not saved.", "Information",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		Path path = jfc.getSelectedFile().toPath();
		
		if(checkExtension(path)) {
			return;
		}
		
		if (Files.exists(path)) {
			int ans = JOptionPane.showConfirmDialog(jvd,
					path.getFileName().toString() + " exists. Would you like to override it?", "Confirmation.",
					JOptionPane.YES_NO_OPTION);
			if (ans == JOptionPane.NO_OPTION) {
				return;
			}
		}
		filepath = path;
	}
	
	/**
	 * Checks whether extension is type of 'jvd'.
	 * 
	 * @param path whose extension is needed to be checked.
	 * @return true if extension is bad, else false.
	 */
	private boolean checkExtension(Path path) {
		String pathString = path.toString();
		if (!pathString.substring(pathString.lastIndexOf(".") + 1).equals("jvd")) {
			JOptionPane.showMessageDialog(jvd, "File " + path + " is not type of '.jvd'.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return true;
		}
		return false;
	}
	
	/**
	 * Saves data to filepath.
	 */
	private void saveDocument() {
		 int size = dm.getSize();
		 StringBuilder sb = new StringBuilder();
		 
		 for(int index = 0; index < size; index++) {
			 GeometricalObject go = dm.getObject(index);
			 go.accept(new GeometricalObjectVisitor() {
				
				@Override
				public void visit(FilledCircle filledCircle) {
					sb.append("FCIRCLE "
							+ filledCircle.getStartX() + " "
							+ filledCircle.getStartY() + " "
							+ filledCircle.getR() + " "
							+ colorToString(filledCircle.getFgColor()) + " "
							+ colorToString(filledCircle.getBgColor()) + "\n"
							);
				}
				
				@Override
				public void visit(Circle circle) {
					sb.append("CIRCLE "
							+ circle.getStartX() + " "
							+ circle.getStartY() + " "
							+ circle.getR() + " "
							+ colorToString(circle.getFgColor()) + "\n"
							);
				}
				
				@Override
				public void visit(Line line) {
					sb.append("LINE "
							+ line.getStartX() + " "
							+ line.getStartY() + " "
							+ line.getEndX() + " "
							+ line.getEndY() + " "
							+ colorToString(line.getFgColor()) + "\n"
							);
				}
			});
		 }
		 sb.deleteCharAt(sb.length()-1);
		 try {
			 Files.writeString(filepath, sb);
		 } catch(IOException e) {
			 JOptionPane.showMessageDialog(jvd, "Error occured. File isn't saved.", "Error",
						JOptionPane.ERROR_MESSAGE);
		 }
	}
	
	/**
	 * Converts color to string.
	 * 
	 * @param color to be converted.
	 * @return converted color.
	 */
	private String colorToString(Color color) {
		return color.getRed() + " " + color.getGreen() + " " + color.getBlue();
	}
}
