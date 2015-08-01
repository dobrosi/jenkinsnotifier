# jenkinsnotifier

## Simple maven run

`mvn exec:java`

## Create jar

`mvn clean compile assembly:single`

## Execute standalone
`java -classpath jenkins-notifier-0.1-jar-with-dependencies.jar hu.dobrosi.jenkinsnotifier.Main http://sample.url/job/ProjectName`

## Configuration in pom.xml

jenkinsProjectUrl = Jenkins URL of your project what you want to follow.
