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

import com.coronaide.core.Application;
import com.coronaide.core.Module;
import com.coronaide.core.Workspace;
import com.coronaide.core.datastore.Datastore;
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
     * @param application
     *            Representation of the running Corona IDE application
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
    <T> void store(Application application, Module module, Datastore<T> datastore, T data);

    /**
     * Loads data from the application context for a specified module and data store
     *
     * @param <T>
     *            The Java representation of the data to store
     * @param application
     *            Representation of the running Corona IDE application
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
    <T> Optional<T> load(Application application, Module module, Datastore<T> datastore);

    /**
     * Clears all data associated with a module from the application context
     *
     * @param application
     *            Representation of the running Corona IDE application
     * @param module
     *            The module the data to clear is associated with
     * @throws DataStorageException
     *             If there is a system I/O error clearing the data. Runtime exception, as this is not an expected
     *             occurrence and indicates base program assumptions have been violated
     * @since 0.1
     */
    void clear(Application application, Module module);

    /**
     * Stores data in the workspace context for a specified module and data store
     *
     * @param <T>
     *            The Java representation of the data to store
     * @param workspace
     *            Representation of the active Corona IDE workspace
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
    <T> void store(Workspace workspace, Module module, Datastore<T> datastore, T data);

    /**
     * Loads data from the workspace context for a specified module and data store
     *
     * @param <T>
     *            The Java representation of the data to store
     * @param workspace
     *            Representation of the active Corona IDE workspace
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
    <T> Optional<T> load(Workspace workspace, Module module, Datastore<T> datastore);

    /**
     * Clears all data associated with a module from the workspace context
     *
     * @param workspace
     *            Representation of the active Corona IDE workspace
     * @param module
     *            The module the data to clear is associated with
     * @throws DataStorageException
     *             If there is a system I/O error clearing the data. Runtime exception, as this is not an expected
     *             occurrence and indicates base program assumptions have been violated
     * @since 0.1
     */
    void clear(Workspace workspace, Module module);
}
