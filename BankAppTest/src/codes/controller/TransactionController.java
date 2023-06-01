package codes.controller;

import codes.main.Configs;
import codes.main.Main;
import codes.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class TransactionController {


    @FXML
    private RadioButton rdoDeposit;

    @FXML
    private RadioButton rdoTransfer;

    @FXML
    private RadioButton rdoWithdraw;

    @FXML
    private TextField tfAcc;

    @FXML
    private TextField tfAmount;

    @FXML
    private TextField tfReceiverAcc;

    private boolean validate() {
        // For All
        if (tfAcc.getText().isEmpty()) {
            Utils.alert("Error!", "Account No. Cannot be Empty!", "Error");
            return false;
        }
        if (tfAmount.getText().isEmpty()) {
            Utils.alert("Error!", "Amount Cannot be Empty!", "Error");
            return false;
        }
        if (!Utils.isNum(tfAmount.getText())) {
            Utils.alert("Error!", "Amount Must be Numeric!", "Error");
            return false;
        }

        // For Receiver Acc
        if (rdoTransfer.isSelected() && tfReceiverAcc.getText().isEmpty()) {
            Utils.alert("Error!", "Receiver Account Cannot be Empty!", "Error");
            return false;
        }

        return true;
    }

    @FXML
    void btnSubmitAction(ActionEvent event) {
        boolean validation = validate();
        if (validation) {
            String acc = tfAcc.getText();
            double amount = Double.parseDouble(tfAmount.getText());
            int response = 6;
            if (rdoDeposit.isSelected())
                response = Main.bank.deposit(acc, amount);
            else if (rdoWithdraw.isSelected())
                response = Main.bank.withdraw(acc, amount);
            else
                response = Main.bank.transfer(acc, tfReceiverAcc.getText(), amount);
            switch (response) {
                case 0:
                    Utils.alert("Success!", "Operation Successful!", "success");
                    Utils.writeBankToFile(Main.bank, Configs.db);
                    break;
                case 1:
                case 2:
                    Utils.alert("Insufficient Balance!", "Insufficient Balance! Please try again after lower the amount.", "Warning");
                    break;
                case 3:
                    Utils.alert("Invalid Amount!", "Please enter a valid amount.", "Warning");
                    break;
                case 4:
                    Utils.alert("Max Withdraw Limit Reached!", "Please lower the withdraw amount.", "Warning");
                    break;
                case 5:
                    Utils.alert("Account Not Found!!", "Please recheck the account(s) id.", "Warning");
                    break;
                case 6: // this will work as default case
                    Utils.alert("Error!", "Unexpected Error!.", "Error");
                    break;
            }
        }
    }

    @FXML
    void transactionTypeAction(MouseEvent e) {
        RadioButton r = (RadioButton) e.getSource();
        if (r.equals(rdoDeposit)) {
            toggle(true, false, false);
            tfAcc.setPromptText("Account No.");
            tfReceiverAcc.setVisible(false);
        } else if (r.equals(rdoWithdraw)) {
            toggle(false, true, false);
            tfAcc.setPromptText("Account No.");
            tfReceiverAcc.setVisible(false);
        } else if (r.equals(rdoTransfer)) {
            toggle(false, false, true);
            tfAcc.setPromptText("Sender Account No.");
            tfReceiverAcc.setVisible(true);
        }
    }

    private void toggle(boolean s, boolean c, boolean st) {
        rdoDeposit.setSelected(s);
        rdoWithdraw.setSelected(c);
        rdoTransfer.setSelected(st);
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
    void paneOverviewAction(MouseEvent event) {
        Utils.goDashboard();
    }


}
