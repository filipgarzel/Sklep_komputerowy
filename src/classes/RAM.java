package classes;

public class RAM extends Komponent {

    private String chlodzenie;
    private String pamiecRAM;
    private int pojemnosc;
    private double napiecie;
    private int liczbaModulow;
    private String taktowanie;
    private String opoznienie;

    public String getChlodzenie() {
        return chlodzenie;
    }

    public void setChlodzenie(String chlodzenie) {
        this.chlodzenie = chlodzenie;
    }

    public String getPamiecRAM() {
        return pamiecRAM;
    }

    public void setPamiecRAM(String pamiecRAM) {
        this.pamiecRAM = pamiecRAM;
    }

    public int getPojemnosc() {
        return pojemnosc;
    }

    public void setPojemnosc(int pojemnosc) {
        this.pojemnosc = pojemnosc;
    }

    public double getNapiecie() {
        return napiecie;
    }

    public void setNapiecie(double napiecie) {
        this.napiecie = napiecie;
    }

    public int getLiczbaModulow() {
        return liczbaModulow;
    }

    public void setLiczbaModulow(int liczbaModulow) {
        this.liczbaModulow = liczbaModulow;
    }

    public String getTaktowanie() {
        return taktowanie;
    }

    public void setTaktowanie(String taktowanie) {
        this.taktowanie = taktowanie;
    }

    public String getOpoznienie() {
        return opoznienie;
    }

    public void setOpoznienie(String opoznienie) {
        this.opoznienie = opoznienie;
    }
}
