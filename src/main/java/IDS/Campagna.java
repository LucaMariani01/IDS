package IDS;

import java.util.Date;
import java.util.PrimitiveIterator;

public abstract class Campagna implements CampagnaSconti {
    private final  int id;
    private final Date dataFine;

    public Campagna(int id, Date dataFine) {
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
