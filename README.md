# CertsProvider

Makes use of [Java Buildpack Security Provider](https://github.com/cloudfoundry/java-buildpack-security-provider) by exposing the certs managed by bosh on a Cloud Foundry installation to a running Java Application. We set a basic RESTful endpoint using `@RestController` on the publizied route for the application running in Cloud Foundry.

## Development
The project depends on Cloud Foundry version 1.9 or higher. To build from source, run the following:
```
$ ./mvnw clean package
```

## Deploy
A PCF manifest is provided for your use.
```
$ cf push
```

## Notes
If you run into a compile error because CloudFoundryContainerProvider, most likely you will need to update your `~/.m2/settings.xml` to include details below, java-buildpack-container-security-provider is only deployed to Spring repositories.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<settings xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.1.0 http://maven.apache.org/xsd/settings-1.1.0.xsd" xmlns="http://maven.apache.org/SETTINGS/1.1.0"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
  <profiles>
    <profile>
      <repositories>
        <repository>
          <snapshots>
            <enabled>false</enabled>
          </snapshots>
          <id>central</id>
          <name>libs-release</name>
          <url>https://repo.spring.io/libs-release</url>
        </repository>
        <repository>
          <snapshots />
          <id>snapshots</id>
          <name>libs-snapshot</name>
          <url>https://repo.spring.io/libs-snapshot</url>
        </repository>
      </repositories>
      <pluginRepositories>
        <pluginRepository>
          <snapshots>
            <enabled>false</enabled>
          </snapshots>
          <id>central</id>
          <name>plugins-milestone</name>
          <url>https://repo.spring.io/plugins-milestone</url>
        </pluginRepository>
        <pluginRepository>
          <snapshots />
          <id>snapshots</id>
          <name>plugins-milestone</name>
          <url>https://repo.spring.io/plugins-milestone</url>
        </pluginRepository>
      </pluginRepositories>
      <id>artifactory</id>
    </profile>
  </profiles>
  <activeProfiles>
    <activeProfile>artifactory</activeProfile>
  </activeProfiles>
</settings>
```
