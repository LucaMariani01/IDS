package IDS;

import java.util.ArrayList;

public interface Livello {
    public int getNumero();
    public double getRequisitoEntrata();
    public ArrayList<Premio> catalogoPremi();
    public String getNome();
}
