package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class PointsForPlasticAccountLoggedInController implements Initializable{
	public Connection con = Main.con;

	private String username;
	private int userPoints;
	@FXML Label user, points;


	public PointsForPlasticAccountLoggedInController(String user1) {
		this.username = user1;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
		user.setText(username);
		String query = "SELECT points FROM users WHERE username = ?";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1, username);
		ResultSet rs = ps.executeQuery();
		if(rs.next()) {
			userPoints = rs.getInt("points");
		}
		points.setText(""+userPoints);
		} catch (SQLException e) {

		}
	}
}
