package beans;

public class User implements Bean<String>
{
	private String user;
	private String password;
	private int personID;
	private int authority;
	
	public User() { }
	public User(String u, String p)
	{
		this.user = u;
		this.password = p;
	}
	
	public String getUser() { return this.user; }
	public String getPassword() { return this.password; }
	public int getPersonID() { return this.personID; }
	public int getAuthority() { return this.authority; }
	
	public void setUser(String user) { this.user = user; }
	public void setPassword(String password) { this.password = password; }
	public void setPersonID(int id) { this.personID = id; }
	public void setAuthority(int auth) { this.authority = auth; }
	
	@Override
	public String toString()
	{
		return user + ";" + password + ";" + personID + ";" + authority;
	}

	@Override
	public String getPrimaryKey() { return this.getUser(); }
}
