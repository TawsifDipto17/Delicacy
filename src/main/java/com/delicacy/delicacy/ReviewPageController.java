package com.delicacy.delicacy;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReviewPageController implements Initializable {
    @FXML
    AnchorPane RV;
    public TableView <ReviewDTO> tbReview;
    public TableColumn<ReviewDTO,String> colItemId;
    public TableColumn<ReviewDTO,String> colName;

    public TableColumn <ReviewDTO, String>colReview;

    public static String selectedReview;







    private void loadAllItems() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://ap-south.connect.psdb.cloud/delicacy?sslMode=VERIFY_IDENTITY",
                    "apike5c6fiy1rsrajmzv",
                    "pscale_pw_9QXgibEIlGrneTReXBvzn80n9OV8HW53gPMndCpo2mB");
            String sql = "SELECT Food_Menu.Item_Code, Food_Menu.Name,Food_Menu.Price,AVG(Review_Rating.Rating),Review_Rating.Review\n" +
                    "                    FROM Review_Rating \n" +
                    "                    INNER JOIN Food_Menu ON Food_Menu.Item_Code=Review_Rating.Item_Code\n" +
                    "                    Group By Food_Menu.Item_Code, Food_Menu.Name,Food_Menu.Price,Review_Rating.Review";
            Statement stmt = con.createStatement();
            ResultSet rst= stmt.executeQuery(sql);
            ObservableList<ReviewDTO> allItems = FXCollections.observableArrayList();

            while (rst.next()) {
                if(selectedReview.equals(rst.getString(1)))
                allItems.add(new ReviewDTO(rst.getString(1), rst.getString(2), rst.getString(3),  rst.getDouble(4), rst.getString(5)));
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
        colItemId.setCellFactory(new Callback<TableColumn<ReviewDTO, String>, TableCell<ReviewDTO, String>>() {
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
        colReview.setCellFactory(new Callback <TableColumn<ReviewDTO, String>, TableCell<ReviewDTO, String>>() {
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






        colItemId.setCellValueFactory(new PropertyValueFactory<>("colItemId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("colName"));
        colReview.setCellValueFactory(new PropertyValueFactory<>("comment"));


    }

    @FXML
    public void PageOnAction(String id) {

        selectedReview=id;

        System.out.println(id);

        Stage stage = new Stage();
        Parent root = null;
        try {

            root = FXMLLoader.load(getClass().getResource("view/Review.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(splashscreen.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initStyle(StageStyle.DECORATED);
        stage.show();

    }
//    @FXML
//    private void back()
//    {
//        AdminViewController a = new AdminViewController();
//        RV.getScene().getWindow().hide();
//        a.BacktoAdminView();
//    }


}
