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

import java.nio.file.Paths;

import org.testng.annotations.Test;

import com.coronaide.core.internal.service.impl.CoreConfiguration;

/**
 * Test basic error behavior of the core configuration representation
 *
 * @author romeara
 */
public class CoreConfigurationErrorTest {

    @Test(expectedExceptions = IllegalStateException.class)
    public void setLocationMultipleTimes() throws Exception {
        CoreConfiguration configuration = new CoreConfiguration();

        configuration.setLocations(Paths.get("first"), Paths.get("first"));
        configuration.setLocations(Paths.get("second"), Paths.get("second"));
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void getApplicationWorkingDirectoryBeforeInitialization() throws Exception {
        CoreConfiguration configuration = new CoreConfiguration();

        configuration.getApplicationWorkingDirectory();
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void getWorksapceDirectoryBeforeInitialization() throws Exception {
        CoreConfiguration configuration = new CoreConfiguration();

        configuration.getActiveWorkspaceDirectory();
    }

}
