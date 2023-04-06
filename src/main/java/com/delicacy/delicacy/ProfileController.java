package com.delicacy.delicacy;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Node;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.sql.*;

import java.util.ResourceBundle;
import java.util.Scanner;


public class ProfileController implements Initializable {
    @FXML
    private AnchorPane PR;
    @FXML
    private Label email;
    @FXML
    private TextField fname;
    @FXML
    private TextField lname;
    @FXML
    private TextField contact;
    @FXML
    private TextArea address;
    @FXML
    private ImageView pImage;

    private static FileInputStream imageFile;

    private static String em;

    @FXML
    public void ProfileStage(ActionEvent event) throws IOException {


        Stage myStage= (Stage)((Node)event.getSource()).getScene().getWindow();
        FXMLLoader loader= new FXMLLoader(main.class.getResource("view/CustomerProfile.fxml"));
        Scene scene=new Scene(loader.load());
        myStage.setScene(scene);

        myStage.show();



    }


    private void loadAllItems() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://ap-south.connect.psdb.cloud/delicacy?sslMode=VERIFY_IDENTITY",
                    "apike5c6fiy1rsrajmzv",
                    "pscale_pw_9QXgibEIlGrneTReXBvzn80n9OV8HW53gPMndCpo2mB");
            String sql = "select * from Customer_Details Where Email=?";
            PreparedStatement pstmt = null;
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, em);
            ResultSet rst = pstmt.executeQuery();
            while(rst.next()){

            fname.setText(rst.getString("First_Name"));
            lname.setText(rst.getString("Last_Name"));
            contact.setText(rst.getString("Contact"));
            address.setText(rst.getString("Address"));

                Image im = null;
                Blob blob=null;
                blob =rst.getBlob("Image");
                if(blob==null)
                {
                    FileInputStream imagefile= new FileInputStream("src/main/resources/com/delicacy/delicacy/Images/profile.png");
                    im = new Image(imagefile);
                    pImage.setImage(im);
                    pImage.setPreserveRatio(true);
                }
                else {
                    InputStream is = blob.getBinaryStream();
                    im = new Image(is);
                    pImage.setImage(im);
                    pImage.setPreserveRatio(true);
                }
            }

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }


    @FXML
    void ChooseFile (ActionEvent actionEvent) throws ClassNotFoundException  {

        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter extFilterpng =
                new FileChooser.ExtensionFilter("*.png", "*.png");
        FileChooser.ExtensionFilter extFilterjpg =
                new FileChooser.ExtensionFilter("*.jpg", "*.jpg");

        fileChooser.getExtensionFilters()
                .addAll( extFilterjpg,  extFilterpng);

        //Show open file dialog
        try{
            File file = fileChooser.showOpenDialog(null);
            imageFile = new FileInputStream(file);
        }
        catch (Exception e)
        {
            System.out.println("File not found");
        };


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

        em=sc.nextLine();
        email.setText(em);

        loadAllItems();

    }

    @FXML
    private void back(ActionEvent event)
    {
        CustomerViewController v= new CustomerViewController();
        v.BacktoCustomerView(event);
        //PR.getScene().getWindow().hide();

    }


    @FXML
    private void authenticator(ActionEvent event)
    {

          passAuthenticator p= new passAuthenticator();
          p.authentic(fname.getText(),lname.getText(),contact.getText(),address.getText(),imageFile,PR.getScene().getWindow());

    }

}
