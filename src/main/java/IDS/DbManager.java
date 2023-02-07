package IDS;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Scanner;

public class DbManager {


    public static Optional<Customer> loginCliente() throws SQLException {
        System.out.println("EMAIL: ");
        Scanner input = new Scanner(System.in);
        String nome = input.next();

        System.out.println("PASSWORD: ");
        String pass = input.next();

        DbConnector.init();
        try {
            if(DbConnector.executeQuery("SELECT * FROM `clienti` WHERE `email` = '"+nome+"' and `password` = '"+pass+"';").next()){
                ResultSet r =  DbConnector.executeQuery("SELECT * FROM `clienti` WHERE `email` = '"+nome+"' and `password` = "+pass+"';");
                return Optional.of(new Customer(r.getNString("nome"), r.getNString("cognome"), r.getNString("email")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    public static Optional<Customer> registrazioneCliente() throws SQLException{
        Scanner input = new Scanner(System.in);
        DbConnector.init();

        System.out.println("NOME: ");
        String nome = input.next();

        System.out.println("COGNOME: ");
        String cognome = input.next();

        System.out.println("EMAIL: ");
        String email = input.next();

        System.out.println("PASSWORD: ");
        String pass = input.next();
        try {
            DbConnector.insertQuery("INSERT INTO clienti(`email`,`nome`,`cognome`,`password`) " +
                    "VALUES ('"+email+"','"+nome+"','"+cognome+"','"+pass+"');");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.of(new Customer(nome, cognome, email));
    }
}
