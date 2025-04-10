public class Automobil {
    private int nr;
    private String marca;
    private String model;
    private String culoare;
    private String tara;
    private double pret;

    public Automobil(int nr, String marca, String model, String culoare, String tara, double pret) {
        this.nr = nr;
        this.marca = marca;
        this.model = model;
        this.culoare = culoare;
        this.tara = tara;
        this.pret = pret;
    }

    // Getters and Setters
    public int getNr() { return nr; }
    public void setNr(int nr) { this.nr = nr; }
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public String getCuloare() { return culoare; }
    public void setCuloare(String culoare) { this.culoare = culoare; }
    public String getTara() { return tara; }
    public void setTara(String tara) { this.tara = tara; }
    public double getPret() { return pret; }
    public void setPret(double pret) { this.pret = pret; }

    @Override
    public String toString() {
        return nr + "," + marca + "," + model + "," + culoare + "," + tara + "," + pret;
    }
}