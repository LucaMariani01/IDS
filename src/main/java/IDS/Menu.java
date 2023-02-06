package IDS;

import java.util.Scanner;

public class Menu {
    public int sceltaCampagna()
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

    public int sceltaTipoCliente()
    {
        Scanner s = new Scanner(System.in);
        System.out.println("1)COSTUMER ");
        System.out.println("2)AZIENDA");

        int n = s.nextInt();

        if (n < 1 || n> 2)
        {
            System.out.println("SCELTA NON VALIDA RIPROVA:");
            return this.sceltaTipoCliente();
        }return n ;
    }
    public int menuCampagna()
    {
        Scanner s = new Scanner(System.in);
        System.out.println("1)CREA NUOVA CAMPAGNA ");
        System.out.println("2)...");

        int n = s.nextInt();

        if (n < 1 || n> 2)
        {
            System.out.println("SCELTA NON VALIDA RIPROVA:");
            return this.sceltaTipoCliente();
        }return n ;
    }

    public int scelteLog()
    {
        Scanner s = new Scanner(System.in);
        System.out.println("1) LOGIN CLIENTE");
        System.out.println("2) ISCRIZIONE CLIENTE");

        System.out.print("INSERIRE SCELTA : ");

        int n = s.nextInt();

        if (n < 1 || n> 2)
        {
            System.out.println("SCELTA NON VALIDA RIPROVA:");
            return this.scelteLog();
        }return n ;

    }


}
