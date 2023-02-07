package classes;

public class Zasilacz extends Komponent {

    private int moc;
    private String standard;
    private int srednica;
    private int wysokosc;
    private int szerokosc;
    private int glebokosc;
    private int sprawnosc;

    public int getMoc() {
        return moc;
    }

    public void setMoc(int moc) {
        this.moc = moc;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public int getSrednica() {
        return srednica;
    }

    public void setSrednica(int srednica) {
        this.srednica = srednica;
    }

    public int getWysokosc() {
        return wysokosc;
    }

    public void setWysokosc(int wysokosc) {
        this.wysokosc = wysokosc;
    }

    public int getSzerokosc() {
        return szerokosc;
    }

    public void setSzerokosc(int szerokosc) {
        this.szerokosc = szerokosc;
    }

    public int getGlebokosc() {
        return glebokosc;
    }

    public void setGlebokosc(int glebokosc) {
        this.glebokosc = glebokosc;
    }

    public int getSprawnosc() {
        return sprawnosc;
    }

    public void setSprawnosc(int sprawnosc) {
        this.sprawnosc = sprawnosc;
    }
}
