package IDS;

import java.sql.SQLException;
import java.util.Optional;

public class DbManagerCarte {

    public static Optional<Carte> creaNuovaCarta(String utente,TipologiaCampagnaSconto tipo , int id) throws SQLException {

        return switch (tipo)
        {
            case cashback -> creaNuovaCartaCash(utente,id);
            case campagnelivello -> creaNuovaCartaLivello(utente,id);
            case membership -> creaNuovaCartaMember(utente,id);
            case campagnepunti -> creaNuovaCartaPunti(utente,id);
            default -> Optional.empty();
        };

    }
    // TODO: 14/02/2023 creare le tabelle per ogni carta con le 3 collone
    public static Optional<Carte> creaNuovaCartaLivello(String utente, int id) throws SQLException {
        DbConnector.init();
        DbConnector.insertQuery("INSERT INTO `cartalivello` (`emailCliente`, `idCampagna`,`livello`)" +
                " VALUES ('"+utente+"','"+id+"','"+0+"');");

        return Optional.of(new CarteLivello(utente, id));
    }

    public static Optional<Carte> creaNuovaCartaPunti(String utente, int p) throws SQLException {
        DbConnector.init();
        DbConnector.insertQuery("INSERT INTO `cartapunti` (`emailCliente`, `idCampagna`,`punti`)" +
                " VALUES ('"+utente+"','"+p+"','"+0+"');");

        return Optional.of(new CartePunti(utente, p));
    }

    public static Optional<Carte> creaNuovaCartaMember(String utente, int m) throws SQLException {
        DbConnector.init();
        DbConnector.insertQuery("INSERT INTO `cartemember` (`emailCliente`, `idCampagna`)" +
                " VALUES ('"+utente+"','"+m+"');");

        return Optional.of(new CarteMembership(m, utente));
    }

    public static Optional<Carte> creaNuovaCartaCash(String utente, int c) throws SQLException {
        DbConnector.init();
        DbConnector.insertQuery("INSERT INTO `cartacash` (`emailCliente`, `idCampagna`,`cash`)" +
                " VALUES ('"+utente+"','"+c+"','"+0+"');");

        return Optional.of(new CarteCashback(c,utente,0));
    }

}
