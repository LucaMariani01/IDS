package IDS;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Scanner;

public class DbManagerAdmin {
    public static Optional<Admin> loginAdmin(Azienda azienda) throws SQLException {
        Scanner input = new Scanner(System.in);
        DbConnector.init();
        ResultSet result;
        String codiceFiscale;

        do{
            System.out.println("Codice Fiscale: ");
            codiceFiscale = input.next();
        }while(codiceFiscale.length() != 16);
        System.out.println("Password: ");
        String password = input.next();

        String queryLogin = "SELECT * FROM `admin` WHERE `codiceFiscale`='"+codiceFiscale+"' and `password`='"+password+"' and `azienda`='"+azienda.getId()+"';";  //verifico che admin sia registrata
        result = DbConnector.executeQuery(queryLogin);
        if(!result.next()) return Optional.empty();  //se admin non è registrato
        String nome = result.getString("nome");
        DbConnector.closeConnection();
        return Optional.of(new Admin(codiceFiscale,nome,azienda));
    }

    public static Optional<Admin> registrazioneAdmin(Azienda azienda) throws SQLException {
        Scanner input = new Scanner(System.in);
        ResultSet result;
        DbConnector.init();

        System.out.println("Nome: ");
        String nome = input.next();
        System.out.println("Codice fiscale: ");
        String codiceFiscale = input.next();
        System.out.println("Password: ");
        String password = input.next();

        String queryLogin = "SELECT * FROM `admin` WHERE `codiceFiscale`='"+codiceFiscale+"';";  //verifico se esiste già admin con codice fiscale inserito
        result = DbConnector.executeQuery(queryLogin);
        if(result.next()) return Optional.empty();  //se esiste, non effettuo la registrazione

        try {
            DbConnector.insertQuery("INSERT INTO `admin` (`codiceFiscale`, `nome`, `password`, `azienda`) VALUES ('"+codiceFiscale+"','"+nome+"', '"+password+"','"+azienda.getId()+"');");
        } catch (SQLException e) {
            System.out.println("Errore nella registrazione dell'admin, codice fiscale già presente.");
            return Optional.empty();
        }

        DbConnector.closeConnection();
        return Optional.of(new Admin(codiceFiscale,nome,azienda));
    }

}
