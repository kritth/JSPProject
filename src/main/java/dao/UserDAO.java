package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.User;

public class UserDAO implements DAO<User, String>
{

	public int insert(User o) throws SQLException, ClassNotFoundException
	{
		PreparedStatement ps = con.getPreparedStatement("INSERT INTO USERS"
				+ "(username, password, owner_id, authority) "
				+ "VALUES(?, ?, ?, ?)");
		ps.setString(1, o.getUser());
		ps.setString(2, o.getPassword());
		ps.setInt(3, o.getPersonID());
		ps.setInt(4, o.getAuthority());
		return con.executePreparedStatement();
	}

	public int update(User o) throws SQLException, ClassNotFoundException
	{
		PreparedStatement ps = con.getPreparedStatement("UPDATE USERS SET "
				+ "password = ?, owner_id = ?, authority = ? WHERE username = ?");
		ps.setString(1, o.getPassword());
		ps.setInt(2, o.getPersonID());
		ps.setInt(3, o.getAuthority());
		ps.setString(4, o.getUser());
		return con.executePreparedStatement();
	}

	public int removeByPrimaryKey(String id) throws SQLException, ClassNotFoundException
	{
		PreparedStatement ps = con.getPreparedStatement("DELETE FROM USERS WHERE USERNAME = ?");
		ps.setString(1, id);
		return con.executePreparedStatement();
	}
	
	public int removeByPersonID(int id) throws SQLException, ClassNotFoundException
	{
		PreparedStatement ps = con.getPreparedStatement("DELETE FROM USERS WHERE OWNER_ID = ?");
		ps.setInt(1, id);
		return con.executePreparedStatement();
	}

	public List<User> getAll() throws SQLException, ClassNotFoundException
	{
		List<User> list = new ArrayList<>();
		
		ResultSet rs = con.executeQuery("SELECT * FROM USERS");
		while (rs.next())
		{
			User u = new User(rs.getString("username"), rs.getString("password"));
			u.setPersonID(rs.getInt("owner_id"));
			u.setAuthority(rs.getInt("authority"));
			list.add(u);
		}
		
		return list;
	}

	public User getByPrimaryKey(String id) throws SQLException, ClassNotFoundException
	{
		User u = null;
		ResultSet rs = con.executeQuery("SELECT * FROM USERS WHERE username = '" + id + "'");
		while (rs.next())
		{
			u = new User(rs.getString("username"), rs.getString("password"));
			u.setPersonID(rs.getInt("owner_id"));
			u.setAuthority(rs.getInt("authority"));
		}
		con.closeConnection(rs);
		return u;
	}

	/****************************SINGLETON**********************************/
	private static UserDAO instance = null;
	private MySQLConnection con = MySQLConnection.getInstance();
	private UserDAO() { }
	
	public static UserDAO getInstance()
	{
		if (instance == null)
			instance = new UserDAO();
		return instance;
	}
}
