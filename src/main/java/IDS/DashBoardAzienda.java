package IDS;

import java.util.Scanner;

public class DashBoardAzienda {


    public int menuAzienda(){
        Scanner s = new Scanner(System.in);
        int n;
        do{
            System.out.println("1)CREA NUOVA CAMPAGNA ");
            System.out.println("2)...");

            n = s.nextInt();
        }while ((n<1)||(n>2)) ;
        return n;
    }
    public int menuCampagna()
    {
        Scanner s = new Scanner(System.in);
        System.out.println("1)CREA CAMPAGNA A PUNTI ");
        System.out.println("2)CREA PROGRAMMMA A LIVELLI");
        System.out.println("3)CREA CAMPAGNA CASHBACK");
        System.out.println("4)CREA MEMBERSHIP ESCLUSIVA");
        System.out.println("5)CREA PROGRAMMA COALIZIONE");

        int n = s.nextInt();

        if (n < 0 || n> 5)
        {
            System.out.println("SCELTA NON VALIDA RIPROVA:");
            return this.sceltaCampagna();
        }return n ;

    }
}
