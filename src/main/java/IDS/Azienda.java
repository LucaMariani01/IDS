package IDS;

import java.util.ArrayList;

public class Azienda extends myCliente{
    private final int partitaIva;
    private ArrayList<Admin> listaAdmin;

    public Azienda(int id, String nome, int partitaIva, ArrayList<Admin> listaAdmin) {
        super(id, nome);
        this.partitaIva = partitaIva;
        this.listaAdmin = listaAdmin;
    }

    public int getPartitaIva() {
        return partitaIva;
    }

    public ArrayList<Admin> getListaAdmin() {
        return listaAdmin;
    }
    
    public boolean addAdmin(Admin newAdmin)
    {
        if(this.listaAdmin.stream().anyMatch(x -> x.getId() == newAdmin.getId())) return false;
        return this.listaAdmin.add(newAdmin);
    }
}
