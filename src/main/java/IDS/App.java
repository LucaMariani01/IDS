package IDS;

import IDS.PlatformMenu.DashBoardAzienda;
import IDS.PlatformMenu.DashBoardCliente;

import java.sql.SQLException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws SQLException{
        int tipoCliente;
        do{
            tipoCliente = sceltaTipoCliente();
            switch (tipoCliente) {
                case 1 -> DashBoardCliente.azioneCliente();
                case 2 -> DashBoardAzienda.azioneAzienda();
            }
        }while(tipoCliente!=0);
    }

    private static int sceltaTipoCliente() {
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
