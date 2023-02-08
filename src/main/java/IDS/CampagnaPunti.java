package IDS;

import java.sql.SQLException;
import java.util.*;

public class CampagnaPunti extends Campagna{
    private final int maxPunti;
    private Map<Premio,Integer> premi;

    public CampagnaPunti(int maxPunti, int id, String nome, String dataFine, String dataInizio) {
        super(id,nome,dataFine, dataInizio);
        this.maxPunti = maxPunti;
        this.premi = new HashMap<>();
    }

    public int getMaxPunti(){
        return this.maxPunti;
    }

    public boolean aggiungiPremi(Map<Premio,Integer> premi) throws SQLException {
        //premi.putAll(Objects.requireNonNull(premi));
        DbConnector.init();
        for ( Premio premio : premi.keySet() ){
            String insertPremioQuery = "INSERT INTO `premi` (`nome`, `campagnaSconto`) VALUES ('"+premio.getNome()+"', '"+this.getId()+"');";
            DbConnector.insertQuery(insertPremioQuery);
        }
        return true;
    }

    public Map<Premio,Integer> getPremi()
    {
        return this.premi;
    }
}
