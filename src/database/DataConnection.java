package database;
import java.math.BigDecimal;
import java.sql.*;
import java.util.Date;

import classes.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.OptionalDouble;
import java.sql.Connection;
import java.sql.DriverManager;


public class DataConnection {

    int kwota;
    private String[] dane = new String [5];
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
        Class.forName("com.mysql.cj.jdbc.Driver");
    }
    public void selectUsers() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/computershop","default", "haslodefault");
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM myuser");
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            System.out.println(resultSet.getString("email"));
            System.out.println(resultSet.getString("haslo"));
        }
        connection.close();
    }

    public void insertUsers(User user) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/computershop","default", "haslodefault");
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO myuser (email, haslo, miasto, ulica, nr_domu, kod_pocztowy, saldo) VALUES(?,?,?,?,?,?,?)");
        preparedStatement.setString(1, user.getEmail());
        preparedStatement.setString(2, user.getHasło());
        preparedStatement.setString(3, user.getMiasto());
        preparedStatement.setString(4, user.getUlica());
        preparedStatement.setInt(5, user.getNrDomu());
        preparedStatement.setInt(6, user.getKodPocztowy());
        preparedStatement.setInt(7, 2000);
        preparedStatement.executeUpdate();
        connection.close();
    }
    private static boolean porównaj;
    public boolean logging(String mail, String hasło) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/computershop","default", "haslodefault");
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM myuser");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            if (Objects.equals(resultSet.getString("email"),mail) && Objects.equals(resultSet.getString("haslo"),hasło)){
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
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/computershop","default", "haslodefault");
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM myuser");
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            if (Objects.equals(resultSet.getString("email"),mail)){
                //resultSet.getString("miasto");
                dane[0] =resultSet.getString("miasto");
                dane[1] =resultSet.getString("ulica");
                dane[2] =resultSet.getString("nr_domu");
                dane[3] =resultSet.getString("kod_pocztowy");
                dane[4] =resultSet.getString("email");
                break;
            }else{
                dane[0] ="brak danych";
                dane[1] ="brak danych";
                dane[2] ="0";
                dane[3] ="0";
                dane[4] ="brak danych";
            }
        }
        connection.close();
        return dane;
    }

    public int getKwota(String mail) throws SQLException{

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/computershop","default", "haslodefault");
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT saldo FROM myuser WHERE email = '" +mail + "'");
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()) {
            kwota = Integer.parseInt(resultSet.getString("saldo"));
        }
        connection.close();
        System.out.println(kwota);
        return kwota;
    }

    public List<Komponent> getKomponent() throws SQLException{
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/computershop","default", "haslodefault");
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM komponent");
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
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/computershop","default", "haslodefault");
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM procesory");
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
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/computershop","default", "haslodefault");
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM karty");
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
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/computershop","default", "haslodefault");
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM plyty");
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
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/computershop","default", "haslodefault");
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM zasilacze");
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
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/computershop","default", "haslodefault");
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM ram");
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


    public void addRating(String x, String productName) throws SQLException {
        System.out.println("wywolanie add rating");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/computershop","default", "haslodefault");
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE komponent SET suma_ocen=?,ilosc_ocen=?, srednia_ocen= ? WHERE nazwa= '" +productName + "'");
        preparedStatement.setInt(1, Integer.parseInt(x));
        preparedStatement.setInt(2,1);
        preparedStatement.setBigDecimal(3, BigDecimal.valueOf(Long.parseLong(x)));
        marks.add(Integer.valueOf(x));
        preparedStatement.executeUpdate();
        connection.close();
    }

    public void newRating(String x, String productName) throws SQLException{
        int suma = 0, ilosc_ocen=0;
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/computershop","default", "haslodefault");
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT suma_ocen, ilosc_ocen FROM komponent WHERE nazwa= '" +productName + "'");
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()) {
            suma = resultSet.getInt("suma_ocen");
            ilosc_ocen = resultSet.getInt("ilosc_ocen");
        }
        int newSuma = suma  + Integer.parseInt(x);
        int newIlosc = ilosc_ocen + 1;
        calcRating(newSuma, newIlosc, productName);
        connection.close();
    }


    public void calcRating(int x, int y, String productName) throws SQLException {
        System.out.println("wywolanie calc rating");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/computershop","default", "haslodefault");
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Komponent SET suma_ocen=?,ilosc_ocen=?,srednia_ocen= ? WHERE nazwa= '" +productName + "'");
        float dzielenie = (float)x/y;
        double srednia = Math.round(dzielenie*Math.pow(10, 2))
                / Math.pow(10, 2);
        System.out.println("dzielenie: " + dzielenie);
        System.out.println("srednia: " + srednia);
        preparedStatement.setInt(1, x);
        preparedStatement.setInt(2,y);
        preparedStatement.setDouble(3, srednia);
        preparedStatement.executeUpdate();
        connection.close();
    }
    public int getIlosc(String nazwa) throws SQLException{
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/computershop","default", "haslodefault");
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) AS ile FROM komponent WHERE status=0 AND nazwa = '" +nazwa + "'");
        ResultSet resultSet = preparedStatement.executeQuery();
        int ilosc = 0;
        if (resultSet.next()) {
            ilosc = resultSet.getInt("ile");
        }
        connection.close();
        return ilosc;
    }

    public void buying(String email, int suma, List<Komponent> koszyk) throws SQLException {
        Date date = new Date();
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/computershop","default", "haslodefault");
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE myuser SET saldo = saldo - '" +suma + "'");
        preparedStatement.executeUpdate();

        for (int i=0; i<koszyk.size();i++){
            PreparedStatement helper1 = connection.prepareStatement("SELECT kompID from komponent where status = 0 and nazwa = '" +koszyk.get(i).getNazwa() + "' LIMIT 1");
            ResultSet resultSet1 = helper1.executeQuery();
            int idik=0;
            if (resultSet1.next()) {
                idik = resultSet1.getInt("kompID");
            }
            PreparedStatement preparedStatement1 = connection.prepareStatement("UPDATE komponent SET status = 1 WHERE kompID = '" +idik + "' ");
            preparedStatement1.executeUpdate();
            PreparedStatement helper = connection.prepareStatement("SELECT ID FROM myuser WHERE email= '" +email + "'");
            ResultSet resultSet = helper.executeQuery();
            int drugiidik=0;
            if (resultSet.next()) {
                drugiidik = resultSet.getInt("ID");
            }
            PreparedStatement preparedStatement2 = connection.prepareStatement("INSERT INTO zakupy (ID, kompID, data) VALUES(?,?,?)");
            preparedStatement2.setInt(1, drugiidik);
            preparedStatement2.setInt(2, idik);
            preparedStatement2.setString(3, date.toString());
            System.out.println(drugiidik);
            preparedStatement2.executeUpdate();


        }




        connection.close();
    }

    public List<String> Selecting(String email) throws SQLException {
        List<String> components = new ArrayList<>();
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/computershop","default", "haslodefault");
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM myuser WHERE email = '" +email + "'");
        ResultSet resultSet = preparedStatement.executeQuery();
        String usr="";
        while(resultSet.next()){
            usr = resultSet.getString("ID");

        }
        PreparedStatement preparedStatement1 = connection.prepareStatement("SELECT * FROM zakupy WHERE ID = '" +usr + "'");
        ResultSet resultSet1 = preparedStatement1.executeQuery();
        String nazwa="";
        String realnazwa="";
        String cena="";
        String line="";
        while(resultSet1.next()){
            nazwa = resultSet1.getString("kompID");
            PreparedStatement preparedStatement2 = connection.prepareStatement("SELECT * FROM komponent WHERE kompID = '" +nazwa + "'");
            ResultSet resultSet2 = preparedStatement2.executeQuery();
            while(resultSet2.next()){
                realnazwa = resultSet2.getString("nazwa");
                cena = resultSet2.getString("cena");
            }
            line = realnazwa + "   " + cena;
            components.add(line);

        }
        connection.close();
        return components;
    }


    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        DataConnection dataConnection = new DataConnection();
        dataConnection.selectUsers();

    }

}
