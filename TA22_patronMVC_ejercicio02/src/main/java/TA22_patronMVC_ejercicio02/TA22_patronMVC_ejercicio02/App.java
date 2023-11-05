package TA22_patronMVC_ejercicio02.TA22_patronMVC_ejercicio02;

import controllers.ClienteController;
import controllers.VideosController;
import models.Cliente;
import models.MySQL;
import models.User;
import models.Videos;
import views.ViewPrincipal;


public class App 
{
    public static void main( String[] args )
    {
        Cliente cliente = new Cliente();
        Videos video = new Videos();
        User user = new User();
        MySQL mysql = new MySQL();
        ViewPrincipal view = new ViewPrincipal();
        ClienteController cliCon = new ClienteController(cliente, view, user, mysql);
        VideosController vidCon = new VideosController(video, view, user, mysql);
        vidCon.iniciarController();
        cliCon.iniciarVista();
    }
}
