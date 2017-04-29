package com.coronaide.ui.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.coronaide.core.service.IProjectService;
import com.coronaide.core.service.IWorkspaceService;
import com.coronaide.ui.controller.MainController;

/**
 * Since we replace the JavaFX FXMLLoader's dependency master with Spring, we need to manually configure our controller
 * classes
 * 
 * @author nickavv
 * @since 0.1
 */
@Configuration
public class UIControllerConfiguration {

    @Bean
    public MainController simpleController(IWorkspaceService workspaceService, IProjectService projectService) {
        return new MainController(workspaceService, projectService);
    }

}
