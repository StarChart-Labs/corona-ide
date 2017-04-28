/*******************************************************************************
 * Copyright (c) Jan 16, 2017 Corona IDE.
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
import java.util.Objects;

import com.google.gson.Gson;

/**
 * Represents a basic datastore stored in the JSON file format
 *
 * <p>
 * Clients are intended to use this datastore for general data storage, and extend it when necessary to support
 * migration and advanced features
 *
 * @author romeara
 *
 * @param <T>
 *            The type which serves as the representation for all data stored within this datastore
 * @since 0.1
 */
public class JsonDatastore<T> implements Datastore<T> {

    private final String key;

    private final Class<T> representation;

    private final Gson gson;

    /**
     * @param key
     *            Unique identifier of the datastore within the context of a module
     * @param representation
     *            The class which serves as the representation for all data stored within this datastore
     * @since 0.1
     */
    public JsonDatastore(String key, Class<T> representation) {
        this.key = Objects.requireNonNull(key);
        this.representation = Objects.requireNonNull(representation);
        gson = new Gson();
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public void store(T data, BufferedWriter destination) throws IOException {
        Objects.requireNonNull(data);
        Objects.requireNonNull(destination);

        gson.toJson(data, destination);
    }

    @Override
    public T load(BufferedReader source) throws IOException {
        Objects.requireNonNull(source);

        return gson.fromJson(source, representation);
    }

}
