package IDS;

import java.util.ArrayList;

public class Customer extends myCliente{
    private final String cognome;
    private final ArrayList<CampagnaSconti> listaCampagneAderite;
    private final String email;

    public Customer(int id, String nome, String cognome, String email) {
        super(id, nome);
        if ( email == null ) throw new NullPointerException();

        this.cognome = cognome;
        this.listaCampagneAderite = new ArrayList<>();
        this.email = email;
    }

    public String getCognome() {
        return cognome;
    }

    public ArrayList<CampagnaSconti> getListaCampgneAderite() {
        return listaCampagneAderite;
    }

    public String getEmail() {
        return email;
    }

    public boolean addCampagna(CampagnaSconti c)
    {
        return this.listaCampagneAderite.add(c);
    }
}
