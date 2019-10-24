set JAVA_HOME=C:\Program Files\Java\jdk1.8.0_144

rem Source: https://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/#using-boot-running-as-a-packaged-application
%java_home%\BIN\java -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=5005,suspend=n -jar target/recipes-0.0.1-SNAPSHOT.jar