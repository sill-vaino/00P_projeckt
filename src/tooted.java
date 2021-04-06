import java.io.File;  // impordib faili klassi
import java.io.FileNotFoundException;  // errorite jaoks
import java.util.Arrays;
import java.util.Scanner; // et tekstifaile lugeda

public class tooted {

    //peameetod
    public static void main(String[] args) throws FileNotFoundException {

    }

    //meetod mis paneb failis olevad tooted koos hinnaga eraldi massiivi(tootenimi, hind)
    public static String[][] tooted_meetodisse(String failinimi) throws FileNotFoundException {
        //Viib failis olema esmalt 체he dimensioonilisesse massiivi
        File fail = new File(failinimi);
        String[] tooted_1 = new String[ridade_arv(failinimi)];
        try (java.util.Scanner lugeja = new java.util.Scanner(fail, "UTF-8")) {
            while (lugeja.hasNextLine()) {
                for (int i = 0; i < ridade_arv(failinimi); i++) {
                    String toode = lugeja.nextLine();
                    tooted_1[i] = toode;
                }
            }
            lugeja.close();

            //Viib 체he dimensioonilise massiivi kahe dimensioonilise massiivi kujule
            String[][] tooted = new String[ridade_arv(failinimi)][2];
            for (int i = 0; i < ridade_arv(failinimi); i++) {
                String[] el = tooted_1[i].split("; ");
                tooted[i][0] = el[0];
                tooted[i][1] = el[1];
            }
            return tooted;
        }
    }

    //See meetod leiab faili ridade arvu(ehk toodete arvu)
    public static int ridade_arv(String failinimi) throws FileNotFoundException {
        int rida = 0;
        File fail = new File(failinimi);
        Scanner lugeja = new Scanner(fail);
        while (lugeja.hasNextLine()) {
            lugeja.nextLine();
            rida++;
        }
        return rida;
    }

    //See meetod arvutab kasutaja tehtud nimekirja j채rgi tal poesk채igul kuluva summa.
    public static float Kokku(String[][] tooted) {
        float kokku = 0;
        for (String[] element : tooted) {
            float ost = Float.parseFloat(element[1]);
            kokku = kokku + ost;
        }
        return kokku;
    }
}
