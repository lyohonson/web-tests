# web-tests
To run tests firstly you need to do the following:
- Install Java version 1.8.0_*


To run tests:
 - On Windows in command line run  "gradlew.bat clean test -Dselenide.baseUrl=BASE_URL"
 - On Linux/MacOs in command line run "./gradlew clean test -Dselenide.baseUrl=BASE_URL"

Allure reports will be generated automatically and opened in default browser after build.

Log files will be in build/test-results/logs/ path.