package IDS.PlatformData;

import java.util.Objects;

public record Admin(String codiceFiscale, String nome, Azienda aziendaAppartenenza) {
    public Admin(String codiceFiscale, String nome, Azienda aziendaAppartenenza) {
        if (codiceFiscale.length() != 16) throw new IllegalArgumentException();
        this.codiceFiscale = Objects.requireNonNull(codiceFiscale);
        this.nome = nome;
        this.aziendaAppartenenza = Objects.requireNonNull(aziendaAppartenenza);
    }
}