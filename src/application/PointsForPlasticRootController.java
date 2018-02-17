package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PointsForPlasticRootController {
	private static Connection con;
	protected Pane root;

	protected String UID;
	protected String UPS;
	protected User user;

	@FXML private TextField txfUser;
	@FXML private PasswordField txfPass;
	public PointsForPlasticRootController(Connection con) {
		this.con = con;

	}

	@FXML protected void handleLoginButton(ActionEvent event) throws SQLException {
		UID = txfUser.getText();
		UPS = txfPass.getText();

		PreparedStatement ps = con.prepareStatement("SELECT * FROM users AS u WHERE u.username = ? AND u.password = ?;");
		ps.setString(1, UID);
		ps.setString(2, UPS);

		ResultSet rs = ps.executeQuery();

		if(rs.next()) {
			String username = rs.getString(1);
			String name = rs.getString(2);
			String email = rs.getString(4);
			int points = rs.getInt(5);

			user = new User(username, name, email, points);
		}
	}

	@FXML protected void handleCreateButton(ActionEvent event) throws IOException {
		Parent loader = FXMLLoader.load(Main.class.getResource("PointsForPlasticCreate.fxml"));
		Scene test = new Scene(loader);
		Stage window = (Stage
	}
}
