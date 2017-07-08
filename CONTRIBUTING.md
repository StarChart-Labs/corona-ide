# Contributing

Corona IDE is currently in pre-alpha development - basic functionality is still being implemented.

## Basic Requirements

Pull requests should include:

- Minimum side-effect changes
- Documented methods, classes, etc
- Tests (where possible)
- Changelog entries

## Libraries Used

Corona IDE is currently written against Java 1.8. Testing use the TestNG framework, and the mock testing library of choice is mockito

## Development Environment Setup

Until Corona IDE reaches general availability with required features, it will be developed within Eclipse. It is recommended to create an isolated workspace for StarChart Labs projects. You should also import the standard formatting and save settings from the [eclipse-configuration repository](https://github.com/StarChart-Labs/eclipse-configuration)

You will also need to install the Eclipse plugin [e(fx)clipse](http://www.eclipse.org/efxclipse/index.html).

Once settings are configured, use the Eclipse Gradle IDE plug-in (we are currently using Buildship) to import all projects from the root of the repository
