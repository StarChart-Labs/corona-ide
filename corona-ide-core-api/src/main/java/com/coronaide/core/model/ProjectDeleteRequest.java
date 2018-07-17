/*
 * Copyright (c) Jul 14, 2018 StarChart Labs Authors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    nickv - initial API and implementation and/or initial documentation
 */
package com.coronaide.core.model;

import java.nio.file.Path;
import java.util.Objects;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a request to remove an existing project from the current workspace
 *
 * @author nickavv
 * @since 0.1.0
 * @deprecated Only used by legacy APIs as of 0.1.0
 */
@Deprecated
@Immutable
public final class ProjectDeleteRequest {

    private final Path rootDirectory;

    private final boolean deleteFromDisk;

    /**
     * @param rootDirectory
     *            The directory which is the root of all files which are considered part of the project
     * @param deleteFromDisk
     *            Whether to also delete the file contents from disk
     * @since 0.1.0
     */
    @JsonCreator
    public ProjectDeleteRequest(@JsonProperty("rootDirectory") Path rootDirectory, @JsonProperty("deleteFromDisk") boolean deleteFromDisk) {
        this.rootDirectory = Objects.requireNonNull(rootDirectory);
        this.deleteFromDisk = deleteFromDisk;
    }

    /**
     * @return The directory which is the root of all files which are considered part of the project
     * @since 0.1.0
     */
    public Path getRootDirectory() {
        return rootDirectory;
    }

    /**
     * @return Whether to also delete the file contents from disk
     * @since 0.1.0
     */
    public boolean getDeleteFromDisk() {
        return deleteFromDisk;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRootDirectory(), getDeleteFromDisk());
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        boolean result = false;

        if (obj instanceof ProjectDeleteRequest) {
            ProjectDeleteRequest compare = (ProjectDeleteRequest) obj;

            result = Objects.equals(compare.getRootDirectory(), getRootDirectory()) &&
                    Objects.equals(compare.getDeleteFromDisk(), getDeleteFromDisk());
        }

        return result;
    }

    @Override
    public String toString() {
        return new StringBuilder().append(getClass().getSimpleName()).append('{')
                .append("rootDirectory").append('=').append(getRootDirectory())
                .append("deleteFromDisk").append('=').append(getDeleteFromDisk())
                .append('}').toString();
    }
}
