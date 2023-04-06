package com.delicacy.delicacy;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class AddItemFormController implements Initializable{

    @FXML
    AnchorPane AD;
    public TextField txtItemId;
    public TextField txtDescription;
    public TextField txtUnitPrice;
    public TextField txtfoodname;


    public TableView <ItemDTO> tblItem;
    public TableColumn<ItemDTO,String> colItemId;
    public TableColumn<ItemDTO,String> colName;
    public TableColumn <ItemDTO,String>colPrice;
    public TableColumn <ItemDTO,ImageView> colImage;
    public TableColumn <ItemDTO,String>colDescription;


    private String itemCode;


    private String food;
    private FileInputStream imageFile;
    private String Description;

    private String unitPrice;

    private int index;




    private void loadAllItems() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://ap-south.connect.psdb.cloud/delicacy?sslMode=VERIFY_IDENTITY",
                    "apike5c6fiy1rsrajmzv",
                    "pscale_pw_9QXgibEIlGrneTReXBvzn80n9OV8HW53gPMndCpo2mB");
            String sql = "select * from Food_Menu";
            Statement stmt = con.createStatement();
            ResultSet rst =stmt.executeQuery(sql);
            ObservableList<ItemDTO> allItems = FXCollections.observableArrayList();

            while (rst.next()) {

                allItems.add(new ItemDTO(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(5), rst.getBlob(4)));
            }
            tblItem.setItems(allItems);
            tblItem.refresh();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadAllItems();
        colItemId.setCellFactory(new Callback <TableColumn<ItemDTO, String>, TableCell<ItemDTO, String>>() {
            @Override
            public TableCell<ItemDTO, String> call(TableColumn<ItemDTO, String> p) {
                TableCell<ItemDTO, String> tc = new TableCell<ItemDTO, String>(){
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
        colName.setCellFactory(new Callback <TableColumn<ItemDTO, String>, TableCell<ItemDTO, String>>() {
            @Override
            public TableCell<ItemDTO, String> call(TableColumn<ItemDTO, String> p) {
                TableCell<ItemDTO, String> tc = new TableCell<ItemDTO, String>(){
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
        colPrice.setCellFactory(new Callback <TableColumn<ItemDTO, String>, TableCell<ItemDTO, String>>() {
            @Override
            public TableCell<ItemDTO, String> call(TableColumn<ItemDTO, String> p) {
                TableCell<ItemDTO, String> tc = new TableCell<ItemDTO, String>(){
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
        colDescription.setCellFactory(new Callback <TableColumn<ItemDTO, String>, TableCell<ItemDTO, String>>() {
            @Override
            public TableCell<ItemDTO, String> call(TableColumn<ItemDTO, String> p) {
                TableCell<ItemDTO, String> tc = new TableCell<ItemDTO, String>(){
                    @Override
                    public void updateItem(String item, boolean empty) {
                        if (item != null){
                            setText(item);
                        }
                    }
                };
                tc.setAlignment(Pos.TOP_LEFT);
                return tc;
            }
        });


        colItemId.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colName.setCellValueFactory(new PropertyValueFactory<>("foodName"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));

        colImage.setCellValueFactory(new PropertyValueFactory<>("imageView"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));


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

    @FXML
    public void getItems(MouseEvent mouseEvent){
        index=tblItem.getSelectionModel().getSelectedIndex();
        if(index<= -1){
            return;
        }
        txtItemId.setText(colItemId.getCellData(index).toString());
        txtfoodname.setText(colName.getCellData(index).toString());
        txtDescription.setText(colDescription.getCellData(index).toString());
        txtUnitPrice.setText(colPrice.getCellData(index).toString());
      System.out.println(colName.getCellData(index).toString());

    }

    boolean addItem() throws ClassNotFoundException, SQLException{
        int flag=0;
        try {
            itemCode= txtItemId.getText();
            food=txtfoodname.getText();
            Description=txtDescription.getText();
            unitPrice=txtUnitPrice.getText();


            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://ap-south.connect.psdb.cloud/delicacy?sslMode=VERIFY_IDENTITY",
                    "apike5c6fiy1rsrajmzv",
                    "pscale_pw_9QXgibEIlGrneTReXBvzn80n9OV8HW53gPMndCpo2mB");
            PreparedStatement pStmt = con.prepareStatement("insert into Food_Menu values(?, ?, ?, ?, ?)");
            pStmt.setString(1, itemCode);
            pStmt.setString(2, food);
            pStmt.setString(3, unitPrice);
            pStmt.setBinaryStream(4, imageFile,imageFile.available());
            pStmt.setString(5, Description);
            pStmt.executeUpdate();

            // closing connection
            loadAllItems();
            con.close();

            flag=1;


        } catch(Exception e){
            System.out.println(e);

        }

        if(flag==1)
        {
            System.out.println("Added");

            return true;

        }
        else
        {
            System.out.println("Not Added");
            return false;
        }

    }




   @FXML
    public void addOnAction (ActionEvent actionEvent) {


        try {

            boolean isAdded = addItem();

            if (isAdded) {
                Notifications N= Notifications.create()
                        .text("Item Added Successfully")
                        .hideAfter(Duration.seconds(1))
                        .position(Pos.TOP_CENTER);
                N.darkStyle();
                N.show();
            } else {
                Notifications N= Notifications.create()
                        .text("Item Not Added ")
                        .hideAfter(Duration.seconds(1))
                        .position(Pos.TOP_CENTER);
                N.darkStyle();
                N.show();                        }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException ex) {

        }


    }

            boolean updateItem() throws ClassNotFoundException, SQLException{
                int flag=0;
                try {
                    itemCode= txtItemId.getText();
                    food=txtfoodname.getText();
                    Description=txtDescription.getText();
                    unitPrice=txtUnitPrice.getText();


                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection(
                            "jdbc:mysql://ap-south.connect.psdb.cloud/delicacy?sslMode=VERIFY_IDENTITY",
                            "apike5c6fiy1rsrajmzv",
                            "pscale_pw_9QXgibEIlGrneTReXBvzn80n9OV8HW53gPMndCpo2mB");

                    String sql = "update Food_Menu set Name=?,Price=?,Description=? where Item_Code=?";
                    PreparedStatement pStmt = con.prepareStatement(sql);

                    pStmt.setObject(1, food);
                    pStmt.setObject(2, unitPrice);
                    pStmt.setObject(3, Description);
                    pStmt.setObject(4, itemCode);
                    pStmt.executeUpdate();

                    loadAllItems();
                    // closing connection
                    con.close();

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
        public void updateOnAction(ActionEvent actionEvent) {

        try {
             boolean updateItem = updateItem();
            String tilte;
            String message;

            if (updateItem) {
                Notifications N= Notifications.create()
                        .text("Item Updated Successfully")
                        .hideAfter(Duration.seconds(1))
                        .position(Pos.TOP_CENTER);
                N.darkStyle();
                N.show();


                //loadAllItems();
            } else {
                Notifications N= Notifications.create()
                        .text("Item Not Updated")
                        .hideAfter(Duration.seconds(1))
                        .position(Pos.TOP_CENTER);
                N.darkStyle();
                N.show();
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }


        boolean deleteItem() throws ClassNotFoundException, SQLException{
            int flag=0;
            try {
                itemCode= txtItemId.getText();


                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://ap-south.connect.psdb.cloud/delicacy?sslMode=VERIFY_IDENTITY",
                        "apike5c6fiy1rsrajmzv",
                        "pscale_pw_9QXgibEIlGrneTReXBvzn80n9OV8HW53gPMndCpo2mB");

                String sql = "DELETE FROM Food_Menu WHERE Item_Code= ?";
                PreparedStatement pStmt = con.prepareStatement(sql);
                pStmt.setObject(1,itemCode);


                pStmt.executeUpdate();

                loadAllItems();
                // closing connection
                con.close();
                flag=1;


            } catch(Exception e){
                System.out.println(e);

            }

            if(flag==1)
            {

                System.out.println("Deleted");
                return true;

            }
            else
            {
                System.out.println("Not Deleted");
                return false;
            }


        }
        @FXML
        public void deleteOnAction(ActionEvent actionEvent) {

        try {
            boolean isDelete = deleteItem();
            String tilte;
            String message;

            if (isDelete) {
                Notifications N= Notifications.create()
                        .text("Item Deleted Successfully")
                        .hideAfter(Duration.seconds(1))
                        .position(Pos.TOP_CENTER);
                N.darkStyle();
                N.show();
            } else {
                Notifications N= Notifications.create()
                        .text("Item Is Not Deleted")
                        .hideAfter(Duration.seconds(1))
                        .position(Pos.TOP_CENTER);
                N.darkStyle();
                N.show();
            }
        } catch (SQLException | ClassNotFoundException e1) {
            e1.printStackTrace();
        }

    }

    @FXML
    private void back(ActionEvent event)
    {
        AdminViewController a = new AdminViewController();
        //AD.getScene().getWindow().hide();
        a.BacktoAdminView(event);
    }
}