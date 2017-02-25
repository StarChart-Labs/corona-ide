/*******************************************************************************
 * Copyright (c) Jan 19, 2017 Corona IDE.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    romeara - initial API and implementation and/or initial documentation
 *******************************************************************************/
package com.coronaide.core.datastore;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.coronaide.core.Version;

public class JsonDatastoreTest {

    private static final String TEST_CONTENT = "test-data";

    // File used for load tests - content is equal to TEST_CONTENT
    private File testSource;

    @BeforeClass
    public void setup() throws Exception {
        testSource = File.createTempFile("test_source", ".json");
        testSource.deleteOnExit();

        // Write some data to the test file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(testSource))) {
            writer.write(TEST_CONTENT);
        }
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void createNullKey() throws Exception {
        new JsonDatastore<>(null, String.class);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void createNullRepresentation() throws Exception {
        new JsonDatastore<>("key", null);
    }

    @Test
    public void getKey() throws Exception {
        JsonDatastore<String> datastore = new JsonDatastore<>("key", String.class);

        Assert.assertEquals(datastore.getKey(), "key");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void storeNullData() throws Exception {
        File temp = File.createTempFile("store_test", ".json");
        temp.deleteOnExit();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(temp))) {
            JsonDatastore<String> datastore = new JsonDatastore<>("key", String.class);

            datastore.store(null, writer);
        }
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void storeNullDestination() throws Exception {
        JsonDatastore<String> datastore = new JsonDatastore<>("key", String.class);

        datastore.store("data", null);
    }

    @Test
    public void store() throws Exception {
        File temp = File.createTempFile("store_test", ".json");
        temp.deleteOnExit();

        String expectedContent = "data";
        JsonDatastore<String> datastore = new JsonDatastore<>("key", String.class);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(temp))) {
            datastore.store(expectedContent, writer);
        }

        List<String> storedLines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(temp))) {
            String line = reader.readLine();

            while (line != null) {
                storedLines.add(line);

                line = reader.readLine();
            }
        }

        Assert.assertEquals(storedLines.size(), 1);
        // We expect surrounding quotes to conform to the JSON standard
        Assert.assertEquals(storedLines.get(0), "\"" + expectedContent + "\"");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void loadNullSource() throws Exception {
        JsonDatastore<String> datastore = new JsonDatastore<>("key", String.class);

        datastore.load(null);
    }

    @Test
    public void load() throws Exception {
        JsonDatastore<String> datastore = new JsonDatastore<>("key", String.class);

        try (BufferedReader reader = new BufferedReader(new FileReader(testSource))) {
            String result = datastore.load(reader);

            Assert.assertEquals(result, TEST_CONTENT);
        }
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void migrateNullSource() throws Exception {
        JsonDatastore<String> datastore = new JsonDatastore<>("key", String.class);

        datastore.migrate(null, new Version(0, 0, 1));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void migrateNullSourceVersion() throws Exception {
        JsonDatastore<String> datastore = new JsonDatastore<>("key", String.class);

        try (BufferedReader reader = new BufferedReader(new FileReader(testSource))) {
            datastore.migrate(reader, null);
        }
    }

    @Test
    public void migrate() throws Exception {
        JsonDatastore<String> datastore = new JsonDatastore<>("key", String.class);

        try (BufferedReader reader = new BufferedReader(new FileReader(testSource))) {
            String result = datastore.migrate(reader, new Version(0, 0, 1));

            Assert.assertEquals(result, TEST_CONTENT);
        }
    }

}
