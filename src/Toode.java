public class Toode {

    private String Toode;
    private double Hind;

    public Toode(String Toode, double Hind) {
        this.Toode = Toode;
        this.Hind = Hind;
    }

    public String getToode() {
        return Toode;
    }
    public double getHind() {
        return Hind;
    }
    //Tulevikus antud programmi arendamisel saab seda rakendada
    public void setHind(double hind) {
        Hind = hind;
    }
    public String toString(){
        return Toode + "...." + Hind + " €";
    }
}
