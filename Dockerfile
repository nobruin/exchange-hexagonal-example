FROM openjdk:11
VOLUME /tmp
EXPOSE 8080
ADD /build/libs/*.jar spring-boot-docker-1.0.jar
ENTRYPOINT ["java","$JAVA_OPTS -XX:+UseContainerSupport","-Xmx300m -Xss512k -XX:CICompilerCount=2","-Dserver.port=$PORT","-jar","spring-boot-docker-1.0.jar"]