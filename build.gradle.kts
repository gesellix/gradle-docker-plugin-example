import de.gesellix.docker.authentication.AuthConfig
import de.gesellix.gradle.docker.tasks.DockerInfoTask
import de.gesellix.gradle.docker.tasks.DockerPingTask
import de.gesellix.gradle.docker.tasks.DockerVersionTask

buildscript {
  repositories {
    mavenCentral()
    gradlePluginPortal()
  }

  dependencies {
    classpath("de.gesellix:docker-client:2025-01-19T00-00-00-groovy-4")
    classpath(localGroovy())
  }
}

// Works with Gradle 2.1+.
// For the old configuration see https://plugins.gradle.org/plugin/de.gesellix.docker
// or use the pluginManagement in the settings.gradle.kts
// to configure another repository.
plugins {
  id("com.github.ben-manes.versions") version "0.51.0"
  id("net.ossindex.audit") version "0.4.11"
  id("de.gesellix.docker") version "2025-01-19T11-11-00"
}

allprojects {
  apply(plugin = "base")
  apply(plugin = "de.gesellix.docker")

//  configurations.all {
//    resolutionStrategy {
//      failOnVersionConflict()
//      dependencySubstitution {
//        all {
//          requested.let {
//            if (it is ModuleComponentSelector && it.group == "org.codehaus.groovy") {
//              logger.lifecycle("substituting $it with 'org.apache.groovy:*:4.0.15'")
//              useTarget(
//                      "org.apache.groovy:${it.module}:4.0.15",
//                      "Changed Maven coordinates since Groovy 4"
//              )
//            }
//          }
//        }
//      }
//    }
//  }
//  configure<de.gesellix.gradle.docker.DockerPluginExtension> {
  docker {
    //    dockerHost = System.env.DOCKER_HOST ?: "unix:///var/run/docker.sock"
//    dockerHost = System.env.DOCKER_HOST ?: "https://192.168.99.100:2376"
//    certPath = System.getProperty("docker.cert.path") ?: "${System.getProperty("user.home")}/.docker/machine/machines/default"
    authConfig = AuthConfig().apply {
      username = "gesellix"
      password = "-yet-another-password-"
      email = "tobias@gesellix.de"
      serveraddress = "https://index.docker.io/v1/"
    }
  }
}

tasks {
  register<DockerPingTask>("ping") {
    doLast {
      println(result.content)
    }
  }

  register<DockerVersionTask>("dockerVersion") {
    doLast {
      println(version.content)
    }
  }

  register<DockerInfoTask>("dockerInfo") {
    doLast {
      println(info.content)
    }
  }

  wrapper {
    gradleVersion = "8.12"
    distributionType = Wrapper.DistributionType.BIN
    // https://gradle.org/release-checksums/
    distributionSha256Sum = "7a00d51fb93147819aab76024feece20b6b84e420694101f276be952e08bef03"
  }
}
