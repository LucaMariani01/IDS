package IDS;

public class CarteCashback extends CarteCampagna{
    private float cashAccumolato;

    public CarteCashback(int idCampagna, String idCliente, float cashAccumolato) {
        super(idCampagna, idCliente);
        this.cashAccumolato = cashAccumolato;
    }

    public float incrementaCash(float cashDaAggiungere)
    {
        // TODO: 14/02/2023 CHIAMATA AL DB PER AGGIORNARE IL VALORE
        return this.cashAccumolato=+ cashDaAggiungere;
    }
    public float decrementaCash(float cashDaAggiungere)
    {
        // TODO: 14/02/2023 CHIAMATA AL DB PER AGGIORNARE IL VALORE
        return this.cashAccumolato=- cashDaAggiungere;
    }

}
