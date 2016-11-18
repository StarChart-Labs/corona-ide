/*******************************************************************************
 * Copyright (c) Nov 17, 2016 Corona IDE.
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

import com.coronaide.core.service.IWorkspaceService;

/**
 * Represents a workspace within Corona IDE. A workspace is a unit of settings, modules, and configuration which forms a
 * customized environment for development
 *
 * <p>
 * Clients are not intended to create instances of this class - retrieve the application instance from the
 * {@link IWorkspaceService} instance
 *
 * @author romeara
 * @since 0.1
 */
@Immutable
public final class Workspace {

    private final Path workingDirectory;

    /**
     * @param workingDirectory
     *            Directory all application generated/managed files for the workspace should be placed within
     * @since 0.1
     */
    public Workspace(Path workingDirectory) {
        this.workingDirectory = Objects.requireNonNull(workingDirectory);
    }

    /**
     * @return Directory where all files managed by the Corona IDE application for the workspace are located
     * @since 0.1
     */
    public Path getWorkingDirectory() {
        return workingDirectory;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getWorkingDirectory());
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        boolean result = false;

        if (obj instanceof Workspace) {
            Workspace compare = (Workspace) obj;

            result = Objects.equals(compare.getWorkingDirectory(), getWorkingDirectory());
        }

        return result;
    }

    @Override
    public String toString() {
        return new StringBuilder().append(getClass().getSimpleName()).append('{')
                .append("workingDirectory").append('=').append(getWorkingDirectory())
                .append('}').toString();
    }
}
