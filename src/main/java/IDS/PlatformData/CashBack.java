package IDS.PlatformData;

import IDS.DbManager.DbConnector;

import java.sql.SQLException;
import java.util.ArrayList;

public class CashBack extends Campagna {
    private  double  sogliaMinimaCashBack;
    private  double  sogliaMaxCashBack;
    private final ArrayList<Categoria> categorieProdotti; // VA TOLTA

    public CashBack(int id, String nome, String dataFine, String dataInizio, double sogliaMinimaCashBack, double sogliaMaxCashBack) {
        super(id, nome, dataFine, dataInizio);
        if(sogliaMaxCashBack < sogliaMinimaCashBack || sogliaMinimaCashBack < 0 ) throw new IllegalArgumentException();
        this.sogliaMinimaCashBack = sogliaMinimaCashBack;
        this.sogliaMaxCashBack = sogliaMaxCashBack;
        this.categorieProdotti = new ArrayList<>();
    }

    public void aggiungiCategoria(Categoria c) throws SQLException {
        DbConnector.init();
        String insertLivelloQuery = "INSERT INTO `categorieProdotti` (`nome`, `descrizione`,`programmaCashBack`) VALUES ('"+c.getNome()+"','"+c.getDecsrizione()+"','"+this.getId()+"');";
        DbConnector.insertQuery(insertLivelloQuery);

    }

    public double getSogliaMinimaCashBack() {
        return sogliaMinimaCashBack;
    }

    public void setSogliaMinimaCashBack(double sogliaMinimaCashBack) {
        if (sogliaMinimaCashBack > this.sogliaMaxCashBack) throw  new IllegalArgumentException();
        this.sogliaMinimaCashBack = sogliaMinimaCashBack;
    }

    public double getSogliaMaxCashBack() {
        return sogliaMaxCashBack;
    }

    public void setSogliaMaxCashBack(double sogliaMaxCashBack) {
        if (sogliaMaxCashBack < this.getSogliaMinimaCashBack()) throw  new IllegalArgumentException();
        this.sogliaMaxCashBack = sogliaMaxCashBack;
    }
}
