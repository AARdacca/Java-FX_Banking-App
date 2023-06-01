module banking.system {
    requires transitive javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    exports codes.main;
    opens codes.main to javafx.fxml;
    exports codes.controller;
    opens codes.controller to javafx.fxml;
    exports codes.utils;
    opens codes.utils to javafx.fxml;
    exports codes.classes;
    opens codes.classes to javafx.fxml;
}