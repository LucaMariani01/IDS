package IDS;

import java.sql.SQLException;
import java.util.Optional;

public class App {
    public static void main(String[] args) throws SQLException{
        int tipoCliente;
        int azioneCliente;
        ClientePiattaforma c;
        Optional<Azienda> aziendaLoggata;
        Optional<Customer> clienteLoggato;

        do{
            tipoCliente = Menu.sceltaTipoCliente();
            switch (tipoCliente) {
                case 1 -> {
                    clienteLoggato = DashBoardCliente.mainMenu();
                    if(clienteLoggato.isPresent()){
                        do{
                            azioneCliente = DashBoardCliente.sceltaAzioniUtente();
                            switch (azioneCliente){
                                case 1 ->  DbManager.sceltaAziendaCampagneDisponibili(clienteLoggato.get());
                                case 2 ->  clienteLoggato.get().stampaCampagneAderite();
                                case 0 ->  {
                                    clienteLoggato = Optional.empty();
                                    System.out.println("logout");
                                }
                            }
                        }while (azioneCliente!=0);
                    }
                }
                case 2 -> { //azienda
                    aziendaLoggata = DashBoardAzienda.mainMenu();
                    if(aziendaLoggata.isPresent()){
                        if(DashBoardAzienda.menuAdmin(aziendaLoggata.get()).isPresent()){
                            switch (DashBoardAzienda.menuAzienda()){
                                case 1 ->  DashBoardAzienda.menuCampagna(aziendaLoggata.get());
                                case 2 ->  System.out.println("Work in progress...");
                                case 0 ->  {
                                    System.out.println("logout admin");
                                    aziendaLoggata = Optional.empty();
                                }
                            }
                        }else System.out.println("errore admin");
                    }
                }
            }
        }while(tipoCliente!=0);
    }
}
