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

        this.listaAdmin.add(newAdmin);
    }
}
