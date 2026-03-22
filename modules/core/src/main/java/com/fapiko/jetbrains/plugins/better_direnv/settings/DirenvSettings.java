package com.fapiko.jetbrains.plugins.better_direnv.settings;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DirenvSettings {
    private final boolean direnvEnabled;
    private final boolean direnvTrusted;
}
