package IDS.PlatformMenu;

import IDS.DbManager.DbManagerAdmin;
import IDS.DbManager.DbManagerAzienda;
import IDS.DbManager.DbManagerCampagne;
import IDS.PlatformData.Admin;
import IDS.PlatformData.Azienda;

import java.sql.SQLException;

import java.util.Optional;
import java.util.Scanner;

public class DashBoardAzienda {

    /**
     * Gestisce le opzioni che l'azienda ha una volta che accede alla piattaforma
     */
    public static void azioneAzienda() throws SQLException {
        Optional<Azienda> aziendaLoggata;
        aziendaLoggata = DashBoardAzienda.mainMenu();
        if(aziendaLoggata.isPresent()){
            if(DashBoardAzienda.menuAdmin(aziendaLoggata.get()).isPresent()){
                do {
                    switch (DashBoardAzienda.menuAzienda()){
                        case 1 ->  DashBoardAzienda.menuCampagna(aziendaLoggata.get());
                        case 2 ->  DbManagerAzienda.visualizzaRecensioniClienti(aziendaLoggata.get());
                        case 0 ->  {
                            System.out.println("LOGOUT ADMIN EFFETTUATO");
                            aziendaLoggata = Optional.empty();
                        }
                    }
                }while (aziendaLoggata.isPresent());
            }else System.out.println("errore admin");
        }
    }

    /**
     * Gestisce il login e registrazione di una azienda
     * @return l'azienda loggata, o appena iscritta alla piattaforma
     */
    public static Optional<Azienda> mainMenu() throws SQLException {
        System.out.println("\nDASHBOARD AZIENDA");
        return switch (sceltaMainMenu()) {
            case 1 -> DbManagerAzienda.loginAzienda();
            case 2 -> DbManagerAzienda.registrazioneAzienda();
            default -> Optional.empty();
        };
    }

    /**
     * Questa funzione gestisce il menu dell'azienda per
     * effettuare il login o la registrazione
     * @return la scelta effettuata dall'azienda
     */
    private static int sceltaMainMenu(){
        int scelta;
        Scanner scr = new Scanner(System.in);
        do{
            System.out.println("1)LOGIN");
            System.out.println("2)REGISTRAZIONE");
            System.out.println("0)ESCI");
            System.out.println("SCEGLI UN'OPZIONE: ");
            scelta=scr.nextInt();
        }while(scelta<0 || scelta>2);
        return scelta;
    }

    /**
     * Gestisce il login e registrazione di un admin di un'azienda
     * @return l'admin loggato, o appena iscritto alla piattaforma
     */
    public static Optional<Admin> menuAdmin(Azienda azienda) throws SQLException {
        System.out.println("\nDASHBOARD ADMIN");
         return switch (sceltaMainMenu()) {
            case 1 -> DbManagerAdmin.loginAdmin(azienda);
            case 2 -> DbManagerAdmin.registrazioneAdmin(azienda);
            default -> Optional.empty();
        };
    }

    /**
     * Questa funzione gestisce il menu dell'azienda per effettuare le possibili azioni
     * all'interno della piattaforma (crea campagna, visualizza recensioni,...)
     * @return la scelta effettuata dall'azienda
     */
    public static int menuAzienda(){
        Scanner s = new Scanner(System.in);
        int n;
        System.out.println("\nDASHBOARD AZIENDA");
        do{
            System.out.println("1)CREA NUOVA CAMPAGNA ");
            System.out.println("2)VISUALIZZA LE RECENSIONI DEI CLIENTI");
            System.out.println("0)ESCI");
            n = s.nextInt();
        }while ((n<0)||(n>2)) ;
        return n;
    }

    /**
     * Gestisce il menu della creazione di una campagna sconto
     * con le possibili campagne che possono essere create
     * @param a è l'azienda loggata nella piattaforma
     */
    public static void menuCampagna(Azienda a) {
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

    /**
     * Gestisce la selezione della creazione di una campagna sconto da parte dell'azienda
     * @param scelta campagna sconti da creare scelta dall'azienda
     * @param a azienda loggata nella piattaforma
     */
    public static void creaCampagnaScelta(int scelta,Azienda a) {
        switch (scelta){
            case(1):
                if(DbManagerCampagne.creaCampagnaPunti(a.getId()).isPresent()) System.out.println("CAMPAGNA A PUNTI CREATA CON SUCCESSO");
                else System.out.println("ERRORE NELLA CREAZIONE DELLA CAMPAGNA A PUNTI");
                break;
            case(2)://campagna livelli
                if(DbManagerCampagne.creaCampagnaLivelli(a.getId()).isPresent())System.out.println("CAMPAGNA A LIVELLI CREATA CON SUCCESSO");
                else System.out.println("ERRORE NELLA CREAZIONE DELLA CAMPAGNA A LIVELLI");
                break;
            case (3): //campagna cashback
                if(DbManagerCampagne.creaCampagnaCashback(a.getId()).isPresent())System.out.println("CAMPAGNA CASHBACK CREATA CON SUCCESSO");
                else System.out.println("ERRORE NELLA CREAZIONE DELLA CAMPAGNA CASHBACK");
                break;
            case (4)://membership esclusiva
                if(DbManagerCampagne.creaMembership(a.getId()).isPresent())System.out.println("CAMPAGNA MEMBERSHIP CREATA CON SUCCESSO");
                else System.out.println("ERRORE NELLA CREAZIONE DELLA CAMPAGNA MEMBERSHIP");
                break;
            case (5): //programma coalizione
                System.out.println("PROGRAMMA COALIZIONE DISPONIBILE PROSSIMAMENTE");
                break;
        }
    }
}