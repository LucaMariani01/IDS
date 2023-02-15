package IDS.DbManager;

public enum TipologiaCampagnaSconto {
    campagnelivello ("CAMPAGNA A LIVELLI"),
    campagnepunti ("CAMPAGNA A PUNTI"),
    cashback ("CAMPAGNA CASHBACK"),
    membership ("CAMPAGNA MEMBERSHIP");

    final String nome;

    TipologiaCampagnaSconto(String nome) {
        this.nome = nome;
    }
}
