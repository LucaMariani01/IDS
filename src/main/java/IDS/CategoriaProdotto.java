package IDS;

public class CategoriaProdotto implements Categoria{
    private int id;
    private String nome;
    private String descrizione;

    public CategoriaProdotto(int id, String nome, String descrizione){
        this.descrizione = descrizione;
        this.id = id;
        this.nome = nome;
    }

    @Override
    public int getId() {
        return 0;
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
