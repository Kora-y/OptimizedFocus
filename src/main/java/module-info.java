module org.efficientfocus {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.efficientfocus to javafx.fxml;
    exports org.efficientfocus;
}