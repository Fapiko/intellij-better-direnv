package com.fapiko.jetbrains.plugins.better_direnv.runconfigs;

import com.fapiko.jetbrains.plugins.better_direnv.settings.DirenvSettings;
import com.fapiko.jetbrains.plugins.better_direnv.settings.ui.RunConfigSettingsEditor;
import com.intellij.execution.ExecutionException;
import com.intellij.execution.Executor;
import com.intellij.execution.Location;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.configurations.RunnerSettings;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.ui.FragmentedSettings;
import com.intellij.javascript.nodejs.execution.AbstractNodeTargetRunProfile;
import com.intellij.javascript.nodejs.execution.runConfiguration.AbstractNodeRunConfigurationExtension;
import com.intellij.javascript.nodejs.execution.runConfiguration.NodeRunConfigurationLaunchSession;
import com.intellij.openapi.options.SettingsEditor;
import com.jetbrains.nodejs.run.NodeJsRunConfiguration;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NodeRunConfiguration extends AbstractNodeRunConfigurationExtension {
    @Override
    protected void readExternal(@NotNull AbstractNodeTargetRunProfile runConfiguration, @NotNull Element element) {
        RunConfigSettingsEditor.readExternal(runConfiguration, element);
    }

    @Override
    protected void writeExternal(@NotNull AbstractNodeTargetRunProfile runConfiguration, @NotNull Element element) {
        RunConfigSettingsEditor.writeExternal(runConfiguration, element);
    }

    @NotNull
    @Override
    public <P extends AbstractNodeTargetRunProfile> SettingsEditor<P> createEditor(@NotNull P configuration) {
        return new RunConfigSettingsEditor<>(configuration);
    }

    @Override
    public boolean isApplicableFor(@NotNull AbstractNodeTargetRunProfile configuration) {
        return true;
    }

    @Override
    protected void patchCommandLine(@NotNull AbstractNodeTargetRunProfile configuration, @Nullable RunnerSettings runnerSettings, @NotNull GeneralCommandLine cmdLine, @NotNull String runnerId, @NotNull Executor executor) throws ExecutionException {
        configuration.getSelectedOptions();
    }

    @Override
    protected void extendCreatedConfiguration(@NotNull AbstractNodeTargetRunProfile configuration, @NotNull Location location) {
        super.extendCreatedConfiguration(configuration, location);
    }

    @Nullable
    @Override
    public String getEditorTitle() {
        return RunConfigSettingsEditor.getEditorTitle();
    }

    @Nullable
    @Override
    public NodeRunConfigurationLaunchSession createLaunchSession(@NotNull AbstractNodeTargetRunProfile configuration, @NotNull ExecutionEnvironment environment) throws ExecutionException {
        NodeJsRunConfiguration config = (NodeJsRunConfiguration) configuration;

        Map<String, String> newEnvs = RunConfigSettingsEditor
                .collectEnv(configuration, config.getWorkingDirectory(), config.getEnvs());

        config.setEnvs(newEnvs);

        return null;
    }
}
