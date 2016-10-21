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
package com.coronaide.core.service;

import java.util.Optional;

import com.coronaide.core.Datastore;
import com.coronaide.core.Module;
import com.coronaide.core.exception.DataStorageException;

/**
 * Allows manipulation of data stores for the application, workspaces, and projects
 *
 * @author romeara
 * @since 0.1
 */
public interface IDatastoreService {

    /**
     * Stores data in the application context for a specified module and data store
     *
     * @param <T>
     *            The Java representation of the data to store
     * @param module
     *            The module the data to store is associated with
     * @param datastore
     *            Data store specifying how to store the desired data
     * @param data
     *            A representation of the data to store
     * @throws DataStorageException
     *             If there is a system I/O error storing the data. Runtime exception, as this is not an expected
     *             occurrence and indicates base program assumptions have been violated
     * @since 0.1
     */
    <T> void storeApplicationData(Module module, Datastore<T> datastore, T data) throws DataStorageException;

    /**
     * Loads data from the application context for a specified module and data store
     *
     * @param <T>
     *            The Java representation of the data to store
     * @param module
     *            The module the data to load is associated with
     * @param datastore
     *            Data store specifying how to load the desired data
     * @return A type representation of the loaded data, or an empty optional if no data was found to load
     * @throws DataStorageException
     *             If there is a system I/O error loading the data. Runtime exception, as this is not an expected
     *             occurrence and indicates base program assumptions have been violated
     * @since 0.1
     */
    <T> Optional<T> loadApplicationData(Module module, Datastore<T> datastore) throws DataStorageException;

    /**
     * Clears all data associated with a module from the application context
     *
     * @param module
     *            The module the data to clear is associated with
     * @throws DataStorageException
     *             If there is a system I/O error clearing the data. Runtime exception, as this is not an expected
     *             occurrence and indicates base program assumptions have been violated
     * @since 0.1
     */
    void clearApplicationData(Module module) throws DataStorageException;
}
