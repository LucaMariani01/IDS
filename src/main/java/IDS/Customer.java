package IDS;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class Customer extends myCliente{
    private final String cognome;
    private final ArrayList<CampagnaSconti> listaCampagneAderite; // puo essere tolto
    private final String email;

    public Customer(String nome, String cognome, String email) {
        super(nome);
        this.cognome = cognome;
        this.listaCampagneAderite = new ArrayList<>();
        this.email = Objects.requireNonNull(email);
    }

    @Override
    public String getId() {
        return email;
    }

    public String getCognome() {
        return cognome;
    }

    public ArrayList<CampagnaSconti> getListaCampagneAderite() {
        return listaCampagneAderite;
    }

    public void stampaCampagneAderite()
    {
        System.out.println("Campagne aderite dal cliente : "+this.cognome);
        try {
            DbManager.getCampagneUtente(this.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean addCampagna(CampagnaSconti c)
    {
        return this.listaCampagneAderite.add(c);
    }
}