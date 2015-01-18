# Gradle-Docker-Plugin example

[![Gradle logo](https://github.com/gesellix-docker/gradle-docker-plugin-example/raw/master/img/gradle-logo.png)](http://www.gradle.org/)
[![Docker logo](https://github.com/gesellix-docker/gradle-docker-plugin-example/raw/master/img/docker-logo.png)](http://www.docker.com/)

Example project showing some use cases of the [gradle-docker-plugin](https://github.com/gesellix-docker/gradle-docker-plugin).

See the [build.gradle](https://github.com/gesellix-docker/gradle-docker-plugin-example/blob/master/build.gradle) file in the
project root for detailed task configuration. Most configuration parameters are optional.

The root project only contains the most trivial tasks `info` and `version`. More advanced use cases can be found in the subprojects:

* **publish** shows a very convenient way to build and push an image to one or more registries.
* **build-and-run-locally** shows a simple use case with an image being build and run as a container locally.
* **build-push-and-run-remotely** shows a more advanced use case including a private registry.
* **run-exec-and-copy-locally** shows how to exec commands in a running container and how to copy files from a container.
