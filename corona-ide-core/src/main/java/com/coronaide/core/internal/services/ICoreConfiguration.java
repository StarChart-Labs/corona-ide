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
package com.coronaide.core.internal.services;

import java.nio.file.Path;

/**
 * Provides access to manipulate and read core configuration parameters, such as the application location
 *
 * @author romeara
 * @since 0.1
 */
public interface ICoreConfiguration {

    /**
     * Sets the location of the application installation. Intended to only be called once at startup, subsequent calls
     * will fail
     *
     * @param applicationLocation
     *            The installation location of the application
     * @throws IllegalStateException
     *             If the application location is set more than once
     * @since 0.1
     */
    void setLocations(Path applicationLocation);

    /**
     * @return The sub-path below a given element which should be considered the working directory of an element
     * @since 0.1
     */
    Path getWorkingDirectoryName();

    /**
     * @return Path to the Corona IDE working directory for the application
     * @throws IllegalStateException
     *             If the location where the application is installed was never initialized
     * @since 0.1
     */
    Path getApplicationWorkingDirectory();

}
