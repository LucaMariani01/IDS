package IDS;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;
import java.text.DateFormat;

public class DbManager {

    public static Optional<Admin> loginAdmin(Azienda azienda) throws SQLException{
        Scanner input = new Scanner(System.in);
        DbConnector.init();
        ResultSet result;
        String codiceFiscale;

        System.out.println("Nome: "); //input dati login
        String nome = input.next();
        do{
            System.out.println("Codice Fiscale: ");
            codiceFiscale = input.next();
        }while(codiceFiscale.length() != 16);
        System.out.println("Password: ");
        String password = input.next();

        String queryLogin = "SELECT * FROM `admin` WHERE `codiceFiscale`='"+codiceFiscale+"' and `password`='"+password+"';";  //verifico che admin sia registrata
        result = DbConnector.executeQuery(queryLogin);
        if(!result.next()) return Optional.empty();  //se admin non è registrato

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

        DbConnector.insertQuery("INSERT INTO `admin` (`codiceFiscale`, `nome`, `password`, `azienda`) VALUES ('"+codiceFiscale+"','"+nome+"', '"+password+"','"+azienda.getId()+"');");
        return Optional.of(new Admin(codiceFiscale,nome,azienda));
    }


    public static Optional<Customer> loginCliente() throws SQLException {
        System.out.println("EMAIL: ");
        Scanner input = new Scanner(System.in);
        String nome = input.next();

        System.out.println("PASSWORD: ");
        String pass = input.next();

        DbConnector.init();
        try {
            if(DbConnector.executeQuery("SELECT * FROM `clienti` WHERE `email` = '"+nome+"' and `password` = '"+pass+"';").next()){
                ResultSet r =  DbConnector.executeQuery("SELECT * FROM `clienti` WHERE `email` = '"+nome+"' and `password` = "+pass+"';");
                return Optional.of(new Customer(r.getNString("nome"), r.getNString("cognome"), r.getNString("email")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    public static Optional<Customer> registrazioneCliente() throws SQLException{
        Scanner input = new Scanner(System.in);
        DbConnector.init();

        System.out.println("NOME: ");
        String nome = input.next();

        System.out.println("COGNOME: ");
        String cognome = input.next();

        System.out.println("EMAIL: ");
        String email = input.next();

        System.out.println("PASSWORD: ");
        String pass = input.next();
        try {
            DbConnector.insertQuery("INSERT INTO clienti(`email`,`nome`,`cognome`,`password`) " +
                    "VALUES ('"+email+"','"+nome+"','"+cognome+"','"+pass+"');");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.of(new Customer(nome, cognome, email));
    }

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

        DbConnector.insertQuery("INSERT INTO `aziende` (`nome`, `partitaIva`, `password`) " +
                "VALUES ('"+nome+"', '"+partitaIva+"', '"+password+"');");
        return Optional.of(new Azienda(nome,partitaIva,new ArrayList<>()));
    }

    public static Optional<CampagnaSconti> creaCampagnaPunti(String azienda) throws SQLException, ParseException {
        DateFormat formatoData = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
        Scanner input = new Scanner(System.in);
        DbConnector.init();

        System.out.println("NOME CAMPAGNA:"); //input dati login
        String nome = input.next();
        System.out.println("INSERIRE MASSIMALE:"); //input dati login
        int maxPunti = Integer.parseInt(input.next());
        System.out.println("Inserisci la data inizio [gg/mm/yyyy]: ");
        String  dateIn = input.nextLine();
        System.out.println("Inserisci la data fine [gg/mm/yyyy]: ");
        String  dateFin = input.nextLine();
        Date dIn = formatoData.parse(dateIn);
        Date dF = formatoData.parse(dateFin);

        if (dIn.getTime()>=dF.getTime()) return Optional.empty();
        DbConnector.insertQuery("INSERT INTO `campagnepunti` (`nome`, `maxPunti`, `dataInizio`,`dataFine`,`azienda`) " +
                "VALUES ('"+nome+"', '"+maxPunti+"', '"+ formattaData(dIn)+"','"+ formattaData(dF)+"','"+azienda+"' );");

        return Optional.of(new CampagnaPunti(maxPunti, 0, nome, formattaData(dF), formattaData(dIn)));
    }

    public static String formattaData(Date d)
    {
        return d.getYear() + "/" + d.getMonth() + "/" + d.getDay();
    }


}
