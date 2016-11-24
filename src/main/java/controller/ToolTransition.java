package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.*;
import dao.*;

/**
 * Servlet implementation class PageTransition
 */
@WebServlet("/toolTransition")
public class ToolTransition extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private static final String HOME_URL = "/list.jsp";
	private static final String TOOL_URL = "/modification.jsp";
	
    public ToolTransition() { super(); }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{
		String url = HOME_URL;
		String page = request.getParameter("page");
		String action = request.getParameter("action");
		
		// Action
		if (action != null)
		{
			switch(action)
			{
				case "forwardInsert": url = forwardInsert(request, request.getParameter("selected")); break;
				case "insert": url = doInsert(request); break;
				case "forwardEdit":
					String[] ary = request.getParameter("object").split(";");
					url = forwardEdit(request, request.getParameter("selected"), ary[0]);
					break;
				case "edit": url = doEdit(request); break;
				case "delete": doDelete(request, page); break;
			}
		}
		
		// Select page
		if (page != null)
		{
			switch(page)
			{
				default:
				case "user":
					request.setAttribute("tab", "User");
					url = getUserContext(request, response);
					break;
				case "person":
					request.setAttribute("tab", "Person");
					url = getPersonContext(request, response);
					break;
				case "student":
					request.setAttribute("tab", "Student");
					url = getStudentContext(request, response);
					break;
				case "modification":
					break;
			}
		}
		else
		{
			request.setAttribute("tab", "User");
			url = getUserContext(request, response);
		}
		
		getServletContext()
			.getRequestDispatcher(url)
			.forward(request, response);
	}
	
	/***************************************************************************************/
	/**********************GETTING CONTEXT FOR LISTING PAGE*********************************/
	/***************************************************************************************/
	
	private String getUserContext(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		UserDAO dao = UserDAO.getInstance();
		
		ArrayList<String> tableColumn = new ArrayList<>();
		tableColumn.add("Username");
		tableColumn.add("Password");
		tableColumn.add("Person ID");
		tableColumn.add("Authority");
		
		request.setAttribute("tableHeader", tableColumn);
		
		try 
		{
			List<User> list = dao.getAll();
			request.setAttribute("list", list);
		}
		catch (ClassNotFoundException | SQLException e)
		{
			e.printStackTrace();
		}
		
		request.setAttribute("page", "user");
		
		return HOME_URL;
	}
	
	private String getPersonContext(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		PersonDAO dao = PersonDAO.getInstance();
		
		ArrayList<String> tableColumn = new ArrayList<>();
		tableColumn.add("ID");
		tableColumn.add("First Name");
		tableColumn.add("Last Name");
		tableColumn.add("Address");
		tableColumn.add("Country");
		tableColumn.add("Telephone");
		tableColumn.add("Email");
		tableColumn.add("Sex");
		tableColumn.add("SIN");
		
		request.setAttribute("tableHeader", tableColumn);
		
		try
		{
			List<Person> list = dao.getAll();
			
			request.setAttribute("list", list);
		}
		catch (SQLException|ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		
		request.setAttribute("page", "person");
		
		return HOME_URL;
	}
	
	private String getStudentContext(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		StudentDAO dao = StudentDAO.getInstance();
		
		ArrayList<String> tableColumn = new ArrayList<>();
		tableColumn.add("Person ID");
		tableColumn.add("Admission Status");
		tableColumn.add("Major");
		tableColumn.add("Minor");
		tableColumn.add("Credit");
		tableColumn.add("Enrolment date");
		
		request.setAttribute("tableHeader", tableColumn);
		
		try
		{
			List<Student> list = dao.getAll();
			request.setAttribute("list", list);
		}
		catch (ClassNotFoundException | SQLException e)
		{
			e.printStackTrace();
		}
		
		request.setAttribute("page", "student");
		
		return HOME_URL;
	}
	
	/***************************************************************************************/
	/********************************DELETE FUNCTION****************************************/
	/***************************************************************************************/

	private void doDelete(HttpServletRequest request, String type)
	{
		String[] ary = request.getParameter("object").split(";");
		String primaryKey = ary[0];
		
		try
		{
			switch (type)
			{
				case "user":
					UserDAO.getInstance().removeByPrimaryKey(primaryKey);
					break;
				case "student":
					StudentDAO.getInstance().removeByPrimaryKey(Integer.parseInt(primaryKey));
					break;
				case "person":
					// Remove dependency
					UserDAO.getInstance().removeByPersonID(Integer.parseInt(primaryKey));
					StudentDAO.getInstance().removeByPrimaryKey(Integer.parseInt(primaryKey));
					// Remove from main table
					PersonDAO.getInstance().removeByPrimaryKey(Integer.parseInt(primaryKey));
					break;
			}
		}
		catch (ClassNotFoundException | SQLException e) { e.printStackTrace(); }
	}
	
	/***************************************************************************************/
	/*******************************INSERT FUNCTIONS****************************************/
	/***************************************************************************************/
	
	@SuppressWarnings("rawtypes")
	private String forwardInsert(HttpServletRequest request, String type)
	{
		request.setAttribute("type", type);
		request.setAttribute("mode", "insert");
		
		List list;
		
		try
		{
			switch(type)
			{
				case "user":
					request.setAttribute("obj", new User());
					list = PersonDAO.getInstance().getAll();
					if (list.size() > 0) request.setAttribute("allPerson", list);
					else return HOME_URL;
					break;
				case "student":
					request.setAttribute("obj", new Student());
					list = PersonDAO.getInstance().getAll();
					if (list.size() > 0) request.setAttribute("allPerson", list);
					else return HOME_URL;
					break;
				case "person":
					request.setAttribute("obj", new Person());
					break;
			}
			
			return TOOL_URL;
		}
		catch (SQLException|ClassNotFoundException e)
		{
			e.printStackTrace();
			return HOME_URL;
		}
	}
	
	private String doInsert(HttpServletRequest request)
	{
		String type = request.getParameter("page");
		int result = 0;
		
		try
		{
			switch(type)
			{
				case "user":
					User user = getUser(request);
					if (user != null)
					{
						result = UserDAO.getInstance().insert(user);
						break;
					}
					else
					{
						request.setAttribute("error", "Passwords do not match.");
						forwardInsert(request, "user");
						return TOOL_URL;
					}
				case "student":
					Student student = getStudent(request);
					result = StudentDAO.getInstance().insert(student);
					break;
				case "person":
					Person person = getPerson(request);
					result = PersonDAO.getInstance().insert(person);
					break;
				default:
					throw new ClassNotFoundException();
			}
			
			if (result <= 0) { throw new SQLException(); } 
		}
		catch (SQLException|ClassNotFoundException e)
		{
			e.printStackTrace();
			request.setAttribute("error", "Cannot insert this user.");
			return TOOL_URL;
		}
		
		return HOME_URL;
	}
	
	/***************************************************************************************/
	/***********************************EDIT FUNCTIONS**************************************/
	/***************************************************************************************/
	
	@SuppressWarnings("rawtypes")
	private String forwardEdit(HttpServletRequest request, String type, String primaryKey)
	{
		request.setAttribute("type", type);
		request.setAttribute("mode", "edit");
		
		List list;
		
		try
		{
			switch(type)
			{
				case "user":
					request.setAttribute("obj", UserDAO.getInstance().getByPrimaryKey(primaryKey));
					list = PersonDAO.getInstance().getAll();
					if (list.size() > 0) request.setAttribute("allPerson", list);
					else return HOME_URL;
					break;
				case "student":
					request.setAttribute("obj", StudentDAO.getInstance().getByPrimaryKey(Integer.parseInt(primaryKey)));
					list = PersonDAO.getInstance().getAll();
					if (list.size() > 0) request.setAttribute("allPerson", list);
					else return HOME_URL;
					break;
				case "person":
					request.setAttribute("obj", PersonDAO.getInstance().getByPrimaryKey(Integer.parseInt(primaryKey)));
					break;
			}
			
			return TOOL_URL;
		}
		catch (SQLException|ClassNotFoundException e)
		{
			e.printStackTrace();
			return HOME_URL;
		}
	}
	
	private String doEdit(HttpServletRequest request)
	{
		String type = request.getParameter("page");
		
		try
		{
			switch(type)
			{
				case "user":
					User user = getUser(request);
					if (user != null)
					{
						UserDAO.getInstance().update(user);
						break;
					}
					else
					{
						request.setAttribute("error", "Passwords do not match.");
						forwardEdit(request, "user", request.getParameter("username"));
						return TOOL_URL;
					}
				case "student":
					Student student = getStudent(request);
					StudentDAO.getInstance().update(student);
					break;
				case "person":
					Person person = getPerson(request);
					PersonDAO.getInstance().update(person);
					break;
				default:
					throw new ClassNotFoundException();
			}
		}
		catch (SQLException|ClassNotFoundException e)
		{
			e.printStackTrace();
			request.setAttribute("error", "Cannot update this user.");
			return TOOL_URL;
		}
		
		return HOME_URL;
	}
	
	/***************************************************************************************/
	/***********************************GET BEANS*******************************************/
	/***************************************************************************************/
	
	private User getUser(HttpServletRequest request)
	{
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String cpassword = request.getParameter("cpassword");
		int person_id = Integer.parseInt(request.getParameter("person_id"));
		int authority = Integer.parseInt(request.getParameter("authority"));
		
		if (password.equals(cpassword))
		{
			User user = new User();
			user.setUser(username);
			user.setPassword(password);
			user.setPersonID(person_id);
			user.setAuthority(authority);
			return user;
		}
		else
		{
			return null;
		}		
	}
	
	private Student getStudent(HttpServletRequest request)
	{
		int person_id = Integer.parseInt(request.getParameter("person_id"));
		String admission_status = request.getParameter("admission_status");
		String major = request.getParameter("major");
		String minor = request.getParameter("minor");
		int credit = Integer.parseInt(request.getParameter("credit"));
		
		Student student = new Student();
		student.setPersonID(person_id);
		student.setAdmissionStatus(admission_status);
		student.setMajor(major);
		student.setMinor(minor);
		student.setCredit(credit);
		
		return student;
	}
	
	private Person getPerson(HttpServletRequest request)
	{
		int person_id = -1;
		
		if (request.getParameter("id") != null) person_id = Integer.parseInt(request.getParameter("id"));
		
		Person person = new Person();
		person.setID(person_id);
		person.setFirstName(request.getParameter("first_name"));
		person.setLastName(request.getParameter("last_name"));
		person.setStreetAddress(request.getParameter("street_address"));
		person.setCity(request.getParameter("city"));
		person.setState(request.getParameter("state"));
		person.setPostal(request.getParameter("zip"));
		person.setCountry(request.getParameter("country"));
		person.setTelephone(request.getParameter("telephone"));
		person.setEmail(request.getParameter("email"));
		person.setGender(request.getParameter("gender"));
		person.setSocialInsuranceNumber(request.getParameter("sin"));
		
		return person;
	}
}
