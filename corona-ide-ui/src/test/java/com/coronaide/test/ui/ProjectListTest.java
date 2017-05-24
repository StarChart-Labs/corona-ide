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

public class ProjectListTest extends CoronaUITest {

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
    public void projectListTest() {
        List<String> projectNamesList = testProjects.stream().map(Project::getName).collect(Collectors.toList());
        ListView<String> listViewProjects = lookup("#listViewProjects").query();
        Assert.assertEquals(2, listViewProjects.getItems().size());
        for (String projectName : listViewProjects.getItems()) {
            Assert.assertTrue(projectNamesList.contains(projectName));
        }
    }

    @After
    public void teardown() throws IOException {
        for (Project t : testProjects) {
            projectService.delete(t);
        }
    }

}
