package searching.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import searching.algorithms.Node;
import searching.algorithms.SearchUtil;
import searching.slagalica.KonfiguracijaSlagalice;
import searching.slagalica.Slagalica;
import searching.slagalica.gui.SlagalicaViewer;

/**
 * Demonstration of {@link Slagalica}. Uses one argument as start state of Slagalica.
 * 
 * Example of expected input: 456781230 
 * */
public class SlagalicaMain{
	
	/**
	 * Length of input
	 * */
	private static final int LENGTH = 9;
	
	/**
	 * Main method expects only one argument.
	 * */
	public static void main(String[] args) {

		if(args.length != 1) {
			System.out.println("Očekivan je samo jedan argument.");
			return;
		}
		
		if(args[0].length() != LENGTH) {
			System.out.println("Očekivano je 9 znamenaka.");
			return;
		}
		int[] array = new int[LENGTH];

		for(int i = 0; i < LENGTH; ++i) {
			if(!Character.isDigit(args[0].charAt(i))) {
				System.out.println(i +"ti simbol nije znamenka.");
				return;
			}
			array[i] = args[0].charAt(i)-'0';
		}
		for(int i = 0; i <LENGTH; ++i) {
			for(int j = i+1; j<LENGTH;++j) {
				if(array[i]== array[j]) {
					System.out.println("Očekivano je 9 različitih znamenki: 0-8.");
					return;
				}
			}
		}
		
		Slagalica slagalica = new Slagalica(new KonfiguracijaSlagalice(array));
		Node<KonfiguracijaSlagalice> rješenje = SearchUtil.bfsv(slagalica, slagalica, slagalica);

		if (rješenje == null) {
			System.out.println("Nisam uspio pronaći rješenje.");
		} else {
			System.out.println("Imam rješenje. Broj poteza je: " + rješenje.getCost());
			List<KonfiguracijaSlagalice> lista = new ArrayList<>();
			Node<KonfiguracijaSlagalice> trenutni = rješenje;
			while (trenutni != null) {
				lista.add(trenutni.getState());
				trenutni = trenutni.getParent();
			}
			Collections.reverse(lista);
			lista.stream().forEach(k -> {
				System.out.println(k);
				System.out.println();
			});
			SlagalicaViewer.display(rješenje);
		}
	}
}