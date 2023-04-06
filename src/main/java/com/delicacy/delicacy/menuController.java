package com.delicacy.delicacy;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class menuController {
    @FXML
    private Label paymentLabel;

    @FXML
    private Button bkashBtn;

    @FXML
    private BorderPane borderpane;

    @FXML
    private Button nagadBtn;

    @FXML
    private Button rocketBtn;

    @FXML
    void onBkash(ActionEvent event) throws IOException {
        AnchorPane view = FXMLLoader.load(getClass().getResource("view/bkash.fxml"));
        borderpane.setCenter(view);
    }

    @FXML
    void onNagad(ActionEvent event) throws IOException {
        AnchorPane view = FXMLLoader.load(getClass().getResource("view/nagad.fxml"));
        borderpane.setCenter(view);
    }

    @FXML
    void onRocket(ActionEvent event) throws IOException {
        AnchorPane view = FXMLLoader.load(getClass().getResource("view/rocket.fxml"));
        borderpane.setCenter(view);
    }

    @FXML
    public void showLabel() throws IOException {
        AnchorPane view = FXMLLoader.load(getClass().getResource("view/thank.fxml"));
        borderpane.setCenter(view);
    }

    public void back(ActionEvent event) throws IOException {
        Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("view/Cart.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void goToHome(ActionEvent event) throws IOException {
        Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(main.class.getResource("view/CustomerView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }
}
