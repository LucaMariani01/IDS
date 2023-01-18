package IDS;

import java.util.Scanner;

public class Menu {


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



}
