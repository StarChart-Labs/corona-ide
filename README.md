# Corona IDE

[![Travis CI](https://img.shields.io/travis/Corona-IDE/corona-ide.svg?branch=master)](https://travis-ci.org/Corona-IDE/corona-ide) [![Code Coverage](https://img.shields.io/codecov/c/github/Corona-IDE/corona-ide.svg)](https://codecov.io/github/Corona-IDE/corona-ide) [![BCH compliancy](https://bettercodehub.com/edge/badge/Corona-IDE/corona-ide)](https://bettercodehub.com) [![Quality Gate](https://sonarqube.com/api/badges/gate?key=com.coronaide:coronaide)](https://sonarqube.com/dashboard/index/com.coronaide:coronaide) [![Black Duck Security Risk](https://copilot.blackducksoftware.com/github/groups/Corona-IDE/locations/corona-ide/public/results/branches/master/badge-risk.svg)](https://copilot.blackducksoftware.com/github/groups/Corona-IDE/locations/corona-ide/public/results/branches/master) [![License](http://img.shields.io/:license-epl 1.0-blue.svg?style=flat)](https://www.eclipse.org/legal/epl-v10.html)

`The'I' in IDE should also stand for "invisible"`

A exploratory project to build a lighter, simpler Java IDE - or learn trying!

One of the harder parts of developing on a team is getting new members setup for development quickly and consistently. Corona IDE aims to provide a light-weight environment which helps developers do what they need to do, and to do it in a way which is portable and repeatable.

## Legal

All assets and code are under the [EPL 1.0 License](https://www.eclipse.org/legal/epl-v10.html) unless otherwise specified

## Contributing

Corona IDE is currently in pre-alpha development - basic functionality is still being implemented.

### Basic Requirements

Pull requests should include:

- Minimum side-effect changes
- Documented methods, classes, etc
- Tests (where possible)
- Changelog entries

### Libraries Used

Corona IDE is currently written against Java 1.8. Testing use the TestNG framework, and the mock testing library of choice is mockito


### Development Environment Setup

Until Corona IDE reaches general availability with required features, it will be developed within Eclipse. It is recommended to create an isolated workspace for Corona IDE. You should also import the standard Corona IDE formatting and save settings from eclipseConfiguration:

- `Java > CodeStyle > Cleanup` is imported from cleanup.xml
- `Java > CodeStyle > Formatter` is imported from codeformatter.xml
- `Java > CodeStyle > Code Templates` is imported from codetemplates.xml

It is also recommended to turn on "save actions", under the Java Editor settings, to format saved lines and perform other cleanup operations

You will also need to install the Eclipse plugin [e(fx)clipse](http://www.eclipse.org/efxclipse/index.html).

Once settings are configured, use the Eclipse Gradle IDE plug-in (we are currently using STS) to import all projects from the root of the repository

#### Gradle Build Scan

Corona IDE has the plug-ins required for [Gradle Build Scan](https://gradle.com/) setup. Simply run a build with the `-Dscan` option, and a build scan will be performed
