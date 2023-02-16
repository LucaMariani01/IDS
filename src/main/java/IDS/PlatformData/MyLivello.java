package IDS.PlatformData;
import java.util.ArrayList;

public record MyLivello<P extends Premio>(int numero, String nome, ArrayList<P> catalogoPremi,
                                          double requisitoEntrata) implements Livello<P> {
    public MyLivello {
        if ((numero < 0) || (requisitoEntrata < 0)) throw new IllegalArgumentException();
    }
}
