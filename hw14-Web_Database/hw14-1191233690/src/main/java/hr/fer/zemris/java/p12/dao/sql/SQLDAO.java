package hr.fer.zemris.java.p12.dao.sql;

import hr.fer.zemris.java.p12.dao.DAO;
import hr.fer.zemris.java.p12.dao.DAOException;
import hr.fer.zemris.java.p12.utility.Poll;
import hr.fer.zemris.java.p12.utility.PollOption;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Ovo je implementacija podsustava DAO uporabom tehnologije SQL. Ova konkretna
 * implementacija očekuje da joj veza stoji na raspolaganju preko
 * {@link SQLConnectionProvider} razreda, što znači da bi netko prije no što
 * izvođenje dođe do ove točke to trebao tamo postaviti. U web-aplikacijama
 * tipično rješenje je konfigurirati jedan filter koji će presresti pozive
 * servleta i prije toga ovdje ubaciti jednu vezu iz connection-poola, a po
 * zavrsetku obrade je maknuti.
 * 
 * @author marcupic
 * @author Jelić, Nikola
 */
public class SQLDAO implements DAO {

	@Override
	public List<PollOption> getOptionsByID(long pollID) throws DAOException {
		List<PollOption> pollOptions = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement(
					"select id, optionTitle, optionLink, pollID, votesCount from PollOptions where pollID=" + pollID
							+ " order by votesCount desc");
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while (rs != null && rs.next()) {
						pollOptions.add(new PollOption(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getLong(4),
								rs.getLong(5)));
					}
				} finally {
					try {
						rs.close();
					} catch (Exception ignorable) {
					}
				}
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
				}
			}
		} catch (Exception ex) {
			throw new DAOException("Error while getting poll options.", ex);
		}
		return pollOptions;
	}

	@Override
	public List<Poll> getPolls() throws DAOException {
		List<Poll> polls = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("select id, title, message from Polls");
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while (rs != null && rs.next()) {
						polls.add(new Poll(rs.getLong(1), rs.getString(2), rs.getString(3)));
					}
				} finally {
					try {
						rs.close();
					} catch (Exception ignorable) {
					}
				}
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
				}
			}
		} catch (Exception ex) {
			throw new DAOException("Error while getting polls.", ex);
		}
		
		return polls;
	}

	@Override
	public Poll getPollByID(long pollID) throws DAOException {
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		Poll poll = null;
		try {
			pst = con.prepareStatement("select id, title, message from Polls where id=" + pollID);
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while (rs != null && rs.next()) {
						poll = new Poll(rs.getLong(1), rs.getString(2), rs.getString(3));
					}
				} finally {
					try {
						rs.close();
					} catch (Exception ignorable) {
					}
				}
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
				}
			}
		} catch (Exception ex) {
			throw new DAOException("Error while getting poll options.", ex);
		}
		
		return poll;
	}

	/**
	 * This method updates votes in PoolOptions table.
	 * 
	 * @param id of incrementing data
	 * @throws DAOException if error occurs
	 */
	public static synchronized void vote(long id) throws DAOException {
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("update PollOptions set votesCount=votesCount + 1 where id=" + id);
			try {
				pst.executeUpdate();
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
				}
			}
		} catch (Exception ex) {
			throw new DAOException("Error while getting poll options.", ex);
		}
		
	}

	@Override
	public Long convertToPollID(long id) throws DAOException {
		
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		Long pollID = null;
		try {
			pst = con.prepareStatement(
					"select pollID from PollOptions where id=" + id);
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while (rs != null && rs.next()) {
						pollID = rs.getLong(1);
						break;
					}
				} finally {
					try {
						rs.close();
					} catch (Exception ignorable) {
					}
				}
			} finally {
				try {
					pst.close();
				} catch (Exception ignorable) {
				}
			}
		} catch (Exception ex) {
			throw new DAOException("Error while getting poll options.", ex);
		}
		
		return pollID;
	}

}