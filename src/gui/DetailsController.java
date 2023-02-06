package gui;

import classes.*;
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
    private TextArea opis;

    @FXML
    private ChoiceBox<String> ocen = new ChoiceBox<>();

    private Komponent komponent ;
    private MyListener myListener;
    private String[] opcjeOcen = {"10", "9", "8", "7", "6", "5", "4", "3", "2", "1"};


    public void setDetails(Komponent komponent, MyListener myListener){
        this.komponent = komponent;
        this.myListener = myListener;
        nameD.setText(komponent.getNazwa());
        priceD.setText(komponent.getCena() + "zł");
        rateD.setText(komponent.getOceny());
        opis.setText(komponent.getOpis());
        //Image image = new Image(getClass().getResourceAsStream(komponent.getImage()));
        //imageD.setImage(image);
        ocen.getItems().addAll(opcjeOcen);
    }

//    public void setSpecifics(Komponent komponent) {
//        if (komponent.getNazwa() == "Aorus Elite") {
//            PłytaGłówna temp = new PłytaGłówna();
//            temp.setMaxpamięć(64);
//            opis.setText("PŁYTA GŁÓWNA"+"\n"+"Maksymalna ilość pamięci: " + temp.getMaxpamięć() + "GB");
//        } else if (komponent.getNazwa() == "1050 TI") {
//            KartaGraficzna temp = new KartaGraficzna();
//            temp.setPamięć(4);
//            opis.setText("KARTA GRAFICZNA"+"\n"+"Maksymalna ilość pamięci GPU: " + temp.getPamięć() + "GB");
//        } else if (komponent.getNazwa() == "GoodRAM") {
//            RAM temp = new RAM();
//            temp.setPamięćRAM(4);
//            opis.setText("PAMIĘĆ RAM"+"\n"+"Maksymalna ilość pamięci RAM: " + temp.getPamięćRAM() + "GB");
//        } else if (komponent.getNazwa() == "Radeon WX") {
//            KartaGraficzna temp = new KartaGraficzna();
//            temp.setPamięć(8);
//            opis.setText("KARTA GRAFICZNA"+"\n"+"Maksymalna ilość pamięci GPU: " + temp.getPamięć()+"GB");
//        } else if (komponent.getNazwa() == "I7-10700F") {
//            Procesor temp = new Procesor();
//            temp.setLiczbaWątków(16);
//            temp.setLiczbaRdzenii(8);
//            temp.setTaktowanie(4.8);
//            opis.setText("PROCESOR"+"\n"+"Liczba rdzenii: " + temp.getLiczbaRdzenii() + "\n"
//                    + "Liczba wątków: " + temp.getLiczbaWątków() + "\n"
//                    + "Częstotliwość taktowania: " + temp.getTaktowanie() + "MHz");
//        } else if (komponent.getNazwa() == "Ryzen 5") {
//            Procesor temp = new Procesor();
//            temp.setLiczbaWątków(12);
//            temp.setLiczbaRdzenii(6);
//            temp.setTaktowanie(4.4);
//            opis.setText("PROCESOR"+"\n"+"Liczba rdzenii: " + temp.getLiczbaRdzenii() + "\n"
//                    + "Liczba wątków: " + temp.getLiczbaWątków() + "\n"
//                    + "Częstotliwość taktowania: " + temp.getTaktowanie() + "MHz");
//        } else if (komponent.getNazwa() == "MSI MPG") {
//            PłytaGłówna temp = new PłytaGłówna();
//            temp.setMaxpamięć(128);
//            opis.setText("PŁYTA GŁÓWNA"+"\n"+"Maksymalna ilość pamięci: " + temp.getMaxpamięć() + "GB");
//        } else if (komponent.getNazwa() == "i3-10100F") {
//            Procesor temp = new Procesor();
//            temp.setLiczbaWątków(8);
//            temp.setLiczbaRdzenii(4);
//            temp.setTaktowanie(3.6);
//            opis.setText("PROCESOR"+"\n"+"Liczba rdzenii: " + temp.getLiczbaRdzenii() + "\n"
//                    + "Liczba wątków: " + temp.getLiczbaWątków() + "\n"
//                    + "Częstotliwość taktowania: " + temp.getTaktowanie() + "MHz");
//        } else if (komponent.getNazwa() == "Radeon RX") {
//            KartaGraficzna temp = new KartaGraficzna();
//            temp.setPamięć(16);
//            opis.setText("KARTA GRAFICZNA"+"\n"+"Maksymalna ilość pamięci GPU: " + temp.getPamięć());
//        } else if (komponent.getNazwa() == "Ram Patriot") {
//            RAM temp = new RAM();
//            temp.setPamięćRAM(8);
//            opis.setText("PAMIĘĆ RAM"+"\n"+"Maksymalna ilość pamięci RAM: " + temp.getPamięćRAM() + "GB");
//        }
//    }

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
