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

import java.util.Collections;
import java.util.Set;
import java.util.UUID;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.coronaide.core.model.ProjectLocation;
import com.coronaide.core.model.WorkspaceMetaData;

public class WorkspaceMetaDataTest {

    private static final Set<ProjectLocation> LOCATIONS = Collections
            .singleton(new ProjectLocation(UUID.randomUUID(), "root"));

    @Test(expectedExceptions = NullPointerException.class)
    public void createNullLocations() throws Exception {
        new WorkspaceMetaData(null);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void createNullLocationInSet() throws Exception {
        new WorkspaceMetaData(Collections.singleton((ProjectLocation) null));
    }

    @Test
    public void getTest() throws Exception {
        WorkspaceMetaData result = new WorkspaceMetaData(LOCATIONS);

        Assert.assertEquals(result.getProjectLocations(), LOCATIONS);
    }

    @Test
    public void hashCodeEqualWhenDataEqual() throws Exception {
        WorkspaceMetaData result1 = new WorkspaceMetaData(LOCATIONS);
        WorkspaceMetaData result2 = new WorkspaceMetaData(LOCATIONS);

        Assert.assertEquals(result1.hashCode(), result2.hashCode());
    }

    @Test
    public void equalsNull() throws Exception {
        WorkspaceMetaData result = new WorkspaceMetaData(LOCATIONS);

        Assert.assertFalse(result.equals(null));
    }

    @Test
    public void equalsDifferentClass() throws Exception {
        WorkspaceMetaData result = new WorkspaceMetaData(LOCATIONS);

        Assert.assertFalse(result.equals("string"));
    }

    @Test
    public void equalsSelf() throws Exception {
        WorkspaceMetaData result = new WorkspaceMetaData(LOCATIONS);

        Assert.assertTrue(result.equals(result));
    }

    @Test
    public void equalsDifferentData() throws Exception {
        Set<ProjectLocation> locations2 = Collections.singleton(new ProjectLocation(UUID.randomUUID(), "root2"));
        WorkspaceMetaData result1 = new WorkspaceMetaData(LOCATIONS);
        WorkspaceMetaData result2 = new WorkspaceMetaData(locations2);

        Assert.assertFalse(result1.equals(result2));
    }

    @Test
    public void equalsSameData() throws Exception {
        WorkspaceMetaData result1 = new WorkspaceMetaData(LOCATIONS);
        WorkspaceMetaData result2 = new WorkspaceMetaData(LOCATIONS);

        Assert.assertTrue(result1.equals(result2));
    }

    @Test
    public void toStringTest() throws Exception {
        WorkspaceMetaData obj = new WorkspaceMetaData(LOCATIONS);

        String result = obj.toString();

        Assert.assertNotNull(result);
        Assert.assertTrue(result.contains("projectLocations=" + LOCATIONS.toString()));
    }

}
