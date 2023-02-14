package IDS;

import java.util.ArrayList;

public class MyLivello<P extends Premio> implements Livello<P>{
    private final int numero;
    private final String nome;
    private final ArrayList<P> catalogoPremi;
    private final double requisitoEntrata;

    public MyLivello(int numero, String nome, ArrayList<P> catalogoPremi, double requisitoEntrata) {
        if((numero < 0) || (requisitoEntrata < 0)) throw new IllegalArgumentException();
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
    public double getRequisitoEntrata() {
        return this.requisitoEntrata;
    }

    @Override
    public ArrayList<P> catalogoPremi() {
        return this.catalogoPremi;
    }

    @Override
    public String getNome() {
        return this.nome;
    }
}
