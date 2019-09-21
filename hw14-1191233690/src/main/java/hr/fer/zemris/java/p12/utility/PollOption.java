package hr.fer.zemris.java.p12.utility;

import java.util.Objects;

/**
 * Used for saving one option.
 * 
 * @author Jelić, Nikola
 */
public class PollOption implements Comparable<PollOption>{

	/**
	 * title of option
	 */
	private String title;
	/**
	 * link to address of option
	 */
	private String link;
	/**
	 * database id of option
	 */
	private long id;
	/**
	 * pollId of option
	 */
	private long pollID;
	/**
	 * number of votes of option
	 */
	private long votesCount;
	
	/**
	 * Constructor that sets all fields.
	 * 
	 * @param id database id of option
	 * @param title title of option
	 * @param link link to address of option
	 * @param pollID pollId of option
	 * @param votesCount number of votes of option
	 */
	public PollOption(long id, String title, String link, long pollID, long votesCount) {
		super();
		this.id = id;
		this.title = title;
		this.link = link;
		this.pollID = pollID;
		this.votesCount = votesCount;
	}
	
	/**
	 * getter of id
	 * @return id
	 */
	public long getId() {
		return id;
	}
	
	/**
	 * getter of title
	 * @return title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * getter of link
	 * @return link
	 */
	public String getLink() {
		return link;
	}
	
	/**
	 * getter of pollId
	 * @return pollID
	 */
	public long getPollID() {
		return pollID;
	}
	
	/**
	 * getter of number of votes
	 * @return votesCount
	 */
	public long getVotesCount() {
		return votesCount;
	}
	
	@Override
	public int compareTo(PollOption o) {
		return votesCount-o.votesCount < 0 ? 1:-1;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id, link, pollID, title, votesCount);
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
		PollOption other = (PollOption) obj;
		return Objects.equals(link, other.link) && pollID == other.pollID && Objects.equals(title, other.title)
				&& votesCount == other.votesCount;
	}
	
	@Override
	public String toString() {
		return "PollOption [\nid=" + id + ",\ntitle=" + title + ",\nlink=" + link + ",\npollID=" + pollID + ",\nvotesCount=" + votesCount
				+ "]";
	}
}
