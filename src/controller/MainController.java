package controller;

import core.Controller;
import model.UsuarioEmpresarialModel;
import model.UsuarioPersonalModel;
import model.VacanteModel;
import view.app.PanelUsuarioPersonalView;
import view.app.PerfilUsuarioPersonalView;
import view.auth.LoginView;
import view.auth.RegisterEnterpriseView;
import view.auth.RegisterPersonalView;

public class MainController extends Controller {
    private final UsuarioPersonalModel usuarioPersonalModel = new UsuarioPersonalModel();
    private final UsuarioEmpresarialModel usuarioEmpresarialModel = new UsuarioEmpresarialModel();
    private final VacanteModel vacanteModel = new VacanteModel();

    private final LoginController loginController = new LoginController(usuarioPersonalModel, usuarioEmpresarialModel);
    private final RegisterPersonalController registerPersonalController = new RegisterPersonalController(usuarioPersonalModel);
    private final RegisterEnterpriseController registerEnterpriseController = new RegisterEnterpriseController(usuarioEmpresarialModel);
    private final PanelUsuarioPersonalController panelUsuarioPersonalController = new PanelUsuarioPersonalController(usuarioPersonalModel, vacanteModel);
    private final PerfilUsuarioPersonalController perfilUsuarioPersonalController = new PerfilUsuarioPersonalController(usuarioPersonalModel);

    @Override
    public void run() {
        usuarioPersonalModel.loadFromDB();
        usuarioEmpresarialModel.loadFromDB();
        vacanteModel.loadFromDB();

        loginController.run();
        registerPersonalController.run();
        registerEnterpriseController.run();
        panelUsuarioPersonalController.run();
        perfilUsuarioPersonalController.run();

        addView("LoginView",getLoginView());
        addView("RegistroUsuarioPersonalView", getRegisterPersonalView());
        addView("RegistroUsuarioEmpresarialView", getRegisterEnterpriseView());
        addView("PanelUsuarioPersonalView", getPanelUsuarioPersonalView());
        addView("PerfilUsuarioPersonalComponent", getPerfilUsuarioPersonalView());

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
}
