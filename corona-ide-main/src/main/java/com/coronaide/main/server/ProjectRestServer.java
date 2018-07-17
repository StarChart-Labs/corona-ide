/*
 * Copyright (c) Jul 14, 2018 StarChart Labs Authors.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    nickv - initial API and implementation and/or initial documentation
 */
package com.coronaide.main.server;

import java.io.IOException;
import java.util.Collection;
import java.util.UUID;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.coronaide.core.model.Project;
import com.coronaide.core.model.ProjectDeleteRequest;
import com.coronaide.core.model.ProjectRequest;
import com.coronaide.core.service.IProjectService;

@RestController
public class ProjectRestServer {

    private final IProjectService projectService;

    @Inject
    public ProjectRestServer(IProjectService projectService) {
        this.projectService = projectService;
    }

    @RequestMapping(value = "/projects", method = RequestMethod.GET)
    public Collection<Project> getAllProjects() {
        return projectService.getAll();
    }

    @RequestMapping(value = "/projects", method = RequestMethod.POST)
    public Project createProject(@RequestBody ProjectRequest request) throws IOException {
        return projectService.create(request);
    }

    /**
     * @deprecated Use {@link #deleteProject(UUID, Boolean)} instead
     */
    @Deprecated
    @RequestMapping(value = "/projects", method = RequestMethod.DELETE)
    public void deleteProjectRequest(@RequestBody ProjectDeleteRequest request) throws IOException {
        projectService.delete(request);
    }

    @RequestMapping(value = "/projects/{projectId}", method = RequestMethod.DELETE)
    public void deleteProject(@PathVariable("projectId") UUID projectId,
            @RequestParam(value = "deleteFromDisk", defaultValue = "false") Boolean deleteFromDisk) throws IOException {
        projectService.delete(projectId, deleteFromDisk);
    }

}
