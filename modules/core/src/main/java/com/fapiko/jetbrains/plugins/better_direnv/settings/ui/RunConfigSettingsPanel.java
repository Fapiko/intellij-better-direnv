package com.fapiko.jetbrains.plugins.better_direnv.settings.ui;

import com.fapiko.jetbrains.plugins.better_direnv.settings.DirenvSettings;
import com.intellij.execution.configurations.RunConfigurationBase;
import com.intellij.openapi.ui.panel.ComponentPanelBuilder;
import com.intellij.util.ui.JBUI;

import javax.swing.*;

public class RunConfigSettingsPanel extends JPanel {
    private final JCheckBox useDirenvCheckbox;
    private final JCheckBox trustDirenvCheckbox;

    public RunConfigSettingsPanel(RunConfigurationBase configuration) {
        useDirenvCheckbox = new JCheckBox("Enable Direnv");
        trustDirenvCheckbox = new JCheckBox("Trust .envrc");
        BoxLayout bl1 = new BoxLayout(this, BoxLayout.PAGE_AXIS);

        JPanel optionsPanel = new JPanel();
        BoxLayout bl2 = new BoxLayout(optionsPanel, BoxLayout.PAGE_AXIS);
        optionsPanel.setLayout(bl2);
        optionsPanel.setBorder(JBUI.Borders.emptyLeft(20));
        optionsPanel.add(new ComponentPanelBuilder(trustDirenvCheckbox).
                withComment("When enabled it will automatically allow direnv to process changes to the .envrc file in " +
                        "the working directory. Only enable this for projects you trust, direnv can execute " +
                        "potentially malicious code.").
                createPanel());


        setLayout(bl1);
        add(new ComponentPanelBuilder(useDirenvCheckbox).createPanel());
        add(optionsPanel);
    }

    public DirenvSettings getState() {
        return new DirenvSettings(
                useDirenvCheckbox.isSelected(),
                trustDirenvCheckbox.isSelected()
        );
    }

    public void setState(DirenvSettings state) {
        useDirenvCheckbox.setSelected(state.isDirenvEnabled());
        trustDirenvCheckbox.setSelected(state.isDirenvTrusted());
    }
}
