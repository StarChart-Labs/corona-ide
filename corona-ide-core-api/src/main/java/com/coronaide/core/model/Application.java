/*******************************************************************************
 * Copyright (c) Oct 24, 2016 Corona IDE.
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

import com.coronaide.core.service.IApplicationService;

/**
 * Represents the Corona IDE application instance currently running.
 *
 * <p>
 * Clients are not intended to create instances of this class - retrieve the application instance from the
 * {@link IApplicationService} instance
 *
 * @author romeara
 * @since 0.1
 */
@Immutable
public final class Application {

    private static final Version VERSION = new Version(0, 0, 1);

    private final Path workingDirectory;

    /**
     * @param workingDirectory
     *            Directory all application generated/managed files should be placed within
     * @since 0.1
     */
    public Application(Path workingDirectory) {
        this.workingDirectory = Objects.requireNonNull(workingDirectory);
    }

    /**
     * @return The current running version of the application
     * @since 0.1
     */
    public Version getVersion() {
        return VERSION;
    }

    /**
     * @return Directory where all files managed by the Corona IDE application are located
     * @since 0.1
     */
    public Path getWorkingDirectory() {
        return workingDirectory;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getVersion(), getWorkingDirectory());
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        boolean result = false;

        if (obj instanceof Application) {
            Application compare = (Application) obj;

            result = Objects.equals(compare.getVersion(), getVersion())
                    && Objects.equals(compare.getWorkingDirectory(), getWorkingDirectory());
        }

        return result;
    }

    @Override
    public String toString() {
        return new StringBuilder().append(getClass().getSimpleName()).append('{')
                .append("version").append('=').append(getVersion()).append(',')
                .append("workingDirectory").append('=').append(getWorkingDirectory())
                .append('}').toString();
    }

}
