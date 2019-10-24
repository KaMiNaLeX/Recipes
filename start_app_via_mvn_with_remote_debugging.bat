set JAVA_HOME=C:\Program Files\Java\jdk1.8.0_144

rem source: https://docs.spring.io/spring-boot/docs/2.2.0.RELEASE/maven-plugin/examples/run-debug.html
call mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005"