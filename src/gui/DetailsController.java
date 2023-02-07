package gui;

import classes.*;
import database.DataConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.sql.SQLException;

public class DetailsController {
    @FXML
    private ImageView imageD;

    @FXML
    private Label nameD;

    @FXML
    private Label priceD;

    @FXML
    private Label rateD;

    @FXML
    private Label ilosc;

    @FXML
    private TextArea opis;

    @FXML
    private ChoiceBox<String> ocen = new ChoiceBox<>();

    private Komponent komponent ;
    private MyListener myListener;
    private String[] opcjeOcen = {"10", "9", "8", "7", "6", "5", "4", "3", "2", "1"};
    private DataConnection dataConnection;

    {
        try {
            dataConnection = new DataConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setDetails(Komponent komponent, MyListener myListener){
        this.komponent = komponent;
        this.myListener = myListener;
        nameD.setText(komponent.getNazwa());
        priceD.setText(komponent.getCena() + "zł");
        rateD.setText(komponent.getOceny());
        //System.out.println("oceny " + rateD.getText());
        opis.setText(komponent.getOpis());
        ilosc.setText(String.valueOf(komponent.getIlosc()));
        //Image image = new Image(getClass().getResourceAsStream(komponent.getImage()));
        //imageD.setImage(image);
        ocen.getItems().addAll(opcjeOcen);
        ocen.setOnAction(this::ocenianie);
    }

    private void ocenianie(ActionEvent event) {
        if(ocen.getValue() != null){
            if(rateD.getText() != null){
                try {
                    System.out.println(komponent.getNazwa());
                    dataConnection.calcRating(ocen.getValue(), komponent.getNazwa());
                    System.out.println("dodano ocene");
                } catch (SQLException e) {
                    e.printStackTrace();
                }}
            else{
                try {
                    System.out.println(komponent.getNazwa());
                    dataConnection.addRating(ocen.getValue(), komponent.getNazwa());
                    System.out.println("dodano ocene");
                } catch (SQLException e) {
                    e.printStackTrace();}
            }
        }
    }

    public void switchtoPrzegladaj(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(HelloApplication.class.getResource("Przeglądaj.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void click(javafx.scene.input.MouseEvent mouseEvent) {
        myListener.onClickListener(komponent);
    }


}
