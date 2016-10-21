/*******************************************************************************
 * Copyright (c) Oct 18, 2016 Corona IDE.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    romeara - initial API and implementation and/or initial documentation
 *******************************************************************************/
package com.coronaide.core.services.impl;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.coronaide.core.Datastore;
import com.coronaide.core.Module;
import com.coronaide.core.Version;
import com.coronaide.core.internal.services.ICoreConfiguration;
import com.coronaide.core.service.IDatastoreService;

/**
 * Test basic error behavior of the datastore service
 *
 * @author romeara
 */
public class DatastoreServiceErrorTest {

    @Mock
    private ICoreConfiguration coreConfiguration;

    @Mock
    private Module module;

    @Mock
    private Datastore<String> datastore;

    private IDatastoreService datastoreService;

    @BeforeMethod
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(module.getId()).thenReturn("com.coronaide.test");
        Mockito.when(module.getVersion()).thenReturn(new Version(1, 0, 0));

        datastoreService = new DatastoreService(coreConfiguration);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void storeApplicationDataNullModule() throws Exception {
        datastoreService.storeApplicationData(null, datastore, "data");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void storeApplicationDataNullDatastore() throws Exception {
        datastoreService.storeApplicationData(module, null, "data");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void storeApplicationDataNullData() throws Exception {
        datastoreService.storeApplicationData(module, datastore, null);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void loadApplicationDataNullModule() throws Exception {
        datastoreService.loadApplicationData(null, datastore);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void loadApplicationDataNullDatastore() throws Exception {
        datastoreService.loadApplicationData(module, null);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void clearApplicationDataNullModule() throws Exception {
        datastoreService.clearApplicationData(null);
    }

}
