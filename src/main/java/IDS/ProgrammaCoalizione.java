package IDS;

import java.util.ArrayList;
import java.util.Date;

public class ProgrammaCoalizione extends Campagna{
    private ArrayList<Azienda> listaAziende;
    private ArrayList<Premio> catalogoPremi;
    private final CampagnaSconti campagnaScelta;

    public ProgrammaCoalizione(int id, Date dataFine, CampagnaSconti campagnaScelta,  Date dataInizio) {
        super(id, dataFine,dataInizio);
        if(dataFine.getTime() < dataInizio.getTime()) throw new IllegalArgumentException();
        this.campagnaScelta = campagnaScelta;
    }

    public ArrayList<Azienda> getListaAziende() {
        return listaAziende;
    }

    public ArrayList<Premio> getCatalogoPremi() {
        return catalogoPremi;
    }

    public CampagnaSconti getCampagnaScelta() {
        return campagnaScelta;
    }
}
