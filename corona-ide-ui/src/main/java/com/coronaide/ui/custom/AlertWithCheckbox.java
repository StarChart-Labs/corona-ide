/*
 * Copyright (c) May 25, 2017 Corona IDE.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    nickavv - initial API and implementation and/or initial documentation
 */
package com.coronaide.ui.custom;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DialogPane;

/**
 * An Alert dialog window with a checkbox. Can be used for opt-outs, or similar behavior. <br>
 * <br>
 * Adapted from this <a href=
 * "https://stackoverflow.com/questions/36949595/how-do-i-create-a-javafx-alert-with-a-check-box-for-do-not-ask-again">StackOverflow
 * answer</a>
 * 
 * @author nickavv
 * @since 0.1.0
 */
public class AlertWithCheckbox extends Alert {

    private boolean checked = false;

    /**
     * Creates an Alert dialog with a checkbox
     * 
     * @param alertType
     *            What type of alert dialog to display
     * @param label
     *            A text label to display next to the checkbox
     * @param buttons
     *            A varargs array of buttons to display in the Alert
     * @since 0.1.0
     */
    public AlertWithCheckbox(AlertType alertType, String label, ButtonType... buttons) {
        super(alertType);
        // Need to force the alert to layout in order to grab the graphic, as we are replacing the dialog pane with a
        // custom pane
        getDialogPane().applyCss();
        Node graphic = getDialogPane().getGraphic();
        setDialogPane(new DialogPane() {
            @Override
            protected Node createDetailsButton() {
                CheckBox checkbox = new CheckBox();
                checkbox.setText(label);
                checkbox.setOnAction(e -> checked = checkbox.isSelected());
                return checkbox;
            }
        });
        // Fool the dialog into thinking there is expandable content. An empty Group won't take up any space
        getDialogPane().setExpandableContent(new Group());
        getDialogPane().setExpanded(true);
        // Put back the initial values
        getDialogPane().setGraphic(graphic);
        getDialogPane().getButtonTypes().addAll(buttons);
    }

    public boolean isChecked() {
        return checked;
    }

}
