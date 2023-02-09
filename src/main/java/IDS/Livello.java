package IDS;

import java.util.ArrayList;

public interface Livello<P> {
    public int getNumero();
    public double getRequisitoEntrata();
    public ArrayList<P> catalogoPremi();
    public String getNome();
}
