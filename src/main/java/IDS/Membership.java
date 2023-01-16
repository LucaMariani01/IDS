package IDS;

import java.util.ArrayList;
import java.util.Date;

public class Membership extends Campagna{
    private final double costo;
    private ArrayList<Premio> catalogoPremi;

    public Membership(int id, Date dataFine, double costo, String nome,  Date dataInizio) {
        super(id,nome, dataFine, dataInizio);
        if (costo <= 0   ) throw new IllegalArgumentException();
        this.costo = costo;
    }

    public double getCosto() {
        return costo;
    }

    public ArrayList<Premio> getCatalogoPremi() {
        return catalogoPremi;
    }

    public boolean addPremio(Premio p)
    {
        if (p == null) throw new NullPointerException();
        if (this.catalogoPremi.contains(p))return false;
        else{
            this.catalogoPremi.add(p);
            return true;
        }
    }
    public boolean removePremio(Premio p)
    {
        if (p == null) throw new NullPointerException();
        return this.catalogoPremi.remove(p);
    }
}
