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




}
