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

import com.coronaide.core.model.Project;
import com.coronaide.core.model.ProjectRequest;

/**
 * Allows interaction with projects within the current workspace
 *
 * @author romeara
 * @since 0.1
 */
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
     * Removes a project from the current workspace, without deleting the project's contents
     *
     * @param project
     *            The project to remove from Corona IDE's context
     * @since 0.1
     */
    void remove(Project project);

    /**
     * Removes a project and deletes it's contents from the file system
     *
     * <p>
     * Equivalent to calling {@link #remove(Project)} and recursively deleting the project directory
     *
     * @param project
     *            The project to remove from Corona IDE's context and delete
     * @throws IOException
     *             If there is an error performing the delete operation
     * @since 0.1
     */
    void delete(Project project) throws IOException;

}
