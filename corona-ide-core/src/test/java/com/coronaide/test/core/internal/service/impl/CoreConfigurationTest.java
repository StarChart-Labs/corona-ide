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
package com.coronaide.test.core.internal.service.impl;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.coronaide.core.internal.datastore.util.Datastores;
import com.coronaide.core.internal.service.impl.CoreConfiguration;

/**
 * Test general operation of core configuration representation
 *
 * @author romeara
 */
public class CoreConfigurationTest {

    private Path applicationDir = Paths.get("applicationDir");

    private Path workspaceDir = Paths.get("workspaceDir");

    private CoreConfiguration coreConfiguration = new CoreConfiguration();

    public CoreConfigurationTest() {
        coreConfiguration.setLocations(applicationDir, workspaceDir);
    }

    @Test
    public void getApplicationWorkingDirectory() throws Exception {
        Path applicationDirectory = coreConfiguration.getApplicationMetaDataDirectory();

        Assert.assertEquals(applicationDirectory, Datastores.getMetaDataDirectory(applicationDir));
    }

    @Test
    public void getActiveWorkspaceDirectory() throws Exception {
        Path workspaceDirectory = coreConfiguration.getActiveWorkspaceDirectory();

        Assert.assertEquals(workspaceDirectory, workspaceDir);
    }

}
