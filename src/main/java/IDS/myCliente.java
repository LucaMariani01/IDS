package IDS;

public abstract class myCliente implements ClientePiattaforma{
    private final String nome;

    public myCliente(String nome) {
        this.nome = nome;
    }

    @Override
    public abstract String getId();

    public String getName() {
        return nome;
    }
}
