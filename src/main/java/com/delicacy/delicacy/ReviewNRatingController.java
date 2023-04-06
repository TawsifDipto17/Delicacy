package com.delicacy.delicacy;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
public class ReviewNRatingController implements Initializable{




    @FXML
    AnchorPane RR;
    public TableView <ReviewDTO> tbReview;
    public TableColumn<ReviewDTO,String> colItemId;
    public TableColumn<ReviewDTO,String> colName;
    public TableColumn <ReviewDTO,String>colPrice;
    public TableColumn <ReviewDTO,Double> colRating;
    public TableColumn <ReviewDTO, Button>colReview;







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


    }
    @FXML
    private void back(ActionEvent event)
    {
        AdminViewController a = new AdminViewController();
        //RR.getScene().getWindow().hide();
        a.BacktoAdminView(event);
    }

    public void getRevData(String id) {
       ReviewPageController P= new ReviewPageController();
       P.PageOnAction(id);
    }


}