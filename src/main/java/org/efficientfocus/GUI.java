package org.efficientfocus;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
public class GUI extends Application {
    @Override
    public void start(Stage stage) {
        stage.setTitle("Age Verification");
        Label ageLabel = new Label("Please enter your age:");
        TextField ageField = new TextField();
        Label label = new Label("Hello, JavaFX!");
        StackPane root = new StackPane();
        root.getChildren().add(label);
        Scene scene = new Scene(root, 300, 200);
        stage.setTitle("JavaFX Application");
        stage.setScene(scene);
        stage.show();
    }
}