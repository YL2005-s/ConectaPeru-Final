package controller;

import controller.auth.LoginController;
import controller.auth.RegisterEnterpriseController;
import controller.auth.RegisterPersonalController;
import controller.component.NavBarController;
import controller.component.SidebarController;
import core.Controller;
import model.*;
import view.app.*;
import view.auth.LoginView;
import view.auth.RegisterEnterpriseView;
import view.auth.RegisterPersonalView;

public class MainController extends Controller {
    private final UsuarioPersonalModel usuarioPersonalModel = new UsuarioPersonalModel();
    private final UsuarioEmpresarialModel usuarioEmpresarialModel = new UsuarioEmpresarialModel();
    private final VacanteModel vacanteModel = new VacanteModel();
    private final CapacitacionModel capacitacionModel = new CapacitacionModel();
    private final PostulacionModel postulacionModel = new PostulacionModel();

    private final LoginController loginController = new LoginController(usuarioPersonalModel, usuarioEmpresarialModel);
    private final RegisterPersonalController registerPersonalController = new RegisterPersonalController(usuarioPersonalModel);
    private final RegisterEnterpriseController registerEnterpriseController = new RegisterEnterpriseController(usuarioEmpresarialModel);
    //Panel
    private final SidebarController sidebarController = new SidebarController(sidebarComponent);
    private final NavBarController navBarController = new NavBarController(navbarComponent);
    private final PanelUsuarioPersonalController panelUsuarioPersonalController = new PanelUsuarioPersonalController(vacanteModel, postulacionModel);
    private final PerfilUsuarioPersonalController perfilUsuarioPersonalController = new PerfilUsuarioPersonalController(usuarioPersonalModel);
    private final CursosUsuarioPersonalController cursosUsuarioPersonalController = new CursosUsuarioPersonalController(capacitacionModel);
    private final PostulacionesUsuarioPersonalController postulacionesUsuarioPersonalController = new PostulacionesUsuarioPersonalController(postulacionModel);

    private final PanelUsuarioEmpresarialController panelUsuarioEmpresarialController = new PanelUsuarioEmpresarialController(vacanteModel);

    @Override
    public void run() {
        usuarioPersonalModel.loadFromDB();
        usuarioEmpresarialModel.loadFromDB();
        vacanteModel.loadFromDB();
        capacitacionModel.loadFromDB();
        postulacionModel.loadFromDB();

        loginController.run();
        registerPersonalController.run();
        registerEnterpriseController.run();
        //Panel
        sidebarController.run();
        navBarController.run();
        panelUsuarioPersonalController.run();
        perfilUsuarioPersonalController.run();
        cursosUsuarioPersonalController.run();
        postulacionesUsuarioPersonalController.run();
        panelUsuarioEmpresarialController.run();

        //Auth
        addView("LoginView",getLoginView());
        addView("RegistroUsuarioPersonalView", getRegisterPersonalView());
        addView("RegistroUsuarioEmpresarialView", getRegisterEnterpriseView());
        //Panel
        addView("PanelUsuarioPersonalView", getPanelUsuarioPersonalView());
        addView("PerfilUsuarioPersonalView", getPerfilUsuarioPersonalView());
        addView("CursosUsuarioPersonalView", getCursosUsuarioPersonalView());
        addView("PostulacionesUsuarioPersonalView", getPostulacionesUsuarioPersonalView());
        addView("PanelUsuarioEmpresarialView", getPanelUsuarioEmpresarialView());

        mainFrame.setVisible(true);
    }

    public LoginView getLoginView() {
        return loginController.getView();
    }

    public RegisterPersonalView getRegisterPersonalView() {
        return registerPersonalController.getView();
    }

    public RegisterEnterpriseView getRegisterEnterpriseView() {
        return registerEnterpriseController.getView();
    }

    public PanelUsuarioPersonalView getPanelUsuarioPersonalView() {
        return panelUsuarioPersonalController.getView();
    }

    public PerfilUsuarioPersonalView getPerfilUsuarioPersonalView() {
        return perfilUsuarioPersonalController.getView();
    }

    public CursosUsuarioPersonalView getCursosUsuarioPersonalView() {
        return cursosUsuarioPersonalController.getView();
    }

    public PostulacionesUsuarioPersonalView getPostulacionesUsuarioPersonalView() {
        return postulacionesUsuarioPersonalController.getView();
    }

    public PanelUsuarioEmpresarialView getPanelUsuarioEmpresarialView() {
        return panelUsuarioEmpresarialController.getView();
    }
}
