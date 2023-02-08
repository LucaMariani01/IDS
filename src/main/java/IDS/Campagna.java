package IDS;

public abstract class Campagna implements CampagnaSconti {
    private final int id;
    private final String nome;
    private final String dataInizio;
    private final String dataFine;

    public Campagna(int id, String nome, String dataFine, String dataInizio) {
        if (id < 0) throw new IllegalArgumentException();
        if (dataFine == null || dataInizio == null) throw new NullPointerException();
        this.id = id;
        this.nome = nome;
        this.dataFine = dataFine;
        this.dataInizio = dataInizio;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getDataInizio() {
        return dataInizio;
    }

    @Override
    public String getDataFine() {
        return dataFine;
    }
}
