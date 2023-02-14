package IDS;

import java.lang.ref.Cleaner;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

public class DbManagerCliente {
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

    public static Optional<Customer> registrazioneCliente() {
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

    public static void sceltaAziendaCampagneDisponibili(Customer cliente)throws SQLException{
        DbConnector.init();
        Scanner scr = new Scanner(System.in);
        String campagnaScelta,continua;
        Optional<Azienda> aziendaScelta = scegliAzienda();
        if (aziendaScelta.isEmpty()) {
            System.out.println("OPERAZIONE ANNULLATA.");
            return;
        }
        do{
            TipologiaCampagnaSconto tipoCampagnaScelta = TipologiaCampagnaSconto.values()[scegliCategoriaCampagna(aziendaScelta.get().getName())];
            String queryCampagneAzScelta = "SELECT * FROM `"+tipoCampagnaScelta+"` WHERE `azienda` = '"+aziendaScelta.get().getId()+"';";
            ResultSet campagneAzSel = DbConnector.executeQuery(queryCampagneAzScelta);
            campagnaScelta = scegliCampagna(campagneAzSel,cliente, tipoCampagnaScelta);  //metodo per selezionare la scelta tra le campagne sconto disponibili
            if(campagnaScelta.compareTo("") != 0){
                ResultSet listaCategoriaCampagnaSconto = DbConnector.executeQuery("SELECT * FROM `"+tipoCampagnaScelta+"` WHERE `nome` = '"+campagnaScelta+"';");

                while (listaCategoriaCampagnaSconto.next()){
                    int idCampScelta=listaCategoriaCampagnaSconto.getInt("id");
                    DbConnector.insertQuery("INSERT INTO `clienticampagnaaderite` (`emailCliente`, `"+tipoCampagnaScelta+"`) VALUES ('"+cliente.getId()+"',"+idCampScelta+");");
                    creaTessera(cliente,tipoCampagnaScelta,idCampScelta);
                }
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
            System.out.println("SCEGLI AZIENDA: "); //scelta azienda della campagna sconti
            scelta = scr.nextInt();
        }while(scelta<0 || scelta>aziendeIscritte.size());
        if(scelta == 0) return Optional.empty();
        return Optional.of(aziendeIscritte.get(scelta-1));
    }
    private static void creaTessera(Customer cliente, TipologiaCampagnaSconto tipoCampagna, int idCampScelta )throws SQLException{
        ResultSet res = DbConnector.executeQuery("SELECT idAdesione FROM `clienticampagnaaderite` " +
                "WHERE `emailCliente`='"+cliente.getId()+"' and `"+tipoCampagna+"` = "+idCampScelta+";");
        if(!res.next()) return;
        switch (tipoCampagna) {
            case cashback -> DbConnector.insertQuery("INSERT INTO `tessere`(`utente`,`cashbackAccumulato`, `adesione`) " +
                    "VALUES ('" + cliente.getId() + "','" + 0 + "','" + res.getInt("idAdesione") + "');");

            case campagnelivello -> DbConnector.insertQuery("INSERT INTO `tessere`(`utente`,`livelloAttuale`, `adesione`) " +
                    "VALUES ('"+cliente.getId()+"','"+1+"','"+res.getInt("idAdesione")+"');");

            case campagnepunti ->  DbConnector.insertQuery("INSERT INTO `tessere`(`utente`,`puntiAccumulati`, `adesione`) " +
                    "VALUES ('" + cliente.getId() + "','" + 0 + "','" + res.getInt("idAdesione") + "');");
        }
        System.out.println("TESSERA CREATA!");
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
     * Permette la scelta di una campagna di un certo tipo appartenente a un'azienda già scelta
     * @param result lista campagna appartenenti a una certa azienda di un certo tipo
     * @return nome campagna sconto scelta
     */
    public static String scegliCampagna(ResultSet result, Customer cliente, TipologiaCampagnaSconto tipoScelto) throws SQLException {
        Scanner scr = new Scanner(System.in);
        int scelta,index=1;
        String campagnaSconto;
        ArrayList<String> campagneDisponibili = new ArrayList<>();
        System.out.println("CAMPAGNE DISPONIBILI\n0) Annulla");
        while(result.next()){
            if(!checkDoppiaIscrizione(cliente,tipoScelto,result.getInt("id"))){
                campagnaSconto = result.getString("nome");
                System.out.println(index+") "+campagnaSconto);
                campagneDisponibili.add(campagnaSconto);
                index ++;
            }
        }
        do {
            System.out.println("A QUALE CAMPAGNA VUOI ISCRIVERTI?");
            scelta = scr.nextInt();
        }while(scelta<0 || scelta>index);

        if(scelta == 0) return "";  //il cliente non è interessato alle campagne sconto proposte
        return campagneDisponibili.get(scelta-1);
    }

    public static boolean checkDoppiaIscrizione(Customer cliente, TipologiaCampagnaSconto tipoScelto, int codiceCampagnaSconto ) throws SQLException {
        ResultSet resultSet = DbConnector.executeQuery("SELECT `emailCliente` ,'"+tipoScelto+"'  FROM `clienticampagnaaderite` WHERE "+tipoScelto+" = "+codiceCampagnaSconto+" and  emailCliente = '"+cliente.getId()+"' ;");
        return resultSet.next();
    }

    public static void getCampagneUtente(String email) throws SQLException {
        DbConnector.init();
        ResultSet campagneLivello = DbConnector.executeQuery("SELECT cl.`nome`,cl.`dataInizio`,cl.`dataFine`,cl.`numLivelli`,az.`nome` as azienda  FROM `clienticampagnaaderite` as cc,`campagnelivello` as cl,`aziende` as az WHERE cc.`campagnelivello` = cl.`id` and cl.`azienda` = az.`partitaIva` and cc.`emailCliente`='"+email+"';");
        ResultSet campagnePunti = DbConnector.executeQuery("SELECT cp.`nome`,cp.`dataInizio`,cp.`dataFine`,cp.`maxPunti`,az.`nome` as azienda FROM `clienticampagnaaderite` as cc,`campagnepunti` as cp,`aziende` as az WHERE cc.`campagnepunti` = cp.`id` and cp.`azienda` = az.`partitaIva` and cc.`emailCliente`='"+email+"';");
        ResultSet campagneCashback = DbConnector.executeQuery("SELECT cb.`nome`,cb.`dataInizio`,cb.`dataFine`,cb.`sogliaMinima`,cb.`sogliaMassima`,az.`nome` as azienda FROM `clienticampagnaaderite` as cc,`cashback` as cb,`aziende` as az WHERE cc.`cashback` = cb.`id` and cb.`azienda` = az.`partitaIva` and cc.`emailCliente`='"+email+"';");
        ResultSet campagneMembership = DbConnector.executeQuery("SELECT cm.`nome`,cm.`dataInizio`,cm.`dataFine`,cm.`costo`,az.`nome` as azienda FROM `clienticampagnaaderite` as cc,`membership` as cm,`aziende` as az WHERE cc.`membership` = cm.`id` and cm.`azienda` = az.`partitaIva` and cc.`emailCliente`='"+email+"';");

        int i = 0;
        while (campagneLivello.next()) {
            System.out.println(i+") "+campagneLivello.getString("nome")+"\t"+campagneLivello.getDate("dataInizio")+
                    "\t"+campagneLivello.getDate("dataFine")+"\tAzienda: "+campagneLivello.getString("azienda")+"\tNum Livelli: "+campagneLivello.getInt("numLivelli"));
            i++;
        }
        while (campagnePunti.next()) {
            System.out.println(i+")"+campagnePunti.getString("nome")+"\t"+campagnePunti.getDate("dataInizio")+
                    "\t"+campagnePunti.getDate("dataFine")+"\tAzienda: "+campagnePunti.getString("azienda")+"\tMassimo punti: "+campagnePunti.getInt("maxPunti"));
            i++;
        }
        while (campagneCashback.next()) {
            System.out.println(i+")"+campagneCashback.getString("nome")+"\t"+campagneCashback.getDate("dataInizio")+
                    "\t"+campagneCashback.getDate("dataFine")+"\tAzienda: "+campagneCashback.getString("azienda")+
                    "\tSoglia minima: "+campagneCashback.getInt("sogliaMinima")+"\tSoglia massima: "+campagneCashback.getInt("sogliaMassima"));
            i++;
        }
        while (campagneMembership.next()) {
            System.out.println(i + ")" + campagneMembership.getString("nome") + "\t" + campagneMembership.getDate("dataInizio") +
                    "\t" + campagneMembership.getDate("dataFine") + "\tAzienda: " + campagneMembership.getString("azienda") + "\tCosto: " + campagneMembership.getInt("costo"));
            i++;
        }
    }

    public static void lasciaRecensione(Customer cliente) throws SQLException {
        DbConnector.init();
        Scanner scr = new Scanner(System.in);
        System.out.println("SELEZIONA L'AZIENDA DA RECENSIRE: ");
        Optional<Azienda> aziendaScelta = scegliAzienda();
        System.out.println("LASCIA UNA RECENSIONE: ");
        String recensione = scr.nextLine();

        DbConnector.insertQuery("INSERT INTO `recensioni`(`utente`, `recensione`, `azienda`) " +
                "VALUES ('"+cliente.getId()+"','"+recensione+"','"+aziendaScelta.get().getId()+"')");

        DbConnector.closeConnection();
    }

    public static void visualizzaRecensioni() throws SQLException {
        DbConnector.init();
        System.out.println("SELEZIONA L'AZIENDA DI CUI VISUALIZZARE LE RECENSIONI: ");
        Optional<Azienda> aziendaScelta = scegliAzienda();
        if(aziendaScelta.isEmpty()) return;
        String query="SELECT DISTINCT c.`nome` as nomeCliente,`recensione` FROM `recensioni` as r,`clienti` as c, `aziende` as a " +
                "WHERE `azienda`='"+aziendaScelta.get().getId()+"';";
        ResultSet result = DbConnector.executeQuery(query);

        while (result.next()){
            System.out.println(result.getString("nomeCliente")+": "+result.getString("recensione"));
        }
        DbConnector.closeConnection();
    }
}
