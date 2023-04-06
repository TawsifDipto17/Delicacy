package com.delicacy.delicacy;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.Vector;

public class CustomerMenuController implements Initializable{

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
    @FXML
    private Label txtname;
    @FXML
    private Label txtid;
    @FXML
    private Label txtprice;
    @FXML
    private Label txtdesc;
    @FXML
    private ImageView txtimg;
    Integer index;
    public static Vector<Integer> vec=new Vector<Integer>(50);
    private Notifications noti= Notifications.create();
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



        // tblItem.getItems().add(allItems);
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
    public void getItems(MouseEvent mouseEvent){
        index=tblItem.getSelectionModel().getSelectedIndex();
        if(index<= -1){
            return;
        }
        txtid.setText(colItemId.getCellData(index).toString());
        txtname.setText(colName.getCellData(index).toString());
        txtdesc.setText(colDescription.getCellData(index).toString());
        txtprice.setText(colPrice.getCellData(index).toString());
        Image img=colImage.getCellData(index).getImage();
        txtimg.setImage(img);
        System.out.println(colName.getCellData(index).toString());

    }

    int idx;
    public ObservableList<CartDTO> cartList= FXCollections.observableArrayList();
    @FXML
    private void addToCart(){
        int i=1;
        index=tblItem.getSelectionModel().getSelectedIndex();
        if(index<= -1){
            return;
        }
        vec.add(index);
        noti.text("Added Successfully!")
                .hideAfter(Duration.seconds(2))
                .position(Pos.TOP_CENTER);
        noti.darkStyle();
        noti.show();

            cartList.add(new CartDTO(i,colItemId.getCellData(idx).toString(),colName.getCellData(idx).toString(),colPrice.getCellData(idx).toString()));

    }


    @FXML
    private void back(ActionEvent event)
    {
        CustomerViewController a=new CustomerViewController();
        a.BacktoCustomerView(event);

    }
//For the cart screen:

    @FXML
    private Button Back;



    @FXML
    private void goToCart(ActionEvent event) throws IOException {
        System.out.println(vec);
//        loadcart();
        Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("view/Cart.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();



    }

    @FXML
    private void viewCart() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("view/miniCart.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initStyle(StageStyle.DECORATED);
        stage.show();
    }


}