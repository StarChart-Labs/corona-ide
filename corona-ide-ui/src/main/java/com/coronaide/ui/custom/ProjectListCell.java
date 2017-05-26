package com.coronaide.ui.custom;

import java.io.IOException;
import java.util.Objects;

import com.coronaide.core.model.Project;
import com.coronaide.core.service.IProjectService;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListCell;
import javafx.scene.control.MenuItem;

public class ProjectListCell extends ListCell<Project> {

    private final IProjectService projectService;

    private ObservableList<Project> containingList;

    private Project item;

    public ProjectListCell(IProjectService projectService, ObservableList<Project> containingList) {
        this.projectService = Objects.requireNonNull(projectService);
        this.containingList = Objects.requireNonNull(containingList);
    }

    @Override
    protected void updateItem(Project item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            this.item = Objects.requireNonNull(item);
            setText(item.getName());

            ContextMenu contextMenu = new ContextMenu();
            MenuItem menuDelete = new MenuItem();
            menuDelete.setText("Delete");
            menuDelete.setOnAction(this::deleteMenuAction);
            contextMenu.getItems().addAll(menuDelete);
            setContextMenu(contextMenu);
        }
    }

    private EventHandler<ActionEvent> deleteMenuAction(ActionEvent event) {
        AlertWithCheckbox alert = new AlertWithCheckbox(AlertType.CONFIRMATION,
                "Also delete files from disk (cannot be undone)", ButtonType.YES);
        alert.setTitle("Delete Project");
        alert.setContentText("Do you want to remove " + item.getName() + " from your workspace?");

        alert.showAndWait()
                .filter(r -> r == ButtonType.YES)
                .ifPresent(r -> {
                    try {
                        if (alert.isChecked()) {
                            projectService.delete(item);
                        } else {
                            projectService.remove(item);
                        }
                        containingList.remove(item);
                    } catch (IOException e) {
                        Alert errorAlert = new Alert(AlertType.ERROR);
                        errorAlert.setTitle("Delete project failed");
                        errorAlert.setHeaderText("Failed to delete project.");
                        errorAlert.showAndWait();
                        // TODO nickavv: create custom "stack trace dialog" to show the actual error
                    }
                });

        return null;
    }
}
