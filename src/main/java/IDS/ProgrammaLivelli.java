package IDS;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProgrammaLivelli<P extends Premio> extends Campagna{

    private int numeroLivelli;
    private ArrayList<myLivello<P>> listaLivelli;

    public ProgrammaLivelli(int id, String nome, String dataFine, int numeroLivelli, ArrayList<myLivello<P>> listaLivelli, String dataInizio) {
        super(id,nome, dataFine, dataInizio);
        this.listaLivelli=listaLivelli;
        this.numeroLivelli=numeroLivelli;
    }

    public int getNumeroLivelli() {
        return numeroLivelli;
    }

    public ArrayList<myLivello<P>> getLivelli() {
        return this.listaLivelli;
    }

    public Boolean addLivello(myLivello l) throws SQLException {
        DbConnector.init();
        String insertLivelloQuery = "INSERT INTO `livelli` (`numLivello`, `campagnaLivello`,`nome`,`requisitoEntrata`) " +
                "VALUES ('"+l.getNumero()+"','"+this.getId()+"','"+l.getNome()+"','"+l.getRequisitoEntrata()+"');";
        DbConnector.insertQuery(insertLivelloQuery);
        DbConnector.closeConnection();
        return true;
    }

    public void removeLivello() throws SQLException {

        int idLivelloDaRimuovere;
        DbConnector.init();
        String selectAllLivelli = "SELECT * FROM `livelli` WHERE `campagnaLivello` = "+this.getId()+"";
        System.out.println("Scegli il livello da eliminare: ");
        int i = 1;
        ResultSet result = DbConnector.executeQuery(selectAllLivelli);

        while (result.next()){
            System.out.println(i+")" +
                    " Numero livello: "+result.getInt("numLivello")+";" +
                    " Nome: "+result.getString("nome")+
                    " Requisito entrata: "+result.getDouble("requisitoEntrata")
            );
            i ++;
        }
        idLivelloDaRimuovere =DashBoardAzienda.livelloDaEliminare(i);
        String removeLivelloQuery = "DELETE FROM `livelli` WHERE `livelli`.`id` = "+idLivelloDaRimuovere+"";
        DbConnector.removeQuery(removeLivelloQuery);
        DbConnector.closeConnection();
    }

}