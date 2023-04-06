package com.delicacy.delicacy;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class main extends Application {

    @Override
    //Is shows the splash screen
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("view/splashscreen.fxml"));
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.initStyle(StageStyle.DECORATED);
        stage.setScene(scene);
        Image icon=new Image(getClass().getResourceAsStream("Images/logo.png"));
        stage.getIcons().add(icon);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}