FROM openjdk
#This is the environment we want the container to run in
COPY . /workspace
#This will copy everything in our project and put it into a folder called workspace
WORKDIR /workspace
#Tell it what folder to work out of
EXPOSE 8080

ENTRYPOINT [ "java", "-jar", "target/Project1_JoshuaOtero-1.0-SNAPSHOT-jar-with-dependencies.jar" ]