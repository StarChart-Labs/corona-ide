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
package com.coronaide.core.service.impl;

import java.nio.file.Path;
import java.util.Objects;

import com.coronaide.core.internal.datastore.util.Datastores;
import com.coronaide.core.internal.service.ICoreConfiguration;
import com.coronaide.core.model.Workspace;
import com.coronaide.core.service.IWorkspaceService;

/**
 * Implementation of {@link IWorkspaceService}. Not intended for direct use by clients - use dependency injection to
 * obtain an instance of {@link IWorkspaceService} instead
 *
 * @author romeara
 * @since 0.1
 */
public class WorkspaceService implements IWorkspaceService {

    private final ICoreConfiguration coreConfiguration;

    /**
     * Creates a new workspace service instance
     *
     * @param coreConfiguration
     *            Configuration of the core Corona IDE application
     * @since 0.1
     */
    public WorkspaceService(ICoreConfiguration coreConfiguration) {
        this.coreConfiguration = Objects.requireNonNull(coreConfiguration);
    }

    @Override
    public Workspace getActiveWorkspace() {
        Path workspaceWorkingDirectory = Datastores
                .getMetaDataDirectory(coreConfiguration.getActiveWorkspaceDirectory());

        return new Workspace(workspaceWorkingDirectory);
    }

}
