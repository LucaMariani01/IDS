package IDS;
import java.util.ArrayList;

public class ProgrammaLivelli<P extends Premio> extends Campagna{

    private final int numeroLivelli;
    private final ArrayList<MyLivello<P>> listaLivelli;

    public ProgrammaLivelli(int id, String nome, String dataFine, int numeroLivelli, ArrayList<MyLivello<P>> listaLivelli, String dataInizio) {
        super(id,nome, dataFine, dataInizio);
        this.listaLivelli=listaLivelli;
        this.numeroLivelli=numeroLivelli;
    }

    public int getNumeroLivelli() {
        // TODO: 13/02/2023 query
        return numeroLivelli;
    }

    public ArrayList<MyLivello<P>> getLivelli() {
        return this.listaLivelli;
    }



}