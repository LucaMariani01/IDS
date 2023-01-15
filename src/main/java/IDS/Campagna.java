package IDS;

import java.util.Date;

public abstract class Campagna implements CampagnaSconti {
    private final  int id;
    private final Date dataInizio;
    private final Date dataFine;

    public Campagna(int id, Date dataFine, Date dataInizio) {
        if (id < 0) throw new IllegalArgumentException();
        if (dataFine == null || dataInizio == null) throw new NullPointerException();
        this.id = id;
        this.dataFine = dataFine;
        this.dataInizio = dataInizio;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public Date getDataInizio() {
        return dataInizio;
    }

    @Override
    public Date getDataFine() {
        return dataFine;
    }
}
