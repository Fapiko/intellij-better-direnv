package com.fapiko.jetbrains.plugins.better_direnv.runconfigs;

import com.fapiko.jetbrains.plugins.better_direnv.settings.DirenvSettings;
import com.fapiko.jetbrains.plugins.better_direnv.settings.ui.RunConfigSettingsEditor;
import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.configurations.RunnerSettings;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.util.NlsContexts;
import com.jetbrains.php.config.interpreters.PhpInterpreter;
import com.jetbrains.php.run.PhpRunConfiguration;
import com.jetbrains.php.run.PhpRunConfigurationExtension;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class PHPRunConfigurationExtension extends PhpRunConfigurationExtension {

    @Override
    protected void readExternal(@NotNull PhpRunConfiguration<?> runConfiguration, @NotNull Element element) {
        RunConfigSettingsEditor.readExternal(runConfiguration, element);
    }

    @Override
    protected void writeExternal(@NotNull PhpRunConfiguration<?> runConfiguration, @NotNull Element element) {
        RunConfigSettingsEditor.writeExternal(runConfiguration, element);
    }

    @Override
    public @Nullable <P extends PhpRunConfiguration<?>> SettingsEditor<P> createEditor(@NotNull P configuration) {
        return new RunConfigSettingsEditor<>(configuration);
    }

    @Override
    protected @Nullable @NlsContexts.TabTitle String getEditorTitle() {
        return RunConfigSettingsEditor.getEditorTitle();
    }

    @Override
    public boolean isApplicable(@Nullable PhpInterpreter phpInterpreter) {
        return true;
    }

    @Override
    public boolean isApplicableFor(@NotNull PhpRunConfiguration<?> configuration) {
        return true;
    }

    @Override
    public boolean isEnabledFor(@NotNull PhpRunConfiguration<?> applicableConfiguration, @Nullable RunnerSettings runnerSettings) {
        return true;
    }

    @Override
    protected void patchCommandLine(@NotNull PhpRunConfiguration<?> configuration, @Nullable RunnerSettings runnerSettings, @NotNull GeneralCommandLine cmdLine, @NotNull String runnerId) throws ExecutionException {
        DirenvSettings direnvSettings = configuration.getCopyableUserData(RunConfigSettingsEditor.USER_DATA_KEY);

        Map<String, String> newEnv = RunConfigSettingsEditor.collectEnv(direnvSettings, configuration.getProject().getBasePath());

        for (Map.Entry<String, String> set : newEnv.entrySet()) {
            cmdLine.withEnvironment(set.getKey(), set.getValue());
        }
    }
}
