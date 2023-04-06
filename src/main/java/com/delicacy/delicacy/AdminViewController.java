package com.delicacy.delicacy;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;


import static javafx.application.Platform.exit;

public class AdminViewController {

    @FXML
    AnchorPane AV;
    Stage stage = new Stage();
    Parent root = null;

    @FXML
    public void MenuOnAction(ActionEvent actionEvent) {

        Stage myStage= (Stage)AV.getScene().getWindow();
        FXMLLoader loader= new FXMLLoader(main.class.getResource("view/addItemForm.fxml"));
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
    public void OrderOnAction(ActionEvent actionEvent) {

        Stage myStage= (Stage)AV.getScene().getWindow();
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
    public void ReviewOnAction(ActionEvent actionEvent) {

        Stage myStage= (Stage)AV.getScene().getWindow();
        FXMLLoader loader= new FXMLLoader(main.class.getResource("view/ReviewNRating.fxml"));
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
    public void SalesOnAction(ActionEvent actionEvent) {


        Stage myStage= (Stage)AV.getScene().getWindow();
        FXMLLoader loader= new FXMLLoader(main.class.getResource("view/SalesNPromotion.fxml"));
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
    private void customer_login (MouseEvent event) throws ClassNotFoundException {

        Stage myStage= (Stage)AV.getScene().getWindow();
        FXMLLoader loader= new FXMLLoader(main.class.getResource("view/loginCustomer.fxml"));
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
    private void Exit()
    {
        exit();
    }

    @FXML

    public void BacktoAdminView(ActionEvent event)
    {
        Node node = (Node) event.getSource();
        Stage myStage = (Stage) node.getScene().getWindow();
        FXMLLoader loader= new FXMLLoader(main.class.getResource("view/AdministratorView.fxml"));
        Scene scene= null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        myStage.setScene(scene);

        myStage.show();

    }
}