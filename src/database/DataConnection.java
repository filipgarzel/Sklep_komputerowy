package database;
import java.sql.*;

import classes.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.OptionalDouble;


public class DataConnection {

    private String[] dane = new String [4];
    private String[] kompData = new String[7];
    private String[] proData = new String[4];
    private String[] kaData = new String[4];
    private String[] plyData = new String[3];
    private String[] raData = new String[8];
    private String[] zaData = new String[8];

    private List<Komponent> components = new ArrayList<>();
    private List<Procesor> procesors = new ArrayList<>();
    private List<KartaGraficzna> karty = new ArrayList<>();
    private List<PłytaGłówna> plyty = new ArrayList<>();
    private List<RAM> ramy = new ArrayList<>();
    private List<Zasilacz> zasilacze = new ArrayList<>();

    ArrayList <Integer> marks = new ArrayList();


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
            kompData[6] = String.valueOf(1);


            kom.setKompID(kompData[0]);
            kom.setMarka(kompData[1]);
            kom.setNazwa(kompData[2]);
            kom.setCena(kompData[3]);
            kom.setOceny(kompData[4]);
            kom.setOpis(kompData[5]);
            kom.setIlosc(Integer.parseInt(kompData[6]));
            components.add(kom);

        }
        connection.close();
        //System.out.println(components);
        return components;
    }

    public List<Procesor> getProcesory() throws SQLException{
        Connection connection = DriverManager.getConnection("jdbc:sqlite:src\\database\\ComponentsStore.db");
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Procesory");
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            Procesor pro = new Procesor();
            proData[0] = resultSet.getString("kompID");
            proData[1] = resultSet.getString("rdzenie");
            proData[2] = resultSet.getString("watki");
            proData[3] = resultSet.getString("taktowanie");

            pro.setKompID(proData[0]);
            pro.setLiczbaRdzenii(Integer.parseInt(proData[1]));
            pro.setLiczbaWątków(Integer.parseInt(proData[2]));
            pro.setTaktowanie(Double.parseDouble(proData[3]));
            procesors.add( pro);

        }
        connection.close();
        return procesors;
    }

    public List<KartaGraficzna> getKarty() throws SQLException{
        Connection connection = DriverManager.getConnection("jdbc:sqlite:src\\database\\ComponentsStore.db");
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Karty");
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            KartaGraficzna karta = new KartaGraficzna();
            kaData[0] = resultSet.getString("kompID");
            kaData[1] = resultSet.getString("czestotliwosc");
            kaData[2] = resultSet.getString("taktowanie");
            kaData[3] = resultSet.getString("pamiec");

            karta.setKompID(kaData[0]);
            karta.setCzestotliwosc(Integer.parseInt(kaData[1]));
            karta.setTaktowanie(Integer.parseInt(kaData[2]));
            karta.setPamięć(Integer.parseInt(kaData[3]));
            karty.add(karta);

        }
        connection.close();
        return karty;
    }

    public List<PłytaGłówna> getPlyty() throws SQLException{
        Connection connection = DriverManager.getConnection("jdbc:sqlite:src\\database\\ComponentsStore.db");
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Plyty");
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            PłytaGłówna plyta = new PłytaGłówna();
            plyData[0] = resultSet.getString("kompID");
            plyData[1] = resultSet.getString("porty");
            plyData[2] = resultSet.getString("pamiec");

            plyta.setKompID(plyData[0]);
            plyta.setPorty(Integer.parseInt(plyData[1]));
            plyta.setMaxpamięć(Integer.parseInt(plyData[2]));
            plyty.add(plyta);
        }
        connection.close();
        return plyty;
    }

    public List<Zasilacz> getZasilacze() throws SQLException{
        Connection connection = DriverManager.getConnection("jdbc:sqlite:src\\database\\ComponentsStore.db");
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Zasilacze");
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            Zasilacz zasilacz = new Zasilacz();
            zaData[0] = resultSet.getString("kompID");
            zaData[1] = resultSet.getString("moc");
            zaData[2] = resultSet.getString("standard");
            zaData[3] = resultSet.getString("srednica");
            zaData[4] = resultSet.getString("wysokosc");
            zaData[5] = resultSet.getString("szerokosc");
            zaData[6] = resultSet.getString("glebokosc");
            zaData[7] = resultSet.getString("sprawnosc");

            zasilacz.setKompID(zaData[0]);
            zasilacz.setMoc(Integer.parseInt(zaData[1]));
            zasilacz.setStandard(zaData[2]);
            zasilacz.setSrednica(Integer.parseInt(zaData[3]));
            zasilacz.setWysokosc(Integer.parseInt(zaData[4]));
            zasilacz.setSzerokosc(Integer.parseInt(zaData[5]));
            zasilacz.setGlebokosc(Integer.parseInt(zaData[6]));
            zasilacz.setSprawnosc(Integer.parseInt(zaData[7]));
            zasilacze.add(zasilacz);
        }
        connection.close();
        return zasilacze;
    }

    public List<RAM> getRAM() throws SQLException{
        Connection connection = DriverManager.getConnection("jdbc:sqlite:src\\database\\ComponentsStore.db");
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM RAM");
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            RAM ram = new RAM();
            raData[0] = resultSet.getString("kompID");
            raData[1] = resultSet.getString("chlodzenie");
            raData[2] = resultSet.getString("rodzaj_pam");
            raData[3] = resultSet.getString("pojemnosc");
            raData[4] = resultSet.getString("napiecie");
            raData[5] = resultSet.getString("liczba_mod");
            raData[6] = resultSet.getString("taktowanie");
            raData[7] = resultSet.getString("opoznienie");

            ram.setKompID(raData[0]);
            ram.setChlodzenie(raData[1]);
            ram.setPamiecRAM(raData[2]);
            ram.setPojemnosc(Integer.parseInt(raData[3]));
            ram.setNapiecie(Double.parseDouble(raData[4]));
            ram.setLiczbaModulow(Integer.parseInt(raData[5]));
            ram.setTaktowanie(raData[6]);
            ram.setOpoznienie(raData[7]);
            ramy.add(ram);
        }
        connection.close();
        return ramy;
    }

    public String getTable(String x) throws SQLException{
        String tableName;
        Connection connection = DriverManager.getConnection("jdbc:sqlite:src\\database\\ComponentsStore.db");

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT kompID FROM RAM;");
        tableName = String.valueOf(preparedStatement);
        System.out.println(tableName);
        connection.close();
        return tableName;
    }

    public void addRating(String x, String productName) throws SQLException {
        System.out.println("wywolanie add rating");
        Connection connection = DriverManager.getConnection("jdbc:sqlite:src\\database\\ComponentsStore.db");
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Komponent SET srednia_ocen= ? WHERE nazwa= '" +productName + "'");
        preparedStatement.setString(1, x);
        marks.add(Integer.valueOf(x));
        preparedStatement.executeUpdate();
        connection.close();
    }

    public void calcRating(String x, String productName) throws SQLException {
        System.out.println("wywolanie calc rating");
        marks.add(Integer.valueOf(x));
        Connection connection = DriverManager.getConnection("jdbc:sqlite:src\\database\\ComponentsStore.db");
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Komponent SET srednia_ocen= ? WHERE nazwa= '" +productName + "'");
        OptionalDouble average = marks
                .stream()
                .mapToDouble(a -> a)
                .average();
        double y = average.getAsDouble();
        System.out.println("wszystkie oceny: " + marks);
        System.out.println("srednia = " + y);
        preparedStatement.setString(1, String.valueOf(y));
        preparedStatement.executeUpdate();
        connection.close();
    }


    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        DataConnection dataConnection = new DataConnection();
        dataConnection.selectUsers();
        System.out.println(dataConnection.getTable("30"));

    }

}
