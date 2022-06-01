package com.fapiko.jetbrains.plugins.better_direnv.commands;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class DirenvCmd {
    private static final String GROUP_DISPLAY_ID = "Better Direnv";
    private static final Logger LOG = Logger.getInstance(DirenvCmd.class);

    private String workDir;

    public DirenvCmd(String workingDirectory) {
        this.workDir = workingDirectory;
    }

    private boolean allow() {
        try {
            DirenvOutput output = run("allow");
            if (output.isError()) {
                LOG.error(output.getOutput());
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            LOG.error(e);
            return false;
        }
    }

    public Map<String, String> importDirenv(boolean trustDirenv) {
        Map<String, String> returnMap = new HashMap<>();

        VirtualFile envrcFile = VfsUtil.findFile(Path.of(workDir, ".envrc"), false);
        if (envrcFile == null || !envrcFile.exists()) {
            return returnMap;
        }

        try {
            DirenvOutput output = run("export", "json");
            if (output.isError()) {
                if (output.getOutput().contains("is blocked") && trustDirenv) {
                    allow();
                    return importDirenv(trustDirenv);
                } else {
                    Notifications.Bus.notify(new Notification(GROUP_DISPLAY_ID, "Direnv not allowed",
                            "Either run `direnv allow` on a terminal or check the `Trust .envrc` box in the" +
                                    "run configuration settings to use direnv integration", NotificationType.WARNING));
                    return returnMap;
                }
            }

            Type type = new TypeToken<Map<String, String>>() {
            }.getType();

            returnMap = new Gson().fromJson(output.getOutput(), type);

            return returnMap;
        } catch (Exception e) {
            LOG.error(e);
            return returnMap;
        }
    }

    private DirenvOutput run(String... args) throws ExecutionException, InterruptedException, IOException {
        String[] newArgArray = new String[1];
        newArgArray[0] = "direnv";
        newArgArray = ArrayUtils.addAll(newArgArray, args);

        GeneralCommandLine cli = new GeneralCommandLine(newArgArray).
                withWorkDirectory(workDir);
        Process process = cli.createProcess();

        if (process.waitFor() != 0) {
            String stdErr = IOUtils.toString(process.getErrorStream(), StandardCharsets.UTF_8);
            DirenvOutput output = new DirenvOutput(stdErr, true);
            return output;
        }

        String stdOut = IOUtils.toString(process.getInputStream(), StandardCharsets.UTF_8);

        return new DirenvOutput(stdOut, false);
    }
}
