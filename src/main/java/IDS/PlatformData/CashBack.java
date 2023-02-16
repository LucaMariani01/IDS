package IDS.PlatformData;

public class CashBack extends Campagna {
    private final double  sogliaMinimaCashBack;
    private final double  sogliaMaxCashBack;


    public CashBack(int id, String nome, String dataFine, String dataInizio, double sogliaMinimaCashBack, double sogliaMaxCashBack) {
        super(id, nome, dataFine, dataInizio);
        if(sogliaMaxCashBack < sogliaMinimaCashBack || sogliaMinimaCashBack < 0 ) throw new IllegalArgumentException();
        this.sogliaMinimaCashBack = sogliaMinimaCashBack;
        this.sogliaMaxCashBack = sogliaMaxCashBack;
    }


    public double getSogliaMinimaCashBack() {
        return sogliaMinimaCashBack;
    }

    public double getSogliaMaxCashBack() {
        return sogliaMaxCashBack;
    }

}
