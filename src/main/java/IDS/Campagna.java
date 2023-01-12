package IDS;

import java.util.Date;

public abstract class Campagna implements CampagnaSconti {
    private final  int id;
    private final Date dataFine;

    public Campagna(int id, Date dataFine) {
        if (id < 0) throw new IllegalArgumentException();
        if (dataFine == null) throw new NullPointerException();
        this.id = id;
        this.dataFine = dataFine;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public Date getDurata() {
        return this.dataFine;
    }
}
