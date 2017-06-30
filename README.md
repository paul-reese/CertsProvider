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

## Test
After `cf push` use the provided route to hit the endpoint and observe the logs. Let's watch logs while we hit the endpoint
```
$ cf logs certs-provider
```
now hit the endpoint
```
$ curl certs-provider.<your domain>
```

sample log output

```
   2017-06-30T16:15:29.56-0600 [APP/PROC/WEB/0] OUT 2017-06-30 22:15:29.562  INFO 15 --- [nio-8080-exec-2] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring FrameworkServlet 'dispatcherServlet'
   2017-06-30T16:15:29.56-0600 [APP/PROC/WEB/0] OUT 2017-06-30 22:15:29.563  INFO 15 --- [nio-8080-exec-2] o.s.web.servlet.DispatcherServlet        : FrameworkServlet 'dispatcherServlet': initialization started
   2017-06-30T16:15:29.58-0600 [APP/PROC/WEB/0] OUT 2017-06-30 22:15:29.582  INFO 15 --- [nio-8080-exec-2] o.s.web.servlet.DispatcherServlet        : FrameworkServlet 'dispatcherServlet': initialization completed in 19 ms
   2017-06-30T16:15:29.72-0600 [APP/PROC/WEB/0] OUT 2017-06-30 22:15:29.723  INFO 15 --- [nio-8080-exec-2] ContainerTrustManagerFactory$PKIXFactory : Adding TrustManager for /etc/ssl/certs/ca-certificates.crt
   2017-06-30T16:15:29.72-0600 [APP/PROC/WEB/0] OUT 2017-06-30 22:15:29.722  INFO 15 --- [nio-8080-exec-2] ContainerTrustManagerFactory$PKIXFactory : Adding System Trust Manager
   2017-06-30T16:15:29.72-0600 [APP/PROC/WEB/0] OUT 2017-06-30 22:15:29.727  INFO 15 --- [ertificates.crt] org.cloudfoundry.security.FileWatcher    : Start watching /etc/ssl/certs/ca-certificates.crt
   2017-06-30T16:15:29.85-0600 [APP/PROC/WEB/0] OUT 2017-06-30 22:15:29.851  INFO 15 --- [nio-8080-exec-2] c.s.FileWatchingX509ExtendedTrustManager : Initialized TrustManager for /etc/ssl/certs/ca-certificates.crt
   2017-06-30T16:15:29.85-0600 [APP/PROC/WEB/0] OUT 2017-06-30 22:15:29.854  INFO 15 --- [nio-8080-exec-2] oundryContainerKeyManagerFactory$SunX509 : Adding System Key Manager
   2017-06-30T16:15:30.03-0600 [APP/PROC/WEB/0] OUT {
   2017-06-30T16:15:30.03-0600 [APP/PROC/WEB/0] OUT   "userId": 1,
   2017-06-30T16:15:30.03-0600 [APP/PROC/WEB/0] OUT }
   2017-06-30T16:15:30.03-0600 [APP/PROC/WEB/0] OUT   "body": "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"
   2017-06-30T16:15:30.03-0600 [APP/PROC/WEB/0] OUT   "title": "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
   2017-06-30T16:15:30.03-0600 [APP/PROC/WEB/0] OUT   "id": 1,
   2017-06-30T16:15:30.05-0600 [RTR/0] OUT certs-provider.cfapps.haas-46.pez.pivotal.io - [2017-06-30T22:15:29.530+0000] "GET / HTTP/1.1" 200 0 292 "-" "curl/7.51.0" "10.193.51.250:37826" "10.193.51.103:61002" x_forwarded_for:"10.35.59.14, 10.193.51.250" x_forwarded_proto:"http" vcap_request_id:"d60fd5cf-7825-4c96-7084-e23cb0f381fd" response_time:0.524215153 app_id:"5ef4208c-da11-4b8d-884a-323d1852a39f" app_index:"0" x_b3_traceid:"02b71968b833195a" x_b3_spanid:"02b71968b833195a" x_b3_parentspanid:"-"
   2017-06-30T16:15:30.05-0600 [RTR/0] OUT 
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
