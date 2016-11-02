/*******************************************************************************
 * Copyright (c) Nov 1, 2016 Corona IDE.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    romeara - initial API and implementation and/or initial documentation
 *******************************************************************************/
package com.coronaide.core.exception;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test basic behavior of the {@link DataStorageException} class
 *
 * @author romeara
 */
public class DataStorageExceptionTest {

    @Test
    public void storedDataTest() throws Exception {
        Exception cause = new IOException();
        String message = "message";

        DataStorageException result = new DataStorageException(message, cause);

        Assert.assertEquals(result.getMessage(), message);
        Assert.assertEquals(result.getCause(), cause);
    }

}
