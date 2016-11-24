package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.Student;

public class StudentDAO implements DAO<Student, Integer>
{
	@Override
	public int insert(Student o) throws SQLException, ClassNotFoundException
	{
		PreparedStatement ps = con.getPreparedStatement("INSERT INTO STUDENTS"
				+ "(person_id, admission_status, major, minor, credit) "
				+ "VALUES(?, ?, ?, ?, ?)");
		ps.setInt(1, o.getPersonID());
		ps.setString(2, o.getAdmissionStatus());
		ps.setString(3, o.getMajor());
		ps.setString(4, o.getMinor());
		ps.setInt(5, o.getCredit());
		return con.executePreparedStatement();
	}

	@Override
	public int update(Student o) throws SQLException, ClassNotFoundException
	{
		PreparedStatement ps = con.getPreparedStatement("UPDATE STUDENTS SET "
				+ "admission_status = ?, major = ?, minor = ?, credit = ? WHERE person_id = ?");
		ps.setString(1, o.getAdmissionStatus());
		ps.setString(2, o.getMajor());
		ps.setString(3, o.getMinor());
		ps.setInt(4, o.getCredit());
		ps.setInt(5, o.getPersonID());
		return con.executePreparedStatement();
	}

	@Override
	public int removeByPrimaryKey(Integer id) throws SQLException, ClassNotFoundException
	{
		PreparedStatement ps = con.getPreparedStatement("DELETE FROM STUDENTS WHERE person_id = ?");
		ps.setInt(1, id);
		return con.executePreparedStatement();
	}

	@Override
	public List<Student> getAll() throws SQLException, ClassNotFoundException
	{
		ResultSet rs = con.executeQuery("SELECT * FROM Students");
		ArrayList<Student> result = new ArrayList<Student>();
		while (rs.next())
		{
			Student s = new Student();
			s.setPersonID(rs.getInt("person_id"));
			s.setAdmissionStatus(rs.getString("admission_status"));
			s.setMajor(rs.getString("major"));
			s.setMinor(rs.getString("minor"));
			s.setCredit(rs.getInt("credit"));
			s.setStartDate(rs.getString("start_date"));
			
			result.add(s);
		}
		con.closeConnection(rs);
		return result;
	}

	@Override
	public Student getByPrimaryKey(Integer id) throws SQLException, ClassNotFoundException
	{
		Student s = null;
		ResultSet rs = con.executeQuery("SELECT * FROM STUDENTS WHERE person_id = '" + id + "'");
		while (rs.next())
		{
			s = new Student();
			s.setPersonID(rs.getInt("person_id"));
			s.setAdmissionStatus(rs.getString("admission_status"));
			s.setMajor(rs.getString("major"));
			s.setMinor(rs.getString("minor"));
			s.setCredit(rs.getInt("credit"));
			s.setStartDate(rs.getString("start_date"));
		}
		con.closeConnection(rs);
		return s;
	}
	
	
	/****************************SINGLETON**********************************/
	private static StudentDAO instance = null;
	private MySQLConnection con = MySQLConnection.getInstance();
	private StudentDAO() { }
	
	public static StudentDAO getInstance()
	{
		if (instance == null)
			instance = new StudentDAO();
		return instance;
	}
}
