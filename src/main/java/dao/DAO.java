package dao;

import java.sql.*;
import java.util.List;

/**
 * 
 * @author Kritth
 *
 * @param <T> Object Type
 * @param <U> ID Type
 */
public interface DAO<T, U>
{
	int insert(T o) throws SQLException, ClassNotFoundException;
	int update(T o) throws SQLException, ClassNotFoundException;
	int removeByPrimaryKey(U id) throws SQLException, ClassNotFoundException;
	List<T> getAll() throws SQLException, ClassNotFoundException;
	T getByPrimaryKey(U id) throws SQLException, ClassNotFoundException;
}
