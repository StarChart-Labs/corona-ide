/*******************************************************************************
 * Copyright (c) Oct 17, 2016 Corona IDE.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    romeara - initial API and implementation and/or initial documentation
 *******************************************************************************/
package com.coronaide.core.internal.services.impl;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import com.coronaide.core.internal.services.ICoreConfiguration;

/**
 * Implementation of {@link ICoreConfiguration}. Not intended for direct use by clients - use dependency injection to
 * obtain an instance of {@link ICoreConfiguration} instead
 *
 * @author romeara
 * @since 0.1
 */
public class CoreConfiguration implements ICoreConfiguration {

    private Path applicationLocation;

    @Override
    public void setLocations(Path applicationLocation) {
        Objects.requireNonNull(applicationLocation);

        if (this.applicationLocation != null) {
            throw new IllegalStateException(
                    "Application location has already been set - additional calls are not permitted");
        }

        this.applicationLocation = applicationLocation;
    }

    @Override
    public Path getCoronaWorkingDirectoryName() {
        return Paths.get(".corona-ide");
    }

    @Override
    public Path getApplicationCoronaIdeDirectory() {
        return applicationLocation.resolve(getCoronaWorkingDirectoryName());
    }

}
