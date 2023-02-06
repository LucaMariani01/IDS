package IDS;

import java.sql.SQLException;
import java.util.Optional;

public class App {
    public static void main(String[] args) throws SQLException {
        Menu m = new Menu();
        ClientePiattaforma c ;
        Optional<Azienda> aziendaLoggata;

        switch (m.sceltaTipoCliente())
        {
            case 1:
                DashBoardCliente.menu();
                break;
            case 2:
                switch (m.scelteLog()) {
                    case 1 -> aziendaLoggata = DashBoardAzienda.login(); //login azienda
                    case 2 -> DashBoardAzienda.registrazione(); //registrazione azienda
                }

                switch (m.menuCampagna()) {
                    case 1:
                        // TODO: 18/01/2023
                    break;
                    default:System.out.println("AL PROSSIMO AGGIORNAMENTO");
                }
                break;
        }
    }
}
