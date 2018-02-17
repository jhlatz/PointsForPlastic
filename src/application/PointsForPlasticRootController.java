package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class PointsForPlasticRootController {
	public Connection con = Main.con;
	protected Pane root;

	protected String UID;
	protected String UPS;
	protected User user;

	@FXML private TextField txfUser, txfName, txfEmail, txfUserName;
	@FXML private PasswordField txfPass, txfPassword, txfVerify;



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
		Parent loader = FXMLLoader.load(getClass().getResource("PointsForPlasticCreate.fxml"));
		//loader.setController(rootController);
		Scene test = new Scene(loader);
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(test);
		window.show();
	}


}
