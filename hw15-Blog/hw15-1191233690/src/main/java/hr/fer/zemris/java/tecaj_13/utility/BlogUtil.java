package hr.fer.zemris.java.tecaj_13.utility;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import hr.fer.zemris.java.tecaj_13.dao.jpa.JPAEMProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Utility class used for Blog application.
 * 
 * @author Jelić, Nikola
 */
public class BlogUtil {

	/**
	 * Calculates SHA-1 from string.
	 * @param s String used for calculation of SHA-1.
	 * @return SHA-1 hash string.
	 */
	public static String sha1(String s) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			md.update(s.getBytes(Charset.forName("UTF-8")));
			return DatatypeConverter.printHexBinary(md.digest()).toLowerCase();
		} catch(NoSuchAlgorithmException e) {
		}
		return null;
	}
	
	/**
	 * Select all entries made by nick name form database.
	 * 
	 * @param nick name used for selection.
	 * @return list of BlogEntries that are made by nick.
	 */
	public static List<BlogEntry> entriesByNick(String nick) {
		List<BlogEntry> entries = JPAEMProvider.getEntityManager().createNamedQuery("BlogEntry.byBU", BlogEntry.class)
				.setParameter("bu", userByNick(nick)).getResultList();
		return entries;
	}
	
	/**
	 * Select BlogUser of specific nick.
	 * 
	 * @param nick name used for selection.
	 * @return BlogUser that has specific nick name. Nick is unique.
	 */
	public static BlogUser userByNick(String nick) {
		List<BlogUser> user = JPAEMProvider.getEntityManager().createNamedQuery("BlogUser.user", BlogUser.class)
				.setParameter("ni", nick).getResultList();
		if(user == null || user.size()==0) return null;
		return user.get(0);
	}
	
	/**
	 * Select all entries that has specific id.
	 * 
	 * @param id used for selection.
	 * @return list of BlogEntries that has specific id.
	 */
	public static List<BlogEntry> entriesByID(Long id) {
		List<BlogEntry> entries = JPAEMProvider.getEntityManager().createNamedQuery("BlogEntry.byID", BlogEntry.class)
				.setParameter("id", id).getResultList();
		return entries;
	}
}
