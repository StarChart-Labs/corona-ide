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
package com.coronaide.test.core.model;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.coronaide.core.model.Project;

public class ProjectTest {

    private static final UUID ID = UUID.randomUUID();

    private static final Path ROOT = Paths.get("root");

    private static final Path WORKING = Paths.get("working");

    @Test(expectedExceptions = NullPointerException.class)
    public void createNullId() throws Exception {
        new Project(null, "name", ROOT, WORKING);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void createNullName() throws Exception {
        new Project(ID, null, ROOT, WORKING);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void createNullRootDirectory() throws Exception {
        new Project(ID, "name", null, WORKING);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void createNullWorkingDirectory() throws Exception {
        new Project(ID, "name", ROOT, null);
    }

    @Test
    public void getTest() throws Exception {
        Project result = new Project(ID, "name", ROOT, WORKING);

        Assert.assertEquals(result.getId(), ID);
        Assert.assertEquals(result.getName(), "name");
        Assert.assertEquals(result.getRootDirectory(), ROOT);
        Assert.assertEquals(result.getWorkingDirectory(), WORKING);
    }

    @Test
    public void hashCodeEqualWhenDataEqual() throws Exception {
        Project result1 = new Project(ID, "name", ROOT, WORKING);
        Project result2 = new Project(ID, "name", ROOT, WORKING);

        Assert.assertEquals(result1.hashCode(), result2.hashCode());
    }

    @Test
    public void equalsNull() throws Exception {
        Project result = new Project(ID, "name", ROOT, WORKING);

        Assert.assertFalse(result.equals(null));
    }

    @Test
    public void equalsDifferentClass() throws Exception {
        Project result = new Project(ID, "name", ROOT, WORKING);

        Assert.assertFalse(result.equals("string"));
    }

    @Test
    public void equalsSelf() throws Exception {
        Project result = new Project(ID, "name", ROOT, WORKING);

        Assert.assertTrue(result.equals(result));
    }

    @Test
    public void equalsDifferentData() throws Exception {
        Project result1 = new Project(ID, "name", ROOT, WORKING);
        Project result2 = new Project(ID, "name2", ROOT, WORKING);

        Assert.assertFalse(result1.equals(result2));
    }

    @Test
    public void equalsSameData() throws Exception {
        Project result1 = new Project(ID, "name", ROOT, WORKING);
        Project result2 = new Project(ID, "name", ROOT, WORKING);

        Assert.assertTrue(result1.equals(result2));
    }

    @Test
    public void toStringTest() throws Exception {
        Project obj = new Project(ID, "name", ROOT, WORKING);

        String result = obj.toString();

        Assert.assertNotNull(result);
        Assert.assertTrue(result.contains("id=" + ID.toString()));
        Assert.assertTrue(result.contains("name=name"));
        Assert.assertTrue(result.contains("rootDirectory=" + ROOT.toString()));
        Assert.assertTrue(result.contains("workingDirectory=" + WORKING.toString()));
    }

}
