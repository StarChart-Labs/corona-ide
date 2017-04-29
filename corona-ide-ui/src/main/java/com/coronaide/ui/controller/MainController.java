/*
 * Copyright (c) Apr 26, 2017 Corona IDE.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    nickavv - initial API and implementation and/or initial documentation
 */
package com.coronaide.ui.controller;

import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javax.inject.Inject;

import com.coronaide.core.model.Project;
import com.coronaide.core.model.Workspace;
import com.coronaide.core.service.IProjectService;
import com.coronaide.core.service.IWorkspaceService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

/**
 * A controller for the simple JavaFX Scene
 * 
 * @author nickavv
 * @since 0.1.0
 */
public class MainController implements Initializable {

    private IWorkspaceService workspaceService;

    private IProjectService projectService;

    @FXML
    private Label labelWorkspace;

    @FXML
    private ListView<String> listViewProjects;

    @Inject
    public MainController(IWorkspaceService workspaceService, IProjectService projectService) {
        this.workspaceService = Objects.requireNonNull(workspaceService);
        this.projectService = Objects.requireNonNull(projectService);
    }

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        Objects.requireNonNull(workspaceService);

        Workspace workspace = workspaceService.getActiveWorkspace();
        Path workingDir = workspace.getWorkingDirectory();

        labelWorkspace
                .setText(workingDir.getName(workingDir.getNameCount() - 1).toString());
        List<String> projectNamesList = projectService.getAll().stream().map(Project::getName).collect(Collectors.toList());
        ObservableList<String> observableProjectNamesList = FXCollections.observableList(projectNamesList);
        listViewProjects.setItems(observableProjectNamesList);
    }

}
