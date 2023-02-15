package IDS;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class DbManagerAzienda {

    public static Optional<Azienda> loginAzienda() throws SQLException{
        Scanner input = new Scanner(System.in);
        DbConnector.init();
        ResultSet result;
        Admin admin;

        System.out.println("Partita IVA:"); //input dati login
        String partitaIva = input.next();
        System.out.println("Password: ");
        String password = input.next();

        String queryLogin = "SELECT * FROM `aziende` WHERE `partitaIva`='"+partitaIva+"' and `password`='"+password+"';";  //verifico che azienda sia registrata
        result = DbConnector.executeQuery(queryLogin);
        if(!result.next()) return Optional.empty();  //se azienda non è registrata

        Azienda azienda = new Azienda(result.getString("nome"),result.getString("partitaIva"),new ArrayList<>());

        result = DbConnector.executeQuery("SELECT * FROM `admin` WHERE `azienda`='"+partitaIva+"';");
        while(result.next()){
            admin = new Admin(result.getString("codiceFiscale"),result.getString("nome"),azienda);
            azienda.addAdmin(admin);
        }
        DbConnector.closeConnection();
        return Optional.of(azienda);
    }

    public static Optional<Azienda> registrazioneAzienda() throws SQLException {
        Scanner input = new Scanner(System.in);
        ResultSet result;
        DbConnector.init();

        System.out.println("Nome:"); //input dati login
        String nome = input.next();
        System.out.println("Partita IVA:"); //input dati login
        String partitaIva = input.next();
        System.out.println("Password:");
        String password = input.next();

        String queryLogin = "SELECT * FROM `aziende` WHERE `partitaIva`='"+partitaIva+"';";  //verifico se esiste già azienda con partita iva inserita
        result = DbConnector.executeQuery(queryLogin);
        if(result.next()) return Optional.empty();  //se esiste, non effettuo la registrazione

        try {
            DbConnector.insertQuery("INSERT INTO `aziende` (`nome`, `partitaIva`, `password`) " +
                    "VALUES ('"+nome+"', '"+partitaIva+"', '"+password+"');");
        } catch (SQLException e) {
            System.out.println("Errore nella registrazione dell'azienda, partita iva già presente.");
            return Optional.empty();
        }
        DbConnector.closeConnection();
        return Optional.of(new Azienda(nome,partitaIva,new ArrayList<>()));
    }

    /**
     * permette all'azienda di visualizzare tutte le recensioni lasciate dai clienti
     * @param azienda azienda loggata che visualizza le recensioni
     * @throws SQLException
     */
    public static void visualizzaRecensioniClienti(Azienda azienda)throws SQLException{
        DbConnector.init();
        System.out.println("RECENSIONI AZIENDA");
        ResultSet result = DbConnector.executeQuery("SELECT * FROM `recensioni` " +
                "inner join clienti on recensioni.utente = clienti.email WHERE azienda = "+azienda.getId()+";");
        while(result.next()){
            System.out.println(result.getString("nome")+": "+result.getString("recensione"));
        }
        DbConnector.closeConnection();
    }

}
