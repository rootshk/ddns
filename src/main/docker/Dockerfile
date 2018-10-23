FROM fabric8/java-alpine-openjdk8-jre
VOLUME /tmp
ADD domain-dns-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]