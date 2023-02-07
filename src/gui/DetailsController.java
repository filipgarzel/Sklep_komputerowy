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
import javafx.stage.Stage;

import java.sql.SQLException;

public class DetailsController {

    @FXML
    private Label nameD;

    @FXML
    private Label priceD;

    @FXML
    private Label rateD;

    @FXML
    private Label ilosc;

    @FXML
    private Label opis;

    @FXML
    private Label daneTechniczne;

    @FXML
    private ChoiceBox<String> ocen = new ChoiceBox<>();

    private Komponent komponent;
    private Procesor procesor;
    private MyListener myListener;
    private String[] opcjeOcen = {"10", "9", "8", "7", "6", "5", "4", "3", "2", "1"};
    private DataConnection dataConnection;
    private int size,sizeP, sizeK, sizePl, sizeZ, sizeR;

    {
        try {sizeP = dataConnection.getProcesory().size();}
        catch (SQLException e) {e.printStackTrace();}
        try {size = dataConnection.getKomponent().size();}
        catch (SQLException e) {e.printStackTrace();}
        try {sizeK = dataConnection.getKarty().size();}
        catch (SQLException e) {e.printStackTrace();}
        try {sizePl = dataConnection.getPlyty().size();}
        catch (SQLException e) {e.printStackTrace();}
        try {sizeZ = dataConnection.getZasilacze().size();}
        catch (SQLException e) {e.printStackTrace();}
        try {sizeR = dataConnection.getRAM().size();}
        catch (SQLException e) {e.printStackTrace();}
    }

    {
        try {
            dataConnection = new DataConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setDetails(Komponent komponent, MyListener myListener) throws SQLException {
        this.komponent = komponent;
        this.myListener = myListener;
        nameD.setText(komponent.getNazwa());
        priceD.setText(komponent.getCena() + "zł");
        rateD.setText(komponent.getOceny());
        //System.out.println("oceny " + rateD.getText());
        opis.setText(komponent.getOpis());
        ilosc.setText(String.valueOf(komponent.getIlosc()));
        ocen.getItems().addAll(opcjeOcen);
        ocen.setOnAction(this::ocenianie);

        if(dataConnection.getTable(komponent.getKompID()).equals("Procesory")){
            for(int i=0; i<sizeP; i++) {
                if(komponent.getKompID().equals(dataConnection.getProcesory().get(i).getKompID())){
                    procesor = dataConnection.getProcesory().get(i);
                }
            }
            daneTechniczne.setText("Liczba rdzenii: " + procesor.getLiczbaRdzenii() + "\n"
                    + "Liczba wątków: " + procesor.getLiczbaWątków() + "\n"
                    + "Częstotliwość taktowania: " + procesor.getTaktowanie() + "MHz");
        }
        else if(dataConnection.getTable(komponent.getKompID()).equals("Ka")){

        }
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
