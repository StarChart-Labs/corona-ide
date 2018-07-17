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
import java.util.UUID;

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
import com.coronaide.core.internal.datastore.util.Datastores;
import com.coronaide.core.model.CoreDatastores;
import com.coronaide.core.model.CoronaIdeCore;
import com.coronaide.core.model.Module;
import com.coronaide.core.model.Project;
import com.coronaide.core.model.ProjectDeleteRequest;
import com.coronaide.core.model.ProjectLocation;
import com.coronaide.core.model.ProjectRequest;
import com.coronaide.core.model.Workspace;
import com.coronaide.core.model.WorkspaceMetaData;
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
        Datastore<WorkspaceMetaData> datastore = com.coronaide.core.model.CoreDatastores
                .getWorkspaceDatastore();

        Mockito.when(workspaceService.getActiveWorkspace()).thenReturn(workspace);
        Mockito.when(datastoreService.load(workspace, module, datastore)).thenReturn(Optional.empty());

        Project result = projectService.create(new ProjectRequest(projectRoot));

        WorkspaceMetaData expectedData = new WorkspaceMetaData(
                Collections.singleton(new ProjectLocation(result.getId(), projectRoot.toString())));

        Assert.assertNotNull(result);
        Assert.assertNotNull(result.getId());
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

        ProjectLocation existingLocation = new ProjectLocation(UUID.randomUUID(), Paths.get("other").toString());

        Set<ProjectLocation> existingLocations = new HashSet<>();
        existingLocations.add(existingLocation);


        Workspace workspace = new Workspace(Paths.get("workspace"));
        Module module = CoronaIdeCore.getModule();
        Datastore<WorkspaceMetaData> datastore = CoreDatastores.getWorkspaceDatastore();
        WorkspaceMetaData existingData = new WorkspaceMetaData(existingLocations);


        Mockito.when(workspaceService.getActiveWorkspace()).thenReturn(workspace);
        Mockito.when(datastoreService.load(workspace, module, datastore)).thenReturn(Optional.of(existingData));

        Project result = projectService.create(new ProjectRequest(projectRoot));

        ProjectLocation newLocation = new ProjectLocation(result.getId(), projectRoot.toString());
        Set<ProjectLocation> expectedLocations = new HashSet<>();
        expectedLocations.add(existingLocation);
        expectedLocations.add(newLocation);
        WorkspaceMetaData expectedData = new WorkspaceMetaData(expectedLocations);

        Assert.assertNotNull(result);
        Assert.assertNotNull(result.getId());
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
        Datastore<WorkspaceMetaData> datastore = CoreDatastores.getWorkspaceDatastore();

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
        Datastore<WorkspaceMetaData> datastore = CoreDatastores.getWorkspaceDatastore();

        Path projectRoot = Paths.get("other");
        ProjectLocation existingLocation = new ProjectLocation(UUID.randomUUID(), projectRoot.toString());
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
        Assert.assertNotNull(project.getId());
        Assert.assertEquals(project.getName(), projectRoot.getFileName().toString());
        Assert.assertEquals(project.getRootDirectory(), projectRoot);
        Assert.assertEquals(project.getWorkingDirectory(), Datastores.getMetaDataDirectory(projectRoot));

        Mockito.verify(workspaceService).getActiveWorkspace();
        Mockito.verify(datastoreService).load(workspace, module, datastore);
    }

    @Test
    public void remove() throws Exception {
        UUID id = UUID.randomUUID();
        Workspace workspace = new Workspace(Paths.get("workspace"));
        Module module = CoronaIdeCore.getModule();
        Datastore<WorkspaceMetaData> datastore = CoreDatastores.getWorkspaceDatastore();

        Path projectRoot = Files.createTempFile("projectService", "-create");
        projectRoot.toFile().deleteOnExit();

        ProjectLocation existingLocation = new ProjectLocation(id, projectRoot.toString());
        Set<ProjectLocation> existingLocations = new HashSet<>();
        existingLocations.add(existingLocation);
        WorkspaceMetaData existingData = new WorkspaceMetaData(existingLocations);

        Mockito.when(workspaceService.getActiveWorkspace()).thenReturn(workspace);
        Mockito.when(datastoreService.load(workspace, module, datastore)).thenReturn(Optional.of(existingData));

        projectService.delete(id, false);

        // Directory should still exist
        Assert.assertTrue(projectRoot.toFile().exists());

        Mockito.verify(workspaceService).getActiveWorkspace();
        Mockito.verify(datastoreService, Mockito.times(2)).load(workspace, module, datastore);
        Mockito.verify(datastoreService).store(workspace, module, datastore,
                new WorkspaceMetaData(Collections.emptySet()));
    }

    @Test
    public void removeRequest() throws Exception {
        Workspace workspace = new Workspace(Paths.get("workspace"));
        Module module = CoronaIdeCore.getModule();
        Datastore<WorkspaceMetaData> datastore = CoreDatastores.getWorkspaceDatastore();

        Path projectRoot = Files.createTempFile("projectService", "-create");
        projectRoot.toFile().deleteOnExit();

        ProjectLocation existingLocation = new ProjectLocation(UUID.randomUUID(), projectRoot.toString());
        Set<ProjectLocation> existingLocations = new HashSet<>();
        existingLocations.add(existingLocation);
        WorkspaceMetaData existingData = new WorkspaceMetaData(existingLocations);

        Mockito.when(workspaceService.getActiveWorkspace()).thenReturn(workspace);
        Mockito.when(datastoreService.load(workspace, module, datastore)).thenReturn(Optional.of(existingData));

        projectService.delete(new ProjectDeleteRequest(projectRoot, false));

        // Directory should still exist
        Assert.assertTrue(projectRoot.toFile().exists());

        Mockito.verify(workspaceService).getActiveWorkspace();
        Mockito.verify(datastoreService, Mockito.times(2)).load(workspace, module, datastore);
        Mockito.verify(datastoreService).store(workspace, module, datastore,
                new WorkspaceMetaData(Collections.emptySet()));
    }

    @Test
    public void delete() throws Exception {
        UUID id = UUID.randomUUID();
        Workspace workspace = new Workspace(Paths.get("workspace"));
        Module module = CoronaIdeCore.getModule();
        Datastore<WorkspaceMetaData> datastore = CoreDatastores.getWorkspaceDatastore();

        Path projectRoot = Files.createTempFile("projectService", "-create");
        projectRoot.toFile().deleteOnExit();

        ProjectLocation existingLocation = new ProjectLocation(id, projectRoot.toString());
        Set<ProjectLocation> existingLocations = new HashSet<>();
        existingLocations.add(existingLocation);
        WorkspaceMetaData existingData = new WorkspaceMetaData(existingLocations);

        Mockito.when(workspaceService.getActiveWorkspace()).thenReturn(workspace);
        Mockito.when(datastoreService.load(workspace, module, datastore)).thenReturn(Optional.of(existingData));

        projectService.delete(id, true);

        // Directory should be deleted
        Assert.assertFalse(projectRoot.toFile().exists());

        Mockito.verify(workspaceService).getActiveWorkspace();
        Mockito.verify(datastoreService, Mockito.times(2)).load(workspace, module, datastore);
        Mockito.verify(datastoreService).store(workspace, module, datastore,
                new WorkspaceMetaData(Collections.emptySet()));
    }

    @Test
    public void deleteRequest() throws Exception {
        Workspace workspace = new Workspace(Paths.get("workspace"));
        Module module = CoronaIdeCore.getModule();
        Datastore<WorkspaceMetaData> datastore = CoreDatastores.getWorkspaceDatastore();

        Path projectRoot = Files.createTempFile("projectService", "-create");
        projectRoot.toFile().deleteOnExit();

        ProjectLocation existingLocation = new ProjectLocation(UUID.randomUUID(), projectRoot.toString());
        Set<ProjectLocation> existingLocations = new HashSet<>();
        existingLocations.add(existingLocation);
        WorkspaceMetaData existingData = new WorkspaceMetaData(existingLocations);

        Mockito.when(workspaceService.getActiveWorkspace()).thenReturn(workspace);
        Mockito.when(datastoreService.load(workspace, module, datastore)).thenReturn(Optional.of(existingData));

        projectService.delete(new ProjectDeleteRequest(projectRoot, true));

        // Directory should be deleted
        Assert.assertFalse(projectRoot.toFile().exists());

        Mockito.verify(workspaceService).getActiveWorkspace();
        Mockito.verify(datastoreService, Mockito.times(2)).load(workspace, module, datastore);
        Mockito.verify(datastoreService).store(workspace, module, datastore,
                new WorkspaceMetaData(Collections.emptySet()));
    }

}
