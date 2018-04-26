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
package com.coronaide.core.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.coronaide.core.datastore.Datastore;
import com.coronaide.core.internal.datastore.util.Datastores;
import com.coronaide.core.model.CoreDatastores;
import com.coronaide.core.model.CoronaIdeCore;
import com.coronaide.core.model.Module;
import com.coronaide.core.model.Project;
import com.coronaide.core.model.ProjectLocation;
import com.coronaide.core.model.ProjectRequest;
import com.coronaide.core.model.Workspace;
import com.coronaide.core.model.WorkspaceMetaData;
import com.coronaide.core.service.IDatastoreService;
import com.coronaide.core.service.IProjectService;
import com.coronaide.core.service.IWorkspaceService;

/**
 * Implementation of {@link IProjectService}. Not intended for direct use by clients - use dependency injection to
 * obtain an instance of {@link IProjectService} instead
 *
 * @author romeara
 * @since 0.1.0
 */
public class ProjectService implements IProjectService {

    private final IWorkspaceService workspaceService;

    private final IDatastoreService datastoreService;

    /**
     * @param workspaceService
     *            API which allows access to the workspace data
     * @param datastoreService
     *            API which handles loading and storing data from persistent storage
     * @since 0.1.0
     */
    public ProjectService(IWorkspaceService workspaceService, IDatastoreService datastoreService) {
        this.workspaceService = Objects.requireNonNull(workspaceService);
        this.datastoreService = Objects.requireNonNull(datastoreService);
    }

    @Override
    public Project create(ProjectRequest request) throws IOException {
        Objects.requireNonNull(request);

        Workspace workspace = workspaceService.getActiveWorkspace();
        Module module = CoronaIdeCore.getModule();
        Datastore<WorkspaceMetaData> datastore = CoreDatastores.getWorkspaceDatastore();

        Optional<WorkspaceMetaData> workspaceData = datastoreService.load(workspace, module, datastore);

        if (isProjectPresent(workspaceData, request.getRootDirectory())) {
            throw new IllegalArgumentException(
                    "Project already exists at location '" + request.getRootDirectory().toString() + "'");
        }

        // Create the project's directory
        File projectRoot = request.getRootDirectory().toFile();

        if (!projectRoot.exists() && !projectRoot.mkdirs()) {
            throw new IOException("Error creating project directory");
        }

        ProjectLocation projectLocation = new ProjectLocation(request.getRootDirectory().toString());
        Set<ProjectLocation> locations = workspaceData.map(WorkspaceMetaData::getProjectLocations)
                .orElse(new HashSet<>());
        locations.add(projectLocation);

        datastoreService.store(workspace, module, datastore, new WorkspaceMetaData(locations));

        return toProject(projectLocation);
    }

    @Override
    public Collection<Project> getAll() {
        Workspace workspace = workspaceService.getActiveWorkspace();
        Module module = CoronaIdeCore.getModule();
        Datastore<WorkspaceMetaData> datastore = CoreDatastores.getWorkspaceDatastore();

        Optional<WorkspaceMetaData> workspaceData = datastoreService.load(workspace, module, datastore);

        return workspaceData.map(WorkspaceMetaData::getProjectLocations)
                .orElse(Collections.emptySet()).stream()
                .map(this::toProject)
                .collect(Collectors.toList());
    }

    @Override
    public void remove(Project project) {
        Objects.requireNonNull(project);

        Workspace workspace = workspaceService.getActiveWorkspace();

        removeFromWorkspace(workspace, project);
    }

    @Override
    public void delete(Project project) throws IOException {
        Objects.requireNonNull(project);

        Workspace workspace = workspaceService.getActiveWorkspace();

        removeFromWorkspace(workspace, project);

        // Delete the directory containing the project
        Files.walk(project.getRootDirectory())
        .sorted(Comparator.reverseOrder())
        .map(Path::toFile)
        .peek(System.out::println)
        .forEach(File::delete);
    }

    /**
     * @param workspace
     *            A workspace to remove a project record from
     * @param project
     *            The project to remove
     */
    private void removeFromWorkspace(Workspace workspace, Project project) {
        Objects.requireNonNull(workspace);
        Objects.requireNonNull(project);

        Module module = CoronaIdeCore.getModule();
        Datastore<WorkspaceMetaData> datastore = CoreDatastores.getWorkspaceDatastore();

        Optional<WorkspaceMetaData> workspaceData = datastoreService.load(workspace, module, datastore);

        // If it doesn't exist, throw an error
        if (!isProjectPresent(workspaceData, project.getRootDirectory())) {
            throw new IllegalArgumentException(
                    "Project does not exist at location '" + project.getRootDirectory().toString() + "'");
        }

        Set<ProjectLocation> updatedLocations = workspaceData.map(WorkspaceMetaData::getProjectLocations)
                .orElse(Collections.emptySet()).stream()
                .filter(input -> !Objects.equals(input.getRootDirectory(), project.getRootDirectory().toString()))
                .collect(Collectors.toSet());

        datastoreService.store(workspace, module, datastore, new WorkspaceMetaData(updatedLocations));
    }

    /**
     * Determines if a project exists in the workspace data
     *
     * @param workspaceData
     *            Loaded workspace meta data
     * @param rootDirectory
     *            The root directory of the project to look for
     * @return True if the project is in the workspace, false otherwise
     */
    private boolean isProjectPresent(Optional<WorkspaceMetaData> workspaceData, Path rootDirectory) {
        Objects.requireNonNull(workspaceData);
        Objects.requireNonNull(rootDirectory);

        return workspaceData.map(WorkspaceMetaData::getProjectLocations)
                .orElse(Collections.emptySet()).stream()
                .filter(input -> Objects.equals(input.getRootDirectory(), rootDirectory.toString()))
                .findAny()
                .isPresent();
    }

    /**
     * @param projectLocation
     *            Root location of the project
     * @return A project representation
     */
    private Project toProject(ProjectLocation projectLocation) {
        Objects.requireNonNull(projectLocation);

        Path location = Paths.get(projectLocation.getRootDirectory());

        String name = location.getFileName().toString();
        Path workingDirectory = Datastores.getMetaDataDirectory(location);

        return new Project(name, location, workingDirectory);
    }

}
