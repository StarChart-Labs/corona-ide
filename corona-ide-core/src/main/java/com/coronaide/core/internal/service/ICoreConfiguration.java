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
package com.coronaide.core.internal.service;

import java.nio.file.Path;

/**
 * Provides access to manipulate and read core configuration parameters, such as the application location
 *
 * @author romeara
 * @since 0.1.0
 */
public interface ICoreConfiguration {

    /**
     * Sets the location of the application installation. Intended to only be called once at startup, subsequent calls
     * will fail
     *
     * @param applicationLocation
     *            The installation location of the application
     * @param workspaceLocation
     *            The location of the active workspace
     * @throws IllegalStateException
     *             If the application location is set more than once
     * @since 0.1.0
     */
    void setLocations(Path applicationLocation, Path workspaceLocation);

    /**
     * @return Path to the Corona IDE working directory for the application
     * @throws IllegalStateException
     *             If the location where the application is installed was never initialized
     * @since 0.1.0
     */
    Path getApplicationMetaDataDirectory();

    /**
     * @return Path to the directory where the currently active workspace is located
     * @throws IllegalStateException
     *             If the location where the workspace is located was never initialized
     * @since 0.1.0
     */
    Path getActiveWorkspaceDirectory();

}
