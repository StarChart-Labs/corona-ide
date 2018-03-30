/*
 * Copyright (c) Apr 1, 2017 Corona IDE.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    romeara - initial API and implementation and/or initial documentation
 */
package com.coronaide.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.coronaide.core.internal.service.ICoreConfiguration;
import com.coronaide.core.internal.service.IDatastoreManager;
import com.coronaide.core.internal.service.impl.CoreConfiguration;
import com.coronaide.core.internal.service.impl.DatastoreManager;
import com.coronaide.core.service.IDatastoreService;
import com.coronaide.core.service.IProjectService;
import com.coronaide.core.service.IWorkspaceService;
import com.coronaide.core.service.impl.DatastoreService;
import com.coronaide.core.service.impl.ProjectService;
import com.coronaide.core.service.impl.WorkspaceService;

/**
 * Configuration which provides instances of APIs for use via dependency injection throughout the application
 *
 * <p>
 * Utilizes the spring framework's Java configuration methodology to provide implementations
 *
 * @author romeara
 * @since 0.1.0
 */
@Configuration
public class CoronaIdeCoreConfiguration {

    @Bean
    public ICoreConfiguration coreConfiguration() {
        return new CoreConfiguration();
    }

    @Bean
    public IDatastoreManager datastoreManager() {
        return new DatastoreManager();
    }

    @Bean
    public IDatastoreService datastoreService(IDatastoreManager datastoreManager) {
        return new DatastoreService(datastoreManager);
    }

    @Bean
    public IWorkspaceService workspaceService(ICoreConfiguration coreConfiguration) {
        return new WorkspaceService(coreConfiguration);
    }

    @Bean
    public IProjectService projectService(IWorkspaceService workspaceService, IDatastoreService datastoreService) {
        return new ProjectService(workspaceService, datastoreService);
    }
}
