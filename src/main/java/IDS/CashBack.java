package IDS;

import java.util.ArrayList;
import java.util.Date;

public class CashBack extends Campagna{

    private  double  sogliaMinimaCashBack;
    private  double  sogliaMaxCashBack;

    private final ArrayList<Categoria> categorieProdotti;

    public CashBack(int id, Date dataFine, Date dataInizio, double sogliaMinimaCashBack, double sogliaMaxCashBack) {
        super(id, dataFine, dataInizio);
        if(sogliaMaxCashBack < sogliaMinimaCashBack || sogliaMinimaCashBack < 0 ) throw new IllegalArgumentException();
        this.sogliaMinimaCashBack = sogliaMinimaCashBack;
        this.sogliaMaxCashBack = sogliaMaxCashBack;
        this.categorieProdotti = new ArrayList<>();
    }

    public boolean aggiungiCategoria(Categoria c)
    {
        if(c == null) throw new NullPointerException();
        if (this.categorieProdotti.contains(c)) return false;
        return this.categorieProdotti.add(c);
    }

    public ArrayList<Categoria> getCategorieProdotti() {
        return categorieProdotti;
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
