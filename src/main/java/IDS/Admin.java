package IDS;

public class Admin extends myCliente {
    private final Azienda a ;

    public Admin(int id, String nome, Azienda a) {
        super(id, nome);

        if (a == null) throw new NullPointerException();
        this.a = a;
    }

    public Azienda getA() {
        return a;
    }
}
