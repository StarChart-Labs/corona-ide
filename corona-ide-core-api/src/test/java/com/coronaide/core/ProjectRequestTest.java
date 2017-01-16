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

public class ProjectRequestTest {

    private static final Path ROOT = Paths.get("root");

    @Test(expectedExceptions = NullPointerException.class)
    public void createNullName() throws Exception {
        new ProjectRequest(null, ROOT);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void createNullRootDirectory() throws Exception {
        new ProjectRequest("name", null);
    }

    @Test
    public void getTest() throws Exception {
        ProjectRequest result = new ProjectRequest("name", ROOT);

        Assert.assertEquals(result.getName(), "name");
        Assert.assertEquals(result.getRootDirectory(), ROOT);
    }

    @Test
    public void hashCodeEqualWhenDataEqual() throws Exception {
        ProjectRequest result1 = new ProjectRequest("name", ROOT);
        ProjectRequest result2 = new ProjectRequest("name", ROOT);

        Assert.assertEquals(result1.hashCode(), result2.hashCode());
    }

    @Test
    public void equalsNull() throws Exception {
        ProjectRequest result = new ProjectRequest("name", ROOT);

        Assert.assertFalse(result.equals(null));
    }

    @Test
    public void equalsDifferentClass() throws Exception {
        ProjectRequest result = new ProjectRequest("name", ROOT);

        Assert.assertFalse(result.equals("string"));
    }

    @Test
    public void equalsSelf() throws Exception {
        ProjectRequest result = new ProjectRequest("name", ROOT);

        Assert.assertTrue(result.equals(result));
    }

    @Test
    public void equalsDifferentData() throws Exception {
        ProjectRequest result1 = new ProjectRequest("name", ROOT);
        ProjectRequest result2 = new ProjectRequest("name2", ROOT);

        Assert.assertFalse(result1.equals(result2));
    }

    @Test
    public void equalsSameData() throws Exception {
        ProjectRequest result1 = new ProjectRequest("name", ROOT);
        ProjectRequest result2 = new ProjectRequest("name", ROOT);

        Assert.assertTrue(result1.equals(result2));
    }

    @Test
    public void toStringTest() throws Exception {
        ProjectRequest obj = new ProjectRequest("name", ROOT);

        String result = obj.toString();

        Assert.assertNotNull(result);
        Assert.assertTrue(result.contains("name=name"));
        Assert.assertTrue(result.contains("rootDirectory=" + ROOT.toString()));
    }

}
