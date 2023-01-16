package IDS;

import java.util.Objects;

public class Admin {
    private final String codiceFiscale;
    private final String nome;
    private final Azienda aziendaAppartenenza;

    public Admin(String codiceFiscale, String nome, Azienda aziendaAppartenenza) {
        if (codiceFiscale.length() != 13) throw new IllegalArgumentException();
        this.codiceFiscale = Objects.requireNonNull(codiceFiscale);
        this.nome = nome;
        this.aziendaAppartenenza  = Objects.requireNonNull(aziendaAppartenenza);
    }

    public String getCodiceFiscale() {
        return this.codiceFiscale;
    }

    public String getNome() {
        return this.nome;
    }

    public Azienda getAziendaAppartenenza() {
        return this.aziendaAppartenenza;
    }
}