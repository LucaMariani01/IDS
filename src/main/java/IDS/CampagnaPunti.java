package IDS;

import java.util.*;

public class CampagnaPunti extends Campagna{
    private final int maxPunti;
    private Map<Premio,Integer> premi;

    public CampagnaPunti(int maxPunti,int id, Date dataFine) {
        super(id,dataFine);
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
