package IDS;

import java.sql.SQLException;
import java.util.ArrayList;

public class Membership extends Campagna{
    private final double costo;
    private ArrayList<Premio> catalogoPremi;

    public Membership(int id, String dataFine, double costo, String nome, String dataInizio) {
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

    public boolean addPremio(Premio p) throws SQLException {
        if (p == null) throw new NullPointerException();
        DbConnector.init();
        String insertLivelloQuery = "INSERT INTO `premio` (`nome`,`campagnaSconto`) " +
                "VALUES ('"+p.getNome()+"','"+this.getId()+"');";
        DbConnector.insertQuery(insertLivelloQuery);
        DbConnector.closeConnection();
        return true;
    }
    public boolean removePremio(Premio p)
    {
        if (p == null) throw new NullPointerException();
        return this.catalogoPremi.remove(p);
    }
}
