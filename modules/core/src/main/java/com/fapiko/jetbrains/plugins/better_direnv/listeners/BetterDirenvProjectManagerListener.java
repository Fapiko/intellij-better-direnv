package com.fapiko.jetbrains.plugins.better_direnv.listeners;

import com.fapiko.jetbrains.plugins.better_direnv.services.BetterDirenvProjectService;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManagerListener;
import org.jetbrains.annotations.NotNull;

public class BetterDirenvProjectManagerListener implements ProjectManagerListener {
    private static final Logger LOG = Logger.getInstance(BetterDirenvProjectManagerListener.class);

    @Override
    public void projectOpened(@NotNull Project project) {
        BetterDirenvProjectService projectService = project.getService(BetterDirenvProjectService.class);
    }
}
