import java.io.File;  // impordib faili klassi
import java.io.FileNotFoundException;  // errorite jaoks
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner; // et tekstifaile lugeda


public class Proto {
    //peameetod
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println(ostukorv(tooted_failist_meetodisse("tooted.txt")));
    }



    public static String[][] ostukorv(String[][] tooted) throws FileNotFoundException {
        ArrayList<String> valikud = new ArrayList<>();

        boolean toe = true;

        while(toe) {
            System.out.println("Toode: ");
            Scanner scan = new Scanner(System.in);
            String vastus = scan.nextLine();

            if(Objects.equals(vastus, "ei")) {
                toe = false;

            }else{
                if(kas_on_olemas(vastus ,tooted_failist_meetodisse("tooted.txt"))){
                    System.out.println("Kogus: ");
                    int kogus = scan.nextInt();
                    for (int j = 0; j < kogus; j++) {
                        valikud.add(vastus);
                    }
                    toe = true;
                }else{
                    System.out.println("Toodet ei leitud :(");
                }
            }
        }


    }




    //meetod mis paneb failis olevad tooted koos hinnaga eraldi massiivi(tootenimi, hind)
    public static String[][] tooted_failist_meetodisse(String failinimi) throws FileNotFoundException {
        //Viib failis olema esmalt ühe dimensioonilisesse massiivi
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

            //Viib ühe dimensioonilise massiivi kahe dimensioonilise massiivi kujule
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

    //See meetod arvutab kasutaja tehtud nimekirja järgi tal poeskäigul kuluva summa.
    public static float Kokku(String[][] tooted) {
        float kokku = 0;
        for (String[] element : tooted) {
            float ost = Float.parseFloat(element[1]);
            kokku = kokku + ost;
        }
        return kokku;
    }

    public static boolean kas_on_olemas(String toode, String[][] tooted){
        boolean vastus = false;
        for (String[] el : tooted) {
            if(el[0].equals(toode)) {
                vastus = true;
            }
        }return vastus;
    }
}
