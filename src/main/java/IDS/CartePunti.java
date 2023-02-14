package IDS;

public class CartePunti extends CarteCampagna{
    private int puntiAccumulati;

    public CartePunti(String idUtente,int idCampagna) {
        super(idCampagna,idUtente);
        this.puntiAccumulati=0;
    }

    public int incrementaPunti(int puntiDaAggiungere) {
        // TODO: 14/02/2023 CHIAMATA AL DB PER AGGIORNARE IL VALORE
        return this.puntiAccumulati =+ puntiDaAggiungere;
    }

    public int decrementaPunti(int puntiDaTogliere) {
        // TODO: 14/02/2023 CHIAMATA AL DB PER AGGIORNARE IL VALORE
        return this.puntiAccumulati =- puntiDaTogliere;
    }

}
