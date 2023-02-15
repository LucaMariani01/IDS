package IDS.PlatformData;

import java.util.List;

public class Azienda extends MyCliente {
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
    
    public void addAdmin(Admin newAdmin) {
        /*String insertAdminQuery = "INSERT INTO `admin` (`codiceFiscale`, `nome`, `azienda`, `password`) VALUES ('"+newAdmin.getCodiceFiscale()+"', '"+newAdmin.getNome()+"', '"+newAdmin.getAziendaAppartenenza()+"','"+newAdmin.()+"');";
        DbConnector.init();
        DbConnector.insertQuery(insertAdminQuery);
        return true;*/
        this.listaAdmin.add(newAdmin);
    }
}
