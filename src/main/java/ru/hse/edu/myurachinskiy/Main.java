package ru.hse.edu.myurachinskiy;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.awt.*;
import java.awt.event.InputEvent;

public class Main extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent canvas = FXMLLoader.load(getClass().getResource("/views/keyboard.fxml"));

        BorderPane root = new BorderPane();
        root.setCenter(canvas);

        Group group = new Group(root);
        Scene scene = new Scene(group);

        root.prefHeightProperty().bind(scene.heightProperty());
        root.prefWidthProperty().bind(scene.widthProperty());

        primaryStage.setScene(scene);

        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(e -> System.exit(0));
        primaryStage.show();
    }
}