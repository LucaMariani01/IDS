package IDS;

import java.util.ArrayList;
import java.util.Objects;

public class Customer extends myCliente{
    private final String cognome;
    private final ArrayList<CampagnaSconti> listaCampagneAderite;
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

    public boolean addCampagna(CampagnaSconti c)
    {
        return this.listaCampagneAderite.add(c);
    }
}