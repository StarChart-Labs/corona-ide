package com.coronaide.test.ui;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.testfx.framework.junit.ApplicationTest;

import com.coronaide.core.internal.service.ICoreConfiguration;
import com.coronaide.test.ui.config.UITestConfiguration;
import com.coronaide.ui.CoronaUIApplication;

import javafx.stage.Stage;

@RunWith(SpringJUnit4ClassRunner.class)
// ApplicationContext will be loaded from the static inner ContextConfiguration class
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = UITestConfiguration.class)
public abstract class CoronaUITest extends ApplicationTest implements ApplicationContextAware {

    protected static ApplicationContext springContext;

    private static boolean initialized = false;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        springContext = applicationContext;
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.show();
    }

    @Before
    public void initialize() throws Exception {
        if (!initialized) {
            Path tempAppDirectory = Files.createTempDirectory("corona-app");
            Path tempWorkspaceDirectory = Files.createTempDirectory("corona-workspace");

            ICoreConfiguration coreConfiguration = springContext.getBean(ICoreConfiguration.class);
            coreConfiguration.setLocations(tempAppDirectory, tempWorkspaceDirectory);

            CoronaUIApplication.setSpringContext(springContext);
            initialized = true;
        }
    }

}
