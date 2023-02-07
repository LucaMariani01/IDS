package IDS;

import java.sql.SQLException;
import java.util.Optional;

public class App {
    public static void main(String[] args) throws SQLException{
        int tipoCliente;
        int azioneCliente;
        ClientePiattaforma c;
        Optional<Azienda> aziendaLoggata = Optional.empty();
        Optional<Customer> clienteLoggato = Optional.empty();

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
                                case 0 ->   System.out.println("logout");
                            }
                        }while (azioneCliente!=0);
                    }else System.out.println("errore");
                }
                case 2 -> { //azienda
                    if(DashBoardAzienda.mainMenu().isPresent()){
                        if(DashBoardAzienda.menuAdmin().isPresent()){
                            switch (DashBoardAzienda.menuAzienda()){
                                case 1 ->  System.out.println("lista campagne da scegliere");
                                case 2 ->  System.out.println("Work in progress...");
                                case 0 ->  System.out.println("logout admin");
                            }
                        }else System.out.println("errore admin");
                    }else System.out.println("errore azienda");
                }
            }
        }while(tipoCliente!=0);

    }
}
