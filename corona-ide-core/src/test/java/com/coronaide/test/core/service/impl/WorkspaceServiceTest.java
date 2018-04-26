/*******************************************************************************
 * Copyright (c) Nov 17, 2016 Corona IDE.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    romeara - initial API and implementation and/or initial documentation
 *******************************************************************************/
package com.coronaide.test.core.service.impl;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.coronaide.core.internal.datastore.util.Datastores;
import com.coronaide.core.internal.service.ICoreConfiguration;
import com.coronaide.core.model.Workspace;
import com.coronaide.core.service.IWorkspaceService;
import com.coronaide.core.service.impl.WorkspaceService;

/**
 * Tests basic operations of the workspace service
 *
 * @author romeara
 */
public class WorkspaceServiceTest {

    private final Path workspaceDirectory = Paths.get("workspace");

    @Mock
    private ICoreConfiguration coreConfiguration;

    private IWorkspaceService workspaceService;

    @BeforeMethod
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(coreConfiguration.getActiveWorkspaceDirectory()).thenReturn(workspaceDirectory);

        workspaceService = new WorkspaceService(coreConfiguration);
    }

    @Test
    public void getActiveWorkspace() throws Exception {
        Workspace workspace = workspaceService.getActiveWorkspace();

        Assert.assertNotNull(workspace);
        Assert.assertEquals(workspace.getWorkingDirectory(), Datastores.getMetaDataDirectory(workspaceDirectory));
    }

}
