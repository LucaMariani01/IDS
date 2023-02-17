package IDS.DbManager;

import IDS.PlatformData.*;

import java.sql.SQLException;
import java.util.*;

public class DbManagerCampagne {

    public static Optional<CampagnaPunti> creaCampagnaPunti(String azienda) {
        Scanner input = new Scanner(System.in);
        Map<MyPremio,Integer> premi = new HashMap<>();
        String nomePremio;
        int puntiRiscatto;
        DbConnector.init();

        System.out.println("NOME CAMPAGNA: "); //input dati login
        String nome = input.next();
        int id = azienda.hashCode() + nome.hashCode();
        System.out.println("INSERIRE MAX PUNTI: "); //input dati login
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
        String continua = "s";
        System.out.println("INSERIRE I PREMI  :");

        while(continua.compareToIgnoreCase("s") == 0){
            System.out.println("PREMIO: ");
            nomePremio=input.next();
            do{
                System.out.println("INSERIRE I NECESSARI PUNTI PER RISCATTARE IL PREMIO (max: "+maxPunti+"): ");
                puntiRiscatto = input.nextInt();
            }while(puntiRiscatto<0 || puntiRiscatto> maxPunti);
            MyPremio p = new MyPremio((nome.hashCode()+Integer.hashCode(puntiRiscatto)), nomePremio);
            premi.put(p,puntiRiscatto);
            System.out.println("INSERIRE ALTRI PREMI? (s/n) :");
            continua = input.next();
        }

        aggiungiPremi(premi,id);
        DbConnector.closeConnection();
        return Optional.of(new CampagnaPunti(maxPunti, id, nome, dateFin, dateIn));

    }


    public static Optional<CampagnaSconti> creaCampagnaCashback(String partitaIvaAzienda) {
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

    public static Optional<CampagnaSconti> creaMembership(String partitaIvaAzienda) {
        Scanner input = new Scanner(System.in);
        DbConnector.init();

        System.out.println("NOME: ");
        String nome = input.nextLine();
        System.out.println("DESCRIZIONE VANTAGGI MEMBERSHIP ESCLUSIVA: ");
        String vantaggi = input.nextLine();
        System.out.println("DATA INIZIO: ");
        String  dateIn = inputDataInizioFineCampagna();
        System.out.println("DATA FINE: ");
        String  dateFin = inputDataInizioFineCampagna();
        int id = partitaIvaAzienda.hashCode() + nome.hashCode();
        System.out.println("COSTO MEMBERSHIP ESCLUSIVA: ");
        int costo = input.nextInt();

        try {
            DbConnector.insertQuery("INSERT INTO membership(`dataInizio`,`nome`,`dataFine`,`costo`,`azienda`,`id`,`descrizione`) " +
                    "VALUES ('"+dateIn+"','"+nome+"','"+dateFin+"','"+costo+"','"+partitaIvaAzienda+"','"+id+"','"+vantaggi+"');");
        } catch (SQLException e) {
            System.out.println("Errore nella creazione della campagna sconto, nome già inserito.");
            return Optional.empty();
        }
        DbConnector.closeConnection();
        return Optional.of(new Membership(id,dateFin, costo,  nome,dateIn,vantaggi));
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

    public static Optional<CampagnaSconti> creaCampagnaLivelli(String partitaIvaAzienda) {
        Scanner input = new Scanner(System.in);
        ArrayList<MyLivello<MyPremio>> listaLivelli = new ArrayList<>();
        ArrayList<MyPremio> listaPremi = new ArrayList<>();
        DbConnector.init();
        int idCampagna,idLivello,numLivelli;
        double requisito;
        String dateFin, dateIn,nome,nomeLivello;
        boolean erroreInserimento;

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
                System.out.println("AGGIUNTA PREMI PER LIVELLO APPENA CREATO");
                do {
                    listaPremi.clear();
                    listaPremi.addAll(getPremi(idLivello));  //input premi corrispondenti al livello
                }while(premioPresente(listaPremi)); //verifico che i premi per questo livello non siano duplicati
                listaLivelli.add(new MyLivello<>((i + 1), nomeLivello, listaPremi, requisito));
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
     * Verifica se si sta cercando d'inserire un premio duplicato per il livello corrente
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

    public static void aggiungiPremi(Map<MyPremio, Integer> premi, int codiceCampagna){
        for (MyPremio key : premi.keySet()) {
            Integer puntiNecessari = premi.get(key);
            try {
                DbConnector.insertQuery("INSERT INTO `premi`(`codice`, `nome`, `premioPunti`, `puntiNecessari`) " +
                        "VALUES (" + key.getCod() + ",'" + key.getNome() + "'," + codiceCampagna + ","+puntiNecessari+");");
            } catch (SQLException ignored) {}
        }
    }
}
