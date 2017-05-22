package com.coronaide.ui;

import java.util.Objects;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class CoronaUIApplication extends Application {

    private static FXMLLoader loader;

    private static AnnotationConfigApplicationContext context;

    /**
     * Start the Corona UI.
     * 
     * @param springContext
     *            The Spring application context
     */
    public static void launchUi(AnnotationConfigApplicationContext springContext) {
        context = Objects.requireNonNull(springContext);
        Application.launch(CoronaUIApplication.class);
    }

    @Override
    public void init() {
        // Configure our FXML loader to use Spring as its dependency master, then start the UI
        loader = new FXMLLoader(getClass().getResource("/fxml/Main.fxml"));
        loader.setControllerFactory(context::getBean);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = loader.load();
        Scene scene = new Scene(root, 1000, 500);
        stage.setTitle("Corona");
        stage.setScene(scene);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/icon.png")));
        stage.show();
    }

}
