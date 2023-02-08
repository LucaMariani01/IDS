package IDS;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Optional;
import java.util.Scanner;

public class DashBoardAzienda {


    private static int sceltaMainMenu(){
        int scelta;
        Scanner scr = new Scanner(System.in);
        do{
            System.out.println("1)LOGIN");
            System.out.println("2)REGISTRAZIONE");
            System.out.println("0)ESCI");
            System.out.println("Inserisci la tua scelta: ");
            scelta=scr.nextInt();
        }while(scelta<0 || scelta>2);

        return scelta;
    }

    public static Optional<Azienda> mainMenu() throws SQLException {
        System.out.println("\nMENU AZIENDA");
        return switch (sceltaMainMenu()) {
            case 1 -> DbManager.loginAzienda();
            case 2 -> DbManager.registrazioneAzienda();
            default -> Optional.empty();
        };
    }

    public static Optional<Admin> menuAdmin(Azienda azienda) throws SQLException {
        System.out.println("\nMENU ADMIN");
         return switch (sceltaMainMenu()) {
            case 1 -> DbManager.loginAdmin(azienda);
            case 2 -> DbManager.registrazioneAdmin(azienda);
            default -> Optional.empty();
        };
    }

    public void dash(Azienda a) throws SQLException, ParseException {
        int scelta;
        do{
            scelta = menuAzienda();
            switch (scelta){
                case (1):this.menuCampagna(a);
                    break;
            }
        }while(scelta!=0);
    }

    public static int menuAzienda(){
        Scanner s = new Scanner(System.in);
        int n;
        do{
            System.out.println("1)CREA NUOVA CAMPAGNA ");
            System.out.println("2)...");
            System.out.println("0)ESCI");
            n = s.nextInt();
        }while ((n<0)||(n>2)) ;
        return n;
    }

    public static void menuCampagna(Azienda a) throws SQLException {
        int scelta;
        Scanner s = new Scanner(System.in);
        do{
            System.out.println("Seleziona la campagna Sconti da creare");
            System.out.println("1)CREA CAMPAGNA A PUNTI ");
            System.out.println("2)CREA PROGRAMMMA A LIVELLI");
            System.out.println("3)CREA CAMPAGNA CASHBACK");
            System.out.println("4)CREA MEMBERSHIP ESCLUSIVA");
            System.out.println("5)CREA PROGRAMMA COALIZIONE");

            scelta = s.nextInt();
        }while (scelta<1 || scelta>5);

        creaCampagnaScelta(scelta,a);
    }

    public static void creaCampagnaScelta(int scelta,Azienda a) throws SQLException{
        switch (scelta){
            case(1):
                if(DbManager.creaCampagnaPunti(a.getId()).isPresent()) System.out.println("CAMPAGNA CREATA CON SUCCESSO");
                else System.out.println("ERRORE NELLA CREAZIONE DELLA CAMPAGNA");
                break;
            case(2)://campagna livelli
                break;
            case (3): //campagna cashback
                DbManager.creaCampagnaCashback(a.getId());
                break;
            case (4)://membership esclusiva
                break;
            case (5)://programma coalizione
        }
    }

    public static int livelloDaEliminare(int i) {
        int n ;
        Scanner s = new Scanner(System.in);
        System.out.println("INSERISCI LIVELLO :");
        do {
            n = s.nextInt();
            if(n < 1 || n >i) System.out.println("VALORE INSERITO NON VALIDO RIPROVA :");
        }while (n<1 || n>i );

        return n;
    }
}
