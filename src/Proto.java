import java.io.File;  // impordib faili klassi
import java.io.FileNotFoundException;  // errorite jaoks
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner; // et tekstifaile lugeda


public class Proto {
    //peameetod
    public static void main(String[] args) throws FileNotFoundException {
        String[][] ostukorv = ostukorv(tooted_failist_meetodisse("tooted.txt"));
        String[] converditud = ostukorvi_converter(ostukorv);
        faas(converditud);
    }

    //Viimane meetod, mis kuvab ostukorvis olevad tooted kasutajale ning märgib ära valitud tooted
    public static void faas(String[] converditud){

        System.out.println("====Tooted on lisatud ostukorvi====");
        Scanner sisestus = new Scanner(System.in);
        System.out.println("!Jätkamiseks vajutage ENTERit!");
        sisestus.nextLine();

        int i = 1;
        System.out.println("========OSTUNIMEKIRI=======");
        for (String el : converditud) {
            System.out.println(i + "." + el);
            i++;
        }
        System.out.println("============================");

        boolean toene = true;
        while (toene) {
            int n = 1;
            System.out.println("Lisan korvi toote number: ");
            Scanner scan = new Scanner(System.in);
            String vastus = scan.nextLine();
            int foo = Integer.parseInt(vastus);
            int f = 0;

            if (foo == 0) { //Kui sisestad "0", siis programm lõpetab töö
                System.out.println("!!!Programm katkestati!!!");
                toene = false;
            }else if (foo > converditud.length){
                System.out.println("!!Sellist toodet ei eksisteeri!!");

            } else if (converditud[foo - 1].contains("+")) {
                System.out.println("!See toode on juba korvis!");
            } else {
                converditud[foo - 1] += " +";
                System.out.println("========OSTUNIMEKIRI=======");
                for (String el : converditud) {
                    System.out.println(n + "." + el);
                    n++;
                }
                System.out.println("============================");
                for (String el : converditud) {
                    if (el.contains("+")) {
                        f++;
                    }
                }
                if (f == converditud.length) {
                    System.out.println("!Kõik tooted on korvis!" +" \n" + "Suunduge kassasse");
                    toene = false;
                }
            }
        }
    }

    //See meetod on meetodi ostukorv() ja faas()-i vahesamm
    public static String[] ostukorvi_converter(String[][] ostukorv) {
        String[] converditud = new String[ostukorv.length];

        for (int i = 0; i < ostukorv.length; i++) {
            Andmed toode = new Andmed(ostukorv[i][0], Double.parseDouble(ostukorv[i][1]));
            String c_el = toode.toString();
            converditud[i] = c_el;
        }
        return converditud;
    }

    //See meetod teeb Küsib kasutajalt tootedi, mida ostukorvi lisada, ning lisab need
    //koos hindadega eraldi massiivi
    public static String[][] ostukorv(String[][] tooted) throws FileNotFoundException {
        ArrayList<String> valikud = new ArrayList<>();

        boolean toe = true;

        while (toe) {
            System.out.println("==========Tooted============");
            for (String[] el : tooted) {
                Andmed toode = new Andmed(el[0], Double.parseDouble(el[1]));
                System.out.println(toode.toString());
            }
            System.out.println("============================");
            System.out.println("Toode: ");
            Scanner scan = new Scanner(System.in);
            String vastus = scan.nextLine().toUpperCase();

            if (Objects.equals(vastus, "EI")) {
                toe = false;

            } else {
                if (kas_on_olemas(vastus, tooted_failist_meetodisse("tooted.txt"))) {
                    System.out.println("Kogus: ");
                    int kogus = scan.nextInt();
                    for (int j = 0; j < kogus; j++) {
                        valikud.add(vastus);
                    }
                    toe = true;
                } else {
                    System.out.println("Toodet ei leitud :(");
                }
            }
        }

        String[][] ostukorvi = new String[valikud.size()][2];

        for (String[] el : tooted) {
            for (int i = 0; i < valikud.size(); i++) {
                if (el[0].equals(valikud.get(i))) {
                    Andmed toode = new Andmed(el[0], Double.parseDouble(el[1]));
                    ostukorvi[i][0] = toode.getToode();
                    ostukorvi[i][1] = String.valueOf(toode.getHind());
                }
            }
        }
        System.out.println("_____________________________");
        System.out.println("Kokku: " + Kokku(ostukorvi) + " €");
        return ostukorvi;
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
                Andmed toode = new Andmed(el[0], Double.parseDouble(el[1]));
                tooted[i][0] = toode.getToode().toUpperCase();
                tooted[i][1] = String.valueOf(toode.getHind());
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

    //See meetod arvutab kasutaja tehtud nimekirja järgi tal poeskäigul kuluva summa (eurodes)
    public static double Kokku(String[][] tooted) {
        double kokku = 0.0;
        for (String[] el : tooted) {
            Andmed toode = new Andmed(el[0], Double.parseDouble(el[1]));
            kokku = kokku + toode.getHind();
        }

        return Math.round(kokku * 100.0) / 100.0;
    }

    //See meetod teeb kindlaks, kas antud element(String) on massiivis olemas
    public static boolean kas_on_olemas(String toode, String[][] tooted) {
        boolean vastus = false;
        for (String[] el : tooted) {
            if (el[0].equals(toode)) {
                vastus = true;
                break;
            }
        }
        return vastus;
    }
}