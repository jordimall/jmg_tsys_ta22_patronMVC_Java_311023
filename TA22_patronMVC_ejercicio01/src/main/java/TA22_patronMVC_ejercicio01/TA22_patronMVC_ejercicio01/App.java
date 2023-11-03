package TA22_patronMVC_ejercicio01.TA22_patronMVC_ejercicio01;


import controllers.ClienteController;
import models.Cliente;
import models.MySQL;
import models.User;
import views.ViewPrincipal;

public class App 
{
    public static void main( String[] args )
    {
        Cliente cliente = new Cliente();
        User user = new User();
        MySQL mysql = new MySQL();
        ViewPrincipal view = new ViewPrincipal();
        ClienteController cliCon = new ClienteController(cliente, view, user, mysql);
        cliCon.iniciarVista();
    }
}
