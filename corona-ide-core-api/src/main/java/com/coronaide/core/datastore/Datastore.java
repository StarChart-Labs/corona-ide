/*******************************************************************************
 * Copyright (c) Oct 16, 2016 Corona IDE.
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
import java.io.IOException;

import com.coronaide.core.model.Version;

/**
 * Represents operations used to load/store persistent data for use within Corona IDE
 *
 * @author romeara
 * @since 0.1
 * @param <T>
 *            Type which represents the data fields being operated on
 */
public interface Datastore<T> {

    /**
     * @return Key which uniquely identifies the data store within the context of a particular module
     * @since 0.1
     */
    String getKey();

    /**
     * Stores a Java type representation of data into a persistent store
     *
     * @param data
     *            Java representation of the data to store
     * @param destination
     *            Data stream to persistent store. Implementors should NOT close this writer
     * @throws IOException
     *             If there is an error writing data
     * @since 0.1
     */
    void store(T data, BufferedWriter destination) throws IOException;

    /**
     * Reads from an input stream, de-serializing data into a Java type representation
     *
     * @param source
     *            Input stream containing data to be read into a Java representation. Implementors should NOT close this
     *            reader
     * @return Java representation of the data provided
     * @throws IOException
     *             If there is an error reading data during de-serialization
     * @since 0.1
     */
    T load(BufferedReader source) throws IOException;

    /**
     * Reads from an input stream, migrating the data from a previous version to the current representation
     *
     * @param source
     *            Reader containing data in a previous version format
     * @param sourceVersion
     *            The version the read data is from
     * @return A migrated representation of the provided data
     * @throws IOException
     *             If there is an error reading data during de-serialization
     * @since 0.1
     */
    T migrate(BufferedReader source, Version sourceVersion) throws IOException;
}
