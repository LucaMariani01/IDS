package IDS;

import java.util.ArrayList;

public class myLivello implements Livello{
    private final int numero;
    private final String nome;
    private final ArrayList<Premio> catalogoPremi;
    private final ArrayList<Premio> requisitoEntrata;

    public myLivello(int numero, String nome, ArrayList<Premio> catalogoPremi, ArrayList<Premio> requisitoEntrata) {
        // TODO: 10/01/2023 CONTROLLI
        this.numero = numero;
        this.nome = nome;
        this.catalogoPremi = catalogoPremi;
        this.requisitoEntrata = requisitoEntrata;
    }

    @Override
    public int getNumero() {
        return this.numero;
    }

    @Override
    public ArrayList<Premio> getRequisitoEntrata() {
        return this.requisitoEntrata;
    }

    @Override
    public ArrayList<Premio> catalogoPremi() {
        return this.catalogoPremi;
    }

    @Override
    public String getNome() {
        return this.nome;
    }
}
