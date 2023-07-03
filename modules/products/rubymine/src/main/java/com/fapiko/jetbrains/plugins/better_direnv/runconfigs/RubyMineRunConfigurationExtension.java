package com.fapiko.jetbrains.plugins.better_direnv.runconfigs;

import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.configurations.RunnerSettings;
import com.intellij.openapi.options.SettingsEditor;
import com.fapiko.jetbrains.plugins.better_direnv.settings.DirenvSettings;
import com.fapiko.jetbrains.plugins.better_direnv.settings.ui.RunConfigSettingsEditor;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.plugins.ruby.ruby.run.configuration.AbstractRubyRunConfiguration;
import org.jetbrains.plugins.ruby.ruby.run.configuration.RubyRunConfigurationExtension;

import java.util.Map;

public class RubyMineRunConfigurationExtension extends RubyRunConfigurationExtension {

    @Override
    protected void readExternal(@NotNull AbstractRubyRunConfiguration runConfiguration, @NotNull Element element) {
        RunConfigSettingsEditor.readExternal(runConfiguration, element);
    }

    @Override
    protected void writeExternal(@NotNull AbstractRubyRunConfiguration runConfiguration, @NotNull Element element) {
        RunConfigSettingsEditor.writeExternal(runConfiguration, element);
    }

    @Override
    protected @Nullable <P extends AbstractRubyRunConfiguration<?>> SettingsEditor<P> createEditor(@NotNull P configuration) {
        return new RunConfigSettingsEditor<>(configuration);
    }

    @Nullable
    @Override
    protected String getEditorTitle() {
        return RunConfigSettingsEditor.getEditorTitle();
    }

    @Override
    public boolean isApplicableFor(@NotNull AbstractRubyRunConfiguration<?> configuration) {
        return true;
    }

    @Override
    public boolean isEnabledFor(@NotNull AbstractRubyRunConfiguration<?> applicableConfiguration, @Nullable RunnerSettings runnerSettings) {
        return true;
    }

    @Override
    protected void patchCommandLine(@NotNull AbstractRubyRunConfiguration configuration, @Nullable RunnerSettings runnerSettings, @NotNull GeneralCommandLine cmdLine, @NotNull String runnerId) {
        DirenvSettings direnvSettings = configuration.getCopyableUserData(RunConfigSettingsEditor.USER_DATA_KEY);

        Map<String, String> newEnv = RunConfigSettingsEditor.collectEnv(direnvSettings, configuration.getProject().getBasePath());

        for (Map.Entry<String, String> set : newEnv.entrySet()) {
            cmdLine.withEnvironment(set.getKey(), set.getValue());
        }
    }
}
