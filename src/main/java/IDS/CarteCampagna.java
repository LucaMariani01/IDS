package IDS;

public abstract class CarteCampagna implements Carte{
    private final int idCampagna;
    private final String idCliente;

    protected CarteCampagna(int idCampagna, String idCliente) {
        this.idCampagna = idCampagna;
        this.idCliente = idCliente;
    }

    public String getIdCliente() {
        return idCliente;
    }

    @Override
    public int getIdCampagna() {
        return idCampagna;
    }
}
