package IDS;

import java.sql.SQLException;
import java.util.*;

public class CampagnaPunti extends Campagna{
    private final int maxPunti;
    private final Map<Premio,Integer> premi;

    public CampagnaPunti(int maxPunti, int id, String nome, String dataFine, String dataInizio) {
        super(id,nome,dataFine, dataInizio);
        this.maxPunti = maxPunti;
        this.premi = new HashMap<>();
    }

    public int getMaxPunti(){
        return this.maxPunti;
    }

    public boolean aggiungiPremi() throws SQLException {
        int puntiNecessari = DashBoardAzienda.inputPuntiNecessari(this.maxPunti);

        for (Premio p: DbManager.getPremi(puntiNecessari)){
            if(this.premi.containsKey(p)) System.out.println("PREMIO PRESENTE NON INSERITO");
            else{
                this.premi.put(p,puntiNecessari);
                DbManager.aggiungiPremio(p,puntiNecessari,super.getId());
            }
        }

        return true;
    }

    public Map<Premio,Integer> getPremi()
    {
        return this.premi;
    }
}
