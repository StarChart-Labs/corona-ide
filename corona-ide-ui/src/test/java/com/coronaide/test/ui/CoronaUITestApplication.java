package com.coronaide.test.ui;

import java.util.Objects;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.testfx.framework.junit.ApplicationTest;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CoronaUITestApplication extends Application {

    private static FXMLLoader loader;

    private static AnnotationConfigApplicationContext context;

    /**
     * Start the Corona UI.
     * 
     * @param springContext
     *            The Spring application context
     * @throws Exception
     *             If the TestFX framework throws an exception
     */
    public static void launchUi(AnnotationConfigApplicationContext springContext) throws Exception {
        context = Objects.requireNonNull(springContext);
        ApplicationTest.launch(CoronaUITestApplication.class);
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
        stage.setTitle("Corona IDE Testing");
        stage.setScene(scene);
        stage.show();
    }

}
