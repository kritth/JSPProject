package beans;

public class Student extends Person implements Bean<Integer>
{
	private int person_id;
	private String admission_status;
	private String major;
	private String minor;
	private int credit;
	private String start_date;
	
	public Student() { }
	
	public int getPersonID() { return this.person_id; }
	public String getAdmissionStatus() { return this.admission_status; }
	public String getMajor() { return this.major; }
	public String getMinor() { return this.minor; }
	public int getCredit() { return this.credit; }
	public String getStartDate() { return this.start_date; }
	
	public void setPersonID(int id) { this.person_id = id; }
	public void setAdmissionStatus(String in) { this.admission_status = in; }
	public void setMajor(String in) { this.major = in; }
	public void setMinor(String in) { this.minor = in; }
	public void setCredit(int in) { this.credit = in; }
	public void setStartDate(String in) { this.start_date = in; }
	
	@Override
	public String toString()
	{
		return person_id + ";" +
				admission_status + ";" +
				(major != null && !major.equals("") ? major : "-") + ";" +
				(minor != null && !minor.equals("") ? minor : "-") + ";" +
				credit + ";" +
				start_date;
	}

	@Override
	public Integer getPrimaryKey() { return this.person_id; }
}
