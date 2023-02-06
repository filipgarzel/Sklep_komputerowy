package gui;

import classes.*;
import database.DataConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;


public class HelloController implements Initializable {
    @FXML
    private Label logged;

    @FXML
    private TextArea opis;

    @FXML
    private TextField haslo;

    @FXML
    private TextField kod;

    @FXML
    private TextField mail;

    @FXML
    private TextField miasto;

    @FXML
    private TextField nrdomu;

    @FXML
    private TextField ulica;

    @FXML
    private ImageView chosenimg;

    @FXML
    private Label chosenname;

    @FXML
    private Label chosenprice;

    @FXML
    private GridPane mygrid = new GridPane();

    @FXML
    private PasswordField wpiszhaslo;

    @FXML
    private TextField wpiszmail;

    @FXML
    private Text infoZarejestruj;

    @FXML
    private ChoiceBox<String> filtruj = new ChoiceBox<>();

    @FXML
    private ChoiceBox<String> sortuj = new ChoiceBox<>();


    private Stage stage;
    private Scene scene;
    private static User usr = new User();
    private Image obraz;
    private MyListener myListener;
    private static String maill;
    private Komponent komponent;
    private Komponent temp;
    private List<Komponent> components = new ArrayList<>();
    private List<Komponent> wKoszyku = new ArrayList<>();
    private String[] opcjeFiltru = {"Procesory", "Karty graficzne", "Płyty główne", "RAM", ""};
    private String[] opcjeSortowania = {"ROSNĄCO: po cenie", "MALEJĄCO: po cenie", "ROSNĄCO: po ocenach", "MALEJĄCO: po ocenach", ""};
    private DataConnection dataConnection = new DataConnection();


    public HelloController() throws ClassNotFoundException {
    }

    public boolean isInteger(String input) {
        if (input != null) {
            try {
                Integer.parseInt(input);
                return true;
            } catch (Exception e) {
                return false;
            }
        } else {
            return false;
        }

    }

    public void submit(ActionEvent event) throws SQLException, ClassNotFoundException {

        if (!mail.getText().contains("@")) {
            infoZarejestruj.setText("Podaj prawidłowy email (zawierający '@')");
        } else if (haslo.getText().length() < 1) {
            infoZarejestruj.setText("Podaj hasło");
        } else if (miasto.getText().length() < 2) {
            infoZarejestruj.setText("Podaj prawidłowe miasto (więcej niż 3 znaki)");
        } else if (ulica.getText().length() < 2) {
            infoZarejestruj.setText("Podaj prawidłową ulicę (więcej niż 3 znaki)");
        } else if (isInteger(nrdomu.getText()) == false) {
            infoZarejestruj.setText("Podaj prawidłowy nr domu");
        } else if (isInteger(kod.getText()) == false) {
            infoZarejestruj.setText("Podaj prawidłowy kod");
        } else {
            usr.setEmail(mail.getText());
            usr.setHasło(haslo.getText());
            usr.setMiasto(miasto.getText());
            usr.setUlica(ulica.getText());
            usr.setNrDomu(Integer.parseInt(nrdomu.getText()));
            usr.setKodPocztowy(Integer.parseInt(kod.getText()));
            dataConnection.insertUsers(usr);
            infoZarejestruj.setText("Formularz wypełniony prawidłowo. Witamy!");

        }
    }

    public void potwierdzlog(ActionEvent event) throws SQLException, ClassNotFoundException {
        if (dataConnection.logging(wpiszmail.getText(), wpiszhaslo.getText())==true) {
            usr.setZalogowany(true);
            logged.setText("Zalogowano");
            maill = wpiszmail.getText();
            usr.setSaldo(10000);
        } else {
            logged.setText("Nie ma takiego użytkownika");
        }
    }

    public void switchtoStart(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(HelloApplication.class.getResource("hello-view.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchtoZaloguj(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(HelloApplication.class.getResource("Zaloguj.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchtoPrzegladaj(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(HelloApplication.class.getResource("Przeglądaj.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchtoZarejestruj(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(HelloApplication.class.getResource("Zarejestruj.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchtoSzczegoly(ActionEvent event)throws Exception {
        //FXMLLoader fxmlLoader = FXMLLoader.load(HelloApplication.class.getResource("details.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("details.fxml"));
        //AnchorPane anchor = fxmLoader.load();
        Parent root = fxmlLoader.load();
        DetailsController detailsController = fxmlLoader.getController();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        detailsController.setDetails(temp, myListener);
        detailsController.setSpecifics(temp);
    }

    public void switchtoKoszyk(ActionEvent event) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("koszyk.fxml"));
        Parent root = fxmlLoader.load();
        KoszykController koszykController = fxmlLoader.getController();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        //System.out.println(usr.getZalogowany());
        usr.setMiasto(dataConnection.getAdres(maill)[0]);
        usr.setUlica(dataConnection.getAdres(maill)[1]);
        usr.setNrDomu(Integer.parseInt(dataConnection.getAdres(maill)[2]));
        usr.setKodPocztowy(Integer.parseInt(dataConnection.getAdres(maill)[3]));
        if (usr.getZalogowany() == true) {
            koszykController.setDane(usr.getMiasto(), usr.getUlica(), Integer.toString(usr.getNrDomu()), Integer.toString(usr.getKodPocztowy()));
        }else{
            koszykController.setDane("brak danych", "brak danych", "brak danych", "brak danych");
        }
        koszykController.odśwież(wKoszyku, usr);
    }

    public void dodajDoKoszyka(ActionEvent event) {
        wKoszyku.add(temp);
    }


    public void filtrowanie(ActionEvent event) {
        if (filtruj.getValue() == "Procesory") {
            components.clear();
            components.addAll(getData());
            for (int i = 0; i < components.size(); i++) {
                if (components.get(i) instanceof Procesor == false) {
                    components.remove(i);
                    i -= 1;
                }
            }
            loadGrid(components, mygrid);
        } else if (filtruj.getValue() == "Karty graficzne") {
            components.clear();
            components.addAll(getData());
            for (int i = 0; i < components.size(); i++) {
                if (components.get(i) instanceof KartaGraficzna == false) {
                    components.remove(i);
                    i -= 1;
                }
            }
            loadGrid(components, mygrid);
        } else if (filtruj.getValue() == "Płyty główne") {
            components.clear();
            components.addAll(getData());
            for (int i = 0; i < components.size(); i++) {
                if (components.get(i) instanceof PłytaGłówna == false) {
                    components.remove(i);
                    i -= 1;
                }
            }
            loadGrid(components, mygrid);
        } else if (filtruj.getValue() == "RAM") {
            components.clear();
            components.addAll(getData());
            for (int i = 0; i < components.size(); i++) {
                if (components.get(i) instanceof RAM == false) {
                    components.remove(i);
                    i -= 1;
                }
            }
            loadGrid(components, mygrid);
        } else {
            components.clear();
            components.addAll(getData());
            loadGrid(components, mygrid);
        }
    }




    private List<Komponent> getData() {
        List<Komponent> components = new ArrayList<>();


        komponent = new PłytaGłówna();
        komponent.setNazwa("Aorus Elite");
        komponent.setCena(400);
        komponent.setImage("img/gigaabyte.jpg");
        komponent.setOceny(7.9);
        components.add(komponent);

        komponent = new KartaGraficzna();
        komponent.setNazwa("1050 TI");
        komponent.setCena(1350);
        komponent.setImage("img/1050ti.jpg");
        komponent.setOceny(6.5);
        components.add(komponent);

        komponent = new RAM();
        komponent.setNazwa("GoodRAM");
        komponent.setCena(75);
        komponent.setImage("img/goodram.jpg");
        komponent.setOceny(7.7);
        components.add(komponent);

        komponent = new KartaGraficzna();
        komponent.setNazwa("Radeon WX");
        komponent.setCena(800);
        komponent.setImage("img/amdradeonpro.jpg");
        komponent.setOceny(7.1);
        components.add(komponent);

        komponent = new Procesor();
        komponent.setNazwa("I7-10700F");
        komponent.setCena(1200);
        komponent.setImage("img/procesori7.jpg");
        komponent.setOceny(8.5);
        components.add(komponent);

        komponent = new Procesor();
        komponent.setNazwa("Ryzen 5");
        komponent.setCena(1400);
        komponent.setImage("img/amdprocesor.jpg");
        komponent.setOceny(8.1);
        components.add(komponent);

        komponent = new PłytaGłówna();
        komponent.setNazwa("MSI MPG");
        komponent.setCena(1000);
        komponent.setImage("img/msi.jpg");
        komponent.setOceny(9.0);
        components.add(komponent);

        komponent = new Procesor();
        komponent.setNazwa("i3-10100F");
        komponent.setCena(350);
        komponent.setImage("img/i3procesor.jpg");
        komponent.setOceny(7.0);
        components.add(komponent);

        komponent = new KartaGraficzna();
        komponent.setNazwa("Radeon RX");
        komponent.setCena(10000);
        komponent.setImage("img/asus.jpg");
        komponent.setOceny(9.5);
        components.add(komponent);

        komponent = new RAM();
        komponent.setNazwa("Ram Patriot");
        komponent.setCena(250);
        komponent.setImage("img/patriotram.jpg");
        komponent.setOceny(8.3);
        components.add(komponent);

        return components;
    }

    private void loadGrid(List<Komponent> componentss, GridPane grid) {
        grid.getChildren().clear();
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < componentss.size(); i++) {
                FXMLLoader fxmLoader = new FXMLLoader(getClass().getResource("items.fxml"));
                AnchorPane anchor = fxmLoader.load();
                ItemController itemcontroller = fxmLoader.getController();
                itemcontroller.setData(componentss.get(i), myListener);
                if (column == 2) {
                    column = 0;
                    row++;
                }
                grid.add(anchor, ++column, row);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    private void setSpecifics() {
//        if (temp.getNazwa() == "Aorus Elite") {
//            PłytaGłówna temp = new PłytaGłówna();
//            temp.setMaxpamięć(64);
//            opis.setText("PŁYTA GŁÓWNA"+"\n"+"Maksymalna ilość pamięci: " + temp.getMaxpamięć() + "GB");
//        } else if (temp.getNazwa() == "1050 TI") {
//            KartaGraficzna temp = new KartaGraficzna();
//            temp.setPamięć(4);
//            opis.setText("KARTA GRAFICZNA"+"\n"+"Maksymalna ilość pamięci GPU: " + temp.getPamięć() + "GB");
//        } else if (temp.getNazwa() == "GoodRAM") {
//            RAM temp = new RAM();
//            temp.setPamięćRAM(4);
//            opis.setText("PAMIĘĆ RAM"+"\n"+"Maksymalna ilość pamięci RAM: " + temp.getPamięćRAM() + "GB");
//        } else if (temp.getNazwa() == "Radeon WX") {
//            KartaGraficzna temp = new KartaGraficzna();
//            temp.setPamięć(8);
//            opis.setText("KARTA GRAFICZNA"+"\n"+"Maksymalna ilość pamięci GPU: " + temp.getPamięć()+"GB");
//        } else if (temp.getNazwa() == "I7-10700F") {
//            Procesor temp = new Procesor();
//            temp.setLiczbaWątków(16);
//            temp.setLiczbaRdzenii(8);
//            temp.setTaktowanie(4.8);
//            opis.setText("PROCESOR"+"\n"+"Liczba rdzenii: " + temp.getLiczbaRdzenii() + "\n"
//                    + "Liczba wątków: " + temp.getLiczbaWątków() + "\n"
//                    + "Częstotliwość taktowania: " + temp.getTaktowanie() + "MHz");
//        } else if (temp.getNazwa() == "Ryzen 5") {
//            Procesor temp = new Procesor();
//            temp.setLiczbaWątków(12);
//            temp.setLiczbaRdzenii(6);
//            temp.setTaktowanie(4.4);
//            opis.setText("PROCESOR"+"\n"+"Liczba rdzenii: " + temp.getLiczbaRdzenii() + "\n"
//                    + "Liczba wątków: " + temp.getLiczbaWątków() + "\n"
//                    + "Częstotliwość taktowania: " + temp.getTaktowanie() + "MHz");
//        } else if (temp.getNazwa() == "MSI MPG") {
//            PłytaGłówna temp = new PłytaGłówna();
//            temp.setMaxpamięć(128);
//            opis.setText("PŁYTA GŁÓWNA"+"\n"+"Maksymalna ilość pamięci: " + temp.getMaxpamięć() + "GB");
//        } else if (temp.getNazwa() == "i3-10100F") {
//            Procesor temp = new Procesor();
//            temp.setLiczbaWątków(8);
//            temp.setLiczbaRdzenii(4);
//            temp.setTaktowanie(3.6);
//            opis.setText("PROCESOR"+"\n"+"Liczba rdzenii: " + temp.getLiczbaRdzenii() + "\n"
//                    + "Liczba wątków: " + temp.getLiczbaWątków() + "\n"
//                    + "Częstotliwość taktowania: " + temp.getTaktowanie() + "MHz");
//        } else if (temp.getNazwa() == "Radeon RX") {
//            KartaGraficzna temp = new KartaGraficzna();
//            temp.setPamięć(16);
//            opis.setText("KARTA GRAFICZNA"+"\n"+"Maksymalna ilość pamięci GPU: " + temp.getPamięć());
//        } else if (temp.getNazwa() == "Ram Patriot") {
//            RAM temp = new RAM();
//            temp.setPamięćRAM(8);
//            opis.setText("PAMIĘĆ RAM"+"\n"+"Maksymalna ilość pamięci RAM: " + temp.getPamięćRAM() + "GB");
//        }
//
//    }

    private void sortowanie(ActionEvent event) {
        if (sortuj.getValue() == "ROSNĄCO: po cenie") {
            int pom;
            Komponent actual;
            for (int i = 1; i < components.size(); i++) {
                pom = i;
                actual = components.get(i);
                while (pom > 0 && components.get(pom - 1).getCena() > actual.getCena()) {
                    components.set(pom, components.get(pom - 1));
                    pom--;
                }
                components.set(pom, actual);
            }

        } else if (sortuj.getValue() == "MALEJĄCO: po cenie") {
            int pom;
            Komponent actual;
            for (int i = 1; i < components.size(); i++) {
                pom = i;
                actual = components.get(i);
                while (pom > 0 && components.get(pom - 1).getCena() < actual.getCena()) {
                    components.set(pom, components.get(pom - 1));
                    pom--;
                }
                components.set(pom, actual);
            }
        } else if (sortuj.getValue() == "ROSNĄCO: po ocenach") {
            int pom;
            Komponent actual;
            for (int i = 1; i < components.size(); i++) {
                pom = i;
                actual = components.get(i);
                while (pom > 0 && components.get(pom - 1).getOceny() > actual.getOceny()) {
                    components.set(pom, components.get(pom - 1));
                    pom--;
                }
                components.set(pom, actual);
            }
        } else if (sortuj.getValue() == "MALEJĄCO: po ocenach") {
            int pom;
            Komponent actual;
            for (int i = 1; i < components.size(); i++) {
                pom = i;
                actual = components.get(i);
                while (pom > 0 && components.get(pom - 1).getOceny() < actual.getOceny()) {
                    components.set(pom, components.get(pom - 1));
                    pom--;
                }
                components.set(pom, actual);
            }

        }
        loadGrid(components, mygrid);
    }


    private void setChosenComponent(Komponent komponent) {
        chosenname.setText(komponent.getNazwa());
        chosenprice.setText(Integer.toString(komponent.getCena()) + "zł");
        obraz = new Image(getClass().getResourceAsStream(komponent.getImage()));
        chosenimg.setImage(obraz);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        filtruj.getItems().addAll(opcjeFiltru);
        sortuj.getItems().addAll(opcjeSortowania);
        components.addAll(getData());
        filtruj.setOnAction(this::filtrowanie);
        sortuj.setOnAction(this::sortowanie);
        myListener = new MyListener() {
            @Override
            public void onClickListener(Komponent komponent) {
                setChosenComponent(komponent);
                temp = komponent;
                //setSpecifics();
            };


        };
        loadGrid(components, mygrid);
    }
}