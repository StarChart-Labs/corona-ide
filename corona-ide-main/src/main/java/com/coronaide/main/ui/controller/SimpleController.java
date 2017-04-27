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
package com.coronaide.main.ui.controller;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import javax.inject.Inject;

import com.coronaide.core.internal.service.ICoreConfiguration;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * A controller for the simple JavaFX Scene
 * 
 * @author nickavv
 * @since 0.1.0
 */
public class SimpleController implements Initializable {

    @Inject
    private ICoreConfiguration configuration;

    @FXML
    private Label labelWorkspace;

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        Objects.requireNonNull(configuration);

        labelWorkspace.setText(configuration.getActiveWorkspaceDirectory().toString());
    }

}
