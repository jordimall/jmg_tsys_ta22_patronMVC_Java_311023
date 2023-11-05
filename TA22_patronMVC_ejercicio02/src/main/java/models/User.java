package models;

public class User {
	private String userName;
	private String password;
	
	final String USER = "root";
	final String PASSWORD = "root";
	
	/**
	 * @param userName
	 * @param password
	 */
	public User() {
		super();
		this.userName = USER;
		this.password = PASSWORD;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}
	
}
