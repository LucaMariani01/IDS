package IDS;

import java.util.ArrayList;
import java.util.Date;

public class ProgrammaLivelli extends Campagna{

    private int numeroLivelli;
    private ArrayList<Livello> listaLivelli;

    public ProgrammaLivelli(int id, Date dataFine,int numeroLivelli,ArrayList<Livello> listaLivelli) {
        // TODO: 10/01/2023 controlli
        super(id, dataFine);
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
        // TODO: 10/01/2023
        return true;
    }

    public Boolean removeLivello(Livello l)
    {
        // TODO: 10/01/2023
        return true;
    }

}
