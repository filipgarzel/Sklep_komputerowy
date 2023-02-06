package database;
import java.sql.*;

import classes.Komponent;
import classes.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class DataConnection {

    private String[] dane = new String [4];
    private String[] kompData = new String[7];
    private List<Komponent> components = new ArrayList<>();
    List<List<Komponent>> listOfLists = new ArrayList<List<Komponent>>();
    private Komponent komponent = new Komponent();
    List<String[]> rowList = new ArrayList<String[]>();
    private int x = 0;

    public DataConnection() throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
    }
    public void selectUsers() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:src\\database\\ComponentsStore.db");
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM User");
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            System.out.println(resultSet.getString("login"));
            System.out.println(resultSet.getString("password"));
        }
        connection.close();
    }

    public void insertUsers(User user) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:src\\database\\ComponentsStore.db");
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users (login, password, miasto, ulica, nrdomu, kodpocztowy) VALUES(?,?,?,?,?,?)");
        preparedStatement.setString(1, user.getEmail());
        preparedStatement.setString(2, user.getHasło());
        preparedStatement.setString(3, user.getMiasto());
        preparedStatement.setString(4, user.getUlica());
        preparedStatement.setInt(5, user.getNrDomu());
        preparedStatement.setInt(6, user.getKodPocztowy());
        preparedStatement.executeUpdate();
        connection.close();
    }
    private static boolean porównaj;
    public boolean logging(String mail, String hasło) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:src\\database\\ComponentsStore.db");
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            if (Objects.equals(resultSet.getString("login"),mail) && Objects.equals(resultSet.getString("password"),hasło)){
                porównaj = true;
                break;
            }else{
                porównaj = false;
            }
        }
        connection.close();
        return porównaj;

    }

    public String[] getAdres(String mail) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:src\\database\\ComponentsStore.db");
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users");
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            if (Objects.equals(resultSet.getString("login"),mail)){
                //resultSet.getString("miasto");
                dane[0] =resultSet.getString("miasto");
                dane[1] =resultSet.getString("ulica");
                dane[2] =resultSet.getString("nrdomu");
                dane[3] =resultSet.getString("kodpocztowy");
                break;
            }else{
                dane[0] ="brak danych";
                dane[1] ="brak danych";
                dane[2] ="0";
                dane[3] ="0";
            }
        }
        connection.close();
        return dane;
    }

    public List<Komponent> getKomponent() throws SQLException{
        Connection connection = DriverManager.getConnection("jdbc:sqlite:src\\database\\ComponentsStore.db");
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Komponent");
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){

            Komponent kom = new Komponent();
            kompData[0] = resultSet.getString("kompID");
            kompData[1] = resultSet.getString("marka");
            kompData[2] = resultSet.getString("nazwa");
            kompData[3] = resultSet.getString("cena");
            kompData[4] = resultSet.getString("srednia_ocen");
            kompData[5] = resultSet.getString("opis");


            kom.setKompID(kompData[0]);
            kom.setMarka(kompData[1]);
            kom.setNazwa(kompData[2]);
            kom.setCena(kompData[3]);
            kom.setOceny(kompData[4]);
            kom.setOpis(kompData[5]);
            components.add(kom);


        }
        connection.close();
        //System.out.println(components);
        return components;
    }


    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        DataConnection dataConnection = new DataConnection();
        dataConnection.selectUsers();


    }

}
