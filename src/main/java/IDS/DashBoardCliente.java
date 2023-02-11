package IDS;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Scanner;

public class DashBoardCliente {

    private static int sceltaMainMenu(){
        int scelta;
        Scanner scr = new Scanner(System.in);
        do{
            System.out.println("1)LOGIN CLIENTE");
            System.out.println("2)REGISTRAZIONE CLIENTE");
            System.out.println("0)EXIT");
            System.out.println("Inserisci la tua scelta: ");
            scelta=scr.nextInt();
        }while(scelta<0 || scelta>2);

        return scelta;
    }

    public static Optional<Customer> mainMenu() throws SQLException {
        return switch (sceltaMainMenu()) {
            case 1 -> DbManager.loginCliente();
            case 2 -> DbManager.registrazioneCliente();
            default -> Optional.empty();
        };
    }
    public static int sceltaAzioniUtente(){
        int scelta;
        Scanner scr = new Scanner(System.in);
        do{
            System.out.println("1)ISCRIVITI AD UNA CAMPAGNA SCONTI");
            System.out.println("2)VISUALIZZA LE TUE CAMPAGNE SCONTI");
            System.out.println("0)LOGOUT");
            System.out.println("Inserisci la tua scelta: ");
            scelta=scr.nextInt();
        }while(scelta<0 || scelta>2);

        return scelta;
    }

    public static void azioneCliente() throws SQLException {
        int azioneCliente;
        Optional<Customer>  clienteLoggato = DashBoardCliente.mainMenu();
        if(clienteLoggato.isPresent()){
            do{
                azioneCliente = DashBoardCliente.sceltaAzioniUtente();
                switch (azioneCliente){
                    case 1 ->  DbManager.sceltaAziendaCampagneDisponibili(clienteLoggato.get());
                    case 2 ->  clienteLoggato.get().stampaCampagneAderite();
                    case 0 ->  {
                        clienteLoggato = Optional.empty();
                        System.out.println("logout");
                    }
                }
            }while (azioneCliente!=0);
        }
    }


}