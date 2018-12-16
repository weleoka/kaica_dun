
# Assignment III - KaicaDungeon
This is a dungeon adventure game for the java VM. It uses Java Persistance API via Hibernate. Current build engine is Gradle and with mavenCentral repositories for dependency sourcing.




#### Setup for development - manual

1) git clone the repository

2) Use gradle to fetch the dependencies into the local project folder. The gradle task for copying the dependencies is `cpDeps`. The linux command is `./gradlew cpDeps`. Windows uses the gradlew.bat for the same jobs. This quickly sets all the dependencies available for development. The location for the local copies of the dependencies is `lib` folder in project root. 

3) Configure your IDE to load dependencies *.jar files from `lib` is done by `file->Project Structure->Libraries->[plus-sign]` and then adding the directory `lib`. Consider deleting all other references to other libraries/locations for the project (if there are any).

To build and run the project with gradle using the gradle wrapper `./gradlew build` and then `./gradlew run` to run the application.



#### Setup for development - automatic with IJ idea
1) git clone the repository
2) Öpen the project in IJ idea.
3) Stand by while dependencies are satisfied. These will not be stored in the project folder usually, but are instead downloaded to gradle cache for access by all java projects.



#### Dependencies
* hibernate-core
* mysql-connector-java
* log4j





### UML diagram
This is the logical model for the database and object persistance.

![ . . . ](uml01.png)


### Joblist & Questions

* Make sure that we are happy with module and its name.

* Should perhaps there be a kaica_dun.main for the main App file, and then import the classes import `main.java.kaica_dun.Entities.Item;`. Main class can be defined in Gradle build file.



### Project directory structure
The [directory structure](https://maven.apache.org/guides/introduction/introduction-to-the-standard-directory-layout.html) for the program follows that default structure recommended by Maven and Gradle.

### Style guide
This project adheres to the following [style guide](https://github.com/weleoka/myJavaStyleGuide).




