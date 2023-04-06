package com.delicacy.delicacy;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;



public class addressformController
{

    @FXML
    private AnchorPane WR;

    @FXML
    private TextField a_pass;
    private CartController cart;

    @FXML
    public void mobilePayment() throws IOException {

        WR.getScene().getWindow().hide();
        Stage stage= new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("view/menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();

    }
    public void setCartController(CartController cartController){
        this.cart=cartController;
    }

    @FXML
    private void Proceed(ActionEvent event) throws SQLException, IOException {


        String address=a_pass.getText();

        File file = new File("src/main/resources/com/delicacy/delicacy/view/pro.txt");
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        String email=sc.nextLine();

        Connection con = null;
        con = DriverManager.getConnection(
                "jdbc:mysql://ap-south.connect.psdb.cloud/delicacy?sslMode=VERIFY_IDENTITY",
                "apike5c6fiy1rsrajmzv",
                "pscale_pw_9QXgibEIlGrneTReXBvzn80n9OV8HW53gPMndCpo2mB");

        String sql = "UPDATE Customer_Details SET Address = ? WHERE Email=?";

        PreparedStatement pStmt = null;
        pStmt = con.prepareStatement(sql);

        pStmt.setString(1, address);
        pStmt.setString(2, email);

        System.out.println(address+email);

        pStmt.executeUpdate();
        con.close();
        System.out.println("Update successful");
        cart.hideCart();
        mobilePayment();
    }
}
