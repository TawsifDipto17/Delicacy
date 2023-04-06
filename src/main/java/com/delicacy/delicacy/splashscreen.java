package com.delicacy.delicacy;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class splashscreen implements Initializable {


    //This function controls splashscreen
    @FXML
    AnchorPane ap;
    Stage stage = new Stage();
    Parent root = null;

    class ShowSplashScreen extends Thread{
        @Override
        public void run(){
            try {
                Thread.sleep(3000);

                Platform.runLater(() -> {

                    try {
                        root = FXMLLoader.load(getClass().getResource("view/dashboard.fxml"));
                    } catch (IOException ex) {
                        Logger.getLogger(splashscreen.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.initStyle(StageStyle.DECORATED);
                    Image icon=new Image(getClass().getResourceAsStream("Images/logo.png"));
                    stage.getIcons().add(icon);


                    stage.show();
                    ap.getScene().getWindow().hide();
                });
            } catch (InterruptedException ex) {
                Logger.getLogger(splashscreen.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        new ShowSplashScreen().start();
    }
}