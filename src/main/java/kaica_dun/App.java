/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package kaica_dun;

import kaica_dun.resources.TestDb;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class App {
    // This logger has a name so that it can retrieved for use from anywhere in the application.
    private static final Logger log = LogManager.getLogger("MAIN");

    public static void main(String[] args) throws Exception {
        String[] arguments = {}; // For use if calling main() methods in for example test classes.


        log.info("- - - K A I C A - - -");



        //Player player = new Player();
        //makeStaticDungeon msd = new makeStaticDungeon(player);
        //Dungeon d = msd.makeDungeon();


        // Testing scenarios:

        //TestDb.main(arguments); // Run all the tests.
        TestDb.testDb_Monster();
        //TestDb.testDb_Room();
    }


}
