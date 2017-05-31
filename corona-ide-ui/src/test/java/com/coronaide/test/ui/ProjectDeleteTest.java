package com.coronaide.test.ui;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.coronaide.core.model.Project;
import com.coronaide.core.model.ProjectRequest;
import com.coronaide.core.service.IProjectService;
import com.coronaide.ui.CoronaUIApplication;

import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;

public class ProjectDeleteTest extends CoronaUITest {

    @Inject
    private IProjectService projectService;

    private List<Project> testProjects = new ArrayList<>();

    @Before
    public void setup() throws Exception {
        testProjects.add(projectService.create(new ProjectRequest(Paths.get("testProject"))));
        testProjects.add(projectService.create(new ProjectRequest(Paths.get("testProject2"))));
        launch(CoronaUIApplication.class);
    }

    @Test
    public void projectRemoveTest() {
        ListView<Project> listViewProjects = lookup("#listViewProjects").query();
        clickOn("testProject", MouseButton.SECONDARY).clickOn("Delete").clickOn("Yes");
        listViewProjects = lookup("#listViewProjects").query();
        Assert.assertTrue(listViewProjects.getItems().stream()
                .filter(p -> p.getName().equals("testProject"))
                .collect(Collectors.toList()).isEmpty());
    }

    @Test
    public void projectDeleteTest() {
        ListView<Project> listViewProjects = lookup("#listViewProjects").query();
        clickOn("testProject2", MouseButton.SECONDARY).clickOn("Delete").clickOn("#alertCheckbox").clickOn("Yes");
        listViewProjects = lookup("#listViewProjects").query();
        Assert.assertTrue(listViewProjects.getItems().stream()
                .filter(p -> p.getName().equals("testProject2"))
                .collect(Collectors.toList()).isEmpty());
    }

    @After
    public void teardown() throws IOException {
        for (Project t : testProjects) {
            try {
                projectService.delete(t);
            } catch (IllegalArgumentException e) {
                // don't worry about deleting projects that we already deleted in the test
            }
        }
    }

}
