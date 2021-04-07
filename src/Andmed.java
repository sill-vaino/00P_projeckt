public class Andmed {
    private String Toode;
    private double Hind;

    public Andmed(String Toode, double Hind) {
        this.Toode = Toode;
        this.Hind = Hind;
    }

    public String getToode() {
        return Toode;
    }
    public double getHind() {
        return Hind;
    }
    public void setToode(String toode) {
        Toode = toode;
    }
    public void setHind(double hind) {
        Hind = hind;
    }
    public String toString(){
        return Toode + "...." + Hind + " €";
    }
}
