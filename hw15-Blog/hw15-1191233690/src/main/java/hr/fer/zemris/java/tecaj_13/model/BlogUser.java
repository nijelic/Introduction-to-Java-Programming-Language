package hr.fer.zemris.java.tecaj_13.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Model of blog users.
 * 
 * @author Jelić, Nikola
 */
@NamedQueries({
	@NamedQuery(name="BlogUser.nicks",query="select u.nick from BlogUser as u"),
	@NamedQuery(name="BlogUser.user",query="select u from BlogUser as u where u.nick=:ni")
})
@Entity
@Table(name="blog_users")
public class BlogUser {
	
	/**
	 * Id of user.
	 */
	private Long id;
	/**
	 * Email of user.
	 */
	private String email;
	/**
	 * Last name of user.
	 */
	private String lastName;
	/**
	 * Nick name of user.
	 */
	private String nick;
	/**
	 * First name of user.
	 */
	private String firstName;
	/**
	 * Hash of password of user.
	 */
	private String passwordHash;
	/**
	 * Entries of user.
	 */
	private List<BlogEntry> entries = new ArrayList<>();

	/**
	 * Getter of user's id.
	 * 
	 * @return id of user.
	 */
	@Id @GeneratedValue
	public Long getId() {
		return id;
	}

	/**
	 * Setter of id.
	 * 
	 * @param id to be set.
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * Getter of user's email.
	 * 
	 * @return email of user.
	 */
	@Column(length=100,nullable=false)
	public String getEmail() {
		return email;
	}

	/**
	 * Setter of email.
	 * 
	 * @param email to be set.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Getter of user's lastName.
	 * 
	 * @return last name of user.
	 */
	@Column(length=50,nullable=false)
	public String getLastName() {
		return lastName;
	}

	/**
	 * Setter of lastName.
	 * 
	 * @param lastName to be set.
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/**
	 * Getter of user's nick.
	 * 
	 * @return nick name of user.
	 */
	@Column(length=50, nullable=false, unique=true)
	public String getNick() {
		return nick;
	}

	/**
	 * Setter of nick.
	 * 
	 * @param nick to be set.
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}
	
	/**
	 * Getter of user's firstName.
	 * 
	 * @return first name of user.
	 */
	@Column(length=50,nullable=false)
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Setter of firstName.
	 * 
	 * @param firstName to be set.
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Getter of user's passwordHash.
	 * 
	 * @return hash of password.
	 */
	@Column(length=100,nullable=false)
	public String getPasswordHash() {
		return passwordHash;
	}

	/**
	 * Setter of passwordHash.
	 * 
	 * @param passwordHash to be set.
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	
	/**
	 * Getter of user's entries.
	 * 
	 * @return entries of user.
	 */
	@OneToMany(mappedBy="creator",fetch=FetchType.LAZY, cascade=CascadeType.PERSIST, orphanRemoval=true)
	@OrderBy("createdAt")
	public List<BlogEntry> getEntries() {
		return entries;
	}

	/**
	 * Setter of entries.
	 * 
	 * @param entries to be set.
	 */
	public void setEntries(List<BlogEntry> entries) {
		this.entries = entries;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BlogUser other = (BlogUser) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
