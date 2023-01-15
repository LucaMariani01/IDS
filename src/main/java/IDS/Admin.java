package IDS;

public class Admin extends myCliente {
    private final Azienda aziendaPartenenza;

    public Admin(int id, String nome, Azienda a) {
        super(id, nome);

        if (a == null) throw new NullPointerException();
        this.aziendaPartenenza = a;
    }

    public Azienda getA() {
        return this.aziendaPartenenza;
    }
}
