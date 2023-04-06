package com.delicacy.delicacy;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import static com.delicacy.delicacy.CartController.PRICE;

public class custReviewController implements Initializable{




    @FXML
    AnchorPane RR;
    public TableView <ReviewDTO> tbReview;
    public TableColumn<ReviewDTO,String> colItemId;
    public TableColumn<ReviewDTO,String> colName;
    public TableColumn <ReviewDTO,String>colPrice;
    public TableColumn <ReviewDTO,Double> colRating;
    public TableColumn <ReviewDTO, Button>colReview;

    @FXML
    private TextField itcode;
    @FXML
    private MenuButton rat;
    @FXML
    private TextArea desc;
    @FXML
    private MenuItem it1;
    @FXML
    private MenuItem it2;
    @FXML
    private MenuItem it3;
    @FXML
    private MenuItem it4;
    @FXML
    private MenuItem it5;

    private Notifications noti= Notifications.create();








    private void loadAllItems() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://ap-south.connect.psdb.cloud/delicacy?sslMode=VERIFY_IDENTITY",
                    "apike5c6fiy1rsrajmzv",
                    "pscale_pw_9QXgibEIlGrneTReXBvzn80n9OV8HW53gPMndCpo2mB");
            String sql = "SELECT Food_Menu.Item_Code, Food_Menu.Name,Food_Menu.Price,AVG(Review_Rating.Rating)\n" +
                    "                    FROM Review_Rating\n" +
                    "                    INNER JOIN Food_Menu ON Food_Menu.Item_Code=Review_Rating.Item_Code\n" +
                    "                    Group By Food_Menu.Item_Code, Food_Menu.Name";
            Statement stmt = con.createStatement();
            ResultSet rst =stmt.executeQuery(sql);
            ObservableList<ReviewDTO> allItems = FXCollections.observableArrayList();

            while (rst.next()) {

                allItems.add(new ReviewDTO(rst.getString(1), rst.getString(2), rst.getString(3),  rst.getDouble(4),rst.getString(3)));
            }
            tbReview.setItems(allItems);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

        tbReview.refresh();


    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadAllItems();
        colItemId.setCellFactory(new Callback <TableColumn<ReviewDTO, String>, TableCell<ReviewDTO, String>>() {
            @Override
            public TableCell<ReviewDTO, String> call(TableColumn<ReviewDTO, String> p) {
                TableCell<ReviewDTO, String> tc = new TableCell<ReviewDTO, String>(){
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
        colName.setCellFactory(new Callback <TableColumn<ReviewDTO, String>, TableCell<ReviewDTO, String>>() {
            @Override
            public TableCell<ReviewDTO, String> call(TableColumn<ReviewDTO, String> p) {
                TableCell<ReviewDTO, String> tc = new TableCell<ReviewDTO, String>(){
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
        colPrice.setCellFactory(new Callback <TableColumn<ReviewDTO, String>, TableCell<ReviewDTO, String>>() {
            @Override
            public TableCell<ReviewDTO, String> call(TableColumn<ReviewDTO, String> p) {
                TableCell<ReviewDTO, String> tc = new TableCell<ReviewDTO, String>(){
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
        colRating.setCellFactory(new Callback <TableColumn<ReviewDTO, Double>, TableCell<ReviewDTO, Double>>() {
            @Override
            public TableCell<ReviewDTO, Double> call(TableColumn<ReviewDTO, Double> p) {
                TableCell<ReviewDTO, Double> tc = new TableCell<ReviewDTO, Double>(){
                    @Override
                    public void updateItem(Double item, boolean empty) {
                        if (item != null){

                            String k=item.toString();
                            setText(k);
                        }
                    }
                };
                tc.setAlignment(Pos.CENTER);
                return tc;
            }
        });





        colItemId.setCellValueFactory(new PropertyValueFactory<>("colItemId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("colName"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("colPrice"));

        colRating.setCellValueFactory(new PropertyValueFactory<>("colRating"));
        colReview.setCellValueFactory(new PropertyValueFactory<>("colReview"));

        it1.setOnAction(event -> {
            rat.setText("1");
        });
        it2.setOnAction(event -> {
            rat.setText("2");
        });
        it3.setOnAction(event -> {
            rat.setText("3");
        });
        it4.setOnAction(event -> {
            rat.setText("4");
        });
        it5.setOnAction(event -> {
            rat.setText("5");
        });


    }
    @FXML
    private void back(ActionEvent event)
    {
        CustomerViewController v= new CustomerViewController();
        v.BacktoCustomerView(event);
        //RR.getScene().getWindow().hide();
    }

    public void getRevData(String id) {
        ReviewPageController P= new ReviewPageController();
        P.PageOnAction(id);
    }
    @FXML
    private void addAction() throws SQLException {
        String code=itcode.getText();

        Integer rating=Integer.parseInt(rat.getText());
        String comment=desc.getText();
//        System.out.println(code+rating+comment);
        Connection con = null;
        con = DriverManager.getConnection(
                "jdbc:mysql://ap-south.connect.psdb.cloud/delicacy?sslMode=VERIFY_IDENTITY",
                "apike5c6fiy1rsrajmzv",
                "pscale_pw_9QXgibEIlGrneTReXBvzn80n9OV8HW53gPMndCpo2mB");

        String sql = "insert into Review_Rating values (?,?,?);";

        PreparedStatement pStmt = null;
        pStmt = con.prepareStatement(sql);

        pStmt.setString(1, code);
        pStmt.setInt(2, rating);
        pStmt.setString(3, comment);

        pStmt.executeUpdate();
        con.close();

        noti.text("Review Added Successfully!")
                .hideAfter(Duration.seconds(2))
                .position(Pos.TOP_CENTER);
        noti.darkStyle();
        noti.show();

        System.out.println("insert successful");
        loadAllItems();
        tbReview.refresh();
    }


}