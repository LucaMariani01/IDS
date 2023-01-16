package IDS;

import java.util.ArrayList;
import java.util.Date;

public class ProgrammaLivelli extends Campagna{

    private int numeroLivelli;
    private ArrayList<Livello> listaLivelli;

    public ProgrammaLivelli(int id,String nome, Date dataFine,int numeroLivelli, ArrayList<Livello> listaLivelli, Date dataInizio) {
        super(id,nome, dataFine, dataInizio);
        if((dataFine.getTime() < dataInizio.getTime()) || (numeroLivelli < 0) || (id < 0)) throw new IllegalArgumentException();
        this.listaLivelli=listaLivelli;
        this.numeroLivelli=numeroLivelli;
    }

    public int getNumeroLivelli() {
        return numeroLivelli;
    }

    public ArrayList<Livello> getLivelli()
    {
        return this.listaLivelli;
    }

    public Boolean addLivello(Livello l){
        if(l == null) new NullPointerException();
        if(this.listaLivelli.contains(l))new IllegalArgumentException();
        return this.listaLivelli.add(l);
    }

    public Boolean removeLivello(Livello l){
        if(l == null)new NullPointerException();
        return this.listaLivelli.remove(l);
    }

}
