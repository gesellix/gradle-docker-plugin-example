# Gradle-Docker-Plugin example

[![Gradle logo](https://github.com/gesellix/gradle-docker-plugin-example/raw/main/img/gradle-logo.png)](https://gradle.org/)
[![Docker logo](https://github.com/gesellix/gradle-docker-plugin-example/raw/main/img/docker-logo.png)](https://www.docker.com/)

Example project showing some use cases of the [gradle-docker-plugin](https://github.com/gesellix/gradle-docker-plugin).

See the [build.gradle.kts](https://github.com/gesellix/gradle-docker-plugin-example/blob/main/build.gradle.kts) file in the
project root for detailed task configuration. Most configuration parameters are optional.

The root project only contains the most trivial tasks `info` and `version`. More advanced use cases can be found in the subprojects:

* **build-with-dockerignore** shows how to use the [`.dockerignore` file](https://docs.docker.com/reference/builder/#the-dockerignore-file) to exclude the Gradle build directory or other files.
* **build-and-run-locally** shows a simple use case with an image being build and run as a container locally.
* **build-push-and-run-remotely** shows a more advanced use case including a private registry.
* **push-and-pull-with-auth** shows how to use the default `~/.dockercfg` for registry authentication.
* **run-exec-and-copy-locally** shows how to exec commands in a running container and how to copy files from a container.
