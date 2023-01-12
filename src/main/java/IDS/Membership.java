package IDS;

import java.util.ArrayList;
import java.util.Date;

public class Membership extends Campagna{
    private final double costo;
    private ArrayList<Premio> catalogoPremi;
    private final String nome;

    public Membership(int id, Date dataFine, double costo, String nome) {
        // TODO: 10/01/2023 controlli
        super(id, dataFine);
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
        // TODO: 10/01/2023 aggiungi un premio alla lista
        return true;
    }
    public boolean removePremio(Premio p)
    {
        // TODO: 10/01/2023 RIMUOVI PREMIO DALLA LISTA
        return true;
    }
}
