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
package com.coronaide.test.core.service.impl;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.coronaide.core.model.Application;
import com.coronaide.core.model.Module;
import com.coronaide.core.model.Version;
import com.coronaide.core.model.Workspace;
import com.coronaide.core.service.IDatastoreService;
import com.coronaide.core.service.impl.DatastoreService;
import com.coronaide.testsupport.core.datastore.TestDatastore;
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
    public void clearApplicationData() throws Exception {
        // Create a fake corona directory
        TestDatastore datastore = new TestDatastore();

        datastoreService.store(application, module, datastore, "string");

        Assert.assertEquals(datastoreService.load(application, module, datastore).orElse(null), "string");

        datastoreService.clear(application, module);

        Assert.assertFalse(datastoreService.load(application, module, datastore).isPresent());
    }

    @Test
    public void storeWorkspaceData() throws Exception {
        // Create a fake corona directory
        TestDatastore datastore = new TestDatastore();

        datastoreService.store(workspace, module, datastore, "string");

        Assert.assertEquals(datastoreService.load(workspace, module, datastore).orElse(null), "string");

        // Check that file contents are as expected
        Path moduleDir = workspace.getWorkingDirectory().resolve(module.getId());

        assertFileContents(moduleDir.resolve(datastore.getKey()), "string");
        assertStoredVersion(moduleDir.resolve("versions.json"), datastore.getKey(), module.getVersion());
    }

    @Test
    public void loadWorkspaceDataNotStored() throws Exception {
        // Create a fake corona directory
        TestDatastore datastore = new TestDatastore();

        Assert.assertFalse(datastoreService.load(workspace, module, datastore).isPresent());
    }

    @Test
    public void loadWorkspaceData() throws Exception {
        // Create a fake corona directory
        TestDatastore datastore = new TestDatastore();

        datastoreService.store(workspace, module, datastore, "string");

        Assert.assertEquals(datastoreService.load(workspace, module, datastore).orElse(null), "string");
    }

    @Test
    public void clearWorkspaceData() throws Exception {
        // Create a fake corona directory
        TestDatastore datastore = new TestDatastore();

        datastoreService.store(workspace, module, datastore, "string");

        Assert.assertEquals(datastoreService.load(workspace, module, datastore).orElse(null), "string");

        datastoreService.clear(workspace, module);

        Assert.assertFalse(datastoreService.load(workspace, module, datastore).isPresent());
    }

    private void assertFileContents(Path path, String expected) throws IOException {
        String result = Files.lines(path)
                .collect(Collectors.joining("\n"));

        Assert.assertEquals(result.toString(), expected);
    }

    private void assertStoredVersion(Path versionFilePath, String datastoreKey, Version expected) throws IOException {
        try (Reader reader = Files.newBufferedReader(versionFilePath)) {
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
