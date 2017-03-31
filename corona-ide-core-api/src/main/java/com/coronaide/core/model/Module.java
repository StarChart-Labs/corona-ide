/*******************************************************************************
 * Copyright (c) Oct 16, 2016 Corona IDE.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    romeara - initial API and implementation and/or initial documentation
 *******************************************************************************/
package com.coronaide.core.model;

/**
 * Represents a collection of related additions or enhancements to the Corona IDE platform
 *
 * @author romeara
 * @since 0.1
 */
public interface Module {

    /**
     * @return ID of the module - usually matches the base package name for source of the module
     * @since 0.1
     */
    String getId();

    /**
     * @return The current module implementation version
     * @since 0.1
     */
    Version getVersion();
}