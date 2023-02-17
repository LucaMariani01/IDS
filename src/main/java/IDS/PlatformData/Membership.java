package IDS.PlatformData;

public class Membership extends Campagna {
    private final double costo;
    private final String vantaggi;
    public Membership(int id, String dataFine, double costo, String nome, String dataInizio,String vantaggi) {
        super(id,nome, dataFine, dataInizio);
        if (costo <= 0) throw new IllegalArgumentException();
        this.costo = costo;
        this.vantaggi = vantaggi;
    }

    public double getCosto() {
        return costo;
    }

    public String getVantaggi() {
        return vantaggi;
    }
}
