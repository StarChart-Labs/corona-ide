package com.coronaide.main.ui;

import java.util.Objects;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CoronaUiApplication extends Application {

    private static FXMLLoader loader;

    private static AnnotationConfigApplicationContext context;

    /**
     * Start the Corona UI.
     * 
     * @param context
     *            The Spring application context
     */
    public static void launchUi(AnnotationConfigApplicationContext springContext) {
        context = Objects.requireNonNull(springContext);
        Application.launch(CoronaUiApplication.class);
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
        Scene scene = new Scene(root, 300, 275);
        stage.setTitle("Corona IDE");
        stage.setScene(scene);
        stage.show();
    }

}
