package com.delicacy.delicacy;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.NotificationPane;
import org.controlsfx.control.Notifications;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

import static com.delicacy.delicacy.CartController.PRICE;


public class addressformController2 {

    @FXML
    private AnchorPane WR;

    @FXML
    private TextField a_pass;
    private CartController cart;

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
    public void CODPayment() throws IOException {
        WR.getScene().getWindow().hide();
        Notifications N= Notifications.create();


        N.text("Order more and enjoy the delightful taste")
                .hideAfter(Duration.seconds(4))
                .position(Pos.TOP_CENTER);
        N.darkStyle();
        N.show();
        N.text("Your order will be delivered soon")
                .hideAfter(Duration.seconds(4))
                .position(Pos.TOP_CENTER);
        N.darkStyle();
        N.show();

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

            String sql = "insert into Customer_Order(Customer_Name,Email,Order_Details,Delivery_Status,Payment_Status,Cost,Date) values (?,?,?,'pending','Due',?,now());";

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
    public void setCartController(CartController cartController){
        this.cart=cartController;
    }

    @FXML
    private void Proceed(ActionEvent event) throws SQLException, IOException {

        CODPayment();

    }
}
