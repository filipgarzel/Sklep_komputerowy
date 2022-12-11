package classes;

public class Komponent {
    private int cena;
    private double oceny;
    private String nazwa;
    private String imagesrc;

    public String getImage() {
        return imagesrc;
    }

    public int getCena() {
        return cena;
    }

    public double getOceny() {
        return oceny;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setCena(int cena) {
        this.cena = cena;
    }

    public void setOceny(double oceny) {
        this.oceny = oceny;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public void setImage(String imagesrc) {
        this.imagesrc = imagesrc;
    }
}
