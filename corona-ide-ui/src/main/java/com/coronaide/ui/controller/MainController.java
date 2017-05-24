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

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javax.inject.Inject;

import com.coronaide.core.model.Project;
import com.coronaide.core.model.ProjectRequest;
import com.coronaide.core.model.Workspace;
import com.coronaide.core.service.IProjectService;
import com.coronaide.core.service.IWorkspaceService;
import com.coronaide.ui.CoronaUIApplication;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
    private ListView<String> listViewProjects;

    @Inject
    public MainController(IWorkspaceService workspaceService, IProjectService projectService) {
        this.workspaceService = Objects.requireNonNull(workspaceService);
        this.projectService = Objects.requireNonNull(projectService);
    }

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        prepareWorkspace();
        showProjectList();
    }

    private void prepareWorkspace() {
        Workspace workspace = workspaceService.getActiveWorkspace();
        Path workingDir = workspace.getWorkingDirectory();

        CoronaUIApplication.getPrimaryStage()
                .setTitle(workingDir.getName(workingDir.getNameCount() - 2).toString() + " - Corona");
    }

    private void showProjectList() {
        List<String> projectNamesList = projectService.getAll().stream().map(Project::getName)
                .collect(Collectors.toList());
        ObservableList<String> observableProjectNamesList = FXCollections.observableList(projectNamesList);
        listViewProjects.setItems(observableProjectNamesList);
    }

    @FXML
    private void handleFileNewProject(ActionEvent event) {
        TextInputDialog newProjectDialog = new TextInputDialog();
        newProjectDialog.setTitle("Create Project");
        newProjectDialog.setHeaderText("Create new project");
        newProjectDialog.setContentText("Project name:");

        newProjectDialog.showAndWait()
                .ifPresent(r -> {
                    Path projectPath = workspaceService.getActiveWorkspace().getWorkingDirectory().resolve(r);
                    try {
                        projectService.create(new ProjectRequest(projectPath));
                        showProjectList();
                    } catch (IOException e) {
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Create project failed");
                        alert.setHeaderText("Failed to create new project.");
                        alert.showAndWait();
                        // TODO nickavv: create custom "stack trace dialog" to show the actual error
                    }
                });
    }

    @FXML
    private void handleFileQuit(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Quit Corona");
        alert.setContentText("Are you sure you want to quit?");

        alert.showAndWait()
                .filter(r -> r == ButtonType.OK)
                .ifPresent(r -> System.exit(0));
    }

    @FXML
    private void handleAboutHelp(ActionEvent event) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(CoronaUIApplication.class.getResource("/fxml/AboutDialog.fxml"));
            DialogPane pane = (DialogPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("About Corona IDE");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.setResizable(false);
            dialogStage.initOwner(CoronaUIApplication.getPrimaryStage());
            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);
            dialogStage.sizeToScene();

            // Set the person into the controller.
            AboutDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
