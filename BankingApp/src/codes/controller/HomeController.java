package codes.controller;

import codes.main.Configs;
import codes.main.Main;
import codes.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class HomeController {
    @FXML
    void btnAccAction(ActionEvent event) {
        Main.isUser = true;
        Utils.changeScene(Configs.accDashPage);
    }

    @FXML
    void btnEmpAction(ActionEvent event) {
        Main.isUser = false;
        Utils.changeScene(Configs.empDashPage);
    }

    @FXML
    void btnQuitAction(ActionEvent event) {
        Utils.exit();
    }
}
