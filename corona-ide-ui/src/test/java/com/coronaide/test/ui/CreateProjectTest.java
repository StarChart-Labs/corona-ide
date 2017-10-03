package com.coronaide.test.ui;

import java.io.IOException;
import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.coronaide.core.model.Project;
import com.coronaide.core.service.IProjectService;
import com.coronaide.ui.CoronaUIApplication;

import javafx.scene.control.ListView;

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

        ListView<Project> listViewProjects = lookup("#listViewProjects").query();
        Collection<String> projectNames = listViewProjects.getItems().stream()
                .map(Project::getName)
                .collect(Collectors.toList());

        boolean found = projectNames.stream()
                .filter(Predicate.isEqual(TEST_PROJECT_NAME))
                .findAny()
                .isPresent();

        Assert.assertTrue("Expected newly created project to be found in project list (" + projectNames + ")", found);
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
