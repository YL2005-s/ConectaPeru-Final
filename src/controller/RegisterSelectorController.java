package controller;

import core.Controller;
import view.auth.RegisterSelectorView;

public class RegisterSelectorController extends Controller {
    private RegisterSelectorView registerSelectorView;

    @Override
    public void run() {
        this.registerSelectorView = new RegisterSelectorView();
        addView("RegisterSelectorView", registerSelectorView);
        loadView("RegisterSelectorView");

        registerSelectorView.getBtn_personal().addActionListener(e -> {
            loadView("RegistroUsuarioPersonalView");
        });

        registerSelectorView.getBtn_enterprise().addActionListener(e -> {
            loadView("RegistroUsuarioEmpresarialView");
        });

        registerSelectorView.getBtn_back().addActionListener(e -> {
            loadView("LoginView");
        });
    }
}