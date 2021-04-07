import java.util.Scanner;
public class test {
    public static void main(String[] args) {
        String[] uus = {"piim....0.5€", "piim....0.5€", "must leib....0.8€", "must leib....0.8€", "must leib....0.8€"};
        int i = 1;
        for (String el : uus) {
            System.out.println(i + "." + el);
            i++;
        }
        boolean tõene = true;
        while (tõene) {
            int n = 1;
            System.out.println("Lisan korvi toote number: ");
            Scanner scan = new Scanner(System.in);
            String vastus = scan.nextLine();
            int foo = Integer.parseInt(vastus);
            int f = 0;

            if (foo == 0) {
                System.out.println("Programm lõpetas töö");
                tõene = false;
            }else if (foo > uus.length){
                System.out.println("Sellist toodet ei eksisteeri!");
                continue;

            } else if (uus[foo - 1].contains("+")) {
                System.out.println("See toode on juba korvis!");
                continue;
            } else {
                uus[foo - 1] += " +";
                for (String el : uus) {
                    System.out.println(n + "." + el);
                    n++;
                }
                for (String el : uus) {
                    if (el.contains("+")) {
                        f++;

                    }

                }
                if (f == uus.length) {
                    System.out.println("Kõik tooted on korvis. Programm lõpetab töö");
                    tõene = false;
                }


            }

        }


    }}
