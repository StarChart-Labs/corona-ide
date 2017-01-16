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
package com.coronaide.core;

import java.nio.file.Path;
import java.util.Objects;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.coronaide.core.service.IProjectService;

/**
 * Represents a project within Corona IDE. A project is a collection of files and settings which constitutes a set of
 * functionality
 *
 * <p>
 * Clients are not intended to create instances of this class - retrieve the instances from the {@link IProjectService}
 * instance
 *
 * @author romeara
 * @since 0.1
 */
@Immutable
public final class Project {

    private final String name;

    private final Path rootDirectory;

    private final Path workingDirectory;

    /**
     * @param name
     *            A simple label identifying the project and describing its purpose
     * @param rootDirectory
     *            The directory which is the root of all files which are considered part of the project
     * @param workingDirectory
     *            Directory all application generated/managed files for the project should be placed within
     * @since 0.1
     */
    public Project(String name, Path rootDirectory, Path workingDirectory) {
        this.name = Objects.requireNonNull(name);
        this.rootDirectory = Objects.requireNonNull(rootDirectory);
        this.workingDirectory = Objects.requireNonNull(workingDirectory);
    }

    /**
     * @return A simple label identifying the project and describing its purpose
     * @since 0.1
     */
    public String getName() {
        return name;
    }

    /**
     * @return The directory which is the root of all files which are considered part of the project
     * @since 0.1
     */
    public Path getRootDirectory() {
        return rootDirectory;
    }

    /**
     * @return Directory all application generated/managed files for the project should be placed within
     * @since 0.1
     */
    public Path getWorkingDirectory() {
        return workingDirectory;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(),
                getRootDirectory(),
                getWorkingDirectory());
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        boolean result = false;

        if (obj instanceof Project) {
            Project compare = (Project) obj;

            result = Objects.equals(compare.getName(), getName())
                    && Objects.equals(compare.getRootDirectory(), getRootDirectory())
                    && Objects.equals(compare.getWorkingDirectory(), getWorkingDirectory());
        }

        return result;
    }

    @Override
    public String toString() {
        return new StringBuilder().append(getClass().getSimpleName()).append('{')
                .append("name").append('=').append(getName())
                .append("rootDirectory").append('=').append(getRootDirectory())
                .append("workingDirectory").append('=').append(getWorkingDirectory())
                .append('}').toString();
    }
}
