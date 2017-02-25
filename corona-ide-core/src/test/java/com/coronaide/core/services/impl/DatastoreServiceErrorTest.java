/*******************************************************************************
 * Copyright (c) Oct 18, 2016 Corona IDE.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    romeara - initial API and implementation and/or initial documentation
 *******************************************************************************/
package com.coronaide.core.services.impl;

import java.nio.file.Paths;

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
import com.coronaide.core.service.IDatastoreService;

/**
 * Test basic error behavior of the datastore service
 *
 * @author romeara
 */
public class DatastoreServiceErrorTest {

    @Mock
    private Module module;

    @Mock
    private Datastore<String> datastore;

    private Application application;

    private Workspace workspace;

    private IDatastoreService datastoreService;

    @BeforeMethod
    public void setup() {
        MockitoAnnotations.initMocks(this);

        Mockito.when(module.getId()).thenReturn("com.coronaide.test");
        Mockito.when(module.getVersion()).thenReturn(new Version(1, 0, 0));

        application = new Application(Paths.get("app-dir"));
        workspace = new Workspace(Paths.get("workspace-dir"));

        datastoreService = new DatastoreService();
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void storeApplicationNullApplication() throws Exception {
        datastoreService.store((Application) null, module, datastore, "data");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void storeApplicationDataNullModule() throws Exception {
        datastoreService.store(application, null, datastore, "data");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void storeApplicationDataNullDatastore() throws Exception {
        datastoreService.store(application, module, null, "data");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void storeApplicationDataNullData() throws Exception {
        datastoreService.store(application, module, datastore, null);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void loadApplicationDataNullApplication() throws Exception {
        datastoreService.load((Application) null, module, datastore);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void loadApplicationDataNullModule() throws Exception {
        datastoreService.load(application, null, datastore);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void loadApplicationDataNullDatastore() throws Exception {
        datastoreService.load(application, module, null);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void clearApplicationDataNullApplication() throws Exception {
        datastoreService.clear((Application) null, module);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void clearApplicationDataNullModule() throws Exception {
        datastoreService.clear(application, null);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void storeWorkspaceNullWorkspace() throws Exception {
        datastoreService.store((Workspace) null, module, datastore, "data");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void storeWorkspaceDataNullModule() throws Exception {
        datastoreService.store(workspace, null, datastore, "data");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void storeWorkspaceDataNullDatastore() throws Exception {
        datastoreService.store(workspace, module, null, "data");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void storeWorkspaceDataNullData() throws Exception {
        datastoreService.store(workspace, module, datastore, null);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void loadWorkspaceDataNullWorkspace() throws Exception {
        datastoreService.load((Workspace) null, module, datastore);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void loadWorkspaceDataNullModule() throws Exception {
        datastoreService.load(workspace, null, datastore);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void loadWorkspaceDataNullDatastore() throws Exception {
        datastoreService.load(workspace, module, null);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void clearWorkspaceDataNullWorkspace() throws Exception {
        datastoreService.clear((Workspace) null, module);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void clearWorkspaceDataNullModule() throws Exception {
        datastoreService.clear(workspace, null);
    }

}
