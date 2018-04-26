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
package com.coronaide.test.core.model;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.coronaide.core.model.ProjectLocation;

public class ProjectLocationTest {

    @Test(expectedExceptions = NullPointerException.class)
    public void createNullRootDirectory() throws Exception {
        new ProjectLocation(null);
    }

    @Test
    public void getTest() throws Exception {
        ProjectLocation result = new ProjectLocation("root");

        Assert.assertEquals(result.getRootDirectory(), "root");
    }

    @Test
    public void hashCodeEqualWhenDataEqual() throws Exception {
        ProjectLocation result1 = new ProjectLocation("root");
        ProjectLocation result2 = new ProjectLocation("root");

        Assert.assertEquals(result1.hashCode(), result2.hashCode());
    }

    @Test
    public void equalsNull() throws Exception {
        ProjectLocation result = new ProjectLocation("root");

        Assert.assertFalse(result.equals(null));
    }

    @Test
    public void equalsDifferentClass() throws Exception {
        ProjectLocation result = new ProjectLocation("root");

        Assert.assertFalse(result.equals("string"));
    }

    @Test
    public void equalsSelf() throws Exception {
        ProjectLocation result = new ProjectLocation("root");

        Assert.assertTrue(result.equals(result));
    }

    @Test
    public void equalsDifferentData() throws Exception {
        ProjectLocation result1 = new ProjectLocation("root");
        ProjectLocation result2 = new ProjectLocation("root2");

        Assert.assertFalse(result1.equals(result2));
    }

    @Test
    public void equalsSameData() throws Exception {
        ProjectLocation result1 = new ProjectLocation("root");
        ProjectLocation result2 = new ProjectLocation("root");

        Assert.assertTrue(result1.equals(result2));
    }

    @Test
    public void toStringTest() throws Exception {
        ProjectLocation obj = new ProjectLocation("root");

        String result = obj.toString();

        Assert.assertNotNull(result);
        Assert.assertTrue(result.contains("rootDirectory=root"));
    }

}
