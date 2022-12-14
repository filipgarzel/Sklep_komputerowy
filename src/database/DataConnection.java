package database;
import java.sql.*;
import classes.User;
import java.text.MessageFormat;
import java.util.Objects;


public class DataConnection {

    private String[] dane = new String [4];

    public DataConnection() throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
    }
    public void selectUsers() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:sqlite:src\\database\\ComponentsStore.db");
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users");
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



    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        DataConnection dataConnection = new DataConnection();
        dataConnection.selectUsers();

    }

}
