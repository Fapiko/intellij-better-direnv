package com.fapiko.jetbrains.plugins.better_direnv.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DirenvOutput {
    private String output;
    private boolean error;
}
