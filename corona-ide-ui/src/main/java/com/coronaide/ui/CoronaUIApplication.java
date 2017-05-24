package com.coronaide.ui;

import java.util.Objects;

import org.springframework.context.ApplicationContext;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class CoronaUIApplication extends Application {

    private static FXMLLoader loader;

    private static ApplicationContext context;

    private static Stage primaryStage;

    public static void setSpringContext(ApplicationContext springContext) {
        context = Objects.requireNonNull(springContext);
    }

    @Override
    public void init() {
        // Configure our FXML loader to use Spring as its dependency master, then start the UI
        loader = new FXMLLoader(getClass().getResource("/fxml/Main.fxml"));
        loader.setControllerFactory(context::getBean);
    }

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        Parent root = loader.load();
        Scene scene = new Scene(root, 1000, 500);
        stage.setScene(scene);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/icon.png")));
        stage.show();
    }

    /**
     * @return The application's primary stage, so that operations can be performed on it such as changing the title
     * @throws IllegalStateException
     *             if the primary stage has not yet been set
     * @since 0.1.0
     */
    public static Stage getPrimaryStage() throws IllegalStateException {
        if (primaryStage == null) {
            throw new IllegalStateException("The primary Stage has not yet been set");
        }
        return primaryStage;
    }

}
