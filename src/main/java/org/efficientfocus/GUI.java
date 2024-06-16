package org.efficientfocus;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
public class GUI extends Application {
    @Override
    public void start(Stage stage) {
        stage.setTitle("Age Verification");
        Label ageLabel = new Label("Please enter your age:");
        TextField ageField = new TextField();
        Button submitButton = new Button("Submit");

        submitButton.setOnAction(event -> {
            String ageText = ageField.getText();
            try {
                int age = Integer.parseInt(ageText);
                if (age < 18) {
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Age Restriction");
                    alert.setHeaderText("Access Denied");
                    alert.setContentText("You must be at least 18 years old in order to use this application");
                    alert.showAndWait();
                    stage.close();
                }
                else {
                    // If this executes, that means the Age is valid and is going to proceed to main application
                    showMainApplication(stage);
                }
            } catch (NumberFormatException e) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Invalid input");
                alert.setHeaderText("Error");
                alert.setContentText("Please enter a valid age.");
                alert.showAndWait();
            }
            } );

        VBox vbox = new VBox(10, ageLabel, ageField, submitButton);
        Scene scene = new Scene(vbox,300,200);
        stage.setScene(scene);
        stage.show();
    }

    private void showMainApplication(Stage stage) {
    Label label = new Label("Welcome to the main application");

    Button addTaskButton = new Button("Add Task");
    addTaskButton.setOnAction(Event -> {
        // it opens a new window and asks user to specify a new "Focus" name
        Stage newTaskStage = new Stage();
        newTaskStage.setTitle("Add New Task");
        Label taskLabel = new Label("Enter task name : ");
        TextField taskField = new TextField();
        Button saveTaskButton = new Button("Save Task");

        saveTaskButton.setOnAction(saveEvent -> {
            String taskName = taskField.getText();
            if (!taskName.isEmpty()) {
                Button taskButton = new Button(taskName);
                //it adds a new "Focus" button to the Vbox on the main screen
                ((VBox) stage.getScene().getRoot()).getChildren().add(taskButton);
                newTaskStage.close();
            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Invalid input");
                alert.setHeaderText("Error");
                alert.setContentText("Task name can not be empty");
                alert.showAndWait();
            }
        });
        VBox taskVBox = new VBox(10, taskLabel, taskField, saveTaskButton);
        Scene newTaskScene = new Scene(taskVBox, 300, 200);
        newTaskStage.setScene(newTaskScene);
        newTaskStage.show();
    } );




    VBox vbox = new VBox(10,label,addTaskButton);
    Scene scene = new Scene(vbox,800,400);
        stage.setScene(scene);
        stage.show();
    }
}