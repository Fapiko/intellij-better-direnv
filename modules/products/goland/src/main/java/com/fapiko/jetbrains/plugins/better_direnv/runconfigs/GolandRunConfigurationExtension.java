package com.fapiko.jetbrains.plugins.better_direnv.runconfigs;

import com.fapiko.jetbrains.plugins.better_direnv.listeners.BetterDirenvProjectManagerListener;
import com.fapiko.jetbrains.plugins.better_direnv.settings.DirenvSettings;
import com.fapiko.jetbrains.plugins.better_direnv.settings.ui.RunConfigSettingsEditor;
import com.goide.execution.GoRunConfigurationBase;
import com.goide.execution.GoRunningState;
import com.goide.execution.extension.GoRunConfigurationExtension;
import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.RunnerSettings;
import com.intellij.execution.target.TargetedCommandLineBuilder;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.util.NlsContexts;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class GolandRunConfigurationExtension extends GoRunConfigurationExtension {
    private static final Logger LOG = Logger.getInstance(BetterDirenvProjectManagerListener.class);

    @Override
    protected void readExternal(@NotNull GoRunConfigurationBase<?> runConfiguration, @NotNull Element element) {
        RunConfigSettingsEditor.readExternal(runConfiguration, element);
    }

    @Override
    protected void writeExternal(@NotNull GoRunConfigurationBase<?> runConfiguration, @NotNull Element element) {
        RunConfigSettingsEditor.writeExternal(runConfiguration, element);
    }

    @Override
    protected @Nullable <P extends GoRunConfigurationBase<?>> SettingsEditor<P> createEditor(@NotNull P configuration) {
        return new RunConfigSettingsEditor<>(configuration);
    }

    @Override
    protected @Nullable @NlsContexts.TabTitle String getEditorTitle() {
        return RunConfigSettingsEditor.getEditorTitle();
    }

    @Override
    public boolean isApplicableFor(@NotNull GoRunConfigurationBase<?> configuration) {
        return true;
    }

    @Override
    public boolean isEnabledFor(@NotNull GoRunConfigurationBase<?> applicableConfiguration, @Nullable RunnerSettings runnerSettings) {
        return true;
    }

    @Override
    protected void patchCommandLine(@NotNull GoRunConfigurationBase<?> configuration, @Nullable RunnerSettings runnerSettings, @NotNull TargetedCommandLineBuilder cmdLine, @NotNull String runnerId, @NotNull GoRunningState<? extends GoRunConfigurationBase<?>> state, GoRunningState.@NotNull CommandLineType commandLineType) throws ExecutionException {
        DirenvSettings direnvSettings = configuration.getCopyableUserData(RunConfigSettingsEditor.USER_DATA_KEY);
        Map<String, String> newEnv = RunConfigSettingsEditor.collectEnv(direnvSettings, configuration.getWorkingDirectory());

        for (Map.Entry<String, String> set : newEnv.entrySet()) {
            cmdLine.addEnvironmentVariable(set.getKey(), set.getValue());
        }
    }
}
