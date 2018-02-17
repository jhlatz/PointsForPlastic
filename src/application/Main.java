package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Main extends Application {
	private static final String url = "jdbc:mysql://158.69.113.179:3306/hackathon?useSSL=true";
	private static final String user = "hackathon";
	private static final String password = "letmeinplease";

	public static Connection con;

	protected Stage stage;
	protected Scene menu,loginMenu;
	protected TextField txfSearch, numEntries, txfID;
	protected PasswordField txfPW;
	protected Label txtInfo;
	protected SplitPane root;
	protected GridPane login;
	private ComboBox<String> genres;
	private String UID, UPS;

	private User user1;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			connect();
		} catch(SQLException e) {
			System.out.println("Could not connect!");
		}
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("PointsForPlasticRoot.fxml"));
            root = (SplitPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void connect() throws SQLException {
		con = DriverManager.getConnection(url, user, password);
		System.out.println("Connected");
	}
}
