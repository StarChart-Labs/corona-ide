package com.coronaide.test.ui;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.testfx.framework.junit.ApplicationTest;

import com.coronaide.core.internal.service.ICoreConfiguration;
import com.coronaide.core.model.Project;
import com.coronaide.core.model.ProjectRequest;
import com.coronaide.core.model.Workspace;
import com.coronaide.core.service.IProjectService;
import com.coronaide.core.service.IWorkspaceService;
import com.coronaide.test.ui.config.UITestConfiguration;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

@RunWith(SpringJUnit4ClassRunner.class)
// ApplicationContext will be loaded from the static inner ContextConfiguration class
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = UITestConfiguration.class)
public class MainTest extends ApplicationTest implements ApplicationContextAware {

    private static ApplicationContext springContext;

    /* Need this because JUnit doesn't have any way to do a non-static BeforeClass method */
    private static boolean initialized = false;

    @Inject
    private IWorkspaceService workspaceService;

    @Inject
    private IProjectService projectService;

    private List<Project> testProjects = new ArrayList<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        springContext = applicationContext;
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.show();
    }

    @Before
    public void setup() throws Exception {
        if (!initialized) {
            ICoreConfiguration coreConfiguration = springContext.getBean(ICoreConfiguration.class);
            coreConfiguration.setLocations(Paths.get("/corona"), Paths.get("/corona/workspace"));
            testProjects.add(projectService.create(new ProjectRequest(Paths.get("testProject"))));
            testProjects.add(projectService.create(new ProjectRequest(Paths.get("testProject2"))));
            CoronaUITestApplication.launchUi(springContext);
            initialized = true;
        }
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
