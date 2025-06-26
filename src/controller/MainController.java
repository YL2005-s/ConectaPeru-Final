package controller;

import core.Controller;
import model.UsuarioEmpresarialModel;
import model.UsuarioPersonalModel;
import view.auth.LoginView;
import view.auth.RegisterEnterpriseView;
import view.auth.RegisterPersonalView;

public class MainController extends Controller {
    private final UsuarioPersonalModel usuarioPersonalModel = new UsuarioPersonalModel();
    private final UsuarioEmpresarialModel usuarioEmpresarialModel = new UsuarioEmpresarialModel();

    private final LoginController loginController = new LoginController(usuarioPersonalModel, usuarioEmpresarialModel);
    private final RegisterPersonalController registerPersonalController = new RegisterPersonalController(usuarioPersonalModel);
    private final RegisterEnterpriseController registerEnterpriseController = new RegisterEnterpriseController(usuarioEmpresarialModel);

    @Override
    public void run() {
        usuarioPersonalModel.loadFromDB();
        usuarioEmpresarialModel.loadFromDB();

        loginController.run();
        registerPersonalController.run();
        registerEnterpriseController.run();

        addView("LoginView",getLoginView());
        addView("RegistroUsuarioPersonalView", getRegisterPersonalView());
        addView("RegistroUsuarioEmpresarialView", getRegisterEnterpriseView());

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
}
