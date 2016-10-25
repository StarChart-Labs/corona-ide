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
package com.coronaide.core.services.impl;

import java.util.Objects;

import com.coronaide.core.Application;
import com.coronaide.core.impl.CoronaIdeApplication;
import com.coronaide.core.internal.services.ICoreConfiguration;
import com.coronaide.core.service.IApplicationService;

/**
 * Implementation of {@link IApplicationService}. Not intended for direct use by clients - use dependency injection to
 * obtain an instance of {@link IApplicationService} instead
 *
 * @author romeara
 * @since 0.1
 */
public class ApplicationService implements IApplicationService {

    private final Application coronaIdeApplication;

    /**
     * Creates a new application service instance
     *
     * @param coreConfiguration
     *            Configuration of the core Corona IDE application
     * @since 0.1
     */
    public ApplicationService(ICoreConfiguration coreConfiguration) {
        Objects.requireNonNull(coreConfiguration);

        coronaIdeApplication = new CoronaIdeApplication(coreConfiguration.getApplicationWorkingDirectory());
    }

    @Override
    public Application get() {
        return coronaIdeApplication;
    }

}
