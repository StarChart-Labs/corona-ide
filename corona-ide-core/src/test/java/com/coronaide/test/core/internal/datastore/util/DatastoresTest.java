/*
 * Copyright (c) May 30, 2017 Corona IDE.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    "romeara" - initial API and implementation and/or initial documentation
 */
package com.coronaide.test.core.internal.datastore.util;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.coronaide.core.internal.datastore.util.Datastores;

public class DatastoresTest {

    @Test(expectedExceptions = NullPointerException.class)
    public void getMetaDataDirectoryNullContextPath() throws Exception {
        Datastores.getMetaDataDirectory(null);
    }

    @Test
    public void getMetaDataDirectoryAlreadyResolved() throws Exception {
        Path contextPath = Paths.get("something", ".corona-ide");

        Path result = Datastores.getMetaDataDirectory(contextPath);

        Assert.assertNotNull(result);
        Assert.assertEquals(result, contextPath);
    }

    @Test
    public void getMetaDataDirectory() throws Exception {
        Path contextPath = Paths.get("something");
        Path expected = Paths.get("something", ".corona-ide");

        Path result = Datastores.getMetaDataDirectory(contextPath);

        Assert.assertNotNull(result);
        Assert.assertEquals(result, expected);
    }

}
