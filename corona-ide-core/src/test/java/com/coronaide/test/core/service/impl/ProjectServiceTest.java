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
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.coronaide.core.datastore.Datastore;
import com.coronaide.core.internal.datastore.impl.ProjectLocation;
import com.coronaide.core.internal.datastore.impl.WorkspaceMetaData;
import com.coronaide.core.internal.datastore.util.Datastores;
import com.coronaide.core.model.CoronaIdeCore;
import com.coronaide.core.model.Module;
import com.coronaide.core.model.Project;
import com.coronaide.core.model.ProjectRequest;
import com.coronaide.core.model.Workspace;
import com.coronaide.core.service.IDatastoreService;
import com.coronaide.core.service.IWorkspaceService;
import com.coronaide.core.service.impl.ProjectService;

public class ProjectServiceTest {

    /** Logger reference to output information to the application log files */
    private static final Logger logger = LoggerFactory.getLogger(ProjectServiceTest.class);

    @Mock
    private IWorkspaceService workspaceService;

    @Mock
    private IDatastoreService datastoreService;

    private ProjectService projectService;

    @BeforeMethod
    public void setup() {
        MockitoAnnotations.initMocks(this);

        projectService = new ProjectService(workspaceService, datastoreService);
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        logger.trace("Verifying mock interactions for {}", result);

        Mockito.verifyNoMoreInteractions(workspaceService,
                datastoreService);
    }

    @Test
    public void createNoWorkspaceMetaData() throws Exception {
        Path projectRoot = Files.createTempFile("projectService", "-create");
        projectRoot.toFile().deleteOnExit();

        Workspace workspace = new Workspace(Paths.get("workspace"));
        Module module = CoronaIdeCore.getModule();
        Datastore<WorkspaceMetaData> datastore = com.coronaide.core.internal.datastore.impl.Datastores
                .getWorkspaceDatastore();
        WorkspaceMetaData expectedData = new WorkspaceMetaData(
                Collections.singleton(new ProjectLocation(projectRoot.toString())));

        Mockito.when(workspaceService.getActiveWorkspace()).thenReturn(workspace);
        Mockito.when(datastoreService.load(workspace, module, datastore)).thenReturn(Optional.empty());

        Project result = projectService.create(new ProjectRequest(projectRoot));

        Assert.assertNotNull(result);
        Assert.assertEquals(result.getName(), projectRoot.getFileName().toString());
        Assert.assertEquals(result.getRootDirectory(), projectRoot);
        Assert.assertEquals(result.getWorkingDirectory(), Datastores.getMetaDataDirectory(projectRoot));

        // Make sure directory exists
        Assert.assertTrue(projectRoot.toFile().exists());

        Mockito.verify(workspaceService).getActiveWorkspace();
        Mockito.verify(datastoreService).load(workspace, module, datastore);
        Mockito.verify(datastoreService).store(workspace, module, datastore, expectedData);
    }

    @Test
    public void createExistingWorkspaceMetaData() throws Exception {
        Path projectRoot = Files.createTempFile("projectService", "-create");
        projectRoot.toFile().deleteOnExit();

        ProjectLocation existingLocation = new ProjectLocation(Paths.get("other").toString());
        ProjectLocation newLocation = new ProjectLocation(projectRoot.toString());
        Set<ProjectLocation> existingLocations = new HashSet<>();
        existingLocations.add(existingLocation);
        Set<ProjectLocation> expectedLocations = new HashSet<>();
        expectedLocations.add(existingLocation);
        expectedLocations.add(newLocation);

        Workspace workspace = new Workspace(Paths.get("workspace"));
        Module module = CoronaIdeCore.getModule();
        Datastore<WorkspaceMetaData> datastore = com.coronaide.core.internal.datastore.impl.Datastores
                .getWorkspaceDatastore();
        WorkspaceMetaData existingData = new WorkspaceMetaData(existingLocations);
        WorkspaceMetaData expectedData = new WorkspaceMetaData(expectedLocations);

        Mockito.when(workspaceService.getActiveWorkspace()).thenReturn(workspace);
        Mockito.when(datastoreService.load(workspace, module, datastore)).thenReturn(Optional.of(existingData));

        Project result = projectService.create(new ProjectRequest(projectRoot));

        Assert.assertNotNull(result);
        Assert.assertEquals(result.getName(), projectRoot.getFileName().toString());
        Assert.assertEquals(result.getRootDirectory(), projectRoot);
        Assert.assertEquals(result.getWorkingDirectory(), Datastores.getMetaDataDirectory(projectRoot));

        // Make sure directory exists
        Assert.assertTrue(projectRoot.toFile().exists());

        Mockito.verify(workspaceService).getActiveWorkspace();
        Mockito.verify(datastoreService).load(workspace, module, datastore);
        Mockito.verify(datastoreService).store(workspace, module, datastore, expectedData);
    }

    @Test
    public void getAllNoWorkspaceMetaData() throws Exception {
        Workspace workspace = new Workspace(Paths.get("workspace"));
        Module module = CoronaIdeCore.getModule();
        Datastore<WorkspaceMetaData> datastore = com.coronaide.core.internal.datastore.impl.Datastores
                .getWorkspaceDatastore();

        Mockito.when(workspaceService.getActiveWorkspace()).thenReturn(workspace);
        Mockito.when(datastoreService.load(workspace, module, datastore)).thenReturn(Optional.empty());

        Collection<Project> result = projectService.getAll();

        Assert.assertNotNull(result);
        Assert.assertTrue(result.isEmpty());

        Mockito.verify(workspaceService).getActiveWorkspace();
        Mockito.verify(datastoreService).load(workspace, module, datastore);
    }

    @Test
    public void getAllExistingWorkspaceMetaData() throws Exception {
        Workspace workspace = new Workspace(Paths.get("workspace"));
        Module module = CoronaIdeCore.getModule();
        Datastore<WorkspaceMetaData> datastore = com.coronaide.core.internal.datastore.impl.Datastores
                .getWorkspaceDatastore();

        Path projectRoot = Paths.get("other");
        ProjectLocation existingLocation = new ProjectLocation(projectRoot.toString());
        Set<ProjectLocation> existingLocations = new HashSet<>();
        existingLocations.add(existingLocation);
        WorkspaceMetaData existingData = new WorkspaceMetaData(existingLocations);

        Mockito.when(workspaceService.getActiveWorkspace()).thenReturn(workspace);
        Mockito.when(datastoreService.load(workspace, module, datastore)).thenReturn(Optional.of(existingData));

        Collection<Project> result = projectService.getAll();

        Assert.assertNotNull(result);
        Assert.assertEquals(result.size(), 1);

        Project project = result.iterator().next();

        Assert.assertNotNull(project);
        Assert.assertEquals(project.getName(), projectRoot.getFileName().toString());
        Assert.assertEquals(project.getRootDirectory(), projectRoot);
        Assert.assertEquals(project.getWorkingDirectory(), Datastores.getMetaDataDirectory(projectRoot));

        Mockito.verify(workspaceService).getActiveWorkspace();
        Mockito.verify(datastoreService).load(workspace, module, datastore);
    }

    @Test
    public void remove() throws Exception {
        Workspace workspace = new Workspace(Paths.get("workspace"));
        Module module = CoronaIdeCore.getModule();
        Datastore<WorkspaceMetaData> datastore = com.coronaide.core.internal.datastore.impl.Datastores
                .getWorkspaceDatastore();

        Path projectRoot = Files.createTempFile("projectService", "-create");
        projectRoot.toFile().deleteOnExit();

        ProjectLocation existingLocation = new ProjectLocation(projectRoot.toString());
        Set<ProjectLocation> existingLocations = new HashSet<>();
        existingLocations.add(existingLocation);
        WorkspaceMetaData existingData = new WorkspaceMetaData(existingLocations);

        Project project = new Project(projectRoot.getFileName().toString(), projectRoot,
                Datastores.getMetaDataDirectory(projectRoot));

        Mockito.when(workspaceService.getActiveWorkspace()).thenReturn(workspace);
        Mockito.when(datastoreService.load(workspace, module, datastore)).thenReturn(Optional.of(existingData));

        projectService.remove(project);

        // Directory should still exist
        Assert.assertTrue(projectRoot.toFile().exists());

        Mockito.verify(workspaceService).getActiveWorkspace();
        Mockito.verify(datastoreService).load(workspace, module, datastore);
        Mockito.verify(datastoreService).store(workspace, module, datastore,
                new WorkspaceMetaData(Collections.emptySet()));
    }

    @Test
    public void delete() throws Exception {
        Workspace workspace = new Workspace(Paths.get("workspace"));
        Module module = CoronaIdeCore.getModule();
        Datastore<WorkspaceMetaData> datastore = com.coronaide.core.internal.datastore.impl.Datastores
                .getWorkspaceDatastore();

        Path projectRoot = Files.createTempFile("projectService", "-create");
        projectRoot.toFile().deleteOnExit();

        ProjectLocation existingLocation = new ProjectLocation(projectRoot.toString());
        Set<ProjectLocation> existingLocations = new HashSet<>();
        existingLocations.add(existingLocation);
        WorkspaceMetaData existingData = new WorkspaceMetaData(existingLocations);

        Project project = new Project(projectRoot.getFileName().toString(), projectRoot,
                Datastores.getMetaDataDirectory(projectRoot));

        Mockito.when(workspaceService.getActiveWorkspace()).thenReturn(workspace);
        Mockito.when(datastoreService.load(workspace, module, datastore)).thenReturn(Optional.of(existingData));

        projectService.delete(project);

        // Directory should be deleted
        Assert.assertFalse(projectRoot.toFile().exists());

        Mockito.verify(workspaceService).getActiveWorkspace();
        Mockito.verify(datastoreService).load(workspace, module, datastore);
        Mockito.verify(datastoreService).store(workspace, module, datastore,
                new WorkspaceMetaData(Collections.emptySet()));
    }

}
