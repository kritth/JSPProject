package dao;

import java.sql.*;

public class MySQLConnection
{
	private final String url = "jdbc:mysql://localhost:3306/JSPProject";
	private final String user = "root";
	private final String password = "admin";
	
	private Connection con = null;
	private Statement stmt = null;
	private PreparedStatement ps = null;
	
	/**
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Connection getConnection() throws SQLException
	{
		con = DriverManager.getConnection(url, user, password);
		return con;
	}
	
	/**
	 * 
	 * @throws SQLException
	 */
	public void closeConnection() throws SQLException
	{
		closeConnection(null);
	}
	
	/**
	 * 
	 * @param rs
	 * @throws SQLException
	 */
	public void closeConnection(ResultSet rs) throws SQLException
	{
		if (rs != null) rs.close();
		if (stmt != null) stmt.close();
		if (con != null) con.close();
	}
	
	/**
	 * 
	 * @param stmt
	 * @return
	 * @throws SQLException
	 */
	public PreparedStatement getPreparedStatement(String stmt) throws SQLException
	{
		ps = getConnection().prepareStatement(stmt);
		return ps;
	}
	
	/**
	 * 
	 * @return
	 * @throws SQLException
	 */
	public int executePreparedStatement() throws SQLException
	{
		int result = ps.executeUpdate();
		ps.close();
		closeConnection();
		return result;
	}
	
	/**
	 * 
	 * @param query
	 * @return
	 * @throws SQLException
	 */
	public ResultSet executeQuery(String query) throws SQLException
	{
		stmt = getConnection().createStatement();
		return stmt.executeQuery(query);
	}
	
	/**************************SINGLETON*********************************/
	private static MySQLConnection instance = null;
	
	/**
	 * hide visibility of default constructor
	 */
	private MySQLConnection()
	{
		try { Class.forName("com.mysql.jdbc.Driver").newInstance();	}
		catch (Exception e) { }
	}
	
	/**
	 * Get Singleton instance
	 * @return
	 */
	public static MySQLConnection getInstance()
	{
		if (instance == null)
			instance = new MySQLConnection();
		return instance;
	}
}
