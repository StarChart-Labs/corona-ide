/*******************************************************************************
 * Copyright (c) Oct 17, 2016 Corona IDE.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    romeara - initial API and implementation and/or initial documentation
 *******************************************************************************/
package com.coronaide.test.core.model;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.coronaide.core.model.Version;

/**
 * Test basic behavior of the {@link Version} class
 *
 * @author romeara
 */
public class VersionTest {

    @Test
    public void getTest() throws Exception {
        Version actual = new Version(1, 2, 3);

        Assert.assertEquals(actual.getMajor(), 1);
        Assert.assertEquals(actual.getMinor(), 2);
        Assert.assertEquals(actual.getMicro(), 3);
    }

    @Test
    public void hashCodeEqualWhenDataEqual() throws Exception {
        Version version1 = new Version(1, 2, 3);
        Version version2 = new Version(1, 2, 3);

        Assert.assertEquals(version1.hashCode(), version2.hashCode());
    }

    @Test
    public void equalsNull() throws Exception {
        Version version1 = new Version(1, 2, 3);

        Assert.assertFalse(version1.equals(null));
    }

    @Test
    public void equalsDifferentClass() throws Exception {
        Version version1 = new Version(1, 2, 3);

        Assert.assertFalse(version1.equals("string"));
    }

    @Test
    public void equalsSelf() throws Exception {
        Version version1 = new Version(1, 2, 3);

        Assert.assertTrue(version1.equals(version1));
    }

    @Test
    public void equalsDifferentMajor() throws Exception {
        Version version1 = new Version(1, 2, 3);
        Version version2 = new Version(4, 2, 3);

        Assert.assertFalse(version1.equals(version2));
    }

    @Test
    public void equalsDifferentMinor() throws Exception {
        Version version1 = new Version(1, 2, 3);
        Version version2 = new Version(1, 4, 3);

        Assert.assertFalse(version1.equals(version2));
    }

    @Test
    public void equalsDifferentMicro() throws Exception {
        Version version1 = new Version(1, 2, 3);
        Version version2 = new Version(1, 2, 4);

        Assert.assertFalse(version1.equals(version2));
    }

    @Test
    public void equalsSameData() throws Exception {
        Version version1 = new Version(1, 2, 3);
        Version version2 = new Version(1, 2, 3);

        Assert.assertTrue(version1.equals(version2));
    }

    @Test
    public void toStringTest() throws Exception {
        Version version = new Version(1, 2, 3);

        Assert.assertEquals(version.toString(), "1.2.3");
    }

}
