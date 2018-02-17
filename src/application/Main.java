package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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

	private static Connection con;

	protected Stage stage;
	protected Scene menu,loginMenu;
	protected TextField txfSearch, numEntries, txfID, txfPW;
	protected Label txtInfo;
	protected Pane root;
	protected GridPane login;
	private ComboBox<String> genres;
	private String UID, UPS;

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
			stage = primaryStage;
			login = buildLoginPane();
			loginMenu = new Scene(login);
			loginMenu.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setTitle("Points For Plastic");
			stage.setScene(loginMenu);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}


	public GridPane buildLoginPane() throws SQLException{
		GridPane gridLogin = new GridPane();
		gridLogin.setVgap(5);
		gridLogin.setHgap(10);
		int row = 0;
		Label title = new Label("Points For Plastic");
		gridLogin.add(title, 0,row++);

		Label id = new Label("User ID");
		gridLogin.add(id, 0, row++);

		txfID = new TextField();
		gridLogin.add(txfID, 0, row++);

		Label pw = new Label("Password");
		gridLogin.add(pw, 0, row++);

		txfPW = new TextField();
		gridLogin.add(txfPW, 0, row++);

		HBox buttons = new HBox();
		buttons.setPadding(new Insets(5,5,5,5));
		buttons.setSpacing(10);

		Button login = new Button("Login");
		login.setOnAction(new loginEventHandler());

		Button createAccount = new Button("Start Saving!");
		createAccount.setOnAction(new createAccountEventHandler());

		buttons.getChildren().addAll(login, createAccount);

		gridLogin.add(buttons, 0, row++);

		return gridLogin;
	}

	private class createAccountEventHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			root = createAccountPane();
			menu = new Scene(root);
			stage.setScene(menu);
		}
	}
	private class loginEventHandler implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			UID = txfID.getText();
			UPS = txfPW.getText();
			//root = buildMenuPane();
			//menu = new Scene(root);
			//menu.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			//stage.setMaximized(true);
		}
	}

	public static VBox createAccountPane() {
		VBox menu  = new VBox();
		menu.setPadding(new Insets(5,5,5,5));
		menu.setSpacing(10);

		GridPane account = new GridPane();
		account.setAlignment(Pos.CENTER);
		account.setHgap(5);
		account.setVgap(5);
		account.setPadding(new Insets(5,5,5,5));

		int row = 0;
		int col = 0;

		Label name = new Label("Name: ");
		account.add(name, col++, row);

		TextField txfName = new TextField();
		account.add(txfName, col--, row++);

		Label email = new Label("Email: ");
		account.add(email, col++, row);

		TextField txfEmail = new TextField();
		account.add(txfEmail, col--, row++);

		Label username = new Label("Username: ");
		account.add(username, col++, row);

		TextField txfuserName = new TextField();
		account.add(txfuserName, col--, row++);

		Label password = new Label("Password: ");
		account.add(password, col++, row);

		TextField txfPassword = new TextField();
		account.add(txfPassword, col--, row++);

		Label confirmPassword = new Label("Confirm Password: ");
		account.add(confirmPassword, col++, row);

		TextField txfConfirmPassword = new TextField();
		account.add(txfConfirmPassword, col--, row++);

		menu.getChildren().add(account);

		return menu;
	}

	public static void connect() throws SQLException {
		con = DriverManager.getConnection(url, user, password);
		System.out.println("Connected");
	}
}
