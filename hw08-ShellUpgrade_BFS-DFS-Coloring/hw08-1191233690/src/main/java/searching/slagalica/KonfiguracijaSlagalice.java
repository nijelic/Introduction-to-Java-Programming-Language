package searching.slagalica;

import java.util.Arrays;

/**
 * Configuration of {@link Slagalica}.
 * */
public class KonfiguracijaSlagalice {

	/**
	 * Array of setup of {@link Slagalica}
	 * */
	private int[] polje;

	/**
	 * Constructor sets polje.
	 * @param polje
	 * */
	public KonfiguracijaSlagalice(int[] polje) {
		super();
		this.polje = polje;
	}

	/**
	 * Polje getter.
	 * @return clone of polje.
	 * */
	public int[] getPolje() {
		return polje.clone();
	}
	
	/**
	 * Returns index of empty space.
	 * @return index of polje equlas to 0
	 * */
	public int indexOfSpace() {
		for(int i = 0; i < 9; ++i) {
			if(polje[i] == 0) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < polje.length; ++i) {
			if(polje[i] == 0) {
				sb.append('*');
			} else {
				sb.append(polje[i]);	
			}
			if(i%3 < 2) {
				sb.append(" ");
			} else {
				sb.append("\n");
			}
		}
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(polje);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		KonfiguracijaSlagalice other = (KonfiguracijaSlagalice) obj;
		return Arrays.equals(polje, other.polje);
	}
	
	
}
