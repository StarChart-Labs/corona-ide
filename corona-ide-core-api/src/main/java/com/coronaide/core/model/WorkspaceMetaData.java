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
import java.util.Set;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

/**
 * Represents the basic data of a workspace. Used within internal data storage to save workspace data to persistent
 * storage
 *
 * <p>
 * Clients are not intended to interact with this class directly
 *
 * @author romeara
 * @since 0.1
 */
@Immutable
public class WorkspaceMetaData {

    private final Set<ProjectLocation> projectLocations;

    /**
     * @param projectLocations
     *            The location information for projects which are part of the described workspace
     * @since 0.1
     */
    public WorkspaceMetaData(Set<ProjectLocation> projectLocations) {
        this.projectLocations = Objects.requireNonNull(projectLocations);
        this.projectLocations.forEach(Objects::requireNonNull);
    }

    /**
     * @return The location information for projects which are part of the described workspace
     * @since 0.1
     */
    public Set<ProjectLocation> getProjectLocations() {
        return projectLocations;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProjectLocations());
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        boolean result = false;

        if (obj instanceof WorkspaceMetaData) {
            WorkspaceMetaData compare = (WorkspaceMetaData) obj;

            result = Objects.equals(compare.getProjectLocations(), getProjectLocations());
        }

        return result;
    }

    @Override
    public String toString() {
        return new StringBuilder().append(getClass().getSimpleName()).append('{')
                .append("projectLocations").append('=').append(getProjectLocations())
                .append('}').toString();
    }

}
