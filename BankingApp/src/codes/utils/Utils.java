package codes.utils;

import codes.classes.Bank;
import codes.main.Configs;
import codes.main.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.*;
import java.util.Objects;

public class Utils {
    private static double xOffset;
    private static double yOffset;

    public static void makeDraggable(Scene scene) {
        // Make Scene Draggable
        scene.setOnMousePressed(event -> {
            xOffset = Main.primaryStage.getX() - event.getScreenX();
            yOffset = Main.primaryStage.getY() - event.getScreenY();
        });
        scene.setOnMouseDragged(event -> {
            Main.primaryStage.setX(event.getScreenX() + xOffset);
            Main.primaryStage.setY(event.getScreenY() + yOffset);
        });
    }

    public static void changeScene(String page, double h, double w) {
        try {
            Main.root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource(page)));
            Main.scene = new Scene(Main.root, w, h);

            // Make Scene Transparent
            Main.scene.setFill(Color.TRANSPARENT);

            // Make Scene Draggable
            makeDraggable(Main.scene);

            // Apply CSS
            Main.root.getStylesheets().add(Objects.requireNonNull(Main.class.getResource(Configs.css)).toExternalForm());

            Main.primaryStage.setScene(Main.scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void changeScene(String page) {
        changeScene(page, Configs.defaultHeight, Configs.defaultWeight);
    }

    public static void exit() {
        System.exit(0);
    }

    public static void copyToClipboard(String text) {
        java.awt.Toolkit.getDefaultToolkit().getSystemClipboard()
                .setContents(new java.awt.datatransfer.StringSelection(text), null);
    }

    public static void alert(String title, String text, String type) {
        // Alert Type
        Alert alert = null;
        if (type.equalsIgnoreCase("error"))
            alert = new Alert(Alert.AlertType.ERROR);
        else if (type.equalsIgnoreCase("success"))
            alert = new Alert(Alert.AlertType.INFORMATION);
        else
            alert = new Alert(Alert.AlertType.WARNING);

        // Alert Window Settings
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(text);

        // Add a custom icon.
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(Objects.requireNonNull(Main.class.getResource(Configs.icon)).toString()));

        alert.showAndWait();
    }

    public static boolean isNum(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    // File IO
    public static Bank readBankFromFile(String filePath) {
        Bank bank = null;
        try {
            FileInputStream fileIn = new FileInputStream(filePath);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            bank = (Bank) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return bank;
    }


    public static void writeBankToFile(Bank bank, String filePath) {
        try {
            FileOutputStream fileOut = new FileOutputStream(filePath);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(bank);
            out.close();
            fileOut.close();
            System.out.println(" - Serialized data is saved in " + filePath);
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public static void checkDB() {
        try {
            File f1 = new File(Configs.db);
            boolean isF1Created = f1.createNewFile();
            if (isF1Created)
                writeBankToFile(new Bank(Configs.bankName), Configs.db);
        } catch (IOException ignored) {
        }
    }


    // Navigating
    public static void goDashboard() {
        if (Main.isUser)
            Utils.changeScene(Configs.accDashPage);
        else
            Utils.changeScene(Configs.empDashPage);
    }

    public static void goHome() {
        Utils.changeScene(Configs.homePage);
    }

    public static void goTransaction() {
        Utils.changeScene(Configs.transactionPage);
    }
}
