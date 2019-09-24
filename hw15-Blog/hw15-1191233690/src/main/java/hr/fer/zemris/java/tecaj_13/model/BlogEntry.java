package hr.fer.zemris.java.tecaj_13.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Model of BlogEntry. Each active user can post new BlogEntry.
 * 
 * @author Jelić, Nikola
 */
@NamedQueries({
	@NamedQuery(name="BlogEntry.byBU",query="select b from BlogEntry as b where b.creator=:bu"),
	@NamedQuery(name="BlogEntry.byID",query="select b from BlogEntry as b where b.id=:id")
})
@Entity
@Table(name="blog_entries")
@Cacheable(true)
public class BlogEntry {

	/**
	 * Id of BlogEntry.
	 */
	private Long id;
	/**
	 * Comments of entry.
	 */
	private List<BlogComment> comments = new ArrayList<>();
	/**
	 * Time when entry was created.
	 */
	private Date createdAt;
	/**
	 * Time when entry was last modified.
	 */
	private Date lastModifiedAt;
	/**
	 * Title of entry.
	 */
	private String title;
	/**
	 * Text of entry.
	 */
	private String text;
	/**
	 * Active user who created entry.
	 */
	private BlogUser creator;
	
	/**
	 * Getter of id.
	 * 
	 * @return id of entry.
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
	 * Getter of comments.
	 * 
	 * @return comments of entry.
	 */
	@OneToMany(mappedBy="blogEntry",fetch=FetchType.LAZY, cascade=CascadeType.PERSIST, orphanRemoval=true)
	@OrderBy("postedOn")
	public List<BlogComment> getComments() {
		return comments;
	}
	
	/**
	 * Setter of comments.
	 * 
	 * @param comments to be set.
	 */
	public void setComments(List<BlogComment> comments) {
		this.comments = comments;
	}

	/**
	 * Getter of createdAt.
	 * 
	 * @return createdAt of entry.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * Setter of createdAt.
	 * 
	 * @param createdAt to be set.
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * Getter of lastModifiedAt.
	 * 
	 * @return lastModifiedAt of entry.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=true)
	public Date getLastModifiedAt() {
		return lastModifiedAt;
	}

	/**
	 * Setter of lastModifiedAt.
	 * 
	 * @param lastModifiedAt to be set.
	 */
	public void setLastModifiedAt(Date lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}

	/**
	 * Getter of title.
	 * 
	 * @return title of entry.
	 */
	@Column(length=200,nullable=false)
	public String getTitle() {
		return title;
	}

	/**
	 * Setter of title.
	 * 
	 * @param title to be set.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Getter of text.
	 * 
	 * @return text of entry.
	 */
	@Column(length=4096,nullable=false)
	public String getText() {
		return text;
	}

	/**
	 * Setter of text.
	 * 
	 * @param text to be set.
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Getter of creator.
	 * 
	 * @return creator of entry.
	 */
	@ManyToOne
	@JoinColumn(name="creator_id")
	public BlogUser getCreator() {
		return creator;
	}

	/**
	 * Setter of creator.
	 * 
	 * @param creator to be set.
	 */
	public void setCreator(BlogUser creator) {
		this.creator = creator;
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
		BlogEntry other = (BlogEntry) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}