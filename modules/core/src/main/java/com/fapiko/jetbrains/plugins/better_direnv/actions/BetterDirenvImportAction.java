package com.fapiko.jetbrains.plugins.better_direnv.actions;

import com.fapiko.jetbrains.plugins.better_direnv.services.BetterDirenvProjectService;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public class BetterDirenvImportAction extends AnAction {
    @Override
    public void update(@NotNull AnActionEvent e) {
        if (e.getProject() == null) {
            e.getPresentation().setEnabledAndVisible(false);
        }
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = e.getProject();
        if (project == null) {
            return;
        }

        BetterDirenvProjectService service = project.getService(BetterDirenvProjectService.class);
//        service.importDirenv();
    }
}
