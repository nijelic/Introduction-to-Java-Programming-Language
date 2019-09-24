package hr.fer.zemris.java.hw07.demo4;

/**
 * This class is used for saving data of students.
 * */
public class StudentRecord {

	/**
	 * Unique number of student.
	 * */
	private String jmbag;
	/**
	 * Last name
	 * */
	private String prezime;
	/**
	 * First name
	 * */
	private String ime;
	/**
	 * Points of first exam
	 * */
	private double bodoviKolokvij;
	/**
	 * Points of final exam
	 * */
	private double bodoviZavrsni;
	/**
	 * Points of practice
	 * */
	private double bodoviVjezbe;
	/**
	 * Grade
	 * */
	private int ocjena;

	/**
	 * Default constructor sets variables
	 * */
	public StudentRecord(String jmbag, String prezime, String ime, double bodoviKolokvij, double bodoviZavrsni,
			double bodoviVjezbe, int ocjena) {
		super();
		this.jmbag = jmbag;
		this.prezime = prezime;
		this.ime = ime;
		this.bodoviKolokvij = bodoviKolokvij;
		this.bodoviZavrsni = bodoviZavrsni;
		this.bodoviVjezbe = bodoviVjezbe;
		this.ocjena = ocjena;
	}

	/**
	 * Getter of jmbag
	 * @return jmbag
	 * */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Getter of prezime
	 * @return prezime
	 * */
	public String getPrezime() {
		return prezime;
	}

	/**
	 * Getter of ime
	 * @return ime
	 * */
	public String getIme() {
		return ime;
	}

	/**
	 * Getter of bodoviKolokvij
	 * @return bodoviKolokvij
	 * */
	public double getBodoviKolokvij() {
		return bodoviKolokvij;
	}

	/**
	 * Getter of bodoviZavrsni
	 * @return bodoviZavrsni
	 * */
	public double getBodoviZavrsni() {
		return bodoviZavrsni;
	}

	/**
	 * Getter of bodoviVjezbe
	 * @return bodoviVjezbe
	 * */
	public double getBodoviVjezbe() {
		return bodoviVjezbe;
	}

	/**
	 * Getter of ocjena
	 * @return grade integer from [1,5]
	 * */
	public int getOcjena() {
		return ocjena;
	}

	/**
	 * Returns string of StudentRecord
	 * @return string of student
	 * */
	@Override
	public String toString() {
		return jmbag + "\t" + prezime + "\t" + ime + "\t" + bodoviKolokvij + "\t" + bodoviZavrsni + "\t" + bodoviVjezbe
				+ "\t" + ocjena;
	}

}
