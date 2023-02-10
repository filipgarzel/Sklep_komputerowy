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
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;


public class HelloController implements Initializable {
    @FXML
    private Label logged;

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
    private MyListener myListener;
    private static String maill;
    private Komponent komponent = new Komponent();
    private Komponent temp;
    private List<Komponent> components = new ArrayList<>();
    private List<Komponent> wKoszyku = new ArrayList<>();
    private String[] opcjeFiltru = {"Procesory", "Karty graficzne", "Płyty główne", "Zasilacze", "RAM", "All"};
    private String[] opcjeSortowania = {"ROSNĄCO: po cenie", "MALEJĄCO: po cenie", "ROSNĄCO: po ocenach", "MALEJĄCO: po ocenach", ""};
    private DataConnection dataConnection = new DataConnection();
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
            usr.setSaldo(dataConnection.getKwota(maill));
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
        usr.setEmail(dataConnection.getAdres(maill)[4]);
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


    public void filtrowanie(ActionEvent event)  {
        if (filtruj.getValue() == "Procesory") {
            int x = 0;
            int array[] = new int[sizeP];
            components.clear();
            for(int i=0; i<sizeP; i++) {
                try {
                    array[i] = Integer.parseInt(dataConnection.getProcesory().get(i).getKompID());
                } catch (SQLException e) {e.printStackTrace();}
            }
            for(int i=0; i<sizeP; i++) {
                try {
                    if (dataConnection.getKomponent().get(i).getKompID().equals(String.valueOf(array[i]))) {
                        if (dataConnection.getKomponent().get(i).getNazwa().equals(dataConnection.getKomponent().get(i+1).getNazwa())){
                            x += 1;
                            continue;
                        }
                        else{
                            int occurrences = x+1;
                            dataConnection.getKomponent().get(i).setIlosc(occurrences);
                            components.add(dataConnection.getKomponent().get(i));
                            x = 0;
                        }
                    }
                } catch (SQLException e) {e.printStackTrace();}
            }
            loadGrid(components, mygrid);
        }


        else if (filtruj.getValue() == "Karty graficzne") {
            int x = 0;
            int array[] = new int[sizeK];
            components.clear();
            for(int i=0; i<sizeK; i++) {
                try {
                    array[i] = Integer.parseInt(dataConnection.getKarty().get(i).getKompID());
                } catch (SQLException e) {e.printStackTrace();}
            }
            for(int i=sizeP; i<sizeP+sizeK; i++) {
                try {
                    if (dataConnection.getKomponent().get(i).getKompID().equals(String.valueOf(array[i-sizeP]))) {
                        if (dataConnection.getKomponent().get(i).getNazwa().equals(dataConnection.getKomponent().get(i+1).getNazwa())){
                            x += 1;
                            continue;
                        }
                        else {
                            int occurrences = x + 1;
                            dataConnection.getKomponent().get(i).setIlosc(occurrences);
                            components.add(dataConnection.getKomponent().get(i));
                            x = 0;
                        }
                    }
                } catch (SQLException e) {e.printStackTrace();}
            }
            loadGrid(components, mygrid);
        }


        else if (filtruj.getValue() == "Płyty główne") {
            int x = 0;
            int array[] = new int[sizePl];
            components.clear();
            for(int i=0; i<sizePl; i++) {
                try {
                    array[i] = Integer.parseInt(dataConnection.getPlyty().get(i).getKompID());
                } catch (SQLException e) {e.printStackTrace();}
            }
            for(int i=sizeP+sizeK; i<sizeP+sizeK+sizePl; i++) {
                try {
                    if (dataConnection.getKomponent().get(i).getKompID().equals(String.valueOf(array[i-sizeP-sizeK]))) {
                        if (dataConnection.getKomponent().get(i).getNazwa().equals(dataConnection.getKomponent().get(i+1).getNazwa())){
                            x += 1;
                            continue;
                        }
                        else {
                            int occurrences = x + 1;
                            dataConnection.getKomponent().get(i).setIlosc(occurrences);
                            components.add(dataConnection.getKomponent().get(i));
                            x = 0;
                        }
                    }
                } catch (SQLException e) {e.printStackTrace();}
            }
            loadGrid(components, mygrid);
        }

        else if (filtruj.getValue() == "Zasilacze") {
            int x = 0;
            int array[] = new int[sizeZ];
            components.clear();
            for(int i=0; i<sizeZ; i++) {
                try {
                    array[i] = Integer.parseInt(dataConnection.getZasilacze().get(i).getKompID());
                } catch (SQLException e) {e.printStackTrace();}
            }
            for(int i=sizeP+sizeK+sizePl; i<sizeP+sizeK+sizePl+sizeZ; i++) {
                try {
                    if (dataConnection.getKomponent().get(i).getKompID().equals(String.valueOf(array[i-sizeP-sizeK-sizePl]))) {
                        if (dataConnection.getKomponent().get(i).getNazwa().equals(dataConnection.getKomponent().get(i+1).getNazwa())){
                            x += 1;
                            continue;
                        }
                        else {
                            int occurrences = x + 1;
                            dataConnection.getKomponent().get(i).setIlosc(occurrences);
                            components.add(dataConnection.getKomponent().get(i));
                            x = 0;
                        }
                    }
                } catch (SQLException e) {e.printStackTrace();}
            }
            loadGrid(components, mygrid);
        }

        else if (filtruj.getValue() == "RAM") {
            int x = 0;
            int array[] = new int[sizeR];
            components.clear();
            for(int i=0; i<sizeR; i++) {
                try {
                    array[i] = Integer.parseInt(dataConnection.getRAM().get(i).getKompID());
                } catch (SQLException e) {e.printStackTrace();}
            }
            for(int i=sizeP+sizeK+sizePl+sizeZ; i<sizeP+sizeK+sizePl+sizeZ+sizeR; i++) {
                try {
                    if (dataConnection.getKomponent().get(i).getKompID().equals(String.valueOf(array[i-sizeP-sizeK-sizePl-sizeZ]))) {
                        if (dataConnection.getKomponent().get(i).getNazwa().equals(dataConnection.getKomponent().get(i+1).getNazwa())){
                            x += 1;
                            continue;
                        }
                        else {
                            int occurrences = x + 1;
                            dataConnection.getKomponent().get(i).setIlosc(occurrences);
                            components.add(dataConnection.getKomponent().get(i));
                            x = 0;
                        }
                    }
                } catch (SQLException e) {e.printStackTrace();}
            }
            loadGrid(components, mygrid);
        }

        else {
            components.clear();
            try {
                components.addAll(getData());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            loadGrid(components, mygrid);
        }
    }


    private List<Komponent> getData() throws SQLException {
        int x = 0;
        List<Komponent> components2 = new ArrayList<>();

        for(int i=0; i<size; i++){
            if (dataConnection.getKomponent().get(i).getNazwa().equals(dataConnection.getKomponent().get(i+1).getNazwa())){
                x += 1;
                continue;
            }
            else{
                int occurrences = x+1;
                //System.out.println("occurrences: " + x);
                dataConnection.getKomponent().get(i).setIlosc(occurrences);
                //System.out.println(dataConnection.getKomponent().get(i).getIlosc());
                components2.add(dataConnection.getKomponent().get(i));
                x = 0;
            }
        }

        return components2;
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

    private void sortowanie(ActionEvent event) {
        if (sortuj.getValue() == "ROSNĄCO: po cenie") {
            int pom;
            Komponent actual;
            for (int i = 1; i < components.size(); i++) {
                pom = i;
                actual = components.get(i);
                while (pom > 0 && Integer.parseInt(components.get(pom - 1).getCena()) > Integer.parseInt(actual.getCena())) {
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
                while (pom > 0 && Integer.parseInt(components.get(pom - 1).getCena()) < Integer.parseInt(actual.getCena())) {
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
                while (pom > 0 && Integer.parseInt(components.get(pom - 1).getOceny()) > Integer.parseInt(actual.getCena())) {
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
                while (pom > 0 && Integer.parseInt(components.get(pom - 1).getOceny()) < Integer.parseInt(actual.getCena())) {
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
        chosenprice.setText(komponent.getCena() + "zł");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        filtruj.getItems().addAll(opcjeFiltru);
        sortuj.getItems().addAll(opcjeSortowania);
        try {
            components = getData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        filtruj.setOnAction(this::filtrowanie);
        sortuj.setOnAction(this::sortowanie);
        myListener = new MyListener() {
            @Override
            public void onClickListener(Komponent komponent) {
                setChosenComponent(komponent);
                temp = komponent;
            };


        };
        loadGrid(components, mygrid);
    }
}