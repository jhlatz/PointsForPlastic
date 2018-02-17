package application;

public class User {
	private String username;
	private String displayName;
	private String email;
	private int points;

	public User(String username, String displayName, String email, int points) {
		this.username = username;
		this.displayName = displayName;
		this.email = email;
		this.points = points;
	}
}
