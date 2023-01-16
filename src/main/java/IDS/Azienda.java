package IDS;

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
    
    public boolean addAdmin(Admin newAdmin)
    {
        if(this.listaAdmin.stream()
                .anyMatch(admin -> admin.getCodiceFiscale().compareTo(newAdmin.getCodiceFiscale()) == 0))
            return false;
        return this.listaAdmin.add(newAdmin);
    }
}
