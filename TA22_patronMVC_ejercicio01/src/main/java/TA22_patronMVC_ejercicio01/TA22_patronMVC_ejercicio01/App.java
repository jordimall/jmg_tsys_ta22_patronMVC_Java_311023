package TA22_patronMVC_ejercicio01.TA22_patronMVC_ejercicio01;


import models.Cliente;
import views.ViewPrincipal;
import controllers.ClienteController;

public class App 
{
    public static void main( String[] args )
    {
        Cliente cliente = new Cliente();
        ViewPrincipal view = new ViewPrincipal();
        ClienteController cliCon = new ClienteController(cliente, view);
        cliCon.iniciarVista();
    }
}
