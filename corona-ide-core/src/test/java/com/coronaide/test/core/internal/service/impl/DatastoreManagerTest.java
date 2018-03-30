/*
 * Copyright (c) May 30, 2017 Corona IDE.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    "romeara" - initial API and implementation and/or initial documentation
 */
package com.coronaide.test.core.internal.service.impl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.coronaide.core.internal.service.impl.DatastoreManager;
import com.coronaide.core.model.Module;
import com.coronaide.core.model.Version;
import com.coronaide.testsupport.core.datastore.TestDatastore;

public class DatastoreManagerTest {

    private final DatastoreManager datastoreManager = new DatastoreManager();

    private final TestDatastore datastore = new TestDatastore();

    @Mock
    private Module module;

    private Path metaDataDirectory;

    @BeforeMethod
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);

        Mockito.when(module.getId()).thenReturn("com.coronaide.test");
        Mockito.when(module.getVersion()).thenReturn(new Version(1, 0, 0));

        // Create a fake corona directory
        metaDataDirectory = Files.createTempDirectory(getClass().getSimpleName() + ".storeApplicationData");
        metaDataDirectory.toFile().deleteOnExit();
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void loadNullCoronaDirectory() throws Exception {
        datastoreManager.load(null, module, datastore);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void loadNullModule() throws Exception {
        datastoreManager.load(metaDataDirectory, null, datastore);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void loadNullDatastore() throws Exception {
        datastoreManager.load(metaDataDirectory, module, null);
    }

    @Test
    public void loadNone() throws Exception {
        Optional<String> result = datastoreManager.load(metaDataDirectory, module, datastore);

        Assert.assertNotNull(result);
        Assert.assertFalse(result.isPresent());
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void storeNullCoronaDirectory() throws Exception {
        datastoreManager.store(null, module, datastore, "string");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void storeNullModule() throws Exception {
        datastoreManager.store(metaDataDirectory, null, datastore, "string");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void storeNullDatastore() throws Exception {
        datastoreManager.store(metaDataDirectory, module, null, "string");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void storeNullData() throws Exception {
        datastoreManager.store(metaDataDirectory, module, datastore, null);
    }

    @Test
    public void storeAndLoad() throws Exception {
        datastoreManager.store(metaDataDirectory, module, datastore, "string");

        try {
            Optional<String> result = datastoreManager.load(metaDataDirectory, module, datastore);

            Assert.assertNotNull(result);
            Assert.assertTrue(result.isPresent());
            Assert.assertEquals(result.get(), "string");
        } finally {
            datastoreManager.clear(metaDataDirectory, module);
        }
    }

    @Test
    public void storeOverPrexisting() throws Exception {
        datastoreManager.store(metaDataDirectory, module, datastore, "string");

        try {
            datastoreManager.store(metaDataDirectory, module, datastore, "another");
            Optional<String> result = datastoreManager.load(metaDataDirectory, module, datastore);

            Assert.assertNotNull(result);
            Assert.assertTrue(result.isPresent());
            Assert.assertEquals(result.get(), "another");
        } finally {
            datastoreManager.clear(metaDataDirectory, module);
        }
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void clearNullMetaDataPath() throws Exception {
        datastoreManager.clear(null, module);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void clearNullModule() throws Exception {
        datastoreManager.clear(metaDataDirectory, null);
    }

    @Test
    public void clear() throws Exception {
        datastoreManager.store(metaDataDirectory, module, datastore, "string");

        try {
            Optional<String> result = datastoreManager.load(metaDataDirectory, module, datastore);

            Assert.assertNotNull(result);
            Assert.assertTrue(result.isPresent());
        } finally {
            datastoreManager.clear(metaDataDirectory, module);
        }

        Optional<String> result = datastoreManager.load(metaDataDirectory, module, datastore);

        Assert.assertNotNull(result);
        Assert.assertFalse(result.isPresent());
    }

}
