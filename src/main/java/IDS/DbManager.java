package IDS;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class DbManager {

    public static Optional<Admin> loginAdmin(Azienda azienda) throws SQLException{
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

        DbConnector.insertQuery("INSERT INTO `admin` (`codiceFiscale`, `nome`, `password`, `azienda`) VALUES ('"+codiceFiscale+"','"+nome+"', '"+password+"','"+azienda.getId()+"');");
        DbConnector.closeConnection();
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
        DbConnector.closeConnection();
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

        DbConnector.insertQuery("INSERT INTO `aziende` (`nome`, `partitaIva`, `password`) " +
                "VALUES ('"+nome+"', '"+partitaIva+"', '"+password+"');");
        DbConnector.closeConnection();
        return Optional.of(new Azienda(nome,partitaIva,new ArrayList<>()));
    }

    public static Optional<CampagnaPunti> creaCampagnaPunti(String azienda) throws SQLException {
        Scanner input = new Scanner(System.in);
        DbConnector.init();

        System.out.println("NOME CAMPAGNA: "); //input dati login
        String nome = input.next();
        int id = azienda.hashCode() + nome.hashCode();
        System.out.println("INSERIRE MASSIMALE: "); //input dati login
        int maxPunti = Integer.parseInt(input.next());
        System.out.println("Inserisci la data inizio: ");
        String  dateIn = inputDataInizioFineCampagna();
        System.out.println("Inserisci la data fine: ");
        String  dateFin = inputDataInizioFineCampagna();

        DbConnector.insertQuery("INSERT INTO `campagnepunti` (`nome`, `maxPunti`, `dataInizio`,`dataFine`,`azienda`,`id`) " +
                "VALUES ('"+nome+"', '"+maxPunti+"', '"+ dateIn+"','"+ dateFin+"','"+azienda+"','"+id+"' );");
        DbConnector.closeConnection();
        return Optional.of(new CampagnaPunti(maxPunti, id, nome, dateFin, dateIn));
    }


    public static Optional<CampagnaSconti> creaCampagnaCashback(String partitaIvaAzienda) throws SQLException {
        Scanner input = new Scanner(System.in);
        DbConnector.init();

        System.out.println("NOME CAMPAGNA CASHBACK: ");
        String nome = input.next();
        int id = partitaIvaAzienda.hashCode() + nome.hashCode();
        System.out.println("SOGLIA MINIMA: ");
        int sogliaMin = input.nextInt();
        System.out.println("SOGLIA MASSIMA: ");
        int sogliaMax = input.nextInt();
        System.out.println("DATA INIZIO");
        String dataInizio = inputDataInizioFineCampagna();
        System.out.println("DATA FINE");
        String dataFine = inputDataInizioFineCampagna();

        DbConnector.insertQuery("INSERT INTO `cashback` (`nome`, `dataInizio`,`dataFine`,`sogliaMinima`,`sogliaMassima`,`azienda`,`id`) " +
                "VALUES ('"+nome+"', '"+dataInizio+"', '"+dataFine+"',"+sogliaMin+","+sogliaMax+",'"+partitaIvaAzienda+"','"+id+"');");
        DbConnector.closeConnection();

        return Optional.of(new CashBack(id, nome,dataFine,dataInizio,sogliaMin,sogliaMax));
    }

    public static Optional<CampagnaSconti> creaMembership(String partitaIvaAzienda) throws SQLException {
        Scanner input = new Scanner(System.in);
        DbConnector.init();

        System.out.println("NOME: ");
        String nome = input.nextLine();

        int id = partitaIvaAzienda.hashCode() + nome.hashCode();
        System.out.println("COSTO MEMBERSHIP ESCLUSIVA: ");
        int costo = input.nextInt();
        System.out.println("DATA INIZIO: ");
        String  dateIn = inputDataInizioFineCampagna();
        System.out.println("DATA FINE: ");
        String  dateFin = inputDataInizioFineCampagna();

        try {
            DbConnector.insertQuery("INSERT INTO membership(`dataInizio`,`nome`,`dataFine`,`costo`,`azienda`,`id`) " +
                    "VALUES ('"+dateIn+"','"+nome+"','"+dateFin+"','"+costo+"','"+partitaIvaAzienda+"','"+id+"');");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        DbConnector.closeConnection();
        return Optional.of(new Membership(id,dateFin, costo,  nome,dateIn));
    }

    private static String inputDataInizioFineCampagna() {
        Scanner input = new Scanner(System.in);
        String giorno, mese, anno;
        System.out.println("Giorno: ");
        giorno = input.nextLine();
        System.out.println("Mese: ");
        mese = input.nextLine();
        System.out.println("Anno: ");
        anno = input.nextLine();
        return anno + "-" + mese + "-" + giorno;
    }

    public static Optional<CampagnaSconti> creaCampagnaLivelli(String partitaIvaAzienda) throws SQLException{
        Scanner input = new Scanner(System.in);
        ArrayList<myLivello<MyPremio>> listaLivelli = new ArrayList<>();
        ArrayList<MyPremio> listaPremi = new ArrayList<>();
        DbConnector.init();
        int idCampagna=0,idLivello=0,numLivelli=0;
        double requisito=0;
        String dateFin="", dateIn="",nome="",nomeLivello="";
        boolean sqlError = false;

        do {
            sqlError = false;
            System.out.println("NOME CAMPAGNA: "); //input dati login
            nome = input.nextLine();
            idCampagna = partitaIvaAzienda.hashCode() + nome.hashCode();
            System.out.println("NUMERO LIVELLI: "); //input dati login
            numLivelli = input.nextInt();
            System.out.println("DATA INIZIO: ");
            dateIn = inputDataInizioFineCampagna();
            System.out.println("DATA FINE: ");
            dateFin = inputDataInizioFineCampagna();
            try {
                DbConnector.insertQuery("INSERT INTO `campagnelivello` (`id`,`nome`,`numLivelli`,`dataInizio`,`dataFine`,`azienda`) " +
                        "VALUES ('"+idCampagna+"','"+nome+"','"+numLivelli+"','"+dateIn+"','"+dateFin+"','"+partitaIvaAzienda+"');");
            } catch (SQLException e) { sqlError = true; }
        }while(sqlError);

        for(int i = 0 ; i < numLivelli; i++){  //input livelli appartenenti alla campagna a livelli creata
            do{
                sqlError = false;
                System.out.println("NOME LIVELLO: ");
                nomeLivello = input.next();
                System.out.println("SPESA MINIMA NECESSARIA: ");
                requisito = input.nextDouble();
                idLivello = nomeLivello.hashCode() + Integer.hashCode(idCampagna); //id livello è hash tra partita iva e nome livello

                try { //aggiunta livello al db
                    DbConnector.insertQuery("INSERT INTO `livelli`(`id`, `numLivello`, `campagnaLivello`, `nome`, `requisitoEntrata`)" +
                        "VALUES ('"+idLivello+"','"+(i+1)+"','"+idCampagna+"','"+nomeLivello+"','"+requisito+"');");
                }catch (SQLException e) { System.out.println("Nome del livello già esistente per questa campagna a livelli, riprovare."); sqlError = true; }
            }while(sqlError);

            do {
                sqlError = false;
                listaPremi.addAll(getPremi(idLivello));  //input premi corrispondenti al livello
                listaLivelli.add(new myLivello<>((i + 1), nomeLivello, listaPremi, requisito));
                for (MyPremio myPremio : listaPremi){ //aggiunta premi al livello corrispondente al db
                    try {
                        DbConnector.insertQuery("INSERT INTO `premi`(`codice`, `nome`, `premioLivello`) VALUES ('" + myPremio.getCod() + "','" + myPremio.getNome() + "','" + idLivello + "');");
                    } catch (SQLException e) {
                        sqlError = true;
                    }
                }
                listaPremi.clear();
            }while(sqlError);
        }
        return Optional.of(new ProgrammaLivelli<>(idCampagna,nome,dateFin ,numLivelli, listaLivelli,dateIn));
    }

    static ArrayList<MyPremio> getPremi(int idLivello){
        Scanner input = new Scanner(System.in);
        ArrayList<MyPremio> listaPremi = new ArrayList<>();
        String continua = "s";
        System.out.println("AGGIUNTA PREMI PER LIVELLO APPENA CREATO");
        while(continua.compareToIgnoreCase("s") == 0){
            System.out.println("NOME PREMIO");
            String nomePremio = input.nextLine();
            int codPremio = Integer.hashCode(idLivello) + nomePremio.hashCode();  //codice premio è hash code tra id livello e nome premio
            listaPremi.add(new MyPremio(codPremio, nomePremio));
            System.out.println("Vuoi inserire un nuovo premio? (s/n)");
            continua = input.nextLine();
        }
        return listaPremi;
    }

}
