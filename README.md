# Corona

`The'I' in IDE should also stand for "invisible"`

A exploratory project to build a lighter, simpler Java IDE - or learn trying!

Many IDEs try to handle many languages and a large range of features. Corona IDE's aim is to present the most important tools and allow developer's to simply focus on writing things. Most importantly, it aims to do all this in a portable manner which can be setup by a new contributor quickly and easily.


## Contributing

Corona IDE is currently in pre-alpha development - basic functionality is still being implemented.

### Basic Requirements

Pull requests should include:

- Minimum side-effect changes
- Documented methods, classes, etc
- Tests (where possible)
- Changelog entries

### Libraries Used

Corona IDE is currently written against Java 1.8. Testing used the TestNG framework, and the mock testing library of choice is mockito


### Development Environment Setup

Until Corona IDE reaches general availability with required features, it will be developed within Eclipse. It is recommended to create an isolated workspace for Corona IDE. You should also import the standard Corona IDE formatting and save settings from eclipseConfiguration:

- `Java > CodeStyle > Cleanup` is imported from cleanup.xml
- `Java > CodeStyle > Formatter` is imported from codeformatter.xml
- `Java > CodeStyle > Code Templates` is imported from codetemplates.xml

It is also recommended to turn on "save actions", under the Java Editor settings, to format saved lines and perform other cleanup operations

Once settings are configured, use the Eclipse Gradle IDE plug-in (we are currently using STS) to import all projects from the root of the repository