# jenkinsnotifier

## Simple maven run

`mvn exec:java`

## Create jar and execute standalone

`mvn clean compile assembly:single`
`java -classpath jenkins-notifier-0.1-jar-with-dependencies.jar hu.dobrosi.jenkinsnotifier.Main http://sample.url/job/ProjectName`

## Configuration in pom.xml

jenkinsProjectUrl = Jenkins URL of your project what you want to follow.
