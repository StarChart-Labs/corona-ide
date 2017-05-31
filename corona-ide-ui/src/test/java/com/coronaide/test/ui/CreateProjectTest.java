package com.coronaide.test.ui;

import java.io.IOException;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.coronaide.core.model.Project;
import com.coronaide.core.service.IProjectService;
import com.coronaide.ui.CoronaUIApplication;

public class CreateProjectTest extends CoronaUITest {

    @Inject
    private IProjectService projectService;

    private final String TEST_PROJECT_NAME = "test_project";

    @Before
    public void setup() throws Exception {
        launch(CoronaUIApplication.class);
    }

    @Test
    public void createProjectTest() {
        clickOn("#menu-file").clickOn("#menu-file-new-project");
        write(TEST_PROJECT_NAME);
        clickOn("OK");
        Assert.assertTrue(projectService.getAll().stream().map(Project::getName).collect(Collectors.toList())
                .contains(TEST_PROJECT_NAME));
    }

    @After
    public void teardown() {
        projectService.getAll().stream()
                .filter(p -> TEST_PROJECT_NAME.equals(p.getName()))
                .forEach(p -> {
                    try {
                        projectService.delete(p);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }

}
