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


class Task {
    private String name;
    private int timeRemaining;
    private Timer timer;
    private boolean isCounting;
    private Label timeLabel;
    private Button startButton;
    private Button stopButton;

    public Task(String name, int timeRemaining, Label timeLabel, Button startButton, Button stopButton) {
        this.name = name;
        this.timeRemaining = timeRemaining;
        this.timeLabel = timeLabel;
        this.startButton = startButton;
        this.stopButton = stopButton;
        this.isCounting = false;
        this.timer = new Timer();

        setupButtons();
    }

    private void setupButtons() {
        startButton.setOnAction(e -> startCountdown());
        stopButton.setOnAction(e -> stopCountdown());
    }

    private void startCountdown() {
        if (isCounting) return;

        isCounting = true;
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if (timeRemaining > 0) {
                        timeRemaining--;
                        int minutes = timeRemaining / 60;
                        int seconds = timeRemaining % 60;
                        timeLabel.setText(String.format("%02d:%02d", minutes, seconds));
                    } else {
                        timer.cancel();
                        isCounting = false;
                    }
                });
            }
        };
        timer.scheduleAtFixedRate(timerTask, 1000, 1000);
    }

    private void stopCountdown() {
        if (!isCounting) return;

        isCounting = false;
        if (timer != null) {
            timer.cancel();
        }
    }
}
