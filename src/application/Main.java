package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class Main extends Application {
	private static final String url = "jdbc:mysql://158.69.113.179:3306/hackathon?useSSL=true";
	private static final String user = "hackathon";
	private static final String password = "letmeinplease";

	private static Connection con;

	@Override
	public void start(Stage primaryStage) {
		try {
			connect();
		} catch(SQLException e) {
			System.out.println("Could not connect!");
		}
		try {
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	public static void connect() throws SQLException {
		con = DriverManager.getConnection(url, user, password);
		System.out.println("Connected");
	}
}
