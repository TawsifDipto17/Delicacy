package com.delicacy.delicacy;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class passAuthenticator {
    private static FileInputStream imageFile;
    private static String email,f_name,l_name,phone,Address;
    @FXML
    private PasswordField a_pass;
    @FXML
    private AnchorPane WR;

    private static Window ev;


    public void authentic(String fname, String lname, String contact, String address, FileInputStream imag, Window ev2)
    {

        File file = new File("src/main/resources/com/delicacy/delicacy/view/pro.txt");
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        email=sc.nextLine();

        f_name=fname;
        l_name=lname;
        phone=contact;
        Address=address;
        imageFile=imag;
        ev=ev2;

        Stage stage = new Stage();

        FXMLLoader loader= new FXMLLoader(main.class.getResource("view/passAuthenticator.fxml"));

        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setResizable(false);
        stage.initStyle(StageStyle.DECORATED);
        //stage.setTitle("Email Exists, Try Different Email");
        stage.setScene(scene);
        stage.show();
        //cs.getScene().getWindow().hide();
    }
    boolean addItem() throws ClassNotFoundException, SQLException {
        int flag=0;
        try {


            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://ap-south.connect.psdb.cloud/delicacy?sslMode=VERIFY_IDENTITY",
                    "apike5c6fiy1rsrajmzv",
                    "pscale_pw_9QXgibEIlGrneTReXBvzn80n9OV8HW53gPMndCpo2mB");
            String sql = "update Customer_Details set First_Name=?,Last_Name=?,Image=?, Contact=?, Address=? where Email=?";
            PreparedStatement pStmt = con.prepareStatement(sql);

            pStmt.setString(1,f_name);
            pStmt.setString(2, l_name);
            pStmt.setBinaryStream(3, imageFile,imageFile.available());
            pStmt.setString(4, phone);
            pStmt.setString(5, Address);
            pStmt.setString(6, email);
            pStmt.executeUpdate();


            con.close();

            flag=1;


        } catch(Exception e){
            System.out.println(e);

        }

        if(flag==1)
        {
            System.out.println("Uploaded");

            return true;

        }
        else
        {
            System.out.println("Not Uploaded");
            return false;
        }

    }




    @FXML
    public void addOnAction (ActionEvent actionEvent) {


        try {

            boolean isAdded = addItem();

            if (isAdded) {
                Notifications N= Notifications.create()
                        .text("Information Uploaded Successfully")
                        .hideAfter(Duration.seconds(3))
                        .position(Pos.TOP_CENTER);
                N.darkStyle();
                N.show();
            } else {
                Notifications N= Notifications.create()
                        .text("ERROR !!! Information Not Uploaded !!!")
                        .hideAfter(Duration.seconds(3))
                        .position(Pos.TOP_CENTER);
                N.darkStyle();
                N.show();                        }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException ex) {

        }


    }


    @FXML
    private void Proceed(ActionEvent event) {
        WR.getScene().getWindow().hide();
        String sql = "select * from Customer_Details where Email=? and Password=SHA1(?)";

        PreparedStatement pStmt = null;
        boolean flag = false;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

        Connection con = null;

            con = DriverManager.getConnection(
                    "jdbc:mysql://ap-south.connect.psdb.cloud/delicacy?sslMode=VERIFY_IDENTITY",
                    "apike5c6fiy1rsrajmzv",
                    "pscale_pw_9QXgibEIlGrneTReXBvzn80n9OV8HW53gPMndCpo2mB");

            pStmt = con.prepareStatement(sql);



            pStmt.setString(1, email);
            pStmt.setString(2, a_pass.getText());

            ResultSet rs = pStmt.executeQuery();

            flag=rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        if(flag)
        {
            addOnAction(event);


            ProfileController p = new ProfileController();
            try {
                Stage myStage= (Stage)ev.getScene().getWindow();
                FXMLLoader loader= new FXMLLoader(main.class.getResource("view/CustomerProfile.fxml"));
                Scene scene=new Scene(loader.load());
                myStage.setScene(scene);

                myStage.show();


            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else
        {
            Alert a = new Alert(Alert.AlertType.ERROR);

            a.setContentText("Wrong password !!! Try Again !!!");
            a.setResizable(false);
            a.show();
        }

    }
}
