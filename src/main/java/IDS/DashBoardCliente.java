package IDS;

import javax.swing.text.html.Option;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Scanner;

public class DashBoardCliente {

    public static Optional<Customer> menu()
    {
        Menu m = new Menu();
        return switch (m.scelteLog()) {
            case 1 -> logIn();
            case 2 -> registrazione();
            default -> Optional.empty();
        };
    }

    public static Optional<Customer> logIn()
    {
        System.out.println("INSERISCI EMAIL :");
        Scanner s = new Scanner(System.in);
        String nome = s.next();

        System.out.println("INSERIRE PASSWORD");
        String pass = s.next();

        DbConnector.init();
        try {
             if(DbConnector.executeQuery("SELECT COUNT(*) FROM clienti WHERE email = "+nome+"&& password == "+pass).next())
             {
                ResultSet r =  DbConnector.executeQuery("SELECT * FROM clienti WHERE email = "+nome+"&& password == "+pass);
                return Optional.of(new Customer(r.getNString("nome"), r.getNString("cognome"), r.getNString("email")));
             }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }


    public static Optional<Customer> registrazione()
    {
        System.out.println("INSERISCI EMAIL :");
        Scanner s = new Scanner(System.in);
        String email = s.next();

        System.out.println("INSERIRE PASSWORD");
        String pass = s.next();

        System.out.println("INSERISCI NOME :");
        String nome = s.next();

        System.out.println("INSERIRE PASSWORD");
        String cognome = s.next();

        DbConnector.init();
        try {
            DbConnector.insertQuery("INSERT INTO clienti(email,nome,cognome,password) " +
                    "VALUES ("+email+","+nome+","+cognome+","+pass+")");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.of(new Customer(nome, cognome, email));
    }
}
