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
package com.coronaide.core;

import java.nio.file.Path;

/**
 * Represents the Corona IDE application instance currently running
 *
 * @author romeara
 * @since 0.1
 */
public interface Application {

    /**
     * @return The current running version of the application
     * @since 0.1
     */
    Version getVersion();

    /**
     * @return Directory where all files managed by the Corona IDE application are located
     * @since 0.1
     */
    Path getWorkingDirectory();

}
