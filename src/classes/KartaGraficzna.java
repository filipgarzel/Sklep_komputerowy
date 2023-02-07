package classes;


public class KartaGraficzna extends Komponent {

    private int czestotliwosc;
    private double taktowanie;
    private int pamięć;

    public int getCzestotliwosc() {
        return czestotliwosc;
    }

    public double getTaktowanie() {
        return taktowanie;
    }

    public double getPamięć() {
        return pamięć;
    }

    public void setCzestotliwosc(int czestotliwosc) {
        this.czestotliwosc = czestotliwosc;
    }

    public void setTaktowanie(double taktowanie) {
        this.taktowanie = taktowanie;
    }

    public void setPamięć(int pamięć) {
        this.pamięć = pamięć;
    }
}
