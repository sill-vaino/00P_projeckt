import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;


public class Keha {
    //Lühidalt: tegu on programmi n.ö kehaga (meetod ostukorv()), mis kasutab loodud meetodeid, et:
    // 1)Küsida kasutajalt soovitud tooteid koos kogustega
    // 2)Arvutada kokkumineva summa (eurodes)
    // 3)Teha paus, kuni kasutaja sisestab "Enteri" (et poodi jõuda)
    // 4)Väljastada valitud toodete nimekiri, kus kasutaja saab ära märkida korvi võetud tooted

    //Antud etappidele viitame eeltoodud järjekorranumbritega ("faas" + järjekorranumber (nt faas 1)))
    public static void ostukorv(String[][] tooted) throws FileNotFoundException {
        ArrayList<String> valikud = new ArrayList<>();

        boolean toe = true;

        //faas 1)
        while (toe) {
            System.out.println("==========Tooted============");
            for (String[] el : tooted) {
                Andmed toode = new Andmed(el[0], Double.parseDouble(el[1]));
                System.out.println(toode.toString());
            }
            System.out.println("============================");
            System.out.println("Valimiseks kirjuta toote nimi, kui valmis kirjuta (OK): ");
            Scanner scan = new Scanner(System.in);
            String vastus = scan.nextLine().toUpperCase();

            if (Objects.equals(vastus, "OK")) {
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
        String[] converditud = ostukorvi_converter(ostukorvi);

        System.out.println("====Tooted on lisatud ostukorvi====");

        int i = 1;
        System.out.println("========OSTUNIMEKIRI=======");
        for (String el : converditud) {
            System.out.println(i + "." + el);
            i++;
        }
        System.out.println("============================");

        //faas 2)
        double kokku = 0.0;
        for (String[] el : ostukorvi) {
            Andmed toode = new Andmed(el[0], Double.parseDouble(el[1]));
            kokku = kokku + toode.getHind();
        }

        System.out.println("Kokku: " + Math.round(kokku * 100.00) / 100.00 + " €");

        System.out.println("_____________________________");

        //faas 3)
        Scanner sisestus = new Scanner(System.in);
        System.out.println("!Jätkamiseks vajutage ENTERit!");
        sisestus.nextLine();

        //faas 4)
        while (true) {

            int n = 1;
            System.out.println("Lisan korvi toote number: ");
            Scanner scan = new Scanner(System.in);
            String vastus = scan.nextLine();
            try {
                int foo = Integer.parseInt(vastus);
                int f = 0;


                if (foo == 0) { //Kui sisestad "0", siis programm lõpetab töö
                System.out.println("!!!Programm katkestati!!!");
                return;
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
                        System.out.println("!Kõik tooted on korvis!" + " \n" + "Suunduge kassasse");
                        return;
                }   }
            }catch (NumberFormatException e) {
                System.out.println("Tähti siin lisada ei saa!");
            }
        }
    }

    //See meetod loob valitud toodetest eraldi ühe-dimensioonilise massiivi (programmi järmises faasis vajalik)
    public static String[] ostukorvi_converter(String[][] ostukorv) {
        String[] converditud = new String[ostukorv.length];

        for (int i = 0; i < ostukorv.length; i++) {
            Andmed toode = new Andmed(ostukorv[i][0], Double.parseDouble(ostukorv[i][1]));
            String c_el = toode.toString();
            converditud[i] = c_el;
        }
        return converditud;
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
    //Vajalik massiivi pikkuse määramiseks
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

    //See meetod teeb kindlaks, kas antud element(String) on massiivis (antud kontekstis toodete seas) olemas
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