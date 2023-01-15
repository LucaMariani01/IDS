package IDS;

import java.util.Date;

public interface CampagnaSconti {
    /**
     * @return id della campagna sconto
     */
    public int getId();

    /**
     * @return data inizio campagna sconti
     */
    public Date getDataInizio();

    /**
     * @return data fine campagna sconti
     */
    public Date getDataFine();
}
