import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Keha {
    //Lühidalt: tegu on programmi n.ö kehaga (meetod ostukorv()), mis kasutab loodud meetodeid, et:
    // 1)Küsida kasutajalt soovitud tooteid koos kogustega
    // 2)Arvutada kokkumineva summa (eurodes)
    // 3)Teha paus, kuni kasutaja sisestab "Enteri" (et poodi jõuda)
    // 4)Väljastada valitud toodete nimekiri, kus kasutaja saab ära märkida korvi võetud tooted

    //Antud etappidele viitame eeltoodud järjekorranumbritega ("faas" + järjekorranumber (nt faas 1)))

    //Meetod küsib kasutajalt eelistatud poodi
    public static void milline_pood() throws PolePoodiError, FileNotFoundException{
        Scanner scan = new Scanner(System.in);
        System.out.println("Millist poodi sooviksite külastada? (Rimi, Coop, Selver)");
        String vastus = scan.nextLine();

        if(vastus.equalsIgnoreCase("Rimi")) {
            Scanner scannn = new Scanner(System.in);
            System.out.println("Kas teil rimi kaarti on? ('jah', 'ei')");
            String kaart = scannn.nextLine();
            boolean t = true;

            while(t)
                if(kaart.equalsIgnoreCase("jah")) {
                    ostukorv(soodukatega(tooted_failist_meetodisse("rimi.txt"),50, 5));
                    t = false;
                }
                else if(kaart.equalsIgnoreCase("ei")) {
                    ostukorv(tooted_failist_meetodisse("rimi.txt"));
                    t = false;
                }
                else {
                    System.out.println("Sisestage kas 'jah' või 'ei' !");
                }
        }
        else if(vastus.equalsIgnoreCase("Coop")) {
            Scanner scannn = new Scanner(System.in);
            System.out.println("Kas teil säästukaarti on? ('jah', 'ei')");
            String kaart = scannn.nextLine();
            boolean t = true;

            while(t)
                if(kaart.equalsIgnoreCase("jah")) {
                    ostukorv(soodukatega(tooted_failist_meetodisse("coop.txt"),50, 5));
                    t = false;
                }
                else if(kaart.equalsIgnoreCase("ei")) {
                    ostukorv(tooted_failist_meetodisse("coop.txt"));
                    t = false;
                }
                else {
                    System.out.println("Sisestage kas 'jah' või 'ei' !");
                }
        }
        else if(vastus.equalsIgnoreCase("Selver")) {
            Scanner scannn = new Scanner(System.in);
            System.out.println("Kas teil partnerkaarti on? ('jah', 'ei')");
            String kaart = scannn.nextLine();
            boolean t = true;

            while(t)
                if(kaart.equalsIgnoreCase("jah")) {
                    ostukorv(soodukatega(tooted_failist_meetodisse("selver.txt"),50, 5));
                    t = false;
                }
                else if(kaart.equalsIgnoreCase("ei")) {
                    ostukorv(tooted_failist_meetodisse("selver.txt"));
                    t = false;
                }
                else {
                    System.out.println("Sisestage kas 'jah' või 'ei' !");
                }
        }
        else {
            throw new PolePoodiError("Antud pood pole saadaval :(");
        }
    }

    public static void ostukorv(List<Toode> tooted){
        ArrayList<String> valikud = new ArrayList<>();

        //faas 1)
        boolean toe = true;

        while (toe) {
            System.out.println("==========Tooted============");
            for (Toode el : tooted) {
                System.out.println(el.toString());
            }

            System.out.println("============================");
            System.out.println("Valimiseks kirjuta toote nimi, kui valmis kirjuta (OK): ");
            Scanner scan = new Scanner(System.in);
            String vastus = scan.nextLine();

            if (vastus.equalsIgnoreCase("OK")) {
                toe = false;

            }
            else {
                if (kas_on_olemas(vastus, tooted)) {
                    System.out.println("Kogus: ");
                    int kogus = scan.nextInt();
                    for (int j = 0; j < kogus; j++) {
                        valikud.add(vastus);
                    }
                    toe = true;
                }
                else {
                    System.out.println("Toodet ei leitud :(");
                }
            }
        }

        List<Toode> valitud_tooted = valitud_tooted(valikud, tooted);

        System.out.println("====Tooted on lisatud ostukorvi====");
        System.out.println("========OSTUNIMEKIRI=======");
        int j = 1;
        for (Toode toode : valitud_tooted) {
            System.out.println(j + "." + toode.toString());
            j++;
        }
        System.out.println("============================");
        System.out.println("Kokku: " + kokku(valitud_tooted) + " €");


        System.out.println("_____________________________");

        //faas 3)
        Scanner sisestus = new Scanner(System.in);
        System.out.println("!Jätkamiseks vajutage ENTERit!");
        sisestus.nextLine();

        //faas 4)
        String[] converditud = convert(valitud_tooted);
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
                }else if (foo > valitud_tooted.size()){
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

    //Antud meetod loob valitud toodete põhjal uue List<Toode>
    public static List<Toode> valitud_tooted(ArrayList<String> valikud, List<Toode> tooted) {
        List<Toode> valitud_tooted = new ArrayList<>();

        for (Toode toode : tooted) {
            for (String valik : valikud) {
                if(valik.equalsIgnoreCase(toode.getToode())) {
                    valitud_tooted.add(new Toode(toode.getToode(), toode.getHind()));
                }
            }
        }
        return valitud_tooted;
    }

    //Meetod viib List<Toode> mugavamale kujule (ostukorv()is vajalik)
    public static String[] convert(List<Toode> tooted) {
        String[] converditud = new String[tooted.size()];

        for (int i = 0; i < tooted.size(); i++) {
            converditud[i] = tooted.get(i).toString();
        }
        return converditud;
    }

    //meetod arvutab antud toodete kokkumineva summa (eurodes)
    public static double kokku(List<Toode> tooted) {
        double kokku = 0;
        for (Toode toode : tooted) {
            kokku += toode.getHind();
        }
        return Math.round(kokku * 100.00) / 100.00;
    }

    //meetod mis paneb failis olevad tooted koos hinnaga eraldi listi(tootenimi, hind)
    public static List<Toode> tooted_failist_meetodisse(String failinimi) throws FileNotFoundException {
        //Lisab failis olevad tooted(toote nimi, hind) toodete listi
        File fail = new File(failinimi);
        List<Toode> tooted = new ArrayList<>();

        try (Scanner lugeja = new Scanner(fail, "UTF-8")) {
            while (lugeja.hasNextLine()) {
                String rida = lugeja.nextLine();
                String[] jupid = rida.split("; ");
                tooted.add(new Toode(jupid[0], Double.parseDouble(jupid[1])));
            }
        }
        return tooted;
    }

    //See meetod teeb kindlaks, kas antud element(String) on listis (antud kontekstis toodete seas) olemas
    public static boolean kas_on_olemas(String toode, List<Toode> tooted) {
        boolean vastus = false;
        for (Toode el : tooted) {
            if (el.getToode().equalsIgnoreCase(toode)) {
                vastus = true;
                break;
            }
        }
        return vastus;
    }

    public static List<Toode> soodukatega(List<Toode> tooted, int max, int min) {
        for (Toode toode : tooted) {
            int juhu_percent = (int)(Math.random() * ((max - min) + 1)) + min;
            double allahindus = (toode.getHind() * juhu_percent) / 100;
            double uus_hind = Math.round((toode.getHind() - allahindus) * 100.0) / 100.0;
            toode.setHind(uus_hind);
        }
        return tooted;
    }
}