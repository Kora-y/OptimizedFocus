package org.efficientfocus;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Timer;
import java.util.TimerTask;

public class GUI extends Application {

    private boolean isCounting = false; // Zamanlayıcının çalışıp çalışmadığını kontrol eder
    private Timer timer;
    private int timeRemaining; // Geri sayımın kalan süresi

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
                } else {
                    showMainApplication(stage);
                }
            } catch (NumberFormatException e) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Invalid input");
                alert.setHeaderText("Error");
                alert.setContentText("Please enter a valid age.");
                alert.showAndWait();
            }
        });

        VBox vbox = new VBox(10, ageLabel, ageField, submitButton);
        Scene scene = new Scene(vbox, 300, 200);
        stage.setScene(scene);
        stage.show();
    }

    private void showMainApplication(Stage stage) {
        Label label = new Label("Welcome to the main application");

        Button addTaskButton = new Button("Add Task");
        addTaskButton.setOnAction(event -> {
            Stage newTaskStage = new Stage();
            newTaskStage.setTitle("Add New Task");

            Label taskLabel = new Label("Enter task name:");
            TextField taskField = new TextField();

            Label taskTimeLabel = new Label("Enter task time (minutes/seconds):");

            TextField minutesField = new TextField();
            minutesField.setPromptText("Minutes");
            TextField secondsField = new TextField();
            secondsField.setPromptText("Seconds");

            Button saveTaskButton = new Button("Save Task");

            saveTaskButton.setOnAction(saveEvent -> {
                String taskName = taskField.getText();
                String taskminutesText = minutesField.getText();
                String tasksecondsText = secondsField.getText();


                try {
                    timeRemaining = Integer.parseInt(taskminutesText); // Kullanıcıdan alınan başlangıç geri sayım süresi
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Invalid input");
                    alert.setHeaderText("Error");
                    alert.setContentText("Please enter a valid time.");
                    alert.showAndWait();
                    return;
                }

                if (!taskName.isEmpty() && timeRemaining > 0) {
                    HBox taskHBox = new HBox(10);
                    Button taskButton = new Button(taskName);
                    Button taskTimeButton = new Button(taskTimeText); // Başlangıçta kullanıcıdan alınan geri sayım süresi

                    taskButton.setOnAction(ti1Event -> {
                        if (isCounting) {
                            stopCountdown();
                        } else {
                            startCountdown(taskTimeButton);
                        }
                    });

                    taskHBox.getChildren().addAll(taskButton, taskTimeButton);
                    ((VBox) stage.getScene().getRoot()).getChildren().add(taskHBox);
                    newTaskStage.close();
                } else {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Invalid input");
                    alert.setHeaderText("Error");
                    alert.setContentText("Task name and time must be valid.");
                    alert.showAndWait();
                }
            });

            VBox taskVBox = new VBox(10, taskLabel, taskField, taskTimeLabel, taskTimeField, saveTaskButton);
            Scene newTaskScene = new Scene(taskVBox, 300, 200);
            newTaskStage.setScene(newTaskScene);
            newTaskStage.show();
        });

        VBox vbox = new VBox(10, label, addTaskButton);
        Scene scene = new Scene(vbox, 800, 400);
        stage.setScene(scene);
        stage.show();
    }

    private void startCountdown(Button taskTimeButton) {
        isCounting = true;
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if (timeRemaining > 0) {
                        timeRemaining--;
                        taskTimeButton.setText(String.valueOf(timeRemaining));
                    } else {
                        timer.cancel();
                        isCounting = false;
                    }
                });
            }
        };getClass()
        timer.scheduleAtFixedRate(timerTask, 1000, 1000); // 1 saniyelik sabit aralıklarla çalıştırılır
    }

    private void stopCountdown() {
        isCounting = false;
        if (timer != null) {
            timer.cancel();
        }
    }
}