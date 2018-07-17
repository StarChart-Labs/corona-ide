/*
 * Copyright (c) Feb 27, 2017 Corona IDE.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    romeara - initial API and implementation and/or initial documentation
 */
package com.coronaide.core.model;

import java.util.Objects;
import java.util.UUID;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

/**
 * Represents the location of a project. Used within internal data storage to save project locations to persistent
 * storage
 *
 * <p>
 * Clients are not intended to interact with this class directly
 *
 * @author romeara
 * @since 0.1.0
 */
@Immutable
public class ProjectLocation {

    private final UUID id;

    private final String rootDirectory;

    /**
     * @param id
     *            Unique identifier for a project in the context of the application
     * @param rootDirectory
     *            The directory which is the root of all files which are considered part of the project
     * @since 0.1.0
     */
    public ProjectLocation(UUID id, String rootDirectory) {
        this.id = Objects.requireNonNull(id);
        this.rootDirectory = Objects.requireNonNull(rootDirectory);
    }

    /**
     * @return Unique identifier for a project in the context of the application
     * @since 0.1.0
     */
    public UUID getId() {
        return id;
    }

    /**
     * @return The directory which is the root of all files which are considered part of the project
     * @since 0.1.0
     */
    public String getRootDirectory() {
        return rootDirectory;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(),
                getRootDirectory());
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        boolean result = false;

        if (obj instanceof ProjectLocation) {
            ProjectLocation compare = (ProjectLocation) obj;

            result = Objects.equals(compare.getId(), getId())
                    && Objects.equals(compare.getRootDirectory(), getRootDirectory());
        }

        return result;
    }

    @Override
    public String toString() {
        return new StringBuilder().append(getClass().getSimpleName()).append('{')
                .append("id").append('=').append(getId())
                .append("rootDirectory").append('=').append(getRootDirectory())
                .append('}').toString();
    }
}
