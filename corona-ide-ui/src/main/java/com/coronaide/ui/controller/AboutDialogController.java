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
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

/**
 * A controller for the Help &gt; About dialog
 * 
 * @author nickavv
 * @since 0.1.0
 */
public class AboutDialogController implements Initializable {

    private Stage dialogStage;

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        // nothing to do
    }

    public void setDialogStage(Stage stage) {
        dialogStage = stage;
    }

    @FXML
    private void close(ActionEvent event) {
        dialogStage.close();
    }

}
