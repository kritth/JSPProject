package beans;

public class Person implements Bean<Integer>
{
	private int ID;
	private String firstName;
	private String lastName;
	private String streetAddress;
	private String city;
	private String state;
	private String postal;
	private String country;
	private String telNo;
	private String email;
	private String gender;
	private String sin;

	public Person() { }
	
	public void setID(int id) { this.ID = id; }
	public void setFirstName(String fn) { this.firstName = fn; }
	public void setLastName(String ln) { this.lastName = ln; }
	public void setStreetAddress(String add) { this.streetAddress = add; }
	public void setCity(String city) { this.city = city; }
	public void setState(String state) { this.state = state; }
	public void setPostal(String postal) { this.postal = postal; }
	public void setCountry(String coun) { this.country = coun; }
	public void setTelephone(String tel) { this.telNo = tel; }
	public void setEmail(String email) { this.email = email; }
	public void setGender(String g) { this.gender = g; }
	public void setSocialInsuranceNumber(String sin) { this.sin = sin; }
	
	public int getID() { return this.ID; }
	public String getFirstName() { return this.firstName; }
	public String getLastName() { return this.lastName; }
	public String getStreetAddress() { return this.streetAddress; }
	public String getCity() { return this.city; }
	public String getState() { return this.state; }
	public String getPostal() { return this.postal; }
	public String getCountry() { return this.country; }
	public String getTelephone() { return this.telNo; }
	public String getEmail() { return this.email; }
	public String getGender() { return this.gender; }
	public String getSocialInsuranceNumber() { return this.sin; }
	
	@Override
	public String toString()
	{
		String address = streetAddress == null || streetAddress.equals("") ? "-"
				: streetAddress + ", " + city + ", " + state + ", " + postal; 
		return ID + ";" + 
				(firstName != null && !firstName.equals("") ? firstName : "-") + ";" +
				(lastName != null && !lastName.equals("") ? lastName : "-") + ";" +
				address + ";" +
				(country != null && !country.equals("") ? country : "-") + ";" +
				(telNo != null && !telNo.equals("") ? telNo : "-") + ";" +
				(email != null && !email.equals("") ? email : "-") + ";" +
				(gender != null && !gender.equals("") ? gender : "-") + ";" +
				(sin != null && !sin.equals("") ? sin : "-");
	}

	@Override
	public Integer getPrimaryKey() { return ID; }
}
