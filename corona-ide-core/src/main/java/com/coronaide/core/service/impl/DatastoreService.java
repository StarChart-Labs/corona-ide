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
package com.coronaide.core.service.impl;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

import com.coronaide.core.datastore.Datastore;
import com.coronaide.core.exception.DataStorageException;
import com.coronaide.core.internal.service.IDatastoreManager;
import com.coronaide.core.model.Application;
import com.coronaide.core.model.Module;
import com.coronaide.core.model.Workspace;
import com.coronaide.core.service.IDatastoreService;

/**
 * Implementation of {@link IDatastoreService}. Not intended for direct use by clients - use dependency injection to
 * obtain an instance of {@link IDatastoreService} instead
 *
 * @author romeara
 * @since 0.1
 */
public class DatastoreService implements IDatastoreService {

    private final IDatastoreManager datastoreManager;

    /**
     * Creates a new data store service instance
     *
     * @param datastoreManager
     *            API providing common,basic datastore handling functions
     * @since 0.1.0
     */
    public DatastoreService(IDatastoreManager datastoreManager) {
        this.datastoreManager = Objects.requireNonNull(datastoreManager);
    }

    @Override
    public <T> void store(Application application, Module module, Datastore<T> datastore, T data) {
        Objects.requireNonNull(application);
        Objects.requireNonNull(module);
        Objects.requireNonNull(datastore);
        Objects.requireNonNull(data);

        try {
            datastoreManager.store(application.getWorkingDirectory(), module, datastore, data);
        } catch (IOException e) {
            throw new DataStorageException(
                    "Error storing data store for module " + module.getId() + " (" + datastore.getKey() + ")", e);
        }
    }

    @Override
    public <T> Optional<T> load(Application application, Module module, Datastore<T> datastore) {
        Objects.requireNonNull(application);
        Objects.requireNonNull(module);
        Objects.requireNonNull(datastore);

        try {
            return datastoreManager.load(application.getWorkingDirectory(), module, datastore);
        } catch (IOException e) {
            throw new DataStorageException(
                    "Error loading data store for module " + module.getId() + " (" + datastore.getKey() + ")", e);
        }
    }

    @Override
    public void clear(Application application, Module module) {
        Objects.requireNonNull(application);
        Objects.requireNonNull(module);

        try {
            datastoreManager.clear(application.getWorkingDirectory(), module);
        } catch (IOException e) {
            throw new DataStorageException("Error clearing data store for module " + module.getId(), e);
        }
    }

    @Override
    public <T> void store(Workspace workspace, Module module, Datastore<T> datastore, T data) {
        Objects.requireNonNull(workspace);
        Objects.requireNonNull(module);
        Objects.requireNonNull(datastore);
        Objects.requireNonNull(data);

        try {
            datastoreManager.store(workspace.getWorkingDirectory(), module, datastore, data);
        } catch (IOException e) {
            throw new DataStorageException(
                    "Error storing data store for module " + module.getId() + " (" + datastore.getKey() + ")", e);
        }
    }

    @Override
    public <T> Optional<T> load(Workspace workspace, Module module, Datastore<T> datastore) {
        Objects.requireNonNull(workspace);
        Objects.requireNonNull(module);
        Objects.requireNonNull(datastore);

        try {
            return datastoreManager.load(workspace.getWorkingDirectory(), module, datastore);
        } catch (IOException e) {
            throw new DataStorageException(
                    "Error loading data store for module " + module.getId() + " (" + datastore.getKey() + ")", e);
        }
    }

    @Override
    public void clear(Workspace workspace, Module module) {
        Objects.requireNonNull(workspace);
        Objects.requireNonNull(module);

        try {
            datastoreManager.clear(workspace.getWorkingDirectory(), module);
        } catch (IOException e) {
            throw new DataStorageException("Error clearing data store for module " + module.getId(), e);
        }
    }

}
