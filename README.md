Project configuration:
- Android Studio Meerkat | 2024.3.1 Patch 2
- Gradle JDK: homebrew-17 - Homebrew OpenJDK 17.0.5 - arch 64
- Java 17
- Android 15 / API level 35
- SDK tools 35.0.0
- Gradle Wrapper: 8.11.1
- Android Gradle Plugin (AGP): 8.2.0

Testing
The project is covered with unit and instrumented tests, to execute them use Jacoco:
 ./gradlew jacocoFullReport

Then check the Jacoco test report at
/build/reports/jacoco/jacocoFullReport/html/index.html

