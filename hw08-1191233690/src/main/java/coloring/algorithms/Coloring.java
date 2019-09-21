package coloring.algorithms;

import marcupic.opjj.statespace.coloring.Picture;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Used for coloring algorithm implementations.
 * */
public class Coloring implements Supplier<Pixel>, Consumer<Pixel>, Function<Pixel, List<Pixel>>, Predicate<Pixel> {

	/**
	 * Starting position
	 * */
	private Pixel reference;
	/**
	 * Used for communication with GUI.
	 * */
	private Picture picture;
	/**
	 * Color to be filled.
	 * */
	private int fillColor;
	/**
	 * Reference color / starting color
	 * */
	private int refColor;

	/**
	 * Constructor sets all private fields.
	 * @param reference starting position
	 * @param picture used for communication with GUI.
	 * @param fillColor color which will be used
	 * */
	public Coloring(Pixel reference, Picture picture, int fillColor) {
		super();
		this.reference = reference;
		this.picture = picture;
		this.fillColor = fillColor;
		this.refColor = picture.getPixelColor(reference.x, reference.y);
	}

	@Override
	public void accept(Pixel t) {
		picture.setPixelColor(t.x, t.y, fillColor);
	}

	@Override
	public List<Pixel> apply(Pixel t) {
		List<Pixel> list = new ArrayList<>();

		if (t.x + 1 < picture.getWidth()) {
			list.add(new Pixel(t.x + 1, t.y));
		}
		if (t.x - 1 >= 0) {
			list.add(new Pixel(t.x - 1, t.y));
		}
		if (t.y + 1 < picture.getHeight()) {
			list.add(new Pixel(t.x, t.y + 1));
		}
		if (t.y - 1 >= 0) {
			list.add(new Pixel(t.x, t.y - 1));
		}
		
		// list of successors
		return list;
	}
	
	@Override
	public Pixel get() {
		return reference;
	}
	
	@Override
	public boolean test(Pixel t) {
		return picture.getPixelColor(t.x, t.y) == refColor;
	}

}
