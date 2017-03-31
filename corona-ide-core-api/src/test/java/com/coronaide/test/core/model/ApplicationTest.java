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
package com.coronaide.test.core.model;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.coronaide.core.model.Application;

/**
 * Tests basic operations of the Corona IDE Application representation
 *
 * @author romeara
 */
public class ApplicationTest {

    @Test(expectedExceptions = NullPointerException.class)
    public void createNullPath() throws Exception {
        new Application(null);
    }

    @Test
    public void getTest() throws Exception {
        Path workingDir = Paths.get("path");
        Application result = new Application(workingDir);

        Assert.assertNotNull(result.getVersion());
        Assert.assertEquals(result.getWorkingDirectory().toString(), workingDir.toString());
    }

    @Test
    public void hashCodeEqualWhenDataEqual() throws Exception {
        Application app1 = new Application(Paths.get("path"));
        Application app2 = new Application(Paths.get("path"));

        Assert.assertEquals(app1.hashCode(), app2.hashCode());
    }

    @Test
    public void equalsNull() throws Exception {
        Application app = new Application(Paths.get("path"));

        Assert.assertFalse(app.equals(null));
    }

    @Test
    public void equalsDifferentClass() throws Exception {
        Application app = new Application(Paths.get("path"));

        Assert.assertFalse(app.equals("string"));
    }

    @Test
    public void equalsSelf() throws Exception {
        Application app = new Application(Paths.get("path"));

        Assert.assertTrue(app.equals(app));
    }

    @Test
    public void equalsDifferentData() throws Exception {
        Application app1 = new Application(Paths.get("path1"));
        Application app2 = new Application(Paths.get("path2"));

        Assert.assertFalse(app1.equals(app2));
    }

    @Test
    public void equalsSameData() throws Exception {
        Application app1 = new Application(Paths.get("path"));
        Application app2 = new Application(Paths.get("path"));

        Assert.assertTrue(app1.equals(app2));
    }

    @Test
    public void toStringTest() throws Exception {
        Application app = new Application(Paths.get("path"));

        String result = app.toString();

        Assert.assertTrue(result.contains("version="));
        Assert.assertTrue(result.contains("workingDirectory=path"));
    }

}
