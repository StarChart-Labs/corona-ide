/*
 * Copyright (c) Mar 29, 2017 Corona IDE.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    romeara - initial API and implementation and/or initial documentation
 */
package com.coronaide.test.core.service.impl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.coronaide.core.datastore.Datastore;
import com.coronaide.core.model.CoreDatastores;
import com.coronaide.core.model.CoronaIdeCore;
import com.coronaide.core.model.Module;
import com.coronaide.core.model.ProjectDeleteRequest;
import com.coronaide.core.model.ProjectLocation;
import com.coronaide.core.model.ProjectRequest;
import com.coronaide.core.model.Workspace;
import com.coronaide.core.model.WorkspaceMetaData;
import com.coronaide.core.service.IDatastoreService;
import com.coronaide.core.service.IWorkspaceService;
import com.coronaide.core.service.impl.ProjectService;

public class ProjectServiceErrorTest {

    /** Logger reference to output information to the application log files */
    private static final Logger logger = LoggerFactory.getLogger(ProjectServiceErrorTest.class);

    @Mock
    private IWorkspaceService workspaceService;

    @Mock
    private IDatastoreService datastoreService;

    private ProjectService projectService;

    @BeforeMethod
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);

        projectService = new ProjectService(workspaceService, datastoreService);
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        logger.trace("Verifying mock interactions for {}", result);

        Mockito.verifyNoMoreInteractions(workspaceService,
                datastoreService);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void createNullWorkspaceService() throws Exception {
        new ProjectService(null, datastoreService);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void createNullDatastoreService() throws Exception {
        new ProjectService(workspaceService, null);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void createNullRequest() throws Exception {
        projectService.create(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void createAlreadyExists() throws Exception {
        Path projectRoot = Files.createTempFile("projectService", "-create");

        Workspace workspace = new Workspace(Paths.get("workspace"));
        Module module = CoronaIdeCore.getModule();
        Datastore<WorkspaceMetaData> datastore = CoreDatastores.getWorkspaceDatastore();
        WorkspaceMetaData existingData = new WorkspaceMetaData(
                Collections.singleton(new ProjectLocation(UUID.randomUUID(), projectRoot.toString())));

        Mockito.when(workspaceService.getActiveWorkspace()).thenReturn(workspace);
        Mockito.when(datastoreService.load(workspace, module, datastore)).thenReturn(Optional.of(existingData));

        try {
            projectService.create(new ProjectRequest(projectRoot));
        } finally {
            Mockito.verify(workspaceService).getActiveWorkspace();
            Mockito.verify(datastoreService).load(workspace, module, datastore);
        }
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void deleteNullRequest() throws Exception {
        projectService.delete(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void removeDoesntExist() throws Exception {
        Workspace workspace = new Workspace(Paths.get("workspace"));
        Module module = CoronaIdeCore.getModule();
        Datastore<WorkspaceMetaData> datastore = CoreDatastores.getWorkspaceDatastore();
        WorkspaceMetaData existingData = new WorkspaceMetaData(Collections.emptySet());

        Mockito.when(workspaceService.getActiveWorkspace()).thenReturn(workspace);
        Mockito.when(datastoreService.load(workspace, module, datastore)).thenReturn(Optional.of(existingData));

        try {
            projectService.delete(UUID.randomUUID(), false);
        } finally {
            Mockito.verify(workspaceService).getActiveWorkspace();
            Mockito.verify(datastoreService).load(workspace, module, datastore);
        }
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void removeRequestDoesntExist() throws Exception {
        Path projectRoot = Paths.get("root");

        Workspace workspace = new Workspace(Paths.get("workspace"));
        Module module = CoronaIdeCore.getModule();
        Datastore<WorkspaceMetaData> datastore = CoreDatastores.getWorkspaceDatastore();
        WorkspaceMetaData existingData = new WorkspaceMetaData(Collections.emptySet());

        Mockito.when(workspaceService.getActiveWorkspace()).thenReturn(workspace);
        Mockito.when(datastoreService.load(workspace, module, datastore)).thenReturn(Optional.of(existingData));

        try {
            projectService.delete(new ProjectDeleteRequest(projectRoot, false));
        } finally {
            Mockito.verify(workspaceService).getActiveWorkspace();
            Mockito.verify(datastoreService).load(workspace, module, datastore);
        }
    }

}
