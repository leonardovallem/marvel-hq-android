# Marvel HQ

Marvel HQ is an app in which you can check out all Marvel comics, as well as save your favorite ones.

### Build

To build this application, just follow the steps:
1. clone the repository
2. open it with Android Studio
3. sync files with Gradle
4. press the play button `Run` to build and run the app

### Marvel API

This application utilizes Marvel's API to retrieve their comics data, so api keys are needed in order for the app to function as expected.
That said, you must insert to the project's `local.properties` file the properties:
- MARVEL_API_PUBLIC_KEY
- MARVEL_API_PRIVATE_KEY

Since this file and api keys must not be versioned, this process is required to do on every local machine.

> Marvel API is **very slow** in its free plan, so please be patient while waiting for the app loadings

### Executing tests

This app contains Android instrumented tests, as well as local JVM tests for the base logics.
The process to run them is straightforward, just pressing the play button next to each class signature to run those tests.

> The **Kotest** plugin will be useful for running tests written with the Kotest library

For the test class `ComicsListScreenTest`, devices (can be emulators) with different screen sizes will be necessary in order to perform some tests.
Since some of their methods tests for layout adjustment according to different screen sizes, each of those tests will require a specific device configuration, like smartphones and tablets.
