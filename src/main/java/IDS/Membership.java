package IDS;

import java.util.ArrayList;
import java.util.Date;

public class Membership extends Campagna{
    private final double costo;
    private ArrayList<Premio> catalogoPremi;
    private final String nome;

    public Membership(int id, Date dataFine, double costo, String nome) {
        super(id, dataFine);

        if ( nome == null) throw new NullPointerException();
        if (costo <= 0   ) throw new IllegalArgumentException();
        this.costo = costo;
        this.nome = nome;
    }

    public double getCosto() {
        return costo;
    }

    public ArrayList<Premio> getCatalogoPremi() {
        return catalogoPremi;
    }

    public String getNome() {
        return nome;
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
