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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class KoszykController {
    @FXML
    private Label koszykInfo;

    @FXML
    private Label koszykKod;

    @FXML
    private Label koszykMiasto;

    @FXML
    private Label koszykNrDomu;

    @FXML
    private Label koszykUlica;

    @FXML
    private Label summary;

    @FXML
    private TextField delete;

    @FXML
    private Label saldo;

    private String display = "";
    private int suma;
    private User usr;
    private List<Komponent> basket = new ArrayList<>();
    DataConnection dataConnection = new DataConnection();

    public KoszykController() throws ClassNotFoundException {
    }


    public void odśwież(List<Komponent> koszyk, User usr){
        this.usr = usr;
        basket = koszyk;
        for (int i = 0; i < koszyk.size(); i++) {
            display += koszyk.get(i).getNazwa() + "\n ";
        }
        summary.setText(display);
        suma =0;
        for(int i=0; i<koszyk.size();i++){
            suma+=Integer.parseInt(koszyk.get(i).getCena());
        }
        koszykInfo.setText("Do zaplaty: "+suma+" zł");
        saldo.setText(Integer.toString(usr.getSaldo()));

    }

    public void kup() throws SQLException {
        if(usr.getZalogowany()){
            if (usr.getSaldo()>=suma){
                usr.setSaldo(usr.getSaldo()-suma);
                dataConnection.buying(usr.getEmail(), suma, basket);
                koszykInfo.setText("Zakupiono");

            }else{
                koszykInfo.setText("Za mało środków na koncie");
            }
        }else{
            koszykInfo.setText("Proszę się zalogować");
        }
        saldo.setText(Integer.toString(usr.getSaldo()));
    }



    public void usuń(javafx.event.ActionEvent event) {
        String result="";
        suma=0;
        if(basket.size()>0){
            basket.remove(Integer.parseInt(delete.getText())-1);
            for (int i = 0; i < basket.size(); i++) {
                result+= basket.get(i).getNazwa() + "\n ";
                suma+=Integer.parseInt(basket.get(i).getCena());
            }
            summary.setText(result);
        }
        int suma =0;
        for(int i=0; i<basket.size();i++){
            suma+=Integer.parseInt(basket.get(i).getCena());
        }
        koszykInfo.setText("Do zaplaty: "+suma+" zł");

    }

    public void switchtoPrzegladaj(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(HelloApplication.class.getResource("Przeglądaj.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
    public void setDane(String miasto, String ulica, String nrdomu, String kod){
        koszykMiasto.setText(miasto);
        koszykUlica.setText(ulica);
        koszykNrDomu.setText(nrdomu);
        koszykKod.setText(kod);
    }

}
