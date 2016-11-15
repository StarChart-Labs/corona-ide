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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.coronaide.core.Application;
import com.coronaide.core.Module;
import com.coronaide.core.Version;
import com.coronaide.core.service.IDatastoreService;
import com.coronaide.test.core.TestDatastore;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Test basic operation of the datastore service
 *
 * @author romeara
 */
public class DatastoreServiceTest {

    @Mock
    private Module module;

    private Application application;

    private IDatastoreService datastoreService;

    @BeforeMethod
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);

        Mockito.when(module.getId()).thenReturn("com.coronaide.test");
        Mockito.when(module.getVersion()).thenReturn(new Version(1, 0, 0));

        // Create a fake corona directory
        Path coronaDir = Files.createTempDirectory(getClass().getSimpleName() + ".storeApplicationData");
        coronaDir.toFile().deleteOnExit();

        application = new Application(coronaDir);

        datastoreService = new DatastoreService();
    }

    @Test
    public void storeApplicationData() throws Exception {
        // Create a fake corona directory
        TestDatastore datastore = new TestDatastore();

        datastoreService.store(application, module, datastore, "string");

        Assert.assertEquals(datastoreService.load(application, module, datastore).orElse(null), "string");

        // Check that file contents are as expected
        Path moduleDir = application.getWorkingDirectory().resolve(module.getId());

        assertFileContents(moduleDir.resolve(datastore.getKey()), "string");
        assertStoredVersion(moduleDir.resolve("versions.json"), datastore.getKey(), module.getVersion());
    }

    @Test
    public void loadApplicationDataNotStored() throws Exception {
        // Create a fake corona directory
        TestDatastore datastore = new TestDatastore();

        Assert.assertFalse(datastoreService.load(application, module, datastore).isPresent());
    }

    @Test
    public void loadApplicationData() throws Exception {
        // Create a fake corona directory
        TestDatastore datastore = new TestDatastore();

        datastoreService.store(application, module, datastore, "string");

        Assert.assertEquals(datastoreService.load(application, module, datastore).orElse(null), "string");
    }

    @Test
    public void loadApplicationDataMigrate() throws Exception {
        // Create a fake corona directory
        TestDatastore datastore = new TestDatastore();

        Mockito.when(module.getVersion()).thenReturn(new Version(0, 0, 1));
        datastoreService.store(application, module, datastore, "string");

        Mockito.when(module.getVersion()).thenReturn(new Version(0, 0, 2));
        Assert.assertEquals(datastoreService.load(application, module, datastore).orElse(null), "string");
        Assert.assertTrue(datastore.isMigrateCalled());
    }

    @Test
    public void clearApplicationData() throws Exception {
        // Create a fake corona directory
        TestDatastore datastore = new TestDatastore();

        datastoreService.store(application, module, datastore, "string");

        Assert.assertEquals(datastoreService.load(application, module, datastore).orElse(null), "string");

        datastoreService.clear(application, module);

        Assert.assertFalse(datastoreService.load(application, module, datastore).isPresent());
    }

    private void assertFileContents(Path path, String expected) throws IOException {
        StringBuilder result = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))) {
            String line = reader.readLine();

            while (line != null) {
                if (result.length() > 0) {
                    result.append('\n');
                }

                result.append(line);
                line = reader.readLine();
            }
        }

        Assert.assertEquals(result.toString(), expected);
    }

    private void assertStoredVersion(Path versionFilePath, String datastoreKey, Version expected) throws IOException {
        try (Reader reader = new FileReader(versionFilePath.toFile())) {
            JsonObject versions = new JsonParser().parse(reader)
                    .getAsJsonObject();

            Assert.assertTrue(versions.has(datastoreKey));

            JsonObject version = versions.getAsJsonObject(datastoreKey);
            Assert.assertEquals(version.get("major").getAsInt(), expected.getMajor());
            Assert.assertEquals(version.get("minor").getAsInt(), expected.getMinor());
            Assert.assertEquals(version.get("micro").getAsInt(), expected.getMicro());
        }
    }

}
