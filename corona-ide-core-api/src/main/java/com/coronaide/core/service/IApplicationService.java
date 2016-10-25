/*******************************************************************************
 * Copyright (c) Oct 24, 2016 Corona IDE.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    romeara - initial API and implementation and/or initial documentation
 *******************************************************************************/
package com.coronaide.core.service;

import com.coronaide.core.Application;

/**
 * Allows retrieval of Corona IDE application information
 *
 * @author romeara
 * @since 0.1
 */
public interface IApplicationService {

    /**
     * @return A representation of basic data for the currently running application instance
     * @since 0.1
     */
    Application get();
}
