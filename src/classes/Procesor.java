package classes;

public class Procesor extends Komponent {

    private int liczbaWątków;
    private int liczbaRdzenii;
    private double taktowanie;


    public int getLiczbaWątków() {
        return liczbaWątków;
    }

    public int getLiczbaRdzenii() {
        return liczbaRdzenii;
    }

    public double getTaktowanie() {
        return taktowanie;
    }

    public void setLiczbaWątków(int liczbaWątków) {
        this.liczbaWątków = liczbaWątków;
    }

    public void setLiczbaRdzenii(int liczbaRdzenii) {
        this.liczbaRdzenii = liczbaRdzenii;
    }

    public void setTaktowanie(double taktowanie) {
        this.taktowanie = taktowanie;
    }
}
