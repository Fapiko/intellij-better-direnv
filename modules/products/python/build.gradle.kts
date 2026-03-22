plugins {
    id("org.jetbrains.intellij.platform.module")
}

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

dependencies {
    implementation(project(":better_direnv-core"))

    intellijPlatform {
        pycharmProfessional(providers.gradleProperty("platformVersion"))
        bundledPlugin("PythonCore")
    }
}
