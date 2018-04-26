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
package com.coronaide.core.internal.datastore.util;

import java.nio.file.Path;
import java.util.Objects;

/**
 * Provides common utility methods for handling data stores
 *
 * @author romeara
 * @since 0.1.0
 */
public final class Datastores {

    private static final String WORKING_DIRECTORY_NAME = ".corona-ide";

    /**
     * Prevent instantiation of utility class
     */
    private Datastores() throws InstantiationException {
        throw new InstantiationException("Cannot instantiate instance of utility class '" + getClass().getName() + "'");
    }

    /**
     * Determines the location to store Corona IDE-specific meta-data for the given directory in
     *
     * @param contextDirectory
     *            The directory of the application, workspace, or project to store meta-data for
     * @return The meta-date directory to use.If an existing meta-data directory is supplied, that directory is returned
     * @since 0.1.0
     */
    public static Path getMetaDataDirectory(Path contextDirectory) {
        Objects.requireNonNull(contextDirectory);

        Path result = contextDirectory;

        if (!contextDirectory.endsWith(WORKING_DIRECTORY_NAME)) {
            result = result.resolve(WORKING_DIRECTORY_NAME);
        }

        return result;
    }
}
