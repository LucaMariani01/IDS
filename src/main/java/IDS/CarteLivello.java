package IDS;

public class CarteLivello extends CartePunti{
    private int livello;

    public CarteLivello(String idUtente,int idCampagna)
    {
        super(idUtente, idCampagna);
        this.livello = 0;
    }

    public int saliDiLivello()
    {
        // TODO: 14/02/2023 CHIAMATA AL DB PER AGGIORNARE IL VALORE
        return this.livello++;
    }

    public int scendiDiLivello()
    {
        // TODO: 14/02/2023 CHIAMATA AL DB PER AGGIORNARE IL VALORE
        return this.livello--;
    }


}
