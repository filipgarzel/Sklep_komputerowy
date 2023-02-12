package gui;
import classes.Komponent;
import classes.User;
import database.DataConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class ProfileController {
    @FXML
    private Label usrname = new Label();

    @FXML
    private ListView mylistview = new ListView();

    private Stage stage;
    private Scene scene;

    public void setUser(String mail, List<String> products){
        usrname.setText(mail);
        mylistview.getItems().addAll(products);
    }
    public void switchtoStart(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(HelloApplication.class.getResource("hello-view.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
