# intellij-better-direnv

![Build](https://github.com/Fapiko/intellij-better-direnv/workflows/Build/badge.svg)
[![Version](https://img.shields.io/jetbrains/plugin/v/19275-better-direnv.svg)](https://plugins.jetbrains.com/plugin/19275-better-direnv)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/19275-better-direnv.svg)](https://plugins.jetbrains.com/plugin/19275-better-direnv)

## Notice - This project is not maintained

I've not been keeping up with issues or maintenance on this project for quite some time. JetBrains implements the run
configuration differently for each language in a way that makes it not straightforward to make this work and keeps
breaking existing ones. I've also not been doing much software development lately and stepping away from the space for a bit.

If someone would like to take this project over please reach out to me and I'd be happy to transfer ownership of the plugin in
the marketplace.

## Description

<!-- Plugin description -->
This plugin adds direnv support to IntelliJ IDEs.

The plugin can be accessed via the Run Configuration settings. To enable it for a given Run Configuration, check the
`Enable Direnv` checkbox. If you want it to automatically `direnv allow` when updates to the .envrc file are detected,
check the `Trust .direnv` checkbox. Note that this should only be done for trusted projects.

Currently supported Run Configurations:
  - Java
  - Go
  - NodeJS
  - Python
  - PHP
  - Ruby

Unfortunately, each run configuration type needs to be added manually. New run configuration support can be added
by request.

<!-- Plugin description end -->

## Installation

- Using IDE built-in plugin system:

  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Marketplace</kbd> > <kbd>Search for "
  intellij-better-direnv"</kbd> >
  <kbd>Install Plugin</kbd>

- Manually:

  Download the [latest release](https://github.com/Fapiko/intellij-better-direnv/releases/latest) and install it
  manually using
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>

---
Plugin based on the [IntelliJ Platform Plugin Template][template].

[template]: https://github.com/JetBrains/intellij-platform-plugin-template
