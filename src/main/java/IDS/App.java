package IDS;

import java.sql.SQLException;
import java.util.Optional;

public class App {
    public static void main(String[] args) throws SQLException{
        int tipoCliente;
        ClientePiattaforma c;
        Optional<Azienda> aziendaLoggata = Optional.empty();

        do{
            tipoCliente = Menu.sceltaTipoCliente();
            switch (tipoCliente) {
                case 1 -> {}//cliente

                        //DashBoardCliente.menu();
                case 2 -> { //azienda
                    //switch () {
                    //    case 1 -> aziendaLoggata = DashBoardAzienda.login(); //login azienda
                    //    case 2 -> DashBoardAzienda.registrazione(); //registrazione azienda
                    //}
                    //if (aziendaLoggata.isEmpty()) break; //se azienda non ha effettuato login esce
                    //if (m.menuCampagna() == 1) {// TODO: 18/01/2023
                    //} else {
                    //    System.out.println("AL PROSSIMO AGGIORNAMENTO");
                    //}
                }
            }
        }while(tipoCliente!=0);

    }
}
