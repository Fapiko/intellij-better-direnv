package com.fapiko.jetbrains.plugins.better_direnv.services;

import com.fapiko.jetbrains.plugins.better_direnv.listeners.BetterDirenvProjectManagerListener;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;

public class BetterDirenvProjectService {

    private static final Logger LOG = Logger.getInstance(BetterDirenvProjectManagerListener.class);

    private Project project;

    public BetterDirenvProjectService(Project project) {
        this.project = project;

        LOG.info(project.getName());
//        importDirenv();
    }
}
