/*******************************************************************************
 * Copyright (c) Oct 25, 2016 Corona IDE.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    romeara - initial API and implementation and/or initial documentation
 *******************************************************************************/
package com.coronaide.core.impl;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Tests basic operations of the Corona IDE Application representation
 *
 * @author romeara
 */
public class CoronaIdeApplicationTest {

    @Test(expectedExceptions = NullPointerException.class)
    public void createNullPath() throws Exception {
        new CoronaIdeApplication(null);
    }

    @Test
    public void getTest() throws Exception {
        Path workingDir = Paths.get("path");
        CoronaIdeApplication result = new CoronaIdeApplication(workingDir);

        Assert.assertNotNull(result.getVersion());
        Assert.assertEquals(result.getWorkingDirectory().toString(), workingDir.toString());
    }

    @Test
    public void hashCodeEqualWhenDataEqual() throws Exception {
        CoronaIdeApplication app1 = new CoronaIdeApplication(Paths.get("path"));
        CoronaIdeApplication app2 = new CoronaIdeApplication(Paths.get("path"));

        Assert.assertEquals(app1.hashCode(), app2.hashCode());
    }

    @Test
    public void equalsNull() throws Exception {
        CoronaIdeApplication app = new CoronaIdeApplication(Paths.get("path"));

        Assert.assertFalse(app.equals(null));
    }

    @Test
    public void equalsDifferentClass() throws Exception {
        CoronaIdeApplication app = new CoronaIdeApplication(Paths.get("path"));

        Assert.assertFalse(app.equals("string"));
    }

    @Test
    public void equalsSelf() throws Exception {
        CoronaIdeApplication app = new CoronaIdeApplication(Paths.get("path"));

        Assert.assertTrue(app.equals(app));
    }

    @Test
    public void equalsDifferentData() throws Exception {
        CoronaIdeApplication app1 = new CoronaIdeApplication(Paths.get("path1"));
        CoronaIdeApplication app2 = new CoronaIdeApplication(Paths.get("path2"));

        Assert.assertFalse(app1.equals(app2));
    }

    @Test
    public void equalsSameData() throws Exception {
        CoronaIdeApplication app1 = new CoronaIdeApplication(Paths.get("path"));
        CoronaIdeApplication app2 = new CoronaIdeApplication(Paths.get("path"));

        Assert.assertTrue(app1.equals(app2));
    }

    @Test
    public void toStringTest() throws Exception {
        CoronaIdeApplication app = new CoronaIdeApplication(Paths.get("path"));

        String result = app.toString();

        Assert.assertTrue(result.contains("version="));
        Assert.assertTrue(result.contains("workingDirectory=path"));
    }

}
