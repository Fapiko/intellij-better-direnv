package com.fapiko.jetbrains.plugins.better_direnv.language;

import com.intellij.lang.Language;
import com.intellij.sh.ShLanguage;

public class DirenvLanguage extends Language {
    public static final Language INSTANCE = ShLanguage.INSTANCE;

    private DirenvLanguage() {
        super("Shell Script");
    }
}
