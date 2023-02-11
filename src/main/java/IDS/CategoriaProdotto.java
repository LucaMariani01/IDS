package IDS;

public class CategoriaProdotto implements Categoria{
    private final String nome;
    private String descrizione;

    public CategoriaProdotto(String nome, String descrizione){
        this.descrizione = descrizione;
        this.nome = nome;
    }

    @Override
    public String getNome() {
        return null;
    }

    @Override
    public String getDecsrizione() {
        return null;
    }
}
