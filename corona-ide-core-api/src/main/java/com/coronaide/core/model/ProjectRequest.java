/*******************************************************************************
 * Copyright (c) Jan 15, 2017 Corona IDE.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    romeara - initial API and implementation and/or initial documentation
 *******************************************************************************/
package com.coronaide.core.model;

import java.nio.file.Path;
import java.util.Objects;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

/**
 * Represents a request to create or alter a project within Corona IDE. A project is a collection of files and settings
 * which constitutes a set of functionality
 *
 * @author romeara
 * @since 0.1
 */
@Immutable
public final class ProjectRequest {

    private final Path rootDirectory;

    /**
     * @param rootDirectory
     *            The directory which is the root of all files which are considered part of the project
     * @since 0.1
     */
    public ProjectRequest(Path rootDirectory) {
        this.rootDirectory = Objects.requireNonNull(rootDirectory);
    }

    /**
     * @return The directory which is the root of all files which are considered part of the project
     * @since 0.1
     */
    public Path getRootDirectory() {
        return rootDirectory;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRootDirectory());
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        boolean result = false;

        if (obj instanceof ProjectRequest) {
            ProjectRequest compare = (ProjectRequest) obj;

            result = Objects.equals(compare.getRootDirectory(), getRootDirectory());
        }

        return result;
    }

    @Override
    public String toString() {
        return new StringBuilder().append(getClass().getSimpleName()).append('{')
                .append("rootDirectory").append('=').append(getRootDirectory())
                .append('}').toString();
    }
}
