package hr.fer.zemris.java.tecaj_13.dao;

import hr.fer.zemris.java.tecaj_13.dao.jpa.JPADAOImpl;

/**
 * Provider of DAO.
 * 
 * @author Jelić, Nikola
 */
public class DAOProvider {

	/**
	 * Initializes dao.
	 */
	private static DAO dao = new JPADAOImpl();
	
	/**
	 * DAO getter.
	 * 
	 * @return dao.
	 */
	public static DAO getDAO() {
		return dao;
	}
	
}