package com.github.fapiko.intellijbetterdirenv.services

import com.intellij.openapi.project.Project
import com.github.fapiko.intellijbetterdirenv.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
