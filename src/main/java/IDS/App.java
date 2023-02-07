package IDS;

import java.sql.SQLException;
import java.util.Optional;

public class App {
    public static void main(String[] args) throws SQLException{
        int tipoCliente;
        int azioneCliente;
        ClientePiattaforma c;
        Optional<Azienda> aziendaLoggata = Optional.empty();

        do{
            tipoCliente = Menu.sceltaTipoCliente();
            switch (tipoCliente) {
                case 1 -> {
                    if(DashBoardCliente.mainMenu().isPresent()){
                        do{
                            azioneCliente = DashBoardCliente.sceltaAzioniUtente();
                            switch (azioneCliente){
                                case 1 ->  System.out.println("lista campagne da scegliere");
                                case 2 ->   System.out.println("campagne aderite");
                            }
                        }while (azioneCliente!=0);

                    }else System.out.println("errore");
                }
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
