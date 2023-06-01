package codes.controller;


import codes.main.Configs;
import codes.main.Main;
import codes.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class AddAccController {

    @FXML
    private RadioButton rdoCurrent;

    @FXML
    private RadioButton rdoSaving;

    @FXML
    private RadioButton rdoStudent;

    @FXML
    private TextField tfBalance;

    @FXML
    private TextField tfMulti;

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfUniName;

    @FXML
    private TextField tfNid;

    @FXML
    private Button btnSubmit;

    @FXML
    void accTypeAction(MouseEvent e) {
        RadioButton r = (RadioButton) e.getSource();
        if (r.equals(rdoSaving)) {
            toggle(true, false, false);
            tfMulti.setPromptText("Enter Max Withdraw Limit");
            tfUniName.setVisible(false);
        } else if (r.equals(rdoCurrent)) {
            toggle(false, true, false);
            tfMulti.setPromptText("Enter Trading License");
            tfUniName.setVisible(false);
        } else if (r.equals(rdoStudent)) {
            toggle(false, false, true);
            tfMulti.setPromptText("Enter Student ID");
            tfUniName.setVisible(true);
        }
    }

    private boolean validate() {
        // For All
        if (tfName.getText().isEmpty()) {
            Utils.alert("Error!", "Name Cannot be Empty!", "Error");
            return false;
        }
        if (tfNid.getText().isEmpty()) {
            Utils.alert("Error!", "NID Cannot be Empty!", "Error");
            return false;
        }
        if (tfBalance.getText().isEmpty()) {
            Utils.alert("Error!", "Balance Cannot be Empty!", "Error");
            return false;
        }
        if (!Utils.isNum(tfBalance.getText())) {
            Utils.alert("Error!", "Balance Must be Numeric!", "Error");
            return false;
        }

        // For Current Acc
        if (rdoCurrent.isSelected() && tfMulti.getText().isEmpty()) {
            Utils.alert("Error!", "Trading License Cannot be Empty!", "Error");
            return false;
        }

        // For Student
        if (rdoStudent.isSelected() && tfMulti.getText().isEmpty()) {
            Utils.alert("Error!", "Student ID Cannot be Empty!", "Error");
            return false;
        }
        if (rdoStudent.isSelected() && tfUniName.getText().isEmpty()) {
            Utils.alert("Error!", "University Name Cannot be Empty!", "Error");
            return false;
        }

        return true;
    }

    @FXML
    void btnSubmitAction(ActionEvent event) {
        boolean validation = validate();
        if (validation) {
            String name = tfName.getText();
            String nid = tfNid.getText();
            boolean num = true;
            double balance = Double.parseDouble(tfBalance.getText());
            if (rdoStudent.isSelected()) {
                Main.bank.addAccount(name, nid, balance, tfMulti.getText(), tfUniName.getText());
            } else if (rdoCurrent.isSelected()) {
                Main.bank.addAccount(name, nid, balance, tfMulti.getText());
            } else {
                num = Utils.isNum(tfMulti.getText());
                if (num)
                    Main.bank.addAccount(name, nid, balance, Double.parseDouble(tfMulti.getText()));
                else
                    Utils.alert("Error!", "Max Withdraw Limit Must be Numeric!", "Error");
            }
            if (num)
                Utils.writeBankToFile(Main.bank, Configs.db);
        }
    }

    @FXML
    void btnBackAction(ActionEvent event) {
        Utils.goDashboard();
    }

    private void toggle(boolean s, boolean c, boolean st) {
        rdoSaving.setSelected(s);
        rdoCurrent.setSelected(c);
        rdoStudent.setSelected(st);
    }
}
