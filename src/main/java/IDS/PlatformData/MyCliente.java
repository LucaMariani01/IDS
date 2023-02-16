package IDS.PlatformData;
public abstract class MyCliente implements ClientePiattaforma {
    private final String nome;
    public MyCliente(String nome) {
        this.nome = nome;
    }
    @Override
    public abstract String getId();

    public String getName() {
        return nome;
    }
}
