package com.coronaide.test.ui;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.testfx.framework.junit.ApplicationTest;

import com.coronaide.core.model.Project;
import com.coronaide.core.model.ProjectRequest;
import com.coronaide.core.model.Workspace;
import com.coronaide.core.service.IProjectService;
import com.coronaide.core.service.IWorkspaceService;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import junit.framework.Assert;

public class MainTest extends ApplicationTest {

    @Inject
    private static AnnotationConfigApplicationContext springContext;

    @Inject
    private IWorkspaceService workspaceService;

    @Inject
    private IProjectService projectService;

    private List<Project> testProjects = new ArrayList<>();

    @Override
    public void start(Stage stage) throws Exception {
        stage.show();
    }

    @BeforeClass
    public static void setup() throws Exception {
        CoronaUITestApplication.launchUi(springContext);
    }

    @Before
    public void initialize() throws IOException, URISyntaxException {
        testProjects.add(projectService.create(new ProjectRequest(Paths.get("testProject"))));
        testProjects.add(projectService.create(new ProjectRequest(Paths.get("testProject2"))));
    }

    @Test
    public void workspaceLabelTextTest() {
        Workspace workspace = workspaceService.getActiveWorkspace();
        Path workingDir = workspace.getWorkingDirectory();
        
        Label labelWorkspace = lookup("#labelWorkspace").query();
        Assert.assertEquals(labelWorkspace.getText(), workingDir.getName(workingDir.getNameCount() - 1).toString());
    }

    @Test
    public void projectListTest() {
        List<String> projectNamesList = testProjects.stream().map(Project::getName).collect(Collectors.toList());
        ListView<String> listViewProjects = lookup("#listViewProjects").query();
        Assert.assertEquals(listViewProjects.getItems().size(), 2);
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
