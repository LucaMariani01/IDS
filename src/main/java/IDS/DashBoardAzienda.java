package IDS;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Scanner;

public class DashBoardAzienda {

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

    private int menuAzienda(){
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

    private void menuCampagna(Azienda a) throws SQLException, ParseException {
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

        this.creaCampagnaScelta(scelta,a);

    }

    private void creaCampagnaScelta(int scelta,Azienda a) throws SQLException, ParseException {
        switch (scelta){
            case(1):
                DbManager.creaCampagnaPunti(a.getId());
                break;
            case(2)://campagna livelli
                break;
            case (3): //campagna cashback
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
