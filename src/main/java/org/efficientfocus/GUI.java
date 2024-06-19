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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class GUI extends Application {
    private Timer timer;

    public void dateAndTime(Label timeLabel) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        timer = new Timer(true);
        TimerTask updateTimeTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    Date currentTime = new Date();
                    timeLabel.setText(formatter.format(currentTime));
                });
            }
        };
        timer.scheduleAtFixedRate(updateTimeTask, 0, 1000); // Her saniye güncelle
    }

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
        stage.setTitle("ImprovedFocus");
        Label label = new Label("Welcome to the main application");
        Label timeLabel = new Label();
        dateAndTime(timeLabel);
        Button addTaskButton = new Button("Add Task");

        VBox tasksVBox = new VBox(10); //******** bunun boş olma olayı ve sonradan nasıl içinin doldurulup tasksvbox a eklendiğine tekrar bakılacak *********
        HBox mainHBox = new HBox(100, new VBox(10, label, addTaskButton), tasksVBox, new VBox(10, timeLabel));

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

            HBox timeInputBox = new HBox(5, minutesField, secondsField);

            Button saveTaskButton = new Button("Save Task");

            saveTaskButton.setOnAction(saveEvent -> {
                int timeRemaining = 0;
                String taskName = taskField.getText();
                String minutesText = minutesField.getText();
                String secondsText = secondsField.getText();
                int minutes = 0;
                int seconds = 0;

                try {
                    if (!minutesText.isEmpty()) {
                        minutes = Integer.parseInt(minutesText);
                    }
                    if (!secondsText.isEmpty()) {
                        seconds = Integer.parseInt(secondsText);
                    }
                     timeRemaining = (minutes * 60) + seconds;
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Invalid input");
                    alert.setHeaderText("Error");
                    alert.setContentText("Please enter valid numbers for minutes and seconds.");
                    alert.showAndWait();
                    return;
                }

                if (!taskName.isEmpty() && timeRemaining > 0) {
                    HBox taskHBox = new HBox(10);

                    Label taskNameLabel = new Label(taskName);
                    Label taskTimeLabel2 = new Label(String.format("%02d:%02d", minutes, seconds));

                    Button startButton = new Button("Start");
                    Button stopButton = new Button("Stop");

                    Task task = new Task(taskName, timeRemaining, taskTimeLabel2, startButton, stopButton);

                    taskHBox.getChildren().addAll(taskNameLabel, taskTimeLabel2, startButton, stopButton);
                    tasksVBox.getChildren().add(taskHBox);
                    newTaskStage.close();
                } else {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Invalid input");
                    alert.setHeaderText("Error");
                    alert.setContentText("Task name and time must be valid.");
                    alert.showAndWait();
                }
            });

            VBox taskVBox = new VBox(10, taskLabel, taskField, taskTimeLabel, timeInputBox, saveTaskButton);
            Scene newTaskScene = new Scene(taskVBox, 300, 200);
            newTaskStage.setScene(newTaskScene);
            newTaskStage.show();
        });

        Scene scene = new Scene(mainHBox, 800, 400);
        stage.setScene(scene);
        stage.show();
    }
}
