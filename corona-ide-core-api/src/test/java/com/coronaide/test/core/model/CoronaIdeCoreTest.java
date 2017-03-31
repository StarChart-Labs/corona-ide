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

import com.coronaide.core.model.CoronaIdeCore;
import com.coronaide.core.model.Module;
import com.coronaide.core.model.Version;

/**
 * Test basic behavior of the {@link Version} class
 *
 * @author romeara
 */
public class CoronaIdeCoreTest {

    @Test
    public void getModule() throws Exception {
        Module module = CoronaIdeCore.getModule();

        Assert.assertNotNull(module);
        Assert.assertNotNull(module.getId());
        Assert.assertNotNull(module.getVersion());
    }

}
