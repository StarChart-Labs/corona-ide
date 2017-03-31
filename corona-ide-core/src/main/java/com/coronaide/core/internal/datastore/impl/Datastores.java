/*
 * Copyright (c) Mar 29, 2017 Corona IDE.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    romeara - initial API and implementation and/or initial documentation
 */
package com.coronaide.core.internal.datastore.impl;

import com.coronaide.core.datastore.Datastore;
import com.coronaide.core.datastore.JsonDatastore;

/**
 * Provides common datastore-related operations to core implementation classes
 *
 * @author romeara
 * @since 0.1
 */
public final class Datastores {

    private static final Datastore<WorkspaceMetaData> WORKSPACE_DATASTORE = new JsonDatastore<>("metadata",
            WorkspaceMetaData.class);

    /**
     * Prevent instantiation of utility class
     */
    private Datastores() throws InstantiationException {
        throw new InstantiationException("Cannot instantiate instance of utility class '" + getClass().getName() + "'");
    }

    /**
     * @return Datastore for use within a workspace to manipulate the basic workspace information
     * @since 0.1
     */
    public static final Datastore<WorkspaceMetaData> getWorkspaceDatastore() {
        return WORKSPACE_DATASTORE;
    }
}
