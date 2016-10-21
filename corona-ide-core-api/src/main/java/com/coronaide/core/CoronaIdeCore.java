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
package com.coronaide.core;

import javax.annotation.concurrent.Immutable;

/**
 * Representation of the Corona IDE Core, which can be used to interact with APIs
 *
 * @author romeara
 * @since 0.1
 */
@Immutable
public final class CoronaIdeCore implements Module {

    private static String ID = "com.coronaide.core";

    private static Version VERSION = new Version(0, 1, 0);

    private static Module MODULE = new CoronaIdeCore();

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public Version getVersion() {
        return VERSION;
    }

    /**
     * @return Module representation of the core IDE system
     * @since 0.1
     */
    public static Module getModule() {
        return MODULE;
    }

}
