/*******************************************************************************
 * Copyright (c) Jan 15, 2017 Corona IDE.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    romeara - initial API and implementation and/or initial documentation
 *******************************************************************************/
package com.coronaide.core;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ProjectTest {

    private static final Path ROOT = Paths.get("root");

    private static final Path WORKING = Paths.get("working");

    @Test(expectedExceptions = NullPointerException.class)
    public void createNullName() throws Exception {
        new Project(null, ROOT, WORKING);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void createNullRootDirectory() throws Exception {
        new Project("name", null, WORKING);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void createNullWorkingDirectory() throws Exception {
        new Project("name", ROOT, null);
    }

    @Test
    public void getTest() throws Exception {
        Project result = new Project("name", ROOT, WORKING);

        Assert.assertEquals(result.getName(), "name");
        Assert.assertEquals(result.getRootDirectory(), ROOT);
        Assert.assertEquals(result.getWorkingDirectory(), WORKING);
    }

    @Test
    public void hashCodeEqualWhenDataEqual() throws Exception {
        Project result1 = new Project("name", ROOT, WORKING);
        Project result2 = new Project("name", ROOT, WORKING);

        Assert.assertEquals(result1.hashCode(), result2.hashCode());
    }

    @Test
    public void equalsNull() throws Exception {
        Project result = new Project("name", ROOT, WORKING);

        Assert.assertFalse(result.equals(null));
    }

    @Test
    public void equalsDifferentClass() throws Exception {
        Project result = new Project("name", ROOT, WORKING);

        Assert.assertFalse(result.equals("string"));
    }

    @Test
    public void equalsSelf() throws Exception {
        Project result = new Project("name", ROOT, WORKING);

        Assert.assertTrue(result.equals(result));
    }

    @Test
    public void equalsDifferentData() throws Exception {
        Project result1 = new Project("name", ROOT, WORKING);
        Project result2 = new Project("name2", ROOT, WORKING);

        Assert.assertFalse(result1.equals(result2));
    }

    @Test
    public void equalsSameData() throws Exception {
        Project result1 = new Project("name", ROOT, WORKING);
        Project result2 = new Project("name", ROOT, WORKING);

        Assert.assertTrue(result1.equals(result2));
    }

    @Test
    public void toStringTest() throws Exception {
        Project obj = new Project("name", ROOT, WORKING);

        String result = obj.toString();

        Assert.assertNotNull(result);
        Assert.assertTrue(result.contains("name=name"));
        Assert.assertTrue(result.contains("rootDirectory=" + ROOT.toString()));
        Assert.assertTrue(result.contains("workingDirectory=" + WORKING.toString()));
    }

}
