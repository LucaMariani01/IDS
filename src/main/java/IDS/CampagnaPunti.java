package IDS;

import java.util.*;

public class CampagnaPunti extends Campagna{
    private final int maxPunti;
    private Map<Premio,Integer> premi;

    public CampagnaPunti(int maxPunti,int id,String nome, Date dataFine,  Date dataInizio) {
        super(id,nome,dataFine, dataInizio);
        this.maxPunti = maxPunti;
        this.premi = new HashMap<>();
    }

    public int getMaxPunti(){
        return this.maxPunti;
    }

    public Boolean aggiungiPremi(Map<Premio,Integer> premi)
    {
        premi.putAll(Objects.requireNonNull(premi));
        return true;
    }

    public Map<Premio,Integer> getPremi()
    {
        return this.premi;
    }
}
