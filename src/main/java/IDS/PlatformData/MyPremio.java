package IDS.PlatformData;

public class MyPremio implements Premio {

    private final int codice;
    private final String nome;

    public MyPremio(int codice, String nome){
        this.codice = codice;
        this.nome = nome;
    }

    @Override
    public int getCod() {
        return codice;
    }

    @Override
    public String getNome() {
        return nome;
    }
}
