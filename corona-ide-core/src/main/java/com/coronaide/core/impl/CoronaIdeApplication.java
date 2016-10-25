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
package com.coronaide.core.impl;

import java.nio.file.Path;
import java.util.Objects;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.coronaide.core.Application;
import com.coronaide.core.Version;

/**
 * Represents the currently running Corona IDE application instance
 *
 * @author romeara
 * @since 0.1
 */
@Immutable
public class CoronaIdeApplication implements Application {

    private static Version VERSION = new Version(0, 0, 1);

    private Path workingDirectory;

    /**
     * @param workingDirectory
     *            Directory all application generated/managed files should be placed within
     * @since 0.1
     */
    public CoronaIdeApplication(Path workingDirectory) {
        this.workingDirectory = Objects.requireNonNull(workingDirectory);
    }

    @Override
    public Version getVersion() {
        return VERSION;
    }

    @Override
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

        if (obj instanceof CoronaIdeApplication) {
            CoronaIdeApplication compare = (CoronaIdeApplication) obj;

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
