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
package com.coronaide.core.internal.service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import com.coronaide.core.datastore.Datastore;
import com.coronaide.core.model.Module;

/**
 * Represents base operations common to all datastore handling
 *
 * @author romeara
 * @since 0.1.0
 */
public interface IDatastoreManager {

    /**
     * Loads data for a module within a given Corona IDE working directory
     *
     * @param <T>
     *            Representation of the data to load
     * @param coronaDirectory
     *            The Corona IDE working directory the data storage system is working within
     * @param module
     *            The module data is being loaded for
     * @param datastore
     *            The data store managing the data
     * @return The loaded data, if any - empty if no data is currently stored for the given datastore and module
     * @throws IOException
     *             If there is a file system I/O error loading the data
     * @since 0.1.0
     */
    <T> Optional<T> load(Path coronaDirectory, Module module, Datastore<T> datastore) throws IOException;

    /**
     * Stores data for the module within a given Corona IDE working directory
     *
     * @param <T>
     *            Representation of the data to store
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
     * @since 0.1.0
     */
    <T> void store(Path coronaDirectory, Module module, Datastore<T> datastore, T data) throws IOException;

    /**
     * Clears all data associated with a given module in a specific working directory
     *
     * @param coronaDirectory
     *            The Corona IDE working directory the data storage system is working within
     * @param module
     *            The module data is being cleared for
     * @throws IOException
     *             If there is a file system I/O error clearing the data
     * @since 0.1.0
     */
    void clear(Path coronaDirectory, Module module) throws IOException;

}
