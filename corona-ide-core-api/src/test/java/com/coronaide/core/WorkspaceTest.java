/*******************************************************************************
 * Copyright (c) Nov 17, 2016 Corona IDE.
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

/**
 * Tests basic operations of the Corona IDE Workspace representation
 *
 * @author romeara
 */
public class WorkspaceTest {

    @Test(expectedExceptions = NullPointerException.class)
    public void createNullPath() throws Exception {
        new Workspace(null);
    }

    @Test
    public void getTest() throws Exception {
        Path workingDir = Paths.get("path");
        Workspace workspace = new Workspace(workingDir);

        Assert.assertEquals(workspace.getWorkingDirectory().toString(), workingDir.toString());
    }

    @Test
    public void hashCodeEqualWhenDataEqual() throws Exception {
        Path workingDir = Paths.get("path");
        Workspace workspace1 = new Workspace(workingDir);
        Workspace workspace2 = new Workspace(workingDir);

        Assert.assertEquals(workspace1.hashCode(), workspace2.hashCode());
    }

    @Test
    public void equalsNull() throws Exception {
        Workspace workspace = new Workspace(Paths.get("path"));

        Assert.assertFalse(workspace.equals(null));
    }

    @Test
    public void equalsDifferentClass() throws Exception {
        Workspace workspace = new Workspace(Paths.get("path"));

        Assert.assertFalse(workspace.equals("string"));

    }

    @Test
    public void equalsSelf() throws Exception {
        Workspace workspace = new Workspace(Paths.get("path"));

        Assert.assertTrue(workspace.equals(workspace));

    }

    @Test
    public void equalsDifferentData() throws Exception {
        Workspace workspace1 = new Workspace(Paths.get("path"));
        Workspace workspace2 = new Workspace(Paths.get("path2"));

        Assert.assertFalse(workspace1.equals(workspace2));
    }

    @Test
    public void equalsSameData() throws Exception {
        Path workingDir = Paths.get("path");
        Workspace workspace1 = new Workspace(workingDir);
        Workspace workspace2 = new Workspace(workingDir);

        Assert.assertTrue(workspace1.equals(workspace2));
    }

    @Test
    public void toStringTest() throws Exception {
        Workspace workspace = new Workspace(Paths.get("path"));

        String result = workspace.toString();

        Assert.assertTrue(result.contains("workingDirectory=path"));
    }

}
