package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class PointsForPlasticCreateController {
	private Connection con = Main.con;

	@FXML TextField name, email, username;
	@FXML PasswordField pass, verify;

	@FXML protected void handleCreateAccount(ActionEvent event) {
		try {
			if(pass.getText().equals(verify.getText())) {
				String query = "INSERT INTO users (username, displayname, password, email, creation_date) VALUE (?,?,?,?,CURRENT_DATE)";
				PreparedStatement ps = con.prepareStatement(query);

				ps.setString(1, username.getText());
				ps.setString(2, name.getText());
				ps.setString(3, pass.getText());
				ps.setString(4, email.getText());
				ps.executeUpdate();
				System.out.println("Works");
			} else {
				System.out.println("Passwords broken");
			}

		} catch (SQLException e1) {
			System.out.println("Shits broken yo");
		}
	}
}
