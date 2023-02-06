package IDS;

import java.sql.SQLException;
import java.util.List;

public class Azienda extends myCliente{
    private final String partitaIva;
    private final List<Admin> listaAdmin;

    public Azienda(String nome, String partitaIva, List<Admin> listaAdmin) {
        super(nome);
        this.partitaIva = partitaIva;
        this.listaAdmin = listaAdmin;
    }

    @Override
    public String getId() {
        return partitaIva;
    }

    public List<Admin> getListaAdmin() {
        return listaAdmin;
    }
    
    public boolean addAdmin(Admin newAdmin) throws SQLException {
        String insertAdminQuery = "INSERT INTO `admin` (`codiceFiscale`, `nome`, `Azienda`) VALUES ('"+newAdmin.getCodiceFiscale()+"', '"+newAdmin.getNome()+"', '"+newAdmin.getAziendaAppartenenza()+"');";
        DbConnector.init();
        DbConnector.insertQuery(insertAdminQuery);
        return true;
    }
}
