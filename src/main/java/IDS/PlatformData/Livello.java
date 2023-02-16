package IDS.PlatformData;

import java.util.ArrayList;

public interface Livello<P> {
    public int numero();
    public double requisitoEntrata();
    public ArrayList<P> catalogoPremi();
    public String nome();
}
