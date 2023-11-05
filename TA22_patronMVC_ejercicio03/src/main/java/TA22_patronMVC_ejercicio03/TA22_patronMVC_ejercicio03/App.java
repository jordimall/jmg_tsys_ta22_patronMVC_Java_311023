package TA22_patronMVC_ejercicio03.TA22_patronMVC_ejercicio03;

import controllers.AsignadoAController;
import controllers.CientificosController;
import controllers.ProyectoController;
import models.Asignado_a;
import models.Cientificos;
import models.MySQL;
import models.Proyecto;
import models.User;
import views.ViewPrincipal;

public class App {
	public static void main(String[] args) {

		Cientificos cientificos = new Cientificos();
		Proyecto proyecto = new Proyecto();
		Asignado_a asigandoA = new Asignado_a();
		User user = new User();
		MySQL mysql = new MySQL();
		ViewPrincipal view = new ViewPrincipal();
		CientificosController cientCont = new CientificosController(cientificos, view, user, mysql);
		ProyectoController proyCont = new ProyectoController(proyecto, view, user, mysql);
		AsignadoAController asigcont = new AsignadoAController(asigandoA, view, user, mysql);

		cientCont.iniciarVista();
		proyCont.iniciarController();
		asigcont.iniciarController();

	}
}
