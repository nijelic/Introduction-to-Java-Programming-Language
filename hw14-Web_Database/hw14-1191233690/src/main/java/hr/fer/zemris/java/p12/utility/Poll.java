package hr.fer.zemris.java.p12.utility;

/**
 * Saves all info about one poll.
 * 
 * @author Jelić, Nikola
 */
public class Poll {

	/**
	 * id of poll in database
	 */
	private long pollID;
	/**
	 * title of poll
	 */
	private String title;
	/**
	 * message of poll
	 */
	private String message;
	
	/**
	 * Constructor sets all fields.
	 * 
	 * @param pollID id of poll in database
	 * @param title title of poll
	 * @param message message of poll
	 */
	public Poll(long pollID, String title, String message) {
		super();
		this.pollID = pollID;
		this.title = title;
		this.message = message;
	}

	/**
	 * getter of pollID
	 * 
	 * @return pollID
	 */
	public long getPollID() {
		return pollID;
	}

	/**
	 * getter of title
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * getter of message
	 * @return message
	 */
	public String getMessage() {
		return message;
	}
}
