package com.delicacy.delicacy;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.net.URL;
import java.sql.*;
import java.util.Collections;
import java.util.ResourceBundle;

import static com.delicacy.delicacy.CustomerMenuController.vec;



public class miniCartController implements Initializable {


    @FXML
    private TableColumn<miniCartDTO,String > miniItem;

    @FXML
    private TableColumn<miniCartDTO,String> miniName;

    @FXML
    private TableColumn<miniCartDTO,String> miniPrice;

    @FXML
    private TableColumn<miniCartDTO,Integer> miniSerial;

    @FXML
    private TableView<miniCartDTO> tbMini;

    private Notifications noti= Notifications.create();

    int i = 0;
    private void loaditems() {
        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://ap-south.connect.psdb.cloud/delicacy?sslMode=VERIFY_IDENTITY",
                    "apike5c6fiy1rsrajmzv",
                    "pscale_pw_9QXgibEIlGrneTReXBvzn80n9OV8HW53gPMndCpo2mB");
            String sql = "select * from Food_Menu";
            Statement stmt = con.createStatement();
            ResultSet rst = stmt.executeQuery(sql);
            ObservableList<miniCartDTO> allItems = FXCollections.observableArrayList();


            System.out.println(vec);
            Collections.sort(vec);
            int rs_idx=0;
            while(rst.next()) {
                for (; i < vec.size(); i++) {

                    int idx = vec.get(i);


                    while (rs_idx != idx && rs_idx<idx) {
                        rst.next();
                        rs_idx++;
                    }


                    allItems.add(new miniCartDTO(i+1, rst.getString(1), rst.getString(2), rst.getString(3)));

                }
            }

            tbMini.setItems(allItems);
            tbMini.refresh();
        }catch (SQLException e) {
            throw new RuntimeException(e);

        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        loaditems();

        miniItem.setCellValueFactory(new PropertyValueFactory<>("miniItem"));
        miniName.setCellValueFactory(new PropertyValueFactory<>("miniName"));
        miniPrice.setCellValueFactory(new PropertyValueFactory<>("miniPrice"));
        miniSerial.setCellValueFactory(new PropertyValueFactory<>("miniSerial"));

        tbMini.refresh();
    }

    @FXML
    private void miniRemove(){
        int i=1;
        int index=tbMini.getSelectionModel().getSelectedIndex();
        if(index<= -1){
            return;
        }
        vec.remove(index);
        tbMini.getItems().remove(index);

        noti.text("Removed Successfully!")
                .hideAfter(Duration.seconds(2))
                .position(Pos.TOP_CENTER);
        noti.darkStyle();
        noti.show();
        tbMini.refresh();
    }
}
