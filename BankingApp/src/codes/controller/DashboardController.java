package codes.controller;

import codes.classes.Account;
import codes.main.Configs;
import codes.main.Main;
import codes.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @FXML
    private TextField tfNidNo;

    @FXML
    void btnAddAccAction(ActionEvent event) {
        Utils.changeScene(Configs.addAccPage, 600, 600);
    }

    @FXML
    void btnDetailsAction(ActionEvent event) {
        Utils.changeScene(Configs.empDetailsPage, 571, 750);
    }

    @FXML
    void btnTransactionAction(ActionEvent event) {
        Utils.goTransaction();
    }


    @FXML
    void paneDetailsAction(MouseEvent event) {
        Utils.changeScene(Configs.empDetailsPage, 571, 750);
    }

    @FXML
    void paneHomeAction(MouseEvent event) {
        Utils.goHome();
    }

    @FXML
    void paneTransactionAction(MouseEvent event) {
        Utils.goTransaction();
    }

    @FXML
    void btnLoginAction(ActionEvent event) {
        String nid = tfNidNo.getText();
        ArrayList<Account> acc = Main.bank.findAccWithNID(nid);
        if (acc.size() > 0) {
            Main.isUser = true;
            Main.nid = nid;
            Utils.changeScene(Configs.accDetailsPage, 571, 750);
        } else {
            Utils.alert("Warning!", "No account found with provided NID!", "warning");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Main.isUser = false;
    }
}
