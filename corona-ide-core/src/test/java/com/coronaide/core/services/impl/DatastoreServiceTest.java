/*******************************************************************************
 * Copyright (c) Oct 20, 2016 Corona IDE.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    romeara - initial API and implementation and/or initial documentation
 *******************************************************************************/
package com.coronaide.core.services.impl;

import java.nio.file.Files;
import java.nio.file.Path;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.coronaide.core.Module;
import com.coronaide.core.Version;
import com.coronaide.core.internal.services.ICoreConfiguration;
import com.coronaide.core.service.IDatastoreService;
import com.coronaide.test.core.TestDatastore;

/**
 * Test basic operation of the datastore service
 *
 * @author romeara
 */
public class DatastoreServiceTest {

    @Mock
    private ICoreConfiguration coreConfiguration;

    @Mock
    private Module module;

    private IDatastoreService datastoreService;

    @BeforeMethod
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(module.getId()).thenReturn("com.coronaide.test");
        Mockito.when(module.getVersion()).thenReturn(new Version(1, 0, 0));

        datastoreService = new DatastoreService(coreConfiguration);
    }

    @Test
    public void storeApplicationData() throws Exception {
        // Create a fake corona directory
        Path coronaDir = Files.createTempDirectory(getClass().getSimpleName() + ".storeApplicationData");
        coronaDir.toFile().deleteOnExit();
        Mockito.when(coreConfiguration.getApplicationWorkingDirectory()).thenReturn(coronaDir);

        TestDatastore datastore = new TestDatastore();

        datastoreService.storeApplicationData(module, datastore, "string");

        Assert.assertEquals(datastoreService.loadApplicationData(module, datastore).orElse(null), "string");
    }

    @Test
    public void loadApplicationDataNotStored() throws Exception {
        // Create a fake corona directory
        Path coronaDir = Files.createTempDirectory(getClass().getSimpleName() + ".loadApplicationDataNotStored");
        coronaDir.toFile().deleteOnExit();
        Mockito.when(coreConfiguration.getApplicationWorkingDirectory()).thenReturn(coronaDir);

        TestDatastore datastore = new TestDatastore();

        Assert.assertFalse(datastoreService.loadApplicationData(module, datastore).isPresent());
    }

    @Test
    public void loadApplicationData() throws Exception {
        // Create a fake corona directory
        Path coronaDir = Files.createTempDirectory(getClass().getSimpleName() + ".loadApplicationData");
        coronaDir.toFile().deleteOnExit();
        Mockito.when(coreConfiguration.getApplicationWorkingDirectory()).thenReturn(coronaDir);

        TestDatastore datastore = new TestDatastore();

        datastoreService.storeApplicationData(module, datastore, "string");

        Assert.assertEquals(datastoreService.loadApplicationData(module, datastore).orElse(null), "string");
    }

    @Test
    public void loadApplicationDataMigrate() throws Exception {
        // Create a fake corona directory
        Path coronaDir = Files.createTempDirectory(getClass().getSimpleName() + ".loadApplicationDataMigrate");
        coronaDir.toFile().deleteOnExit();
        Mockito.when(coreConfiguration.getApplicationWorkingDirectory()).thenReturn(coronaDir);

        TestDatastore datastore = new TestDatastore();

        Mockito.when(module.getVersion()).thenReturn(new Version(0, 0, 1));
        datastoreService.storeApplicationData(module, datastore, "string");

        Mockito.when(module.getVersion()).thenReturn(new Version(0, 0, 2));
        Assert.assertEquals(datastoreService.loadApplicationData(module, datastore).orElse(null), "string");
        Assert.assertTrue(datastore.isMigrateCalled());
    }

    @Test
    public void clearApplicationData() throws Exception {
        // Create a fake corona directory
        Path coronaDir = Files.createTempDirectory(getClass().getSimpleName() + ".clearApplicationData");
        coronaDir.toFile().deleteOnExit();
        Mockito.when(coreConfiguration.getApplicationWorkingDirectory()).thenReturn(coronaDir);

        TestDatastore datastore = new TestDatastore();

        datastoreService.storeApplicationData(module, datastore, "string");

        Assert.assertEquals(datastoreService.loadApplicationData(module, datastore).orElse(null), "string");

        datastoreService.clearApplicationData(module);

        Assert.assertFalse(datastoreService.loadApplicationData(module, datastore).isPresent());
    }

}
