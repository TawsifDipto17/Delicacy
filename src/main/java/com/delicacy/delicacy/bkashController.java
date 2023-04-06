package com.delicacy.delicacy;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import static com.delicacy.delicacy.CartController.PRICE;

public class bkashController implements Initializable {
    @FXML
    private AnchorPane anchorpane;

    @FXML
    private Button submitBtn;

    @FXML
    private Label amountField;



    @FXML
    private Label amountLabel;

    @FXML
    private Label bkashLabel;

    @FXML
    private TextField numberField;

    @FXML
    private PasswordField pinField;

    @FXML
    private Label pinLabel;

    private Notifications noti= Notifications.create();

    private String em="";
    private String fn;
    private String ln;
    private String order="";



    private void load()  {

        try {
            FileReader reader=new FileReader("src/main/resources/com/delicacy/delicacy/view/pro.txt");
            int data=reader.read();
            while (data!=-1){
                em+=(char)data;
                data=reader.read();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Connection con = null;
        try {
            con = DriverManager.getConnection(
                    "jdbc:mysql://ap-south.connect.psdb.cloud/delicacy?sslMode=VERIFY_IDENTITY",
                    "apike5c6fiy1rsrajmzv",
                    "pscale_pw_9QXgibEIlGrneTReXBvzn80n9OV8HW53gPMndCpo2mB");

            String sql = "select * from Customer_Details where Email=? ";

            PreparedStatement pStmt = null;
            pStmt = con.prepareStatement(sql);

            pStmt.setString(1, em);
            ResultSet rs = pStmt.executeQuery();

            while (rs.next()){
                fn=rs.getString(1);
                ln=rs.getString(2);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }


    @FXML
    public void onSubmit(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Warning!");
        alert.setHeaderText("You're about to pay via Bkash ");
        alert.setContentText("Do you want to confirm? ");

        if (alert.showAndWait().get() == ButtonType.OK) {


            noti.text("Payment Processed Successfully!")
                    .hideAfter(Duration.seconds(3))
                    .position(Pos.TOP_CENTER);
            noti.darkStyle();
            noti.show();

        }

        try {
            FileReader reader=new FileReader("src/main/resources/com/delicacy/delicacy/view/order.txt");
            int data=reader.read();
            while (data!=-1){
                order+=(char)data;
                data=reader.read();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        load();

        Connection con = null;
        try {
            int i=1000;
            con = DriverManager.getConnection(
                    "jdbc:mysql://ap-south.connect.psdb.cloud/delicacy?sslMode=VERIFY_IDENTITY",
                    "apike5c6fiy1rsrajmzv",
                    "pscale_pw_9QXgibEIlGrneTReXBvzn80n9OV8HW53gPMndCpo2mB");

            String sql = "insert into Customer_Order(Customer_Name,Email,Order_Details,Delivery_Status,Payment_Status,Cost,Date) values (?,?,?,'Pending','Due',?,now());";

            PreparedStatement pStmt = null;
            pStmt = con.prepareStatement(sql);

            pStmt.setString(1, fn+ln);
            pStmt.setString(2, em);
            pStmt.setString(3, order);
            pStmt.setInt(4, PRICE.intValue());


            pStmt.executeUpdate();
            con.close();
            System.out.println("insert successful");


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }




    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        amountField.setText(PRICE.toString());
    }
}
