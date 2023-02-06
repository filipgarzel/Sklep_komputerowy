package classes;

public class Komponent {

    private String kompID;
    private String marka;
    private String nazwa;
    private String cena;
    private String oceny;
    private String opis;
    //private String imagesrc;

    public String getKompID() {
        return kompID;
    }

    public String getMarka() {
        return marka;
    }

    public String getNazwa() {
        return nazwa;
    }

    public String getCena() {
        return cena;
    }

    public String getOceny() {
        return oceny;
    }

    public String getOpis() {
        return opis;
    }

   // public String getImagesrc() {
//        return imagesrc;
//    }

    public void setKompID(String kompID) {
        this.kompID = kompID;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public void setCena(String cena) {
        this.cena = cena;
    }

    public void setOceny(String oceny) {
        this.oceny = oceny;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

//    public void setImagesrc(String imagesrc) {
//        this.imagesrc = imagesrc;
//    }
}
