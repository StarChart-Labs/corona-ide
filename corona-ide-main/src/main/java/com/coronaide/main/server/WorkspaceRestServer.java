package com.coronaide.main.server;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coronaide.core.model.Workspace;
import com.coronaide.core.service.IWorkspaceService;

@RestController
public class WorkspaceRestServer {

	private final IWorkspaceService workspaceService;
	
	@Inject
	public WorkspaceRestServer(IWorkspaceService workspaceService) {
		this.workspaceService = workspaceService;
	}
	
	@RequestMapping("/workspace")
	public Workspace getActiveWorkspace() {
		return workspaceService.getActiveWorkspace();
	}
	
}
