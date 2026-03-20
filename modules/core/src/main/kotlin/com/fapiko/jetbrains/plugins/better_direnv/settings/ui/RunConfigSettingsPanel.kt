package com.fapiko.jetbrains.plugins.better_direnv.settings.ui

import com.fapiko.jetbrains.plugins.better_direnv.settings.DirenvSettings
import com.intellij.execution.configurations.RunConfigurationBase
import com.intellij.ui.dsl.builder.bindSelected
import com.intellij.ui.dsl.builder.panel
import javax.swing.JComponent

class RunConfigSettingsPanel(configuration: RunConfigurationBase<*>) {
    private var useDirenvEnabled = false
    private var direnvTrusted = false

    val component: JComponent = panel {
        row {
            checkBox("Enable Direnv")
                .bindSelected(::useDirenvEnabled)
        }
        indent {
            row {
                checkBox("Trust .envrc")
                    .bindSelected(::direnvTrusted)
            }
            row {
                comment(
                    """When enabled it will automatically allow direnv to process changes to the .envrc file in
                       the working directory. Only enable this for projects you trust, direnv can execute
                       potentially malicious code.""".trimIndent()
                )
            }
        }
    }

    fun getState(): DirenvSettings {
        return DirenvSettings(useDirenvEnabled, direnvTrusted)
    }

    fun setState(state: DirenvSettings) {
        useDirenvEnabled = state.isDirenvEnabled()
        direnvTrusted = state.isDirenvTrusted()
    }
}