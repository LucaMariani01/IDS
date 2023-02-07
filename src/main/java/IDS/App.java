package IDS;

import java.sql.SQLException;
import java.util.Optional;

public class App {
    public static void main(String[] args) throws SQLException{
        Menu m = new Menu();
        ClientePiattaforma c ;
        Optional<Azienda> aziendaLoggata = Optional.empty();
        int tipoCliente;
        do{
            tipoCliente=m.sceltaTipoCliente();
            switch (tipoCliente) {
                case 1 -> //cliente
                        DashBoardCliente.menu();
                case 2 -> { //azienda
                    switch (m.scelteLog()) {
                        case 1 -> aziendaLoggata = DashBoardAzienda.login(); //login azienda
                        case 2 -> DashBoardAzienda.registrazione(); //registrazione azienda
                    }
                    if (aziendaLoggata.isEmpty()) break; //se azienda non ha effettuato login esce
                    if (m.menuCampagna() == 1) {// TODO: 18/01/2023
                    } else {
                        System.out.println("AL PROSSIMO AGGIORNAMENTO");
                    }
                }
            }
        }while(tipoCliente!=0);

    }
}
