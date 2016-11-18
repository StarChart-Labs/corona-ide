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
package com.coronaide.core.service;

import com.coronaide.core.Workspace;

/**
 * Allows retrieval of development environment information
 *
 * @author romeara
 * @since 0.1
 */
public interface IWorkspaceService {

    /**
     * @return A representation of the workspace the application is currently using as its development environment
     * @since 0.1
     */
    Workspace getActiveWorkspace();
}
