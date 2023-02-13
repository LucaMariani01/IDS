package IDS;

import java.sql.SQLException;

import java.util.Optional;
import java.util.Scanner;

public class DashBoardAzienda {


    private static int sceltaMainMenu(){
        int scelta;
        Scanner scr = new Scanner(System.in);
        System.out.println("\nDASHBOARD AZIENDA");
        do{
            System.out.println("1)LOGIN");
            System.out.println("2)REGISTRAZIONE");
            System.out.println("0)ESCI");
            System.out.println("SCEGLI UN'OPZIONE: ");
            scelta=scr.nextInt();
        }while(scelta<0 || scelta>2);

        return scelta;
    }

    public static Optional<Azienda> mainMenu() throws SQLException {
        System.out.println("\nDASHBOARD AZIENDA");
        return switch (sceltaMainMenu()) {
            case 1 -> DbManager.loginAzienda();
            case 2 -> DbManager.registrazioneAzienda();
            default -> Optional.empty();
        };
    }

    public static Optional<Admin> menuAdmin(Azienda azienda) throws SQLException {
        System.out.println("\nDASHBOARD ADMIN");
         return switch (sceltaMainMenu()) {
            case 1 -> DbManager.loginAdmin(azienda);
            case 2 -> DbManager.registrazioneAdmin(azienda);
            default -> Optional.empty();
        };
    }

    public void dash(Azienda a) throws SQLException { // ??
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
        System.out.println("\nDASHBOARD AZIENDA");
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
        System.out.println("\nDASHBOARD CREAZIONE CAMPAGNA SCONTO");
        do{
            System.out.println("1)CREA CAMPAGNA A PUNTI ");
            System.out.println("2)CREA PROGRAMMMA A LIVELLI");
            System.out.println("3)CREA CAMPAGNA CASHBACK");
            System.out.println("4)CREA MEMBERSHIP ESCLUSIVA");
            System.out.println("5)CREA PROGRAMMA COALIZIONE");
            System.out.println("SCEGLI UNA CAMPAGNA SCONTO DA CREARE: ");
            scelta = s.nextInt();
        }while (scelta<1 || scelta>5);

        creaCampagnaScelta(scelta,a);
    }

    public static void creaCampagnaScelta(int scelta,Azienda a) throws SQLException{
        switch (scelta){
            case(1):
                if(DbManager.creaCampagnaPunti(a.getId()).isPresent()) System.out.println("CAMPAGNA A PUNTI CREATA CON SUCCESSO");
                else System.out.println("ERRORE NELLA CREAZIONE DELLA CAMPAGNA A PUNTI");
                break;
            case(2)://campagna livelli
                if(DbManager.creaCampagnaLivelli(a.getId()).isPresent())System.out.println("CAMPAGNA A LIVELLI CREATA CON SUCCESSO");
                else System.out.println("ERRORE NELLA CREAZIONE DELLA CAMPAGNA A LIVELLI");
                break;
            case (3): //campagna cashback
                if(DbManager.creaCampagnaCashback(a.getId()).isPresent())System.out.println("CAMPAGNA CASHBACK CREATA CON SUCCESSO");
                else System.out.println("ERRORE NELLA CREAZIONE DELLA CAMPAGNA CASHBACK");
                break;
            case (4)://membership esclusiva
                if(DbManager.creaMembership(a.getId()).isPresent())System.out.println("CAMPAGNA MEMBERSHIP CREATA CON SUCCESSO");
                else System.out.println("ERRORE NELLA CREAZIONE DELLA CAMPAGNA MEMBERSHIP");
                break;
            case (5)://programma coalizione
        }
    }

    public static int inputPuntiNecessari(int maxPunti) {
        int n;
        Scanner s = new Scanner(System.in);
        System.out.println("PUNTI NECESSARI PER ENTRARE IN QUESTO LIVELLO : ");
        do {
            n = s.nextInt();
            if (n<0 || n >maxPunti) System.out.println("VALORE INSERITO NON VALIDO");
        }while (n<0 || n >maxPunti);
        return n;
    }

    public static void azioneAzienda() throws SQLException {
        Optional<Azienda> aziendaLoggata;
        aziendaLoggata = DashBoardAzienda.mainMenu();
        if(aziendaLoggata.isPresent()){
            if(DashBoardAzienda.menuAdmin(aziendaLoggata.get()).isPresent()){
                switch (DashBoardAzienda.menuAzienda()){
                    case 1 ->  DashBoardAzienda.menuCampagna(aziendaLoggata.get());
                    case 2 ->  System.out.println("WORK IN PROGRESS...");
                    case 0 ->  {
                        System.out.println("LOGOUT ADMIN EFFETTUATO");
                        aziendaLoggata = Optional.empty();
                    }
                }
            }else System.out.println("errore admin");
        }
    }
}



