package com.delicacy.delicacy;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.delicacy.delicacy.CustomerMenuController.vec;
import static javafx.application.Platform.exit;

public class CustomerViewController implements Initializable {
    @FXML
    private ImageView pic;

    @FXML
    AnchorPane AV;
    Stage stage = new Stage();
    Parent root = null;

    @FXML
    public void MenuOnAction(ActionEvent event) {

        vec.clear();

        Node node = (Node) event.getSource();
        Stage myStage = (Stage) node.getScene().getWindow();
        FXMLLoader loader= new FXMLLoader(main.class.getResource("view/CustomerMenu.fxml"));
        Scene scene= null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        myStage.setScene(scene);
//        Image icon=new Image(getClass().getResourceAsStream("Images/logo.png"));
//        myStage.getIcons().add(icon);

        myStage.show();
    }


    @FXML
    public void OrderOnAction(ActionEvent event) {

        Node node = (Node) event.getSource();
        Stage myStage = (Stage) node.getScene().getWindow();

        FXMLLoader loader= new FXMLLoader(main.class.getResource("view/CurrentOrder.fxml"));
        Scene scene= null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        myStage.setScene(scene);

        myStage.show();



    }

    @FXML
    public void custReviewOnAction(ActionEvent actionEvent) throws IOException {


        Stage stage1=(Stage) ((Node)actionEvent.getSource()).getScene().getWindow();

        FXMLLoader loader=new FXMLLoader(getClass().getResource("view/custReview.fxml"));
        Scene scene= new Scene(loader.load());

        stage1.setScene(scene);
//        Image icon=new Image(getClass().getResourceAsStream("Images/logo.png"));
//        stage1.getIcons().add(icon);
        stage1.show();


    }




    @FXML
    public void admin_login (MouseEvent event) throws ClassNotFoundException {
        Node node = (Node) event.getSource();
        Stage myStage = (Stage) node.getScene().getWindow();

        FXMLLoader loader= new FXMLLoader(main.class.getResource("view/adminLogin.fxml"));
        Scene scene= null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        myStage.setScene(scene);
//        Image icon=new Image(getClass().getResourceAsStream("Images/logo.png"));
//        myStage.getIcons().add(icon);

        myStage.show();

    }
    @FXML
    private void Exit()
    {
        exit();
    }


    public void BacktoCustomerView(ActionEvent event)
    {

        Node node = (Node) event.getSource();
        Stage myStage = (Stage) node.getScene().getWindow();

        FXMLLoader loader= new FXMLLoader(main.class.getResource("view/CustomerView.fxml"));
        Scene scene= null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        myStage.setScene(scene);
//        Image icon=new Image(getClass().getResourceAsStream("Images/logo.png"));
//        myStage.getIcons().add(icon);

        myStage.show();

    }

    @FXML
    private void profileOnAction(ActionEvent event) throws IOException {
        ProfileController p =new ProfileController();
        p.ProfileStage(event);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        File file = new File("src/main/resources/com/delicacy/delicacy/view/pro.txt");
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        String email = sc.nextLine();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        Connection con = null;
        try {
            con = DriverManager.getConnection(
                    "jdbc:mysql://ap-south.connect.psdb.cloud/delicacy?sslMode=VERIFY_IDENTITY",
                    "apike5c6fiy1rsrajmzv",
                    "pscale_pw_9QXgibEIlGrneTReXBvzn80n9OV8HW53gPMndCpo2mB");

            String sql = "select Image from Customer_Details Where Email=?";
            PreparedStatement pstmt = null;
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, email);
            ResultSet rst = pstmt.executeQuery();
            while (rst.next()) {

                Image im = null;
                Blob blob = null;
                blob = rst.getBlob("Image");
                if (blob == null) {
                    FileInputStream imageFile=new FileInputStream("src/main/resources/com/delicacy/delicacy/Images/profile.png");
                    im = new Image(imageFile);
                    pic.setImage(im);
                    pic.setPreserveRatio(true);


                } else {
                    InputStream is = blob.getBinaryStream();
                    im = new Image(is);
                    pic.setImage(im);
                    pic.setPreserveRatio(true);
                }
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void logout()
    {
        stage= (Stage) AV.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("view/dashboard.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        stage.setScene(scene);
//        Image icon=new Image(getClass().getResourceAsStream("Images/logo.png"));
//        stage.getIcons().add(icon);
        stage.show();
    }
}