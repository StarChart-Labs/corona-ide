/*******************************************************************************
 * Copyright (c) Jan 5, 2017 Corona IDE.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    romeara - initial API and implementation and/or initial documentation
 *******************************************************************************/
package com.coronaide.core.service;

import java.io.IOException;
import java.util.Collection;
import java.util.UUID;

import com.coronaide.core.model.Project;
import com.coronaide.core.model.ProjectDeleteRequest;
import com.coronaide.core.model.ProjectRequest;

/**
 * Allows interaction with projects within the current workspace
 *
 * @author romeara
 * @since 0.1
 */
@SuppressWarnings("deprecation")
public interface IProjectService {

    /**
     * Creates a new project
     *
     * @param request
     *            Description of the initial values to create the project with
     * @return The project representation created
     * @throws IOException
     *             If there is an error creating the project directory
     * @since 0.1
     */
    Project create(ProjectRequest request) throws IOException;

    /**
     * @return A collection of all the projects within the current workspace
     * @since 0.1
     */
    Collection<Project> getAll();

    /**
     * Removes a project and optionally deletes it's contents from the file system
     *
     * @param id
     *            The unique identifier of the project to delete
     * @param deleteFromDisk
     *            Whether to also delete the file contents from disk
     * @throws IOException
     *             If there is an error performing the delete operation
     * @since 0.1.0
     */
    void delete(UUID id, boolean deleteFromDisk) throws IOException;

    /**
     * Removes a project and optionally deletes it's contents from the file system
     *
     * @param request
     *            A request containing the path of the project to delete and whether to also remove its contents from
     *            disk
     * @throws IOException
     *             If there is an error performing the delete operation
     * @since 0.1.0
     * @deprecated Use {@link #delete(UUID, boolean)} instead
     */
    @Deprecated
    void delete(ProjectDeleteRequest request) throws IOException;

}
