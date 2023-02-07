package IDS;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class DashBoardAzienda {

    public void dash(){
        int scelta;
        do{
            scelta = menuAzienda();
            switch (scelta){
                case (1):this.menuCampagna();
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


    private void menuCampagna() {
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

        this.creaCampagnaScelta(scelta);

    }

    private void creaCampagnaScelta(int scelta){
        switch (scelta){
            case(1)://campagna punti
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

    public static int livelloDaEliminare(int i)
    {
        int n ;
        Scanner s = new Scanner(System.in);
        System.out.println("INSERISCI LIVELLO :");
        do {
            n = s.nextInt();
            if(n < 1 || n >i) System.out.println("VALORE INSERITO NON VALIDO RIPROVA :");
        }while (n<1 || n>i );

        return n;
    }

    public static Optional<Azienda> login() throws SQLException{
        Scanner input = new Scanner(System.in);
        DbConnector.init();
        ResultSet result;
        Admin admin;

        System.out.println("Partita IVA:"); //input dati login
        String partitaIva = input.next();
        System.out.println("Password:");
        String password = input.next();

        String queryLogin = "SELECT * FROM `aziende` WHERE `partitaIva`="+partitaIva+" and `password`="+password+";";  //verifico che azienda sia registrata
        result = DbConnector.executeQuery(queryLogin);
        if(!result.next()) return Optional.empty();  //se azienda non è registrata

        Azienda azienda = new Azienda(result.getString("nome"),result.getString("partitaIva"),new ArrayList<>());

        result = DbConnector.executeQuery("SELECT * FROM `admin` WHERE `Azienda`="+partitaIva+";");
        while(result.next()){
            admin = new Admin(result.getString("codiceFiscale"),result.getString("nome"),azienda);
            azienda.addAdmin(admin);
        }
        return Optional.of(azienda);
    }

    public static void registrazione() throws SQLException {
        Scanner input = new Scanner(System.in);
        DbConnector.init();

        System.out.println("Nome:"); //input dati login
        String nome = input.next();
        System.out.println("Partita IVA:"); //input dati login
        String partitaIva = input.next();
        System.out.println("Password:");
        String password = input.next();

        DbConnector.insertQuery("INSERT INTO `aziende` (`nome`, `partitaIva`, `password`) VALUES ('"+nome+"', '"+partitaIva+"', '"+password+"');");
    }

}
