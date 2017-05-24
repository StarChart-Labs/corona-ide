package com.coronaide.test.ui;

import java.nio.file.Path;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.coronaide.core.model.Workspace;
import com.coronaide.core.service.IWorkspaceService;
import com.coronaide.ui.CoronaUIApplication;

public class WindowFeaturesTest extends CoronaUITest {

    @Inject
    private IWorkspaceService workspaceService;

    @Before
    public void setup() throws Exception {
        launch(CoronaUIApplication.class);
    }

    @Test
    public void workspaceTitleTest() {
        Workspace workspace = workspaceService.getActiveWorkspace();
        Path workingDir = workspace.getWorkingDirectory();

        Assert.assertEquals(CoronaUIApplication.getPrimaryStage().getTitle(),
                workingDir.getName(workingDir.getNameCount() - 2).toString() + " - Corona");
    }

}
