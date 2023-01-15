package IDS;

public abstract class myCliente implements ClientePiattaforma{
    private final int id;
    private final String nome;

    public myCliente(int id, String nome) {
        if((id < 0) || (nome.compareTo("") == 0)) throw new IllegalArgumentException();
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
