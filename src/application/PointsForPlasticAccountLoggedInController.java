package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;

public class PointsForPlasticAccountLoggedInController implements Initializable{
	public Connection con = Main.con;

	private String username;
	private int userPoints;
	@FXML Label user, points, message, alert;
	@FXML TextField code;
	@FXML ListView<String> userCoupons;
	@FXML TableView<Coupon> allCoupons;
	@FXML TableColumn<Coupon, String> couponName, couponPrice;

	public PointsForPlasticAccountLoggedInController(String user1) {
		this.username = user1;
	}

	public class Coupon {
		private final SimpleStringProperty name = new SimpleStringProperty("");
		private final SimpleStringProperty price = new SimpleStringProperty("");
		private final SimpleStringProperty code = new SimpleStringProperty("");

		public Coupon() {
			this("","","");
		}

		public Coupon(String name, String price, String code) {
			setName(name);
			setPrice(price);
			setCode(code);
		}

		public String getName() {
			return name.get();
		}

		public void setName(String cName) {
			name.set(cName);
		}

		public String getPrice() {
			return price.get();
		}

		public void setPrice(String cPrice) {
			price.set(cPrice);
		}

		public String getCode() {
			return code.get();
		}

		public void setCode(String cCode) {
			code.set(cCode);
		}
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
			message.setVisible(false);

			query = "SELECT name FROM coupons AS c, userCoupons AS u WHERE u.user = ? AND u.code = c.code";
			ps = con.prepareStatement(query);
			ps.setString(1, username);
			rs = ps.executeQuery();
			ObservableList<String> myCoupons = FXCollections.observableArrayList();
			while(rs.next()) {
				myCoupons.add(rs.getString("name"));
			}
			userCoupons.setItems(myCoupons);



			query = "SELECT name, cost, code FROM coupons";
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			ObservableList<Coupon> coupons = FXCollections.observableArrayList();
			couponName.setCellValueFactory(new PropertyValueFactory<Coupon,String>("Name"));
			couponPrice.setCellValueFactory(new PropertyValueFactory<Coupon, String>("Price"));
			while(rs.next()) {
				Coupon coupon = new Coupon(rs.getString("name"), ""+rs.getInt("cost"), ""+rs.getInt("code"));
				coupons.add(coupon);
			}
			allCoupons.getItems().setAll(coupons);

		} catch (SQLException e) {

		}
	}

	@FXML public void handleEnterCode() throws SQLException {
		String enteredCode = code.getText();
		String query = "SELECT code, value FROM receipt WHERE user = ?";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setString(1, username);
		ResultSet rs = ps.executeQuery();
		if(rs.next()) {
			if(rs.getString("code").equals(enteredCode)) {
				query = "UPDATE users SET points = points + ? WHERE username = ?";
				ps = con.prepareStatement(query);
				ps.setInt(1, rs.getInt("value"));
				ps.setString(2, username);
				ps.executeUpdate();
				message.setVisible(true);
			}
		}
		query = "DELETE FROM receipt WHERE user = ? and code = ?";
		ps = con.prepareStatement(query);
		ps.setString(1, username);
		ps.setString(2, enteredCode);
		ps.executeUpdate();

		query = "SELECT points FROM users WHERE username = ?";
		ps = con.prepareStatement(query);
		ps.setString(1, username);
		rs = ps.executeQuery();
		if(rs.next()) {
			userPoints = rs.getInt("points");
		}
		points.setText(""+userPoints);
	}

	@FXML public void handlePurchase() throws SQLException {
		try {
			Coupon coupon = allCoupons.getSelectionModel().getSelectedItem();
			int uPoints = Integer.parseInt(points.getText());
			if(uPoints >= Integer.parseInt(coupon.getPrice())){
				String query = "UPDATE users SET points = points - ? WHERE username = ?";
				PreparedStatement ps = con.prepareStatement(query);
				ps.setInt(1, Integer.parseInt(coupon.getPrice()));
				ps.setString(2,username);
				ps.executeUpdate();

				query = "INSERT INTO userCoupons(user, code) VALUES(?,?)";
				ps = con.prepareStatement(query);
				ps.setString(1,username);
				ps.setInt(2, Integer.parseInt(coupon.getCode()));
				ps.executeUpdate();

				userCoupons.getItems().add(coupon.getName());
				alert.setText("Coupon Purchased Successfully!");
				alert.setTextFill(Color.rgb(30, 210, 30));
				points.setText(""+(uPoints-Integer.parseInt(coupon.getPrice())));
			}
		}catch(SQLException e) {
			alert.setText("You already own this coupon!");
			alert.setTextFill(Color.rgb(210, 30, 30));
		}
	}
}
