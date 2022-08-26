package com.fapiko.jetbrains.plugins.better_direnv.runconfigs;

import com.fapiko.jetbrains.plugins.better_direnv.settings.DirenvSettings;
import com.fapiko.jetbrains.plugins.better_direnv.settings.ui.RunConfigSettingsEditor;
import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.configurations.RunnerSettings;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.util.NlsContexts;
import com.jetbrains.python.run.AbstractPythonRunConfiguration;
import com.jetbrains.python.run.PythonRunConfigurationExtension;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class PycharmRunConfigurationExtension extends PythonRunConfigurationExtension {

    @Override
    protected void readExternal(@NotNull AbstractPythonRunConfiguration<?> runConfiguration, @NotNull Element element) {
        RunConfigSettingsEditor.readExternal(runConfiguration, element);
    }

    @Override
    protected void writeExternal(@NotNull AbstractPythonRunConfiguration<?> runConfiguration, @NotNull Element element) {
        RunConfigSettingsEditor.writeExternal(runConfiguration, element);
    }

    @Override
    protected @Nullable <P extends AbstractPythonRunConfiguration<?>> SettingsEditor<P> createEditor(@NotNull P configuration) {
        return new RunConfigSettingsEditor<>(configuration);
    }

    @Override
    protected @Nullable @NlsContexts.TabTitle String getEditorTitle() {
        return RunConfigSettingsEditor.getEditorTitle();
    }

    @Override
    public boolean isApplicableFor(@NotNull AbstractPythonRunConfiguration<?> configuration) {
        return true;
    }

    @Override
    public boolean isEnabledFor(@NotNull AbstractPythonRunConfiguration<?> applicableConfiguration, @Nullable RunnerSettings runnerSettings) {
        return true;
    }

    @Override
    protected void patchCommandLine(@NotNull AbstractPythonRunConfiguration<?> configuration, @Nullable RunnerSettings runnerSettings, @NotNull GeneralCommandLine cmdLine, @NotNull String runnerId) throws ExecutionException {
        DirenvSettings direnvSettings = configuration.getCopyableUserData(RunConfigSettingsEditor.USER_DATA_KEY);
        Map<String, String> newEnvs = RunConfigSettingsEditor.collectEnv(direnvSettings, configuration.getWorkingDirectory());

        cmdLine.withEnvironment(newEnvs);
    }
}
