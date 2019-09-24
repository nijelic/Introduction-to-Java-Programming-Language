package hr.fer.zemris.java.p12.dao;

import hr.fer.zemris.java.p12.utility.Poll;
import hr.fer.zemris.java.p12.utility.PollOption;
import java.util.List;

/**
 * Sučelje prema podsustavu za perzistenciju podataka.
 * 
 * @author marcupic
 * @author Jelić, Nikola
 */
public interface DAO {
	
	/**
	 * Gets options from database by pollID.
	 * @param pollID of options.
	 * @return options with pollID
	 * @throws DAOException if another exception occurs
	 */
	public List<PollOption> getOptionsByID(long pollID) throws DAOException;
	
	/**
	 * Returns list of available polls.
	 * @return List of available polls
	 * @throws DAOException if another exception occurs
	 */
	public List<Poll> getPolls() throws DAOException;
	
	/**
	 * Returns poll of specific pollID
	 * @param pollID of poll
	 * @return poll of specific pollId
	 * @throws DAOException if another exception occurs
	 */
	public Poll getPollByID(long pollID) throws DAOException;

	/**
	 * Finds poll Option with id, and returns its pollID.
	 * @param id of poll Option
	 * @return its pollID
	 * @throws DAOException if another exception occurs
	 */
	public Long convertToPollID(long id) throws DAOException;
}