package IDS;

import java.sql.SQLException;
public class App {
    public static void main(String[] args) throws SQLException{
        int tipoCliente;
        do{
            tipoCliente = Menu.sceltaTipoCliente();
            switch (tipoCliente) {
                case 1 -> DashBoardCliente.azioneCliente();
                case 2 -> DashBoardAzienda.azioneAzienda();
            }
        }while(tipoCliente!=0);
    }
}
