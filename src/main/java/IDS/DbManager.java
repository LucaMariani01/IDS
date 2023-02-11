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
        Scanner input = new Scanner(System.in);
        ResultSet result;
        DbConnector.init();

        System.out.println("EMAIL: ");
        String email = input.next();
        System.out.println("PASSWORD: ");
        String password = input.next();

        String queryLogin = "SELECT * FROM `clienti` WHERE `email` = '"+email+"' AND `password` = '"+password+"';";
        result = DbConnector.executeQuery(queryLogin);
        if(!result.next()) return Optional.empty();

        Customer cliente = new Customer(result.getString("nome"),result.getString("cognome"),result.getString("email"));
        DbConnector.closeConnection();
        return Optional.of(cliente);
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
            System.out.println("Errore nell'aggiunta di un premio.");
            return false;
        }
        return true;
    }

    public static void sceltaAziendaCampagneDisponibili(Customer cliente)throws SQLException{
        DbConnector.init();
        Scanner scr = new Scanner(System.in);
        String campagnaScelta,continua;
        Optional<Azienda> aziendaScelta = scegliAzienda();
        if (aziendaScelta.isEmpty()) {
            System.out.println("NESSUNA AZIENDA ISCRITTA ALLA PIATTAFORMA AL MOMENTO");
            return;
        }

        do{
            TipologiaCampagnaSconto tipoCampagnaScelta = TipologiaCampagnaSconto.values()[scegliCategoriaCampagna(aziendaScelta.get().getName())];
            String queryCampagneAzScelta = "SELECT * FROM `"+tipoCampagnaScelta+"` WHERE `azienda` = '"+aziendaScelta.get().getId()+"';";
            ResultSet campagneAzSel = DbConnector.executeQuery(queryCampagneAzScelta);
            campagnaScelta = scegliCampagna(campagneAzSel);  //metodo per selezionare la scelta tra le campagne sconto disponibili
            if(campagnaScelta.compareTo("") != 0){
                ResultSet listaCategoriaCampagnaSconto = DbConnector.executeQuery("SELECT * FROM `"+tipoCampagnaScelta+"` WHERE `nome` = '"+campagnaScelta+"';");

                while (listaCategoriaCampagnaSconto.next())
                    DbConnector.insertQuery("INSERT INTO `clienticampagnaaderite` (`emailCliente`, `"+tipoCampagnaScelta+"`)" +
                        " VALUES ('"+cliente.getId()+"',"+listaCategoriaCampagnaSconto.getInt("id")+");");
            } else System.out.println("PROCEDURA DI ISCRIZIONE ANNULLATA.");

            System.out.println("VUOLE ISCRIVERSI AD UN'ALTRA CAMPAGNA SCONTO? (s/n)");
            continua = scr.next();
        }while(!(continua.compareToIgnoreCase("n") ==0));
    }

    /**
     * Metodo per scegliere l'azienda di cui visualizzare le campagne sconto
     * @return azienda scelta
     */
    private static Optional<Azienda> scegliAzienda() throws SQLException {
        ArrayList<Azienda> aziendeIscritte = new ArrayList<>();
        Scanner scr = new Scanner(System.in);
        DbConnector.init();
        int scelta;

        ResultSet result = DbConnector.executeQuery("SELECT `partitaIva`,`nome` FROM `aziende`;");  //scelta dell'azienda
        System.out.println("AZIENDE ISCRITTE: \n0) Annulla");
        while(result.next()){
            System.out.println(result.getRow()+") "+result.getString("nome"));
            aziendeIscritte.add(new Azienda(result.getString("nome"),result.getString("partitaIva"),null));
        }
        do{
            System.out.println("AZIENDA DI CUI VISUALIZZARE LE CAMPAGNE SCONTO: "); //scelta azienda della campagna sconti
            scelta = scr.nextInt();
        }while(scelta<0 || scelta>aziendeIscritte.size());
        if(scelta == 0) return Optional.empty();
        return Optional.of(aziendeIscritte.get(scelta-1));
    }

    /**
     * Metodo per scegliere la categoria di campagna sconto interessata al cliente
     * @param nomeAzienda nome dell'azienda avente la campagna sconto
     * @return
     */
    private static int scegliCategoriaCampagna(String nomeAzienda){
        Scanner scr = new Scanner(System.in);
        int scelta, index = 1;
        System.out.println("TIPOLOGIA CAMPAGNE SCONTO "+nomeAzienda);
        do{
            System.out.println(index+") "+TipologiaCampagnaSconto.campagnelivello.nome);
            System.out.println((++index)+") "+TipologiaCampagnaSconto.campagnepunti.nome);
            System.out.println((++index)+") "+TipologiaCampagnaSconto.cashback.nome);
            System.out.println((++index)+") "+TipologiaCampagnaSconto.membership.nome);
            scelta = scr.nextInt();
        }while( (scelta < 1) || (scelta > (TipologiaCampagnaSconto.values().length)));
        return scelta-1;
    }

    /**
     * Permette la scelta di una campagna di un certo tipo appartenente ad una azienda già scelta
     * @param result lista campagna appartenenti ad una certa azienda di un certo tipo
     * @return nome campagna sconto scelta
     */
    public static String scegliCampagna(ResultSet result) throws SQLException {
        Scanner scr = new Scanner(System.in);
        int scelta,index=1;
        String campagnaSconto;
        ArrayList<String> campagneDisponibili = new ArrayList<>();

        System.out.println("CAMPAGNE DISPONIBILI\n0) Annulla");
        while(result.next()){
            campagnaSconto = result.getString("nome");
            System.out.println(index+") "+campagnaSconto);
            campagneDisponibili.add(campagnaSconto);
            index ++;
        }
        do {
            System.out.println("A QUALE CAMPAGNA VUOI ISCRIVERTI?");
            scelta = scr.nextInt();
        }while(scelta<0 || scelta>index);
        if(scelta == 0) return "";  //il cliente non è interessato alle campagne sconto proposte
        return campagneDisponibili.get(scelta-1);
    }

}
