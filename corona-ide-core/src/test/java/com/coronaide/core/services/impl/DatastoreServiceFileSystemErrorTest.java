/*******************************************************************************
 * Copyright (c) Nov 25, 2016 Corona IDE.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    romeara - initial API and implementation and/or initial documentation
 *******************************************************************************/
package com.coronaide.core.services.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.coronaide.core.Application;
import com.coronaide.core.Module;
import com.coronaide.core.Version;
import com.coronaide.core.Workspace;
import com.coronaide.core.datastore.Datastore;
import com.coronaide.core.exception.DataStorageException;
import com.coronaide.core.service.IDatastoreService;
import com.coronaide.test.core.TestDatastore;

/**
 * Test IO error handling behavior of the datastore service
 *
 * @author romeara
 */
public class DatastoreServiceFileSystemErrorTest {

    @Mock
    private Module module;

    @Mock
    private Datastore<String> datastore;

    private Application application;

    private Workspace workspace;

    private IDatastoreService datastoreService;

    @BeforeMethod
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);

        Mockito.when(module.getId()).thenReturn("com.coronaide.test");
        Mockito.when(module.getVersion()).thenReturn(new Version(1, 0, 0));

        // Create a fake corona directory
        Path coronaDir = Files.createTempDirectory(getClass().getSimpleName() + ".storeApplicationData");
        coronaDir.toFile().deleteOnExit();

        // Create fake workspace directory
        Path workspaceDir = Files.createTempDirectory(getClass().getSimpleName() + ".storeWorkspaceData");
        workspaceDir.toFile().deleteOnExit();

        application = new Application(coronaDir);
        workspace = new Workspace(workspaceDir);

        datastoreService = new DatastoreService();
    }

    @Test(expectedExceptions = DataStorageException.class)
    public void storeApplicationDataIOError() throws Exception {
        Mockito.when(datastore.getKey()).thenReturn("test-data-store");
        Mockito.doThrow(new IOException()).when(datastore).store(Mockito.any(), Mockito.any());

        datastoreService.store(application, module, datastore, "string");
    }

    @Test(expectedExceptions = DataStorageException.class)
    public void loadApplicationDataIOError() throws Exception {
        // Need to store something for it to try to load it
        TestDatastore validStore = new TestDatastore();
        datastoreService.store(application, module, validStore, "string");

        Mockito.when(datastore.getKey()).thenReturn(validStore.getKey());
        Mockito.when(datastore.load(Mockito.any())).thenThrow(new IOException());

        datastoreService.load(application, module, datastore);
    }

    @Test(expectedExceptions = DataStorageException.class)
    public void storeWorkspaceDataIOError() throws Exception {
        Mockito.when(datastore.getKey()).thenReturn("test-data-store");
        Mockito.doThrow(new IOException()).when(datastore).store(Mockito.any(), Mockito.any());

        datastoreService.store(workspace, module, datastore, "string");
    }

    @Test(expectedExceptions = DataStorageException.class)
    public void loadWorkspaceDataIOError() throws Exception {
        // Need to store something for it to try to load it
        TestDatastore validStore = new TestDatastore();
        datastoreService.store(workspace, module, validStore, "string");

        Mockito.when(datastore.getKey()).thenReturn(validStore.getKey());
        Mockito.when(datastore.load(Mockito.any())).thenThrow(new IOException());

        datastoreService.load(workspace, module, datastore);
    }

}
