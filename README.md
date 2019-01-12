
# Assignment III - KaicaDungeon
This is a dungeon adventure game for the java VM. It uses Java Persistance API via Hibernate. Current build engine is Gradle and with mavenCentral repositories for dependency sourcing.




# Documentation
This application uses a persistance system based on the JPA specifications called Hibernate.

It also uses a transaction management implementation included in the Spring framework.

To compile the program for the HSQL database it is necessary to manually switch out the dataSourceBeans.


### Service classes
The service classes have the following responsibilities, as well as for certain processes.

#### GameService
- Avatars 
- Dungeons

#### MovementService
- Avatars
- Rooms

#### CombatService
- Avatars
- Monsters

#### ActionEngineService 
- Monsters
- Directions

#### UserService 
- Users and the authentication.




### Setup for development - manual
These steps are one way of getting the repository ready for development.

1. git clone the repository
2. Use gradle to fetch the dependencies into the local project folder. The gradle task for copying the dependencies is `cpDeps`. The task is run: `./gradlew cpDeps` results dependencies available for development in lib/ folder. IDE loading will more efficiently keep local copies of repositories to be available for multiple projects.
3. Configure your IDE to load dependencies *.jar files from `lib` is done by `file->Project Structure->Libraries->[plus-sign]` and then adding the directory `lib`. Consider deleting all other references to other libraries/locations for the project (if there are any).
4. Modify the src/resources/hibernate.cfg.xml to use mariaDB or mySQL and set your credentials and url parameters.

To build and run the project with gradle using the gradle wrapper `./gradlew build` and then `./gradlew run` to run the application.

If `./gradlew run` does not work then try the `./gradlew installDist` or `./gradlew distZip` and tun the resulting file in the `/bin` folder.


### Setup for development - automatic with IJ idea
This is probably the usual method for work on this project

1. git clone the repository
2. Open the project in IJ idea.
3. Stand by while dependencies are satisfied. These will not be stored in the project folder usually, but are instead downloaded to gradle cache for access by all java projects.
4. Modify the `src/resources/hibernate.cfg.xml` to use mariaDB or mySQL db, and to set your credentials, utl, port etc.

Building and running will likely be within the realms of the IDE in these instances.



### Compiling for production
As always there are few tweaks to be done before compiling successfully for production.

For HSQLDB - standalone game:

1. Switch out the DataSource bean by removing `/cfg/DataSourceCfg.java` and putting `cfg/DataSourceHsCfg.java` in place. (todo: change)
2. Change the dialect in application.config to the HSQL dialect by switching the commenting out.
3. Optional: change the ddl mode in application.config from create-drop to update, but only after ensuring that the tables are in existence. This is a question of weather or not you want save game functions.
4. In build.gradle uncomment the HSQL db dependency, and consider commenting out mariDb and sqlDb dependencies.
5. Change the debug in `cfg/KaicaDunCfg.java` from true to false. 
6. Finally set the logging level to warn for all the loggers in `log4j2.xml` and consider changing the appender to file out so you can monitor log/app.log for issues. 

Now it is possible to run the builds using gradle wrapper. I'm not going to tell you how to use gradle here, but running `./gradlew distZip` or `gradlew.bat distZip` in windows should yield results.



# Structure and design

### Dependencies
* hibernate-core
* mysql-connector-java
* log4j2
* Spring boot 5.2
* HSQLDB for file based relational db.

### UML diagram
This is the logical model for the the application object oriented design (not necessarily 100% up to date).
![ . . . ](model_uml_app.png)

### Logical Database Design
The primary keys of tables are created by the database server. This means that the annotation `@GeneratedValue(strategy = GenerationType.IDENTITY)` is used. There are significant drawback to this method as it can be substantially slower due to Hibernate not knowing which is the next PK value before the INSERT is made.

#### Entities mappings
```xml
<mapping class="kaica_dun_system.User"/>

<mapping class="kaica_dun.entities.Dungeon"/>
<mapping class="kaica_dun.entities.Room"/>
<mapping class="kaica_dun.entities.Direction"/>
<mapping class="kaica_dun.entities.ItemConsumable"/>
<mapping class="kaica_dun.entities.Item"/>
<mapping class="kaica_dun.entities.Armor"/>
<mapping class="kaica_dun.entities.Weapon"/>
<mapping class="kaica_dun.entities.Fighter"/>
<mapping class="kaica_dun.entities.Avatar"/>
<mapping class="kaica_dun.entities.Monster"/>
<mapping class="kaica_dun.entities.Inventory"/>
```
        
#### Logical Diagram
This is a diagram of the logical database model (not necessarily 100% up to date) resulting from forward engineering database tables using Hibernate and JPA.
Other aspects to the projects logical design are the method for autoincrementing the Primary Key values wich in this case follows the SEQUENCE method where Java will dictate the sequencing.  [Auto-incrementing and Java Persistence API](https://thoughts-on-java.org/jpa-generate-primary-keys/)
![ . . . ](model_db_logical.png)



### Application logging
Log can be written to stdout, but also to file `log/app.log`. Settings concerning application logging are specified in `resources/log4j2.xml`.


### Project directory structure
The [directory structure](https://maven.apache.org/guides/introduction/introduction-to-the-standard-directory-layout.html) for tthis project follows the default structure of Maven and Gradle.


### Style guide
This project attempts to adhere to the following [style guide](https://github.com/weleoka/myJavaStyleGuide).  


