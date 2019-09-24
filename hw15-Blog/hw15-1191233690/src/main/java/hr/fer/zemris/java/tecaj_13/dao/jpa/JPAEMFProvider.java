package hr.fer.zemris.java.tecaj_13.dao.jpa;

import javax.persistence.EntityManagerFactory;

/**
 * Provider of JPA EntriryManagerFactory.
 * 
 * @author Jelić, Nikola
 */
public class JPAEMFProvider {

	/**
	 * Entity manager factory
	 */
	public static EntityManagerFactory emf;
	
	/**
	 * Getter of EntityManagerFactory.
	 * 
	 * @return EntityManagerFactory.
	 */
	public static EntityManagerFactory getEmf() {
		return emf;
	}
	
	/**
	 * Setter of EntityManagerFactory.
	 * 
	 * @param emf sets EntityManagerFactory.
	 */
	public static void setEmf(EntityManagerFactory emf) {
		JPAEMFProvider.emf = emf;
	}
}