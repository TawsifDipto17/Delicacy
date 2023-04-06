package com.delicacy.delicacy;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;


public class SalesNPromotionController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       initLineChart();
       initLineChart1();
       Summary();
    }
    @FXML
    AnchorPane SP;
    @FXML
    private TextField SalesPromoCode;

    @FXML
    private TextField Amount;
    @FXML
    private Text LMTorders;
    @FXML
    private Text LMCOrder;
    @FXML
    private Text LMTSales;
    @FXML
    private Text LSTOrder;
    @FXML
    private Text LSTCorder;
    @FXML
    private Text LSTSales;

    @FXML
    private TextArea SalesMessage;




    @FXML
    private LineChart<?,?>GraphLastMonth;
    @FXML
    private LineChart<?,?>GraphSix;

    boolean flag=false;
    Notifications Ne= Notifications.create();



        @FXML
        public void email (ActionEvent event) {


            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            //alert.setTitle(" ");
            //alert.setHeaderText(" ");
            alert.setResizable(false);
            alert.setContentText("Do you want to send mail?");

            Optional<ButtonType> result = alert.showAndWait();
            ButtonType button = result.orElse(ButtonType.CANCEL);

            if (button == ButtonType.OK) {

                // Recipient's email ID needs to be mentioned.
                String to;

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

                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection(
                            "jdbc:mysql://ap-south.connect.psdb.cloud/delicacy?sslMode=VERIFY_IDENTITY",
                            "apike5c6fiy1rsrajmzv",
                            "pscale_pw_9QXgibEIlGrneTReXBvzn80n9OV8HW53gPMndCpo2mB");

                    String sqlA = "select Email From Customer_Details";

                    Statement stmtA = con.createStatement();
                    ResultSet rstA = stmtA.executeQuery(sqlA);

                    // Create a default MimeMessage object.
                    MimeMessage message = new MimeMessage(session);

                    // Set From: header field of the header.
                    message.setFrom(new InternetAddress(from));

                    while (rstA.next()) {
                        to = rstA.getString(1);
                        // Set To: header field of the header.
                        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

                        // Set Subject: header field
                        message.setSubject("Delicacy Promotion");

                        // Now set the actual message
                        String x = SalesMessage.getText();
                        message.setText(x);

                        System.out.println("sending...");
                        // Send message
                        Transport.send(message);
                        System.out.println("Sent message successfully....");
                    }
                    con.close();
                    flag = true;

                    if (flag) {


                        Ne.text("Message Sent successfully")
                                .hideAfter(Duration.seconds(2))
                                .position(Pos.TOP_CENTER);
                        Ne.darkStyle();
                        Ne.show();

                    } else {


                        Ne.text("Unsuccessful!!! Invalid Email")
                                .hideAfter(Duration.seconds(2))
                                .position(Pos.TOP_CENTER);
                        Ne.darkStyle();
                        Ne.show();

                    }

                } catch (MessagingException mex) {
                    mex.printStackTrace();
                    (new Alert(Alert.AlertType.INFORMATION, "Unsuccessful!!! Invalid Recipient ", new ButtonType[]{ButtonType.OK})).show();


                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }

            }

            else return;

        }
    private void Summary()
    {
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://ap-south.connect.psdb.cloud/delicacy?sslMode=VERIFY_IDENTITY",
                    "apike5c6fiy1rsrajmzv",
                    "pscale_pw_9QXgibEIlGrneTReXBvzn80n9OV8HW53gPMndCpo2mB");

            String sqlA = "select Count(*)\n" +
                    "From Customer_Order\n" +
                    "Where Date >= (DATE_SUB( now(), INTERVAL 1 month ));";

            String sqlB = "select Count(*)\n" +
                    "From Customer_Order\n" +
                    "Where Date >= (DATE_SUB( now(), INTERVAL 1 month )) and Delivery_Status='Delivered';";
            String sqlC = "select Sum(Cost)\n" +
                    "From Customer_Order\n" +
                    "Where Date >= (DATE_SUB( now(), INTERVAL 1 month ));";
            String sqlD = "select Count(*)\n" +
                    "From Customer_Order\n" +
                    "Where Date >= (DATE_SUB( now(), INTERVAL 6 month ));";
            String sqlE = "select Count(*)\n" +
                    "From Customer_Order\n" +
                    "Where Date >= (DATE_SUB( now(), INTERVAL 6 month )) and Delivery_Status='Delivered';";
            String sqlF = "select Sum(Cost)\n" +
                    "From Customer_Order\n" +
                    "Where Date >= (DATE_SUB( now(), INTERVAL 6 month ));";


            Statement stmtA = con.createStatement();
            ResultSet rstA =stmtA.executeQuery(sqlA);
            int y;
            while (rstA.next())
            {
                y=rstA.getInt(1);
                String x;
                x=Integer.toString(y);
                LMTorders.setText(x);
            }
             rstA =stmtA.executeQuery(sqlB);
            while (rstA.next())
            {
                y=rstA.getInt(1);
                String x;
                x=Integer.toString(y);
                LMCOrder.setText(x);
            }
            rstA =stmtA.executeQuery(sqlC);
            while (rstA.next())
            {
                y=rstA.getInt(1);
                String x;
                x=Integer.toString(y);
                LMTSales.setText(x);
            }

            rstA =stmtA.executeQuery(sqlD);
            while (rstA.next())
            {
                y=rstA.getInt(1);
                String x;
                x=Integer.toString(y);
                LSTOrder.setText(x);
            }
            rstA =stmtA.executeQuery(sqlE);
            while (rstA.next())
            {
                y=rstA.getInt(1);
                String x;
                x=Integer.toString(y);
                LSTCorder.setText(x);
            }
            rstA =stmtA.executeQuery(sqlF);
            while (rstA.next())
            {
                y=rstA.getInt(1);
                String x;
                x=Integer.toString(y);
                LSTSales.setText(x);
            }

         con.close();
        } catch(Exception e){
            System.out.println(e);

        }

    }
    private void initLineChart()
    {

        XYChart.Series series =new XYChart.Series();


        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://ap-south.connect.psdb.cloud/delicacy?sslMode=VERIFY_IDENTITY",
                    "apike5c6fiy1rsrajmzv",
                    "pscale_pw_9QXgibEIlGrneTReXBvzn80n9OV8HW53gPMndCpo2mB");

            String sqlA = "select sum(Cost)\n" +
                    "From Customer_Order\n" +
                    "Where Date >= (DATE_SUB( now(), INTERVAL 1 week ));";
            String sqlB = "select sum(Cost)\n" +
                    "From Customer_Order\n" +
                    "Where Date >= (DATE_SUB( now(), INTERVAL 2 week ));";
            String sqlC = "select sum(Cost)\n" +
                    "From Customer_Order\n" +
                    "Where Date >= (DATE_SUB( now(), INTERVAL 3 week ));";
            String sqlD = "select sum(Cost)\n" +
                    "From Customer_Order\n" +
                    "Where Date >= (DATE_SUB( now(), INTERVAL 4 week ));";
            Statement stmtA = con.createStatement();

            ResultSet rstA =stmtA.executeQuery(sqlA);
            int sum=0;
            while (rstA.next()) {
                series.getData().add(new XYChart.Data("Week 1", rstA.getInt(1)));
                sum=rstA.getInt(1);
            }
            rstA =stmtA.executeQuery(sqlB);
            while (rstA.next()) {
                series.getData().add(new XYChart.Data("Week 2", rstA.getInt(1)-sum));
                sum=rstA.getInt(1);
            }

            rstA =stmtA.executeQuery(sqlC);
            while (rstA.next()) {
                series.getData().add(new XYChart.Data("Week 3", rstA.getInt(1)-sum));
                sum=rstA.getInt(1);
            }

            rstA =stmtA.executeQuery(sqlD);
            while (rstA.next()) {
                series.getData().add(new XYChart.Data("Week 4", rstA.getInt(1)-sum));
                sum=rstA.getInt(1);
            }

            con.close();


        } catch(Exception e){
            System.out.println(e);

        }
        series.setName("BDT");
        GraphLastMonth.getData().addAll(series);
        GraphLastMonth.lookup(".chart-plot-background").setStyle("-fx-background-color:#bc4343;");
        series.getNode().setStyle("-fx-stroke: #FFD6DC");



    }

    private void initLineChart1()
    {

        XYChart.Series series =new XYChart.Series();


        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://ap-south.connect.psdb.cloud/delicacy?sslMode=VERIFY_IDENTITY",
                    "apike5c6fiy1rsrajmzv",
                    "pscale_pw_9QXgibEIlGrneTReXBvzn80n9OV8HW53gPMndCpo2mB");

            String sqlA = "select sum(Cost)\n" +
                    "From Customer_Order\n" +
                    "Where Date >= (DATE_SUB( now(), INTERVAL 1 month ));";
            String sqlB = "select sum(Cost)\n" +
                    "From Customer_Order\n" +
                    "Where Date >= (DATE_SUB( now(), INTERVAL 2 month ));";
            String sqlC = "select sum(Cost)\n" +
                    "From Customer_Order\n" +
                    "Where Date >= (DATE_SUB( now(), INTERVAL 3 month ));";
            String sqlD = "select sum(Cost)\n" +
                    "From Customer_Order\n" +
                    "Where Date >= (DATE_SUB( now(), INTERVAL 4 month ));";
            String sqlE = "select sum(Cost)\n" +
                    "From Customer_Order\n" +
                    "Where Date >= (DATE_SUB( now(), INTERVAL 5 month ));";
            String sqlF = "select sum(Cost)\n" +
                    "From Customer_Order\n" +
                    "Where Date >= (DATE_SUB( now(), INTERVAL 6 month ));";

            int sumA=0, sumB=0,sumC=0,sumD=0,sumE=0,sumF=0;
            Statement stmtA = con.createStatement();

            ResultSet rstA =stmtA.executeQuery(sqlF);
            while (rstA.next()) {

                sumA=rstA.getInt(1);

            }

            ResultSet rstB =stmtA.executeQuery(sqlE);
            while (rstB.next()) {

                sumB=rstB.getInt(1);
            }
            ResultSet rstC =stmtA.executeQuery(sqlD);
            while (rstC.next()) {

                sumC=rstC.getInt(1);
            }
            ResultSet rstD =stmtA.executeQuery(sqlC);
            while (rstD.next()) {

                sumD=rstD.getInt(1);
            }
            ResultSet rstE =stmtA.executeQuery(sqlB);
            while (rstE.next()) {

                sumE=rstE.getInt(1);
            }

            ResultSet rstF =stmtA.executeQuery(sqlA);
            while (rstF.next())
                sumF=rstF.getInt(1);

          con.close();
            series.getData().add(new XYChart.Data("Month 6", sumA-sumB));
            series.getData().add(new XYChart.Data("Month 5", sumB-sumC));
            series.getData().add(new XYChart.Data("Month 4", sumC-sumD));
            series.getData().add(new XYChart.Data("Month 3", sumD-sumE));
            series.getData().add(new XYChart.Data("Month 2", sumE-sumF));
            series.getData().add(new XYChart.Data("Last Month",sumF));



        } catch(Exception e){
            System.out.println(e);

        }
        series.setName("BDT");
        GraphSix.getData().addAll(series);
        GraphSix.lookup(".chart-plot-background").setStyle("-fx-background-color:#bc4343;");
        series.getNode().setStyle("-fx-stroke: #FFD6DC");



    }

    boolean addPromoCode() throws ClassNotFoundException, SQLException{
        int flag=0;
        try {
            String itemCode= SalesPromoCode.getText();
            int amount = Integer.parseInt(Amount.getText());


            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://ap-south.connect.psdb.cloud/delicacy?sslMode=VERIFY_IDENTITY",
                    "apike5c6fiy1rsrajmzv",
                    "pscale_pw_9QXgibEIlGrneTReXBvzn80n9OV8HW53gPMndCpo2mB");
            PreparedStatement pStmt = con.prepareStatement("insert into Promo_Code values(?,?,now())");
            pStmt.setString(1, itemCode);
            pStmt.setInt(2, amount);

            pStmt.executeUpdate();

            con.close();

            flag=1;


        } catch(Exception e){
            System.out.println(e);

        }

        if(flag==1)
        {
            return true;

        }
        else
        {

            return false;
        }

    }




    @FXML
    public void addPromoCodeOnAction (ActionEvent actionEvent) {


        try {

            boolean isAdded = addPromoCode();

            if (isAdded) {

                        Ne.text("Promo Code Added Successfully")
                        .hideAfter(Duration.seconds(1.5))
                        .position(Pos.TOP_CENTER);
                Ne.darkStyle();
                Ne.show();

            } else {

                        Ne.text("Promo Code Not Added")
                        .hideAfter(Duration.seconds(1.5))
                        .position(Pos.TOP_CENTER);
                Ne.darkStyle();
                Ne.show();

            }
            // tray.showAndDismiss(Duration.millis(3000));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException ex) {


        }


    }

   @FXML
    private void back(ActionEvent event)
   {
       AdminViewController a = new AdminViewController();
       //SP.getScene().getWindow().hide();
       a.BacktoAdminView(event);
   }

}
