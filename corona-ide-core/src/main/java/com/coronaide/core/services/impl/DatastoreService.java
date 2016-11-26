/*******************************************************************************
 * Copyright (c) Oct 17, 2016 Corona IDE.
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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Objects;
import java.util.Optional;

import com.coronaide.core.Application;
import com.coronaide.core.Datastore;
import com.coronaide.core.Module;
import com.coronaide.core.Version;
import com.coronaide.core.Workspace;
import com.coronaide.core.exception.DataStorageException;
import com.coronaide.core.service.IDatastoreService;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;

/**
 * Implementation of {@link IDatastoreService}. Not intended for direct use by clients - use dependency injection to
 * obtain an instance of {@link IDatastoreService} instead
 *
 * @author romeara
 * @since 0.1
 */
public class DatastoreService implements IDatastoreService {

    private final String VERSION_FILE = "versions.json";

    private final Gson gson;

    private final JsonParser jsonParser;

    /**
     * Creates a new data store service instance
     *
     * @since 0.1
     */
    public DatastoreService() {
        gson = new Gson();
        jsonParser = new JsonParser();
    }

    @Override
    public <T> void store(Application application, Module module, Datastore<T> datastore, T data)
            throws DataStorageException {
        Objects.requireNonNull(application);
        Objects.requireNonNull(module);
        Objects.requireNonNull(datastore);
        Objects.requireNonNull(data);

        try {
            store(application.getWorkingDirectory(), module, datastore, data);
        } catch (IOException e) {
            throw new DataStorageException(
                    "Error storing data store for module " + module.getId() + " (" + datastore.getKey() + ")", e);
        }
    }

    @Override
    public <T> Optional<T> load(Application application, Module module, Datastore<T> datastore)
            throws DataStorageException {
        Objects.requireNonNull(application);
        Objects.requireNonNull(module);
        Objects.requireNonNull(datastore);

        try {
            return load(application.getWorkingDirectory(), module, datastore);
        } catch (IOException e) {
            throw new DataStorageException(
                    "Error loading data store for module " + module.getId() + " (" + datastore.getKey() + ")", e);
        }
    }

    @Override
    public void clear(Application application, Module module) throws DataStorageException {
        Objects.requireNonNull(application);
        Objects.requireNonNull(module);

        try {
            clear(application.getWorkingDirectory(), module);
        } catch (IOException e) {
            throw new DataStorageException("Error clearing data store for module " + module.getId(), e);
        }
    }

    @Override
    public <T> void store(Workspace workspace, Module module, Datastore<T> datastore, T data)
            throws DataStorageException {
        Objects.requireNonNull(workspace);
        Objects.requireNonNull(module);
        Objects.requireNonNull(datastore);
        Objects.requireNonNull(data);

        try {
            store(workspace.getWorkingDirectory(), module, datastore, data);
        } catch (IOException e) {
            throw new DataStorageException(
                    "Error storing data store for module " + module.getId() + " (" + datastore.getKey() + ")", e);
        }
    }

    @Override
    public <T> Optional<T> load(Workspace workspace, Module module, Datastore<T> datastore)
            throws DataStorageException {
        Objects.requireNonNull(workspace);
        Objects.requireNonNull(module);
        Objects.requireNonNull(datastore);

        try {
            return load(workspace.getWorkingDirectory(), module, datastore);
        } catch (IOException e) {
            throw new DataStorageException(
                    "Error loading data store for module " + module.getId() + " (" + datastore.getKey() + ")", e);
        }
    }

    @Override
    public void clear(Workspace workspace, Module module) throws DataStorageException {
        Objects.requireNonNull(workspace);
        Objects.requireNonNull(module);

        try {
            clear(workspace.getWorkingDirectory(), module);
        } catch (IOException e) {
            throw new DataStorageException("Error clearing data store for module " + module.getId(), e);
        }
    }

    /**
     * Loads data for a module within a given Corona IDE working directory
     *
     * @param coronaDirectory
     *            The Corona IDE working directory the data storage system is working within
     * @param module
     *            The module data is being loaded for
     * @param datastore
     *            The data store managing the data
     * @return The loaded data, if any - empty if no data is currently stored for the given datastore and module
     * @throws IOException
     *             If there is a file system I/O error loading the data
     */
    private <T> Optional<T> load(Path coronaDirectory, Module module, Datastore<T> datastore) throws IOException {
        T result = null;

        Path moduleDirectory = getOrCreateModuleDirectory(coronaDirectory, module);
        Optional<Version> existingVersion = loadVersion(moduleDirectory, datastore.getKey());
        File datastoreFile = moduleDirectory.resolve(datastore.getKey()).toFile();

        if (existingVersion.isPresent() && datastoreFile.exists()) {
            try (BufferedReader input = new BufferedReader(new FileReader(datastoreFile))) {
                if (!Objects.equals(existingVersion.get(), module.getVersion())) {
                    // Migrate the old data - store must wait until reader is closed
                    result = datastore.migrate(input, existingVersion.get());
                } else {
                    result = datastore.load(input);
                }
            }

            if (!Objects.equals(existingVersion.get(), module.getVersion())) {
                store(coronaDirectory, module, datastore, result);
            }
        }

        return Optional.ofNullable(result);
    }

    /**
     * Stores data for the module within a given Corona IDE working directory
     *
     * @param coronaDirectory
     *            The Corona IDE working directory the data storage system is working within
     * @param module
     *            The module data is being stored for
     * @param datastore
     *            The data store managing the data
     * @param data
     *            The data representation to store
     * @throws IOException
     *             If there is a file system I/O error storing the data
     */
    private <T> void store(Path coronaDirectory, Module module, Datastore<T> datastore, T data) throws IOException {
        Path moduleDirectory = getOrCreateModuleDirectory(coronaDirectory, module);
        storeVersion(moduleDirectory, module.getVersion(), datastore.getKey());

        File datastoreFile = moduleDirectory.resolve(datastore.getKey()).toFile();

        if (!datastoreFile.exists() && !datastoreFile.createNewFile()) {
            throw new IOException("Failed to create datastore file (" + datastoreFile.toPath().toString() + ")");
        }

        if (data != null) {
            try (BufferedWriter output = new BufferedWriter(new FileWriter(datastoreFile))) {
                datastore.store(data, output);
            }
        }
    }

    /**
     * Clears all data associated with a given module in a specific working directory
     *
     * @param coronaDirectory
     *            The Corona IDE working directory the data storage system is working within
     * @param module
     *            The module data is being cleared for
     * @throws IOException
     *             If there is a file system I/O error clearing the data
     */
    private void clear(Path coronaDirectory, Module module) throws IOException {
        Path moduleDirectory = getOrCreateModuleDirectory(coronaDirectory, module);

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

        File directoryFile = directory.toFile();

        if (!directoryFile.exists() && !directoryFile.mkdirs()) {
            throw new IOException("Failed to create module directory (" + directory.toString() + ")");
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
        File versionFile = moduleDirectory.resolve(VERSION_FILE).toFile();

        if (versionFile.exists()) {
            try (Reader reader = new FileReader(versionFile)) {
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
        File versionFile = moduleDirectory.resolve(VERSION_FILE).toFile();
        JsonObject allVersions = null;

        // Load the JSON if it exists, otherwise initialize the representation
        if (versionFile.exists()) {
            try (Reader reader = new BufferedReader(new FileReader(versionFile))) {
                allVersions = jsonParser.parse(reader).getAsJsonObject();
            }
        } else {
            if (versionFile.createNewFile()) {
                allVersions = new JsonObject();
            } else {
                throw new IOException(
                        "Failed to create datastore version file (" + versionFile.toPath().toString() + ")");
            }
        }

        // Add/replace the entry
        JsonElement newVersion = gson.toJsonTree(version);
        allVersions.add(datastoreKey, newVersion);

        // Write out to file
        try (JsonWriter writer = gson.newJsonWriter((new FileWriter(versionFile, false)))) {
            gson.toJson(allVersions, writer);
        }
    }

}
