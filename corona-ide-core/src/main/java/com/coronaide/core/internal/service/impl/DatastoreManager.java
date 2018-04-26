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
package com.coronaide.core.internal.service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Objects;
import java.util.Optional;

import com.coronaide.core.datastore.Datastore;
import com.coronaide.core.internal.service.IDatastoreManager;
import com.coronaide.core.model.Module;
import com.coronaide.core.model.Version;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;

/**
 * Implementation of {@link IDatastoreManager}. Not intended for direct use by clients - use dependency injection to
 * obtain an instance of {@link IDatastoreManager} instead
 *
 * @author romeara
 * @since 0.1.0
 */
public class DatastoreManager implements IDatastoreManager {

    private static final String VERSION_FILE = "versions.json";

    private final Gson gson;

    private final JsonParser jsonParser;

    public DatastoreManager() {
        gson = new Gson();
        jsonParser = new JsonParser();
    }

    @Override
    public <T> Optional<T> load(Path metaDataDirectory, Module module, Datastore<T> datastore) throws IOException {
        Objects.requireNonNull(metaDataDirectory);
        Objects.requireNonNull(module);
        Objects.requireNonNull(datastore);

        T result = null;

        Path moduleDirectory = getOrCreateModuleDirectory(metaDataDirectory, module);
        Optional<Version> existingVersion = loadVersion(moduleDirectory, datastore.getKey());
        Path datastorePath = moduleDirectory.resolve(datastore.getKey());

        if (existingVersion.isPresent() && Files.exists(datastorePath)) {
            try (BufferedReader input = Files.newBufferedReader(datastorePath)) {
                result = datastore.load(input);
            }

            if (!Objects.equals(existingVersion.get(), module.getVersion())) {
                store(metaDataDirectory, module, datastore, result);
            }
        }

        return Optional.ofNullable(result);
    }

    @Override
    public <T> void store(Path metaDataDirectory, Module module, Datastore<T> datastore, T data) throws IOException {
        Objects.requireNonNull(metaDataDirectory);
        Objects.requireNonNull(module);
        Objects.requireNonNull(datastore);
        Objects.requireNonNull(data);

        Path moduleDirectory = getOrCreateModuleDirectory(metaDataDirectory, module);
        storeVersion(moduleDirectory, module.getVersion(), datastore.getKey());

        Path datastoreFile = moduleDirectory.resolve(datastore.getKey());

        if (!Files.exists(datastoreFile)) {
            Files.createFile(datastoreFile);
        }

        if (data != null) {
            try (BufferedWriter output = Files.newBufferedWriter(datastoreFile)) {
                datastore.store(data, output);
            }
        }
    }

    @Override
    public void clear(Path metaDataDirectory, Module module) throws IOException {
        Path moduleDirectory = getOrCreateModuleDirectory(metaDataDirectory, module);

        Files.walkFileTree(moduleDirectory, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    /**
     * Constructs a reference to the working directory for a module, creating it if it does not yet exist
     *
     * @param parentDir
     *            The parent directory which should contain the module directory
     * @param module
     *            The module to find the directory for
     * @return A reference to the module working directory
     * @throws IOException
     *             If there is a file system I/O error creating the module working directory
     */
    private Path getOrCreateModuleDirectory(Path parentDir, Module module) throws IOException {
        Path directory = parentDir.resolve(module.getId());

        if (!Files.exists(directory)) {
            Files.createDirectories(directory);
        }

        return directory;
    }

    /**
     * Loads the current version of stored data for a given data store
     *
     * @param moduleDirectory
     *            The working directory for files associated with the module
     * @param datastoreKey
     *            The identifier of the data store the record is being loaded for
     * @return The current stored version of data, empty if no version is recorded
     * @throws IOException
     *             If there is a file system I/O error reading the version
     */
    private Optional<Version> loadVersion(Path moduleDirectory, String datastoreKey) throws IOException {
        Version result = null;
        Path versionPath = moduleDirectory.resolve(VERSION_FILE);

        if (Files.exists(versionPath)) {
            try (Reader reader = Files.newBufferedReader(versionPath)) {
                JsonElement versionElement = jsonParser.parse(reader)
                        .getAsJsonObject()
                        .get(datastoreKey);

                // Safe, as this is documented to return null if versionElement is null
                result = gson.fromJson(versionElement, Version.class);
            }
        }

        return Optional.ofNullable(result);
    }

    /**
     * Stores a version as the current version used with the given data store
     *
     * @param moduleDirectory
     *            The working directory for files associated with the module
     * @param version
     *            The current version to record for the data store
     * @param datastoreKey
     *            The identifier of the data store the record is being updated for
     * @throws IOException
     *             If there is a file system I/O error storing the version
     */
    private void storeVersion(Path moduleDirectory, Version version, String datastoreKey) throws IOException {
        Path versionPath = moduleDirectory.resolve(VERSION_FILE);
        JsonObject allVersions = null;

        // Load the JSON if it exists, otherwise initialize the representation
        if (Files.exists(versionPath)) {
            try (Reader reader = Files.newBufferedReader(versionPath)) {
                allVersions = jsonParser.parse(reader).getAsJsonObject();
            }
        } else {
            Files.createFile(versionPath);
            allVersions = new JsonObject();
        }

        // Add/replace the entry
        JsonElement newVersion = gson.toJsonTree(version);
        allVersions.add(datastoreKey, newVersion);

        // Write out to file
        try (JsonWriter writer = gson.newJsonWriter(Files.newBufferedWriter(versionPath))) {
            gson.toJson(allVersions, writer);
        }
    }

}
