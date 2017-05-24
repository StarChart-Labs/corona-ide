/*
 * Copyright (c) Apr 1, 2017 Corona IDE.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    romeara - initial API and implementation and/or initial documentation
 */
package com.coronaide.main;

import java.nio.file.Paths;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.coronaide.core.internal.service.ICoreConfiguration;
import com.coronaide.main.config.CoronaIdeMainConfiguration;
import com.coronaide.ui.CoronaUIApplication;

import javafx.application.Application;

/**
 * Entry point for the IDE, started as per standard Java application patterns
 *
 * @author romeara
 * @since 0.1
 */
public class Main {

    /** Logger reference to output information to the application log files */
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    @Option(name = "--applicationDirectory", usage = "Directory the application is running from", required = true)
    private String applicationDirectory;

    @Option(name = "--workspaceDirectory", usage = "Working directory being used as a context", required = true)
    private String workspaceDirectory;

    public static void main(String[] args) {
        new Main().start(args);
    }

    /**
     * Starts the application, running until it is closed
     *
     * @param args
     *            Arguments used to control application behavior
     */
    public void start(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);

        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println();
            System.err.println(e.getMessage());
            parser.printUsage(System.err);
            return;
        }

        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                CoronaIdeMainConfiguration.class)) {
            // Lookup the core configuration and initialize - this is the ONLY place the setters should be called
            ICoreConfiguration coreConfiguration = context.getBean(ICoreConfiguration.class);
            coreConfiguration.setLocations(Paths.get(applicationDirectory), Paths.get(workspaceDirectory));

            logger.info("Running in workspace {} from {}", workspaceDirectory, applicationDirectory);
            CoronaUIApplication.setSpringContext(context);
            Application.launch(CoronaUIApplication.class);
        }
    }

}
