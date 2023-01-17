package IDS;

import java.sql.SQLException;
import java.util.ArrayList;

public class Azienda extends myCliente{
    private final String partitaIva;
    private final ArrayList<Admin> listaAdmin;

    public Azienda(String nome, String partitaIva, ArrayList<Admin> listaAdmin) {
        super(nome);
        this.partitaIva = partitaIva;
        this.listaAdmin = listaAdmin;
    }

    @Override
    public String getId() {
        return partitaIva;
    }

    public ArrayList<Admin> getListaAdmin() {
        return listaAdmin;
    }
    
    public boolean addAdmin(Admin newAdmin) throws SQLException {
        /*if(this.listaAdmin.stream()
                .anyMatch(admin -> admin.getCodiceFiscale().compareTo(newAdmin.getCodiceFiscale()) == 0))
            return false;
        return this.listaAdmin.add(newAdmin);*/
        String insertAdminQuery = "INSERT INTO `admin` (`codiceFiscale`, `nome`, `Azienda`) VALUES ('"+newAdmin.getCodiceFiscale()+"', '"+newAdmin.getNome()+"', '"+newAdmin.getAziendaAppartenenza()+"');";
        //String codiceFiscaleEsistenteQuery = "SELECT * FROM `admin` WHERE `admin`.`codiceFiscale` = "+newAdmin.getCodiceFiscale()+";";

        DbConnector.init();
        DbConnector.insertQuery(insertAdminQuery);
        return true;
    }
}
