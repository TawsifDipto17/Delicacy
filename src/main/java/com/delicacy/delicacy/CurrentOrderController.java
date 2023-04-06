package com.delicacy.delicacy;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;



public class CurrentOrderController implements Initializable{


    Notifications N= Notifications.create();

    @FXML
    AnchorPane CO;

    public TextField txtCustomerId;


    public TableView <OrderDTO> tbOrder;
    public TableColumn<OrderDTO,String> orderId;
    public TableColumn<OrderDTO,String> customerName;
    public TableColumn <OrderDTO,String>customerEmail;
    public TableColumn <OrderDTO,String> orderDetails;
    public TableColumn <OrderDTO,String>deliveryStatus;

    public TableColumn <OrderDTO,String>paymentStatus;


    private String id;




    private void loadAllItems() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://ap-south.connect.psdb.cloud/delicacy?sslMode=VERIFY_IDENTITY",
                    "apike5c6fiy1rsrajmzv",
                    "pscale_pw_9QXgibEIlGrneTReXBvzn80n9OV8HW53gPMndCpo2mB");
            String sql = "select * from Customer_Order Where Date >= (DATE_SUB( now(), INTERVAL 1 Day ))";
            Statement stmt = con.createStatement();
            ResultSet rst =stmt.executeQuery(sql);
            ObservableList<OrderDTO> allItems = FXCollections.observableArrayList();

            while (rst.next()) {

                allItems.add(new OrderDTO(rst.getString(1), rst.getString(2), rst.getString(3),  rst.getString(4),rst.getString(5),rst.getString(6)));
            }
            tbOrder.setItems(allItems);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

        tbOrder.refresh();

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadAllItems();
        orderId.setCellFactory(new Callback <TableColumn<OrderDTO, String>, TableCell<OrderDTO, String>>() {
            @Override
            public TableCell<OrderDTO, String> call(TableColumn<OrderDTO, String> p) {
                TableCell<OrderDTO, String> tc = new TableCell<OrderDTO, String>(){
                    @Override
                    public void updateItem(String item, boolean empty) {
                        if (item != null){
                            setText(item);
                        }
                    }
                };
                tc.setAlignment(Pos.CENTER);
                return tc;
            }
        });
        customerName.setCellFactory(new Callback <TableColumn<OrderDTO, String>, TableCell<OrderDTO, String>>() {
            @Override
            public TableCell<OrderDTO, String> call(TableColumn<OrderDTO, String> p) {
                TableCell<OrderDTO, String> tc = new TableCell<OrderDTO, String>(){
                    @Override
                    public void updateItem(String item, boolean empty) {
                        if (item != null){
                            setText(item);
                        }
                    }
                };
                tc.setAlignment(Pos.CENTER);
                return tc;
            }
        });
        customerEmail.setCellFactory(new Callback <TableColumn<OrderDTO, String>, TableCell<OrderDTO, String>>() {
            @Override
            public TableCell<OrderDTO, String> call(TableColumn<OrderDTO, String> p) {
                TableCell<OrderDTO, String> tc = new TableCell<OrderDTO, String>(){
                    @Override
                    public void updateItem(String item, boolean empty) {
                        if (item != null){
                            setText(item);
                        }
                    }
                };
                tc.setAlignment(Pos.CENTER);
                return tc;
            }
        });
        orderDetails.setCellFactory(new Callback <TableColumn<OrderDTO, String>, TableCell<OrderDTO, String>>() {
            @Override
            public TableCell<OrderDTO, String> call(TableColumn<OrderDTO, String> p) {
                TableCell<OrderDTO, String> tc = new TableCell<OrderDTO, String>(){
                    @Override
                    public void updateItem(String item, boolean empty) {
                        if (item != null){
                            setText(item);
                        }
                    }
                };
                tc.setAlignment(Pos.CENTER);
                return tc;
            }
        });
        deliveryStatus.setCellFactory(new Callback <TableColumn<OrderDTO, String>, TableCell<OrderDTO, String>>() {
            @Override
            public TableCell<OrderDTO, String> call(TableColumn<OrderDTO, String> p) {
                TableCell<OrderDTO, String> tc = new TableCell<OrderDTO, String>(){
                    @Override
                    public void updateItem(String item, boolean empty) {
                        if (item != null){
                            setText(item);
                        }
                    }
                };
                tc.setAlignment(Pos.CENTER);
                return tc;
            }
        });


        paymentStatus.setCellFactory(new Callback <TableColumn<OrderDTO, String>, TableCell<OrderDTO, String>>() {
            @Override
            public TableCell<OrderDTO, String> call(TableColumn<OrderDTO, String> p) {
                TableCell<OrderDTO, String> tc = new TableCell<OrderDTO, String>(){
                    @Override
                    public void updateItem(String item, boolean empty) {
                        if (item != null){
                            setText(item);
                        }
                    }
                };
                tc.setAlignment(Pos.CENTER);
                return tc;
            }
        });

        orderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        deliveryStatus.setCellValueFactory(new PropertyValueFactory<>("deliveryStatus"));

        customerEmail.setCellValueFactory(new PropertyValueFactory<>("customerEmail"));
        orderDetails.setCellValueFactory(new PropertyValueFactory<>("orderDetails"));
        paymentStatus.setCellValueFactory(new PropertyValueFactory<>("paymentStatus"));


    }


    boolean deliveredBool() throws ClassNotFoundException, SQLException{
        int flag=0;
        try {
           id=txtCustomerId.getText();


            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://ap-south.connect.psdb.cloud/delicacy?sslMode=VERIFY_IDENTITY",
                    "apike5c6fiy1rsrajmzv",
                    "pscale_pw_9QXgibEIlGrneTReXBvzn80n9OV8HW53gPMndCpo2mB");

            String sql = "update Customer_Order set Delivery_Status=? where Order_Id=?";
            PreparedStatement pStmt = con.prepareStatement(sql);
            String status="Delivered";
            pStmt.setObject(1, status);
            pStmt.setObject(2, id);

            pStmt.executeUpdate();


            // closing connection
            con.close();

            loadAllItems();
            flag=1;


        } catch(Exception e){
            System.out.println(e);

        }

        if(flag==1)
        {
            System.out.println("Updated");

            return true;

        }
        else
        {
            System.out.println("Not Updated");
            return false;
        }

    }
    @FXML
    public void DeliverOnAction(ActionEvent actionEvent) {

        try {
            boolean updateItem = deliveredBool();


            if (updateItem) {

                        N.text("Status Updated Successfully")
                        .hideAfter(Duration.seconds(1))
                        .position(Pos.TOP_CENTER);
                N.darkStyle();
                N.show();
            } else {

                        N.text("Status Not Updated")
                        .hideAfter(Duration.seconds(1))
                        .position(Pos.TOP_CENTER);
                N.darkStyle();
                N.show();

            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }


    boolean PendingBool() throws ClassNotFoundException, SQLException{
        int flag=0;
        try {
            id=txtCustomerId.getText();


            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://ap-south.connect.psdb.cloud/delicacy?sslMode=VERIFY_IDENTITY",
                    "apike5c6fiy1rsrajmzv",
                    "pscale_pw_9QXgibEIlGrneTReXBvzn80n9OV8HW53gPMndCpo2mB");

            String sql = "update Customer_Order set Delivery_Status=? where Order_Id=?";
            PreparedStatement pStmt = con.prepareStatement(sql);
            String status="Pending";
            pStmt.setObject(1, status);
            pStmt.setObject(2, id);

            pStmt.executeUpdate();


            // closing connection
            con.close();

            loadAllItems();
            flag=1;


        } catch(Exception e){
            System.out.println(e);

        }

        if(flag==1)
        {
            System.out.println("Updated");

            return true;

        }
        else
        {
            System.out.println("Not Updated");
            return false;
        }

    }
    @FXML
    public void PendingOnAction(ActionEvent actionEvent) {

        try {
            boolean updateItem = PendingBool();

            if (updateItem) {

                        N.text("Status Updated Successfully")
                        .hideAfter(Duration.seconds(1))
                        .position(Pos.TOP_CENTER);
                N.darkStyle();
                N.show();

                loadAllItems();
            } else {

                        N.text("Status Not Updated")
                        .hideAfter(Duration.seconds(1))
                        .position(Pos.TOP_CENTER);
                N.darkStyle();
                N.show();
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }




    boolean completedBool() throws ClassNotFoundException, SQLException{
        int flag=0;
        try {
            id=txtCustomerId.getText();


            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://ap-south.connect.psdb.cloud/delicacy?sslMode=VERIFY_IDENTITY",
                    "apike5c6fiy1rsrajmzv",
                    "pscale_pw_9QXgibEIlGrneTReXBvzn80n9OV8HW53gPMndCpo2mB");

            String sql = "update Customer_Order set Payment_Status=? where Order_Id=?";
            PreparedStatement pStmt = con.prepareStatement(sql);
            String status="Completed";
            pStmt.setObject(1, status);
            pStmt.setObject(2, id);

            pStmt.executeUpdate();


            // closing connection
            con.close();

            loadAllItems();
            flag=1;


        } catch(Exception e){
            System.out.println(e);

        }

        if(flag==1)
        {
            System.out.println("Updated");

            return true;

        }
        else
        {
            System.out.println("Not Updated");
            return false;
        }

    }
    @FXML
    public void CompletedOnAction(ActionEvent actionEvent) {

        try {
            boolean updateItem = completedBool();


            if (updateItem) {

                N.text("Status Updated Successfully")
                        .hideAfter(Duration.seconds(1))
                        .position(Pos.TOP_CENTER);
                N.darkStyle();
                N.show();
            } else {

                N.text("Status Not Updated")
                        .hideAfter(Duration.seconds(1))
                        .position(Pos.TOP_CENTER);
                N.darkStyle();
                N.show();

            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }


    boolean DueBool() throws ClassNotFoundException, SQLException{
        int flag=0;
        try {
            id=txtCustomerId.getText();


            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://ap-south.connect.psdb.cloud/delicacy?sslMode=VERIFY_IDENTITY",
                    "apike5c6fiy1rsrajmzv",
                    "pscale_pw_9QXgibEIlGrneTReXBvzn80n9OV8HW53gPMndCpo2mB");

            String sql = "update Customer_Order set Payment_Status=? where Order_Id=?";
            PreparedStatement pStmt = con.prepareStatement(sql);
            String status="Due";
            pStmt.setObject(1, status);
            pStmt.setObject(2, id);

            pStmt.executeUpdate();


            // closing connection
            con.close();

            loadAllItems();
            flag=1;


        } catch(Exception e){
            System.out.println(e);

        }

        if(flag==1)
        {
            System.out.println("Updated");

            return true;

        }
        else
        {
            System.out.println("Not Updated");
            return false;
        }

    }
    @FXML
    public void DueOnAction(ActionEvent actionEvent) {

        try {
            boolean updateItem = DueBool();

            if (updateItem) {

                N.text("Status Updated Successfully")
                        .hideAfter(Duration.seconds(1))
                        .position(Pos.TOP_CENTER);
                N.darkStyle();
                N.show();

                loadAllItems();
            } else {

                N.text("Status Not Updated")
                        .hideAfter(Duration.seconds(1))
                        .position(Pos.TOP_CENTER);
                N.darkStyle();
                N.show();
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }



    @FXML
    public void back(ActionEvent event)
    {
        AdminViewController a= new AdminViewController();
        //CO.getScene().getWindow().hide();
        a.BacktoAdminView(event);
    }

    @FXML
    public void getItems(MouseEvent mouseEvent){
        int index=tbOrder.getSelectionModel().getSelectedIndex();
        if(index<= -1){
            return;
        }
        txtCustomerId.setText(orderId.getCellData(index).toString());


    }

}