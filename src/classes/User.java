package classes;

public class User {
    private String email;
    private String hasło;
    private String miasto;
    private String ulica;
    private int nrDomu;
    private int kodPocztowy;
    private boolean zalogowany = false;
    private int saldo=0;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHasło() {
        return hasło;
    }

    public void setHasło(String hasło) {
        this.hasło = hasło;
    }

    public String getMiasto() {
        return miasto;
    }

    public void setMiasto(String miasto) {
        this.miasto = miasto;
    }

    public String getUlica() {
        return ulica;
    }

    public void setUlica(String ulica) {
        this.ulica = ulica;
    }

    public int getNrDomu() {
        return nrDomu;
    }

    public void setNrDomu(int nrDomu) {
        this.nrDomu = nrDomu;
    }

    public int getKodPocztowy() {
        return kodPocztowy;
    }

    public void setKodPocztowy(int kodPocztowy) {
        this.kodPocztowy = kodPocztowy;
    }

    public boolean getZalogowany() {
        return zalogowany;
    }

    public void setZalogowany(boolean zalogowany) {
        this.zalogowany = zalogowany;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }
}
