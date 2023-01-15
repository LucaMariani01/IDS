package IDS;

import java.util.ArrayList;
import java.util.Date;

public class ProgrammaLivelli extends Campagna{

    private int numeroLivelli;
    private ArrayList<Livello> listaLivelli;

    public ProgrammaLivelli(int id, Date dataFine,int numeroLivelli, ArrayList<Livello> listaLivelli, Date dataInizio) {
        super(id, dataFine, dataInizio);
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
        if(l == null) new IllegalArgumentException();
        if(this.listaLivelli.contains(l))new IllegalArgumentException();
        return this.listaLivelli.add(l);
    }

    public Boolean removeLivello(Livello l){
        if(l == null)new IllegalArgumentException();
        return this.listaLivelli.remove(l);
    }

}
