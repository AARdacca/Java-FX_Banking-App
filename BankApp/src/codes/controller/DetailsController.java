package codes.controller;


import codes.classes.Account;
import codes.classes.Transaction;
import codes.main.Configs;
import codes.main.Main;
import codes.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;

public class DetailsController implements Initializable {

    @FXML
    private Button btnAccDetails;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnTransaction;

    @FXML
    private ChoiceBox<String> chbxAccType;

    @FXML
    private TableColumn<Account, String> colAcc;

    @FXML
    private TableColumn<Account, Double> colBalance;

    @FXML
    private TableColumn<Account, Double> colMinBalance;

    @FXML
    private TableColumn<Account, String> colName;

    @FXML
    private TableColumn<Account, String> colNid;

    @FXML
    private TableView<Account> table;

    @FXML
    private TextField tfAccId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // choice box
        chbxAccType.getItems().add("All");
        chbxAccType.getItems().add("CurrentAccount");
        chbxAccType.getItems().add("SavingAccount");
        chbxAccType.getItems().add("StudentAccount");
        chbxAccType.getSelectionModel().selectFirst();

        chbxAccType.setOnAction(this::chbxAccTypeAction);

        // Table
        colAcc.setCellValueFactory(new PropertyValueFactory<>("acc"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colNid.setCellValueFactory(new PropertyValueFactory<>("nid"));
        colBalance.setCellValueFactory(new PropertyValueFactory<>("balance"));
        colMinBalance.setCellValueFactory(new PropertyValueFactory<>("minBalance"));
        table.setItems(getAcc());

        if (Main.isUser)
            search(Main.nid);
    }

    public ObservableList<Account> getAcc() {
        ObservableList<Account> allAcc = FXCollections.observableArrayList();
        for (Map.Entry<String, Account> entry : Main.bank.getAccounts().entrySet()) {
            allAcc.addAll(entry.getValue());
        }

        // sort by Line No.
        allAcc.sort(Comparator.comparing(Account::getAcc));
        return allAcc;
    }

    private void filter(String str) {
        ObservableList<Account> newAcc = FXCollections.observableArrayList();

        for (Map.Entry<String, Account> entry : Main.bank.getAccounts().entrySet()) {
            if (entry.getValue().getClass().getSimpleName().equalsIgnoreCase(str))
                newAcc.add(entry.getValue());
        }
        newAcc.sort(Comparator.comparing(Account::getAcc));
        table.setItems(newAcc);
    }

    private void chbxAccTypeAction(ActionEvent actionEvent) {
        filter(chbxAccType.getSelectionModel().getSelectedItem());
    }

    @FXML
    void btnAccDetailsAction(ActionEvent event) {
        Account acc = table.getSelectionModel().getSelectedItem();
        if (acc != null) {
            Alert alert = new Alert(null, null, ButtonType.OK);

            // Alert Window Settings
            alert.setTitle("View Data");
            alert.setHeaderText(null);
            alert.getDialogPane().setPrefSize(600, 200);

            // Add a custom icon.
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResource(Configs.icon)).toString()));

            // Content
            TextArea t1 = new TextArea(acc.toString());
            t1.setEditable(false);
            t1.setPrefSize(550, 250);
            t1.setWrapText(true);

            // Bind
            VBox layout = new VBox();
            layout.getChildren().add(new Label("Details"));
            layout.getChildren().add(t1);

            // Show
            alert.getDialogPane().setContent(layout);
            alert.showAndWait();
        } else {
            Utils.alert("Warning!", "Please an item first!", "warning");
        }
    }

    @FXML
    void btnBackAction(ActionEvent event) {
        Utils.goDashboard();
    }

    @FXML
    void btnTransactionAction(ActionEvent event) {
        Account acc = table.getSelectionModel().getSelectedItem();
        if (acc != null) {
            Alert alert = new Alert(null, null, ButtonType.OK);

            // Alert Window Settings
            alert.setTitle("Transaction History");
            alert.setHeaderText(null);
            alert.getDialogPane().setPrefSize(600, 200);

            // Add a custom icon.
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResource(Configs.icon)).toString()));

            // Content
            TableView<Transaction> t = new TableView<>();
            TableColumn<Transaction, LocalDateTime> colTime = new TableColumn<>("Time");
            colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
            colTime.setMinWidth(200);
            t.getColumns().add(colTime);

            TableColumn<Transaction, Double> colAmount = new TableColumn<>("Amount");
            colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
            colAmount.setMinWidth(200);
            t.getColumns().add(colAmount);

            TableColumn<Transaction, String> colType = new TableColumn<>("Type");
            colType.setCellValueFactory(new PropertyValueFactory<>("type"));
            colType.setMinWidth(200);
            t.getColumns().add(colType);

            // add data to table
            ArrayList<Transaction> tr = acc.getTransactions();
            ObservableList<Transaction> trans = FXCollections.observableArrayList();
            trans.addAll(tr);
            t.setItems(trans);

            // Bind
            final VBox vbox = new VBox();
            vbox.setSpacing(5);
            vbox.setPadding(new Insets(10, 0, 0, 10));
            vbox.getChildren().add(t);

            // Show
            alert.getDialogPane().setContent(vbox);
            alert.showAndWait();
        } else {
            Utils.alert("Warning!", "Please an item first!", "warning");
        }
    }

    @FXML
    void btnSearchAction(ActionEvent event) {
        search(tfAccId.getText());
    }

    private void search(String str) {
        if (str.isEmpty())
            table.setItems(getAcc());
        else {
            Account acc = Main.bank.findAcc(str);
            ArrayList<Account> search = Main.bank.findAccWithNID(str);
            ObservableList<Account> newAcc = FXCollections.observableArrayList();
            if (acc != null)
                newAcc.add(acc);
            newAcc.addAll(search);
            table.setItems(newAcc);
        }
    }

    @FXML
    void btnDeleteAction(ActionEvent event) {
        Account acc = table.getSelectionModel().getSelectedItem();
        if (acc != null) {
            table.getItems().remove(acc);
            Main.bank.getAccounts().remove(acc.getAcc());
            Utils.writeBankToFile(Main.bank, Configs.db);
        } else {
            Utils.alert("Warning!", "Please an item first!", "warning");
        }
    }
}
