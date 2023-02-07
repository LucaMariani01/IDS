package IDS;

import java.sql.SQLException;
import java.util.Optional;

public class DashBoardCliente {

    public static Optional<Customer> menu() throws SQLException {
        Menu m = new Menu();
        return switch (m.scelteLog()) {
            case 1 -> DbManager.loginCliente();
            case 2 -> DbManager.registrazioneCliente();
            default -> Optional.empty();
        };
    }

}