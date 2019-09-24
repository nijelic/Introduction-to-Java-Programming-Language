package hr.fer.zemris.java.tecaj_13.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Model of blog comments.
 * 
 * @author Jelić, Nikola
 */
@Entity
@Table(name="blog_comments")
public class BlogComment {

	/**
	 * Id of comment.
	 */
	private Long id;
	/**
	 * BlogEntry of comment.
	 */
	private BlogEntry blogEntry;
	/**
	 * Email of comment's user.
	 */
	private String usersEMail;
	/**
	 * Text of comment.
	 */
	private String message;
	/**
	 * Time of post.
	 */
	private Date postedOn;
	
	/**
	 * Id Getter.
	 * 
	 * @return id.
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
	 * Getter of BlogEntry.
	 * 
	 * @return BlogEntry.
	 */
	@ManyToOne
	@JoinColumn(nullable=false)
	public BlogEntry getBlogEntry() {
		return blogEntry;
	}
	
	/**
	 * Setter of BlogEntry.
	 * 
	 * @param BlogEntry to be set.
	 */
	public void setBlogEntry(BlogEntry blogEntry) {
		this.blogEntry = blogEntry;
	}

	/**
	 * Getter of user's email.
	 * 
	 * @return email of user.
	 */
	@Column(length=100,nullable=false)
	public String getUsersEMail() {
		return usersEMail;
	}

	/**
	 * Setter of email.
	 * 
	 * @param usersEMail to be set.
	 */
	public void setUsersEMail(String usersEMail) {
		this.usersEMail = usersEMail;
	}

	/**
	 * Getter of message.
	 * 
	 * @return message.
	 */
	@Column(length=4096,nullable=false)
	public String getMessage() {
		return message;
	}

	/**
	 * Setter of message.
	 * 
	 * @param message to be set.
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Getter of postedOn.
	 * 
	 * @return posted time of comment.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	public Date getPostedOn() {
		return postedOn;
	}

	/**
	 * Setter of postedOn.
	 * 
	 * @param postedOn to be set.
	 */
	public void setPostedOn(Date postedOn) {
		this.postedOn = postedOn;
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
		BlogComment other = (BlogComment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}