package IDS;

public class App {
    public static void main(String[] args) {
        Menu m = new Menu();
        ClientePiattaforma c ;
        switch (m.sceltaTipoCliente())
        {
            case 1:
                DashBoardCliente.menu();
                break;
            case 2:
                    switch (m.menuCampagna())
                    {
                        case 1:
                            // TODO: 18/01/2023
                        break;
                        default:System.out.println("AL PROSSIMO AGGIORNAMENTO");
                    }

                break;
        }


    }
}
