package com.fapiko.jetbrains.plugins.better_direnv.commands;

import com.fapiko.jetbrains.plugins.better_direnv.listeners.BetterDirenvProjectManagerListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.openapi.diagnostic.Logger;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class DirenvCmd {
    private static final Logger LOG = Logger.getInstance(BetterDirenvProjectManagerListener.class);

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
                LOG.info("direnv allowed on this project");
                return true;
            }
        } catch (Exception e) {
            LOG.error(e);
            return false;
        }
    }

    public Map<String, String> importDirenv(boolean trustDirenv) {
        Map<String, String> returnMap = new HashMap<>();

        try {
            DirenvOutput output = run("export", "json");
            if (output.isError()) {
                if (output.getOutput().contains("is blocked") && trustDirenv) {
                    allow();
                    return importDirenv(trustDirenv);
                } else {
                    LOG.error("Direnv is blocked and untrusted");
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
