package hr.fer.zemris.java.p12;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.InputStream;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Initialization of the voting-app.
 * 
 * @author Jelić, Nikola
 */
@WebListener
public class Initialization implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {

		String dbSettings = sce.getServletContext().getRealPath("/WEB-INF/dbsettings.properties");

		if (!Files.exists(Paths.get(dbSettings))) {
			throw new InitializationException("dbsettings.properties does not exist");
		}

		Properties settingsProp;
		try {
			settingsProp = getProperties(dbSettings);
		} catch (IOException e) {
			throw new InitializationException(e.getMessage());
		}
		if (!settingsProp.containsKey("host")) {
			exception("host");
		}
		if (!settingsProp.containsKey("port")) {
			exception("port");
		}
		if (!settingsProp.containsKey("name")) {
			exception("name");
		}
		if (!settingsProp.containsKey("user")) {
			exception("user");
		}
		if (!settingsProp.containsKey("password")) {
			exception("password");
		}

		// making connection wiht DB
		String connectionURL = "jdbc:derby://" + settingsProp.getProperty("host") + ":"
				+ settingsProp.getProperty("port") + "/" + settingsProp.getProperty("name");

		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
		} catch (PropertyVetoException e1) {
			throw new InitializationException("Pogreška prilikom inicijalizacije poola.", e1);
		}

		cpds.setJdbcUrl(connectionURL);
		cpds.setUser(settingsProp.getProperty("user"));
		cpds.setPassword(settingsProp.getProperty("password"));
		cpds.setInitialPoolSize(5);
		cpds.setMinPoolSize(5);
		cpds.setAcquireIncrement(5);
		cpds.setMaxPoolSize(20);
		
		// preparing DB
		try {

			Connection con = cpds.getConnection();

			PreparedStatement pst = con
					.prepareCall("CREATE TABLE Polls\n" + " (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,\n"
							+ " title VARCHAR(150) NOT NULL,\n" + " message CLOB(2048) NOT NULL" + ")");

			createTableIfNeeded(pst);

			pst = con.prepareCall("CREATE TABLE PollOptions" + " (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,"
					+ " optionTitle VARCHAR(100) NOT NULL," + " optionLink VARCHAR(150) NOT NULL," + " pollID BIGINT,"
					+ " votesCount BIGINT," + " FOREIGN KEY (pollID) REFERENCES Polls(id)" + ")");

			createTableIfNeeded(pst);

			fillTablesIfNeeded(sce, con);

			try {
				pst.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			try {
				con.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		} catch (SQLException e) {
			throw new InitializationException(e.getMessage());
		}

		sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ComboPooledDataSource cpds = (ComboPooledDataSource) sce.getServletContext()
				.getAttribute("hr.fer.zemris.dbpool");
		if (cpds != null) {
			try {
				DataSources.destroy(cpds);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Returns properties of specific path. In this class this method is used for getting properties of DB. 
	 * @param path of properties
	 * @return properties
	 * @throws IOException if exception occurs while reading properties.
	 */
	private Properties getProperties(String path) throws IOException {
		InputStream is;
		is = Files.newInputStream(Paths.get(path));
		Properties prop = new Properties();
		prop.load(is);
		return prop;
	}

	/**
	 * Throws exception with explanation which parameter was a problem.
	 * 
	 * @param param which causes a problem
	 */
	private void exception(String param) {
		throw new InitializationException("'dbsettings.properties' does not contain " + param + ".");
	}

	/**
	 * Tries to execute creating table. If doesn't succeed catch
	 * {@link SQLException}.
	 * 
	 * @param pst prepared statement which creates table.
	 * @throws SQLException if Exception occurs while reading strings.
	 */
	private void createTableIfNeeded(PreparedStatement pst) throws SQLException {
		try {
			pst.executeUpdate();
		} catch (SQLException e) {
			if (!e.getSQLState().equals("X0Y32")) {
				throw e;
			}
			System.out.println("Table already exist.");
			return;
		}
		System.out.println("Table created.");
	}

	/**
	 * Fills tables with data if needed. Check if tables are changed.
	 * 
	 * @param sce servlet context event
	 * @param con connection with DB
	 * @throws SQLException if other exception occurs
	 */
	private void fillTablesIfNeeded(ServletContextEvent sce, Connection con) throws SQLException {
		String[] polls;
		String[] firstPoll;
		String[] secondPoll;

		try {
			polls = readFile(sce, "/WEB-INF/polls.txt");
			firstPoll = readFile(sce, "/WEB-INF/poll-1-options.txt");
			secondPoll = readFile(sce, "/WEB-INF/poll-2-options.txt");
		} catch (IOException e) {
			throw new InitializationException(e.getMessage());
		}

		if (countTable(con, "Polls") != polls.length
				|| countTable(con, "PollOptions") != firstPoll.length + secondPoll.length) {
			long[] keys = updatePolls(polls, con);
			updatePollOptions(keys, con, firstPoll, secondPoll);
			System.out.println("Tables are updated.");
		}

	}

	/**
	 * Reads file and returns array of lines.
	 * 
	 * @param sce request used for getting path
	 * @return array of lines.
	 * @throws IOException if Exception occurs while reading strings.
	 */
	private static String[] readFile(ServletContextEvent sce, String stringPath) throws IOException {
		String fileName = sce.getServletContext().getRealPath(stringPath);
		Path path = Paths.get(fileName);

		String[] str = Files.readString(path).split("\n");
		return str;
	}

	/**
	 * Counts rows in table.
	 * 
	 * @param con   connection to database
	 * @param table name of table
	 * @return return number of rows
	 * @throws SQLException if exception occurs while inserting
	 */
	private static int countTable(Connection con, String table) throws SQLException {
		PreparedStatement pst = con.prepareCall("SELECT COUNT(*)\n" + "FROM " + table);
		ResultSet result = pst.executeQuery();
		result.next();
		int count = result.getInt(1);
		result.close();
		try {
			pst.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return count;
	}

	/**
	 * Updates 'Polls' table. Firstly, clears all data, than inserts all data.
	 * 
	 * @param polls data to be inserted, represents each poll
	 * @param con   connection to database
	 * @return generated keys
	 * @throws SQLException if exception occurs while inserting
	 */
	private long[] updatePolls(String[] polls, Connection con) throws SQLException {
		PreparedStatement pst = con.prepareStatement("DELETE FROM Polls");
		pst.executeUpdate();
		pst = con.prepareStatement("INSERT INTO Polls (title, message) values (?,?)", Statement.RETURN_GENERATED_KEYS);
		long[] keys = new long[polls.length];

		for (int i = 0; i < polls.length; i++) {
			String[] info = polls[i].split("\t");
			pst.setString(1, info[0]);
			pst.setString(2, info[1]);
			if (pst.executeUpdate() != 1) {
				throw new InitializationException("Error while inserting row.");
			}

			ResultSet rset = pst.getGeneratedKeys();
			if (rset != null && rset.next()) {
				keys[i] = rset.getLong(1);
			} else {
				{
					throw new InitializationException("Error while inserting row: Generated key");
				}
			}
		}
		try {
			pst.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return keys;
	}

	/**
	 * Updates PollOptions table. Firstly, clears all data, than inserts all data.
	 * 
	 * @param keys       pollIDs
	 * @param con        connection to database
	 * @param firstPoll  data of first poll
	 * @param secondPoll data of second poll
	 * @throws SQLException if exception occurs while inserting
	 */
	private void updatePollOptions(long[] keys, Connection con, String[] firstPoll, String[] secondPoll)
			throws SQLException {
		PreparedStatement pst = con.prepareStatement("DELETE FROM PollOptions");
		pst.executeUpdate();
		pst = con.prepareStatement(
				"INSERT INTO PollOptions (optionTitle, optionLink, pollID, votesCount) values (?,?,?,?)");

		inserSpecificPoll(firstPoll, pst, keys[0]);
		inserSpecificPoll(secondPoll, pst, keys[1]);

		try {
			pst.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Insert data to table.
	 * 
	 * @param poll data of specific poll.
	 * @param pst  prepared statement
	 * @param key  pollID
	 * @throws SQLException if exception occurs while inserting
	 */
	private void inserSpecificPoll(String[] poll, PreparedStatement pst, long key) throws SQLException {
		for (int i = 0; i < poll.length; i++) {
			String[] info = poll[i].split("\t");
			pst.setString(1, info[0]);
			pst.setString(2, info[1]);
			pst.setLong(3, key);
			pst.setLong(4, 0L);
			if (pst.executeUpdate() != 1) {
				throw new InitializationException("Error while inserting row.");
			}
		}
	}
}