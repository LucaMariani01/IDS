package IDS.PlatformMenu;

import java.util.Scanner;

public class Menu {
    public static int sceltaTipoCliente() {
        Scanner input = new Scanner(System.in);
        int scelta;

        do {
            System.out.println("1)CLIENTE ");
            System.out.println("2)AZIENDA");
            System.out.println("0)ESCI");
            scelta = input.nextInt();
        }while((scelta < 0) || (scelta > 2));
        return scelta;
    }

}