package IDS;

public abstract class myCliente implements ClientePiattaforma{
    private final int id;
    private final String nome;

    public myCliente(int id, String nome) {
        // TODO: 10/01/2023 CONTROLLI
        this.id = id;
        this.nome = nome;
    }

    @Override
    public int getId() {
        return id;
    }

    public String getName() {
        return nome;
    }
}
