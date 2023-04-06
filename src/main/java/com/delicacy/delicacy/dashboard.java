package com.delicacy.delicacy;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import static javafx.application.Platform.exit;


public class dashboard {
    String OTP;
    @FXML private TextField a_l_email;

    @FXML private PasswordField a_l_password;



    @FXML public TextField c_s_email;
    @FXML private TextField c_s_otp;

    @FXML private PasswordField c_s_password;
    @FXML private PasswordField c_s_confirmPassword;

    @FXML public TextField c_l_email;


    @FXML private PasswordField c_l_password;

    @FXML
    AnchorPane db;
    @FXML
    BorderPane as;
    @FXML
    BorderPane al;
    @FXML
    BorderPane cs;
    @FXML
    BorderPane cl;
    @FXML
    AnchorPane as_ap;
    Stage stage = new Stage();

    Parent root= null;
    @FXML
    private void admin_login_submit (ActionEvent event) throws ClassNotFoundException {

        String em = a_l_email.getText();
        String pass = a_l_password.getText();

        //If email and password field is empty again login page will load


        if (em.length()==0) {

            try {
                root = FXMLLoader.load(getClass().getResource("view/adminLogin.fxml"));
            } catch (IOException ex) {
                Logger.getLogger(splashscreen.class.getName()).log(Level.SEVERE, null, ex);
            }
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.initStyle(StageStyle.DECORATED);
            stage.setScene(scene);

            //stage.setTitle("Email Field Is Empty");
//            Image icon=new Image(getClass().getResourceAsStream("Images/logo.png"));
//            stage.getIcons().add(icon);
            stage.show();
            al.getScene().getWindow().hide();

            Notifications N= Notifications.create()
                    .text("Email Field Is Empty ")
                    .hideAfter(Duration.seconds(1))
                    .position(Pos.TOP_CENTER);
            // N.darkStyle();
            N.show();
        }
        //If everything is ok then data will be loaded from database to see if given email and password matches
        else {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://ap-south.connect.psdb.cloud/delicacy?sslMode=VERIFY_IDENTITY",
                        "apike5c6fiy1rsrajmzv",
                        "pscale_pw_9QXgibEIlGrneTReXBvzn80n9OV8HW53gPMndCpo2mB");

                String sql = "select * from Admin_Details where Email=? and Password=SHA1(?)";

                PreparedStatement pStmt = con.prepareStatement(sql);
                boolean flag = false;


                pStmt.setString(1, em);
                pStmt.setString(2, pass);

                ResultSet rs = pStmt.executeQuery();
                flag=rs.next();

                //Given email and password don't match with database then flag= 0 else flag =1

                if (flag == false) {

                    try {
                        root = FXMLLoader.load(getClass().getResource("view/adminLogin.fxml"));
                    } catch (IOException ex) {
                        Logger.getLogger(splashscreen.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Scene scene = new Scene(root);
                    stage.setResizable(false);
                    stage.initStyle(StageStyle.DECORATED);
                    //stage.setTitle("Email or Password Is Wrong");
                    stage.setScene(scene);
//                    Image icon=new Image(getClass().getResourceAsStream("Images/logo.png"));
//                    stage.getIcons().add(icon);
                    stage.show();
                    al.getScene().getWindow().hide();

                    Notifications N= Notifications.create()
                            .text("Email or Password Is Wrong !!!")
                            .hideAfter(Duration.seconds(3))
                            .position(Pos.TOP_CENTER);
                    // N.darkStyle();
                    N.show();

                }
                else {

                    con.close();

                    try {
                        root = FXMLLoader.load(getClass().getResource("view/AdministratorView.fxml"));
                    } catch (IOException ex) {
                        Logger.getLogger(splashscreen.class.getName()).log(Level.SEVERE, null, ex);
                    }


                    Scene scene = new Scene(root);
                    stage.setResizable(false);
                    stage.initStyle(StageStyle.DECORATED);
                    stage.setScene(scene);
//                    Image icon=new Image(getClass().getResourceAsStream("Images/logo.png"));
//                    stage.getIcons().add(icon);
                    stage.show();
                    al.getScene().getWindow().hide();

                }


            } catch(Exception e){
                System.out.println(e);
            }

        }
    }

    @FXML
    public void email (String to,String x) throws MessagingException {


        boolean flag= false;
        // Sender's email ID needs to be mentioned
        String from = "adcommerce247@gmail.com";

        // Assuming you are sending email from through gmails smtp
        String host = "smtp.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server

        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", host);
        prop.put("mail.smtp.port", 587);
        prop.put("mail.smtp.ssl.trust", host);

        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(from, "tfcjomgxlxowquhz");

            }

        });

        // Used to debug SMTP issues
        session.setDebug(true);



        // Create a default MimeMessage object.
        MimeMessage message = new MimeMessage(session);

        // Set From: header field of the header.
        message.setFrom(new InternetAddress(from));

        // Set To: header field of the header.
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

        // Set Subject: header field
        message.setSubject("Delicacy OTP");

        // Now set the actual message

        message.setText(x);

        System.out.println("sending...");
        // Send message
        Transport.send(message);
        System.out.println("Sent message successfully....");


        flag = true;
        Notifications Ne= Notifications.create();

        if (flag) {


            Ne.text("OTP Sent successfully")
                    .hideAfter(Duration.seconds(2))
                    .position(Pos.TOP_CENTER);
            // Ne.darkStyle();
            Ne.show();

        } else {


            Ne.text("Unsuccessful!!! Invalid Email")
                    .hideAfter(Duration.seconds(2))
                    .position(Pos.TOP_CENTER);
            // Ne.darkStyle();
            Ne.show();

        }

        System.out.println(OTP);

    }
    @FXML
    private void sendOTP(ActionEvent event)
    {
        Random rand = new Random();
        int resRandom = rand.nextInt((9999 - 100) + 1) + 10;
        OTP=Integer.toString(resRandom);
        try {
            email(c_s_email.getText(),OTP);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private void customer_signup_submit (ActionEvent event) throws ClassNotFoundException {
        //This function will execute when customer_signup submit button is pressed

        String otp=c_s_otp.getText();
        String em = c_s_email.getText();
        String pass = c_s_password.getText();
        String c_pass = c_s_confirmPassword.getText();

        //If password and confirm password don't match login signup page will load

        if (!pass.equals(c_pass)) {

            try {
                root = FXMLLoader.load(getClass().getResource("view/signupCustomer.fxml"));
            } catch (IOException ex) {
                Logger.getLogger(splashscreen.class.getName()).log(Level.SEVERE, null, ex);
            }
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.initStyle(StageStyle.DECORATED);
            stage.setScene(scene);
//            Image icon=new Image(getClass().getResourceAsStream("Images/logo.png"));
//            stage.getIcons().add(icon);
            //stage.setTitle("Confirm Password Did Not Match!");
            stage.show();
            cs.getScene().getWindow().hide();

            Notifications N= Notifications.create()
                    .text("Confirm Password Did Not Match !!!")
                    .hideAfter(Duration.seconds(3))
                    .position(Pos.TOP_CENTER);
            // N.darkStyle();
            N.show();
        }
        //If name and email field is empty again signup page will load

        else if (em.length()==0||em.contains("@")==false) {

            try {
                root = FXMLLoader.load(getClass().getResource("view/signupCustomer.fxml"));
            } catch (IOException ex) {
                Logger.getLogger(splashscreen.class.getName()).log(Level.SEVERE, null, ex);
            }
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.initStyle(StageStyle.DECORATED);
            stage.setScene(scene);
//            Image icon=new Image(getClass().getResourceAsStream("Images/logo.png"));
//            stage.getIcons().add(icon);
            //stage.setTitle("Name or Email Is Empty");
            stage.show();
            cs.getScene().getWindow().hide();

            Notifications N= Notifications.create()
                    .text("Email Field Is Empty !!!")
                    .hideAfter(Duration.seconds(3))
                    .position(Pos.TOP_CENTER);
            // N.darkStyle();
            N.show();
        }


        else if (pass.length()!=6) {

            try {
                root = FXMLLoader.load(getClass().getResource("view/signupCustomer.fxml"));
            } catch (IOException ex) {
                Logger.getLogger(splashscreen.class.getName()).log(Level.SEVERE, null, ex);
            }
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.initStyle(StageStyle.DECORATED);
            stage.setScene(scene);
//            Image icon=new Image(getClass().getResourceAsStream("Images/logo.png"));
//            stage.getIcons().add(icon);

            stage.show();
            cs.getScene().getWindow().hide();

            Notifications N= Notifications.create()
                    .text("Password should be 6 letters or above !!!")
                    .hideAfter(Duration.seconds(3))
                    .position(Pos.TOP_CENTER);
            // N.darkStyle();
            N.show();
        }

        else if(otp.equals(OTP)) {

            try {

                //check if email is already in database

                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://ap-south.connect.psdb.cloud/delicacy?sslMode=VERIFY_IDENTITY",
                        "apike5c6fiy1rsrajmzv",
                        "pscale_pw_9QXgibEIlGrneTReXBvzn80n9OV8HW53gPMndCpo2mB");

                String sql = "select * from Customer_Details where Email=?";

                PreparedStatement pStmt = con.prepareStatement(sql);
                boolean flag = false;


                pStmt.setString(1, em);
                //pStmt.setString(2, pass);

                ResultSet rs = pStmt.executeQuery();

                flag = rs.next();
                //System.out.println(flag);

                if (flag == true) {
                    Stage stage = new Stage();
                    Parent root = null;
                    try {
                        root = FXMLLoader.load(getClass().getResource("view/signupCustomer.fxml"));
                    } catch (IOException ex) {
                        Logger.getLogger(splashscreen.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Scene scene = new Scene(root);
                    stage.setResizable(false);
                    stage.initStyle(StageStyle.DECORATED);
                    //stage.setTitle("Email Exists, Try Different Email");
                    stage.setScene(scene);
//                    Image icon=new Image(getClass().getResourceAsStream("Images/logo.png"));
//                    stage.getIcons().add(icon);
                    stage.show();
                    cs.getScene().getWindow().hide();

                    Notifications N = Notifications.create()
                            .text("Email Exists, Try Different Email !!!")
                            .hideAfter(Duration.seconds(3))
                            .position(Pos.TOP_CENTER);
                    // N.darkStyle();
                    N.show();
                }

                //If everything is ok then data will be inserted in database
                else {
                    PreparedStatement pStmt2 = con.prepareStatement("insert into Customer_Details (Email, Password) values(?, SHA1(?))");
                    pStmt2.setString(1, em);
                    pStmt2.setString(2, pass);
                    pStmt2.executeUpdate();

                    // closing connection
                    con.close();

                    FileWriter writer= new FileWriter("src/main/resources/com/delicacy/delicacy/view/pro.txt");
                    writer.write(em);
                    writer.close();

                    Stage stage = new Stage();
                    Parent root = null;
                    try {
                        root = FXMLLoader.load(getClass().getResource("view/CustomerView.fxml"));
                    } catch (IOException ex) {
                        Logger.getLogger(splashscreen.class.getName()).log(Level.SEVERE, null, ex);
                    }


                    Scene scene = new Scene(root);
                    stage.setResizable(false);
                    stage.initStyle(StageStyle.DECORATED);
                    stage.setScene(scene);
//                    Image icon=new Image(getClass().getResourceAsStream("Images/logo.png"));
//                    stage.getIcons().add(icon);
                    stage.show();
                    cs.getScene().getWindow().hide();

                }


            } catch (Exception e) {
                System.out.println(e);
            }
        }
        else
        {
            Notifications N = Notifications.create()
                    .text("OTP DID NOT MATCH. TRY AGAIN !!!")
                    .hideAfter(Duration.seconds(3))
                    .position(Pos.TOP_CENTER);
            // N.darkStyle();
            N.show();
        }

    }


    @FXML
    private void customer_login_submit (ActionEvent event) throws ClassNotFoundException {
        String em = c_l_email.getText();
        String pass = c_l_password.getText();
        //If email and password is empty again login page will be loaded
        if (em.length()==0 || pass.length()==0) {

            try {
                root = FXMLLoader.load(getClass().getResource("view/loginCustomer.fxml"));
            } catch (IOException ex) {
                Logger.getLogger(splashscreen.class.getName()).log(Level.SEVERE, null, ex);
            }
            Scene scene = new Scene(root);
            stage.setResizable(false);
            stage.initStyle(StageStyle.DECORATED);
            stage.setScene(scene);
//            Image icon=new Image(getClass().getResourceAsStream("Images/logo.png"));
//            stage.getIcons().add(icon);
            //stage.setTitle("Email or Password Field Is Empty");
            stage.show();
            cl.getScene().getWindow().hide();

            Notifications N= Notifications.create()
                    .text("Email or Password Field Is Empty !!!")
                    .hideAfter(Duration.seconds(3))
                    .position(Pos.TOP_CENTER);
            //// N.darkStyle();
            N.show();
        }
        //If everything is ok connect the database
        else {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://ap-south.connect.psdb.cloud/delicacy?sslMode=VERIFY_IDENTITY",
                        "apike5c6fiy1rsrajmzv",
                        "pscale_pw_9QXgibEIlGrneTReXBvzn80n9OV8HW53gPMndCpo2mB");

                String sql = "select * from Customer_Details where Email=? and Password=SHA1(?)";

                PreparedStatement pStmt = con.prepareStatement(sql);
                boolean flag = false;


                pStmt.setString(1, em);
                pStmt.setString(2, pass);

                ResultSet rs = pStmt.executeQuery();
                flag=rs.next();
                // System.out.println(flag);

                //If given email and password don't match with database then again login page will be loaded
                if (flag == false) {

                    try {
                        root = FXMLLoader.load(getClass().getResource("view/loginCustomer.fxml"));
                    } catch (IOException ex) {
                        Logger.getLogger(splashscreen.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Scene scene = new Scene(root);
                    stage.setResizable(false);
                    stage.initStyle(StageStyle.DECORATED);
                    // stage.setTitle("Email or Password Is Wrong");
                    stage.setScene(scene);
//                    Image icon=new Image(getClass().getResourceAsStream("Images/logo.png"));
//                    stage.getIcons().add(icon);
                    stage.show();
                    cl.getScene().getWindow().hide();


                    Notifications N= Notifications.create()
                            .text("Email or Password Is Wrong !!!")
                            .hideAfter(Duration.seconds(3))
                            .position(Pos.TOP_CENTER);
                    //// N.darkStyle();
                    N.show();
                }

                //load next page
                else {

                    con.close();
//                    "src/main/resources/com/delicacy/delicacy/view/pro.txt"
                    FileWriter writer= new FileWriter("src/main/resources/com/delicacy/delicacy/view/pro.txt");
                    writer.write(em);
                    writer.close();

                    try {
                        root = FXMLLoader.load(getClass().getResource("view/CustomerView.fxml"));
                    } catch (IOException ex) {
                        Logger.getLogger(splashscreen.class.getName()).log(Level.SEVERE, null, ex);
                    }


                    Scene scene = new Scene(root);
                    stage.setResizable(false);
                    stage.initStyle(StageStyle.DECORATED);
                    stage.setScene(scene);
//                    Image icon=new Image(getClass().getResourceAsStream("Images/logo.png"));
//                    stage.getIcons().add(icon);
                    stage.show();
                    cl.getScene().getWindow().hide();

                }


            } catch(Exception e){
                System.out.println(e);
            }

        }
    }


    @FXML
    public void admin_login (ActionEvent event) throws ClassNotFoundException {

        //After clicking login option in dashboard this function will occur

        try {
            root = FXMLLoader.load(getClass().getResource("view/adminLogin.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(splashscreen.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.initStyle(StageStyle.DECORATED);
        stage.setScene(scene);
//        Image icon=new Image(getClass().getResourceAsStream("Images/logo.png"));
//        stage.getIcons().add(icon);

        stage.show();
        db.getScene().getWindow().hide();

    }
    @FXML
    public void admin_login2 (MouseEvent event) throws ClassNotFoundException {

        //After clicking login option in adminSignup.fxml as user already has account, this function will occur

        try {
            root = FXMLLoader.load(getClass().getResource("view/adminLogin.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(splashscreen.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.initStyle(StageStyle.DECORATED);
        stage.setScene(scene);
//        Image icon=new Image(getClass().getResourceAsStream("Images/logo.png"));
//        stage.getIcons().add(icon);
        stage.show();
        as.getScene().getWindow().hide();

    }

    @FXML
    private void customer_signup (ActionEvent event) throws ClassNotFoundException {
        //After clicking signup option in dashboard this function will occur

        try {
            root = FXMLLoader.load(getClass().getResource("view/signupCustomer.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(splashscreen.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.initStyle(StageStyle.DECORATED);
        stage.setScene(scene);
//        Image icon=new Image(getClass().getResourceAsStream("Images/logo.png"));
//        stage.getIcons().add(icon);

        stage.show();
        db.getScene().getWindow().hide();
    }
    @FXML
    public void customer_login (ActionEvent event) throws ClassNotFoundException {
        //After clicking login option in dashboard this function will occur

        try {
            root = FXMLLoader.load(getClass().getResource("view/loginCustomer.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(splashscreen.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.initStyle(StageStyle.DECORATED);
        stage.setScene(scene);
//        Image icon=new Image(getClass().getResourceAsStream("Images/logo.png"));
//        stage.getIcons().add(icon);
        stage.show();
        db.getScene().getWindow().hide();

    }
    @FXML
    public void customer_login2 (MouseEvent event) throws ClassNotFoundException {
        //After clicking login option in adminSignup.fxml as user already has account, this function will occur

        try {
            root = FXMLLoader.load(getClass().getResource("view/loginCustomer.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(splashscreen.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.initStyle(StageStyle.DECORATED);
        stage.setScene(scene);
//        Image icon=new Image(getClass().getResourceAsStream("Images/logo.png"));
//        stage.getIcons().add(icon);
        stage.show();
        cs.getScene().getWindow().hide(); //change 1
    }
    @FXML
    private void Exit()
    {
        exit();
    }

    @FXML
    public void Back(ActionEvent event) throws IOException {

        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("view/dashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
//        Image icon=new Image(getClass().getResourceAsStream("Images/logo.png"));
//        stage.getIcons().add(icon);
        stage.show();

    }

}
