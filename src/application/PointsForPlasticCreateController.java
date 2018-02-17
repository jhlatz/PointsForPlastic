package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PointsForPlasticCreateController {
	private Connection con = Main.con;

	@FXML TextField name, email, username;
	@FXML PasswordField pass, verify;


	@FXML protected void handleCreateAccount(ActionEvent event) throws IOException {
		try {
			if(pass.getText().equals(verify.getText())) {
				String query = "INSERT INTO users (username, displayname, password, email, creation_date) VALUE (?,?,?,?,CURRENT_DATE)";
				PreparedStatement ps = con.prepareStatement(query);

				ps.setString(1, username.getText());
				ps.setString(2, name.getText());
				ps.setString(3, pass.getText());
				ps.setString(4, email.getText());
				ps.executeUpdate();
			} else {
				System.out.println("Passwords broken");
			}

		} catch (SQLException e1) {
			System.out.println("Shits broken yo");
		}

		FXMLLoader loader = new FXMLLoader(getClass().getResource("PointsForPlasticAccountLoggedIn.fxml"));
		PointsForPlasticAccountLoggedInController controller = new PointsForPlasticAccountLoggedInController(username.getText());
		loader.setController(controller);
		Parent parent = loader.load();
		//Parent loader = FXMLLoader.load(getClass().getResource("PointsForPlasticAccountLoggedIn.fxml"));
		Scene test = new Scene(parent);
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(test);
		window.show();

	}
}
