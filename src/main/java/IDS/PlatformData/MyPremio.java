package IDS.PlatformData;

import IDS.PlatformData.Premio;

public class MyPremio implements Premio {

    private int codice;

    private String nome;

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
