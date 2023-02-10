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

        try {
            DbConnector.insertQuery("INSERT INTO `admin` (`codiceFiscale`, `nome`, `password`, `azienda`) VALUES ('"+codiceFiscale+"','"+nome+"', '"+password+"','"+azienda.getId()+"');");
        } catch (SQLException e) {
            System.out.println("Errore nella registrazione dell'admin, codice fiscale già presente.");
            return Optional.empty();
        }

        DbConnector.closeConnection();
        return Optional.of(new Admin(codiceFiscale,nome,azienda));
    }


    public static Optional<Customer> loginCliente() throws SQLException {
        System.out.println("EMAIL: ");
        Scanner input = new Scanner(System.in);
        ResultSet result;
        String nome = input.next();

        System.out.println("PASSWORD: ");
        String pass = input.next();

        DbConnector.init();

        String queryLogin = "SELECT * FROM `clienti` WHERE `email` = '"+nome+"' AND `password` = '"+pass+"';";
        result = DbConnector.executeQuery(queryLogin);
        String nomeUtente = result.getString("nome");
        String cognomeUtente =  result.getString("cognome");
        String emailUtente =  result.getString("email");
        DbConnector.closeConnection();
        if(!result.next()) return Optional.empty();
        return Optional.of(new Customer(nomeUtente,cognomeUtente,emailUtente));

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
            System.out.println("Errore nella registrazione cliente, email già presente.");
            return Optional.empty();
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

    public static Optional<CampagnaPunti> creaCampagnaPunti(String azienda) throws SQLException {
        Scanner input = new Scanner(System.in);
        DbConnector.init();

        System.out.println("NOME CAMPAGNA: "); //input dati login
        String nome = input.next();
        int id = azienda.hashCode() + nome.hashCode();
        System.out.println("INSERIRE MASSIMALE: "); //input dati login
        int maxPunti = Integer.parseInt(input.next());
        System.out.println("DATA INIZIO: ");
        String  dateIn = inputDataInizioFineCampagna();
        System.out.println("DATA FINE: ");
        String  dateFin = inputDataInizioFineCampagna();

        try {
            DbConnector.insertQuery("INSERT INTO `campagnepunti` (`nome`, `maxPunti`, `dataInizio`,`dataFine`,`azienda`,`id`) " +
                    "VALUES ('"+nome+"', '"+maxPunti+"', '"+ dateIn+"','"+ dateFin+"','"+azienda+"','"+id+"' );");
        } catch (SQLException e) {
            System.out.println("Errore nella creazione della campagna sconto, nome già inserito.");
            return Optional.empty();
        }
        DbConnector.closeConnection();

        CampagnaPunti c = new CampagnaPunti(maxPunti, id, nome, dateFin, dateIn);
        int flag = 1;
        while (flag == 1)
        {
            System.out.println("INSERISCI PREMI :");
            c.aggiungiPremi();

            System.out.println("VUOI INSERIRE ALTRI PREMI : [ALTRO-no] [1-si]");
            flag = input.nextInt();
        }

        return Optional.of(c);
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

        try {
            DbConnector.insertQuery("INSERT INTO `cashback` (`nome`, `dataInizio`,`dataFine`,`sogliaMinima`,`sogliaMassima`,`azienda`,`id`) " +
                    "VALUES ('"+nome+"', '"+dataInizio+"', '"+dataFine+"',"+sogliaMin+","+sogliaMax+",'"+partitaIvaAzienda+"','"+id+"');");
        } catch (SQLException e) {
            System.out.println("Errore nella creazione della campagna sconto, nome già inserito.");
            return Optional.empty();
        }

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
            System.out.println("Errore nella creazione della campagna sconto, nome già inserito.");
            return Optional.empty();
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
        boolean erroreInserimento = false;

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
        } catch (SQLException e) {
            System.out.println("Errore nella creazione della campagna sconto, nome già inserito.");
            return Optional.empty();
        }

        for(int i = 0 ; i < numLivelli; i++){  //input livelli appartenenti alla campagna a livelli creata
            do{
                erroreInserimento = false;
                System.out.println("NOME LIVELLO: ");
                nomeLivello = input.next();
                System.out.println("SPESA MINIMA NECESSARIA: ");
                requisito = input.nextDouble();
                idLivello = nomeLivello.hashCode() + Integer.hashCode(idCampagna); //id livello è hash tra partita iva e nome livello

                try { //aggiunta livello al db
                    DbConnector.insertQuery("INSERT INTO `livelli`(`id`, `numLivello`, `campagnaLivello`, `nome`, `requisitoEntrata`)" +
                        "VALUES ('"+idLivello+"','"+(i+1)+"','"+idCampagna+"','"+nomeLivello+"','"+requisito+"');");
                }catch (SQLException e) { System.out.println("Nome del livello già esistente per questa campagna a livelli, riprovare."); erroreInserimento = true; }
            }while(erroreInserimento);

            do {
                erroreInserimento = false;
                do {
                    listaPremi.clear();
                    listaPremi.addAll(getPremi(idLivello));  //input premi corrispondenti al livello
                }while(premioPresente(listaPremi)); //verifico che i premi per questo livello non siano duplicati
                listaLivelli.add(new myLivello<>((i + 1), nomeLivello, listaPremi, requisito));
                for (MyPremio myPremio : listaPremi){ //aggiunta premi al livello corrispondente al db
                    try {
                        DbConnector.insertQuery("INSERT INTO `premi`(`codice`, `nome`, `premioLivello`) VALUES ('" + myPremio.getCod() + "','" + myPremio.getNome() + "','" + idLivello + "');");
                    } catch (SQLException e) {
                        System.out.println("Il nome di uno o più premi già esistente per questo livello, riprovare.");
                        erroreInserimento = true;
                    }
                }
                listaPremi.clear();
            }while(erroreInserimento);
        }
        return Optional.of(new ProgrammaLivelli<>(idCampagna,nome,dateFin ,numLivelli, listaLivelli,dateIn));
    }

    /**
     * Verifica se si sta cercando di inserire un premio duplicato per il livello corrente
     * @param listaPremi è la lista dei premi del livello corrente
     * @return true se uno dei premi è già presente in questo livello
     */
    private static boolean premioPresente(ArrayList<MyPremio> listaPremi) {
        for(int i = 0 ; i < listaPremi.size() ; i ++) {
            MyPremio premio = listaPremi.get(i);
            for (MyPremio premioAttuale : listaPremi) {
                if(listaPremi.indexOf(premioAttuale) != i)
                    if(premio.getNome().compareTo(premioAttuale.getNome())==0) return true;
            }
        }
        return false;
    }

    /**
     * Aggiunge premi alla campagna
     * @param idLivello livello dei premi
     * @return lista dei premi
     */
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

    public static boolean aggiungiPremio(Premio p,int puntiNecessari,int codiceCampagna) throws SQLException {
        DbConnector.init();

        try {
            DbConnector.insertQuery("INSERT INTO `premi`(`codice`, `nome`, `premioPunti`, `puntiNecessari`) " +
                    "VALUES ('" + p.getCod() + "','" + p.getNome() + "','" + codiceCampagna + "','"+puntiNecessari+"');");
        } catch (SQLException e) {
            return false;
        }
        return true;

    }

    public static void sceltaAziendaCampagneDisponibili(Customer cliente)throws SQLException{
        DbConnector.init();
        Scanner scr = new Scanner(System.in);
        int scelta;
        String campagnaScelta;
        String continua ;
        //scleta dell'azienda
        ResultSet result = DbConnector.executeQuery("SELECT `partitaIva`,`nome` FROM `aziende`;");
        int index = 1;
        System.out.println("Aziende iscritte alla piattaforma");
        while(result.next()){
            System.out.println(index+") "+result.getString("nome"));
            index++;
        }

        //scelta della campgna sconti
        do{
            System.out.println("Scegli l'aziende di cui vuoi visualizzare la campagna socnti");
            scelta = scr.nextInt();
        }while(scelta<0 || scelta>index);

        do{
            System.out.println("Quale categoria di campagne sconti vuoi visualizzare fra?");
            do{
                System.out.println(TipologiaCampagnaSconto.campagneLivelli.name());
                System.out.println(TipologiaCampagnaSconto.campagnePunti.name());
                System.out.println(TipologiaCampagnaSconto.cashback.name());
                System.out.println(TipologiaCampagnaSconto.membership.name());
                scelta = scr.nextInt();
            }while( (scelta < 1) || (scelta > (TipologiaCampagnaSconto.values().length)));

            //visualizzo le campagne sconti dell'azienda scelta
            switch(scelta){
                case(1)->{
                    ResultSet result2 = DbConnector.executeQuery("SELECT * FROM `campagnelivello` WHERE azienda = '"+result.getInt("partitaIva")+"';");
                    campagnaScelta = scegliCampagna(result2);
                    result2 = DbConnector.executeQuery("SELECT * FROM `campagnelivello` WHERE `nome` = '"+campagnaScelta+"';");
                    DbConnector.insertQuery("INSERT INTO `clienticampagnaaderite`(`emailCliente`, `idCampagna`)"+
                            "VALUES ('"+cliente.getId()+"','"+result2.getInt("id")+"';");
                }
                case(2)->{
                    ResultSet result2 = DbConnector.executeQuery("SELECT * FROM `campagnePunti` WHERE azienda = '"+result.getInt("partitaIva")+"';");
                    campagnaScelta = scegliCampagna(result2);
                    result2 = DbConnector.executeQuery("SELECT * FROM `campagnePunti` WHERE `nome` = '"+campagnaScelta+"';");
                    DbConnector.insertQuery("INSERT INTO `clienticampagnaaderite`(`emailCliente`, `idCampagna`)"+
                            "VALUES ('"+cliente.getId()+"','"+result2.getInt("id")+"';");
                }
                case(3)->{
                    ResultSet result2 = DbConnector.executeQuery("SELECT * FROM `cashback` WHERE azienda = '"+result.getInt("partitaIva")+"';");
                    campagnaScelta = scegliCampagna(result2);
                    result2 = DbConnector.executeQuery("SELECT * FROM `cashback` WHERE `nome` = '"+campagnaScelta+"';");
                    DbConnector.insertQuery("INSERT INTO `clienticampagnaaderite`(`emailCliente`, `idCampagna`)"+
                            "VALUES ('"+cliente.getId()+"','"+result2.getInt("id")+"';");
                }
                case(4)->{
                    ResultSet result2 = DbConnector.executeQuery("SELECT * FROM `membership` WHERE azienda = '"+result.getInt("partitaIva")+"';");
                    campagnaScelta = scegliCampagna(result2);
                    result2 = DbConnector.executeQuery("SELECT * FROM `membership` WHERE `nome` = '"+campagnaScelta+"';");
                    DbConnector.insertQuery("INSERT INTO `clienticampagnaaderite`(`emailCliente`, `idCampagna`)"+
                            "VALUES ('"+cliente.getId()+"','"+result2.getInt("id")+"';");
                }
            }
            System.out.println("Vuoi visualizzare altre campagne sconti? (s/n)");
            continua = scr.nextLine();
        }while(!(continua.compareToIgnoreCase("n") ==0));
    }

    public static String scegliCampagna(ResultSet result) throws SQLException {
        Scanner scr = new Scanner(System.in);
        String scelta;
        int i = 1;
        System.out.println("Campagne disponibili");
        while(result.next()){
            System.out.println(i+") "+result.getString("nome"));
            i++;
        }
        System.out.println("scegli a quale campagna vuoi iscriverti");
        scelta = scr.nextLine();
        return scelta;
    }

}
