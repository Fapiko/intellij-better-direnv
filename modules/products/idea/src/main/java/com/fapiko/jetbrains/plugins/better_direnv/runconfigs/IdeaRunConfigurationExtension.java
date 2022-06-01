package com.fapiko.jetbrains.plugins.better_direnv.runconfigs;

import com.fapiko.jetbrains.plugins.better_direnv.settings.ui.RunConfigSettingsEditor;
import com.intellij.execution.ExecutionException;
import com.intellij.execution.RunConfigurationExtension;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.configurations.JavaParameters;
import com.intellij.execution.configurations.RunConfigurationBase;
import com.intellij.execution.configurations.RunnerSettings;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.options.SettingsEditor;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class IdeaRunConfigurationExtension extends RunConfigurationExtension {
    private static final Logger LOG = Logger.getInstance(IdeaRunConfigurationExtension.class);

    @Override
    public <T extends RunConfigurationBase<?>> void updateJavaParameters(@NotNull T configuration, @NotNull JavaParameters params, RunnerSettings runnerSettings) throws ExecutionException {
        String workDir = params.getWorkingDirectory();

        Map<String, String> sourceEnv = new GeneralCommandLine()
                .withEnvironment(params.getEnv())
                .withParentEnvironmentType(
                        params.isPassParentEnvs() ? GeneralCommandLine.ParentEnvironmentType.CONSOLE : GeneralCommandLine.ParentEnvironmentType.NONE
                )
                .getEffectiveEnvironment();

        if (RunConfigSettingsEditor.getState(configuration).isDirenvEnabled()) {
            Map<String, String> envVars = RunConfigSettingsEditor.collectEnv(configuration, workDir, sourceEnv);

            params.setEnv(envVars);
        }
    }

    @Override
    protected void readExternal(@NotNull RunConfigurationBase<?> runConfiguration, @NotNull Element element) {
        RunConfigSettingsEditor.readExternal(runConfiguration, element);
    }

    @Override
    protected void writeExternal(@NotNull RunConfigurationBase<?> runConfiguration, @NotNull Element element) {
        RunConfigSettingsEditor.writeExternal(runConfiguration, element);
    }

    @Override
    protected @Nullable <P extends RunConfigurationBase<?>> SettingsEditor<P> createEditor(@NotNull P configuration) {
        return new RunConfigSettingsEditor<>(configuration);
    }

    @Override
    public boolean isApplicableFor(@NotNull RunConfigurationBase<?> configuration) {
        return true;
    }
}
