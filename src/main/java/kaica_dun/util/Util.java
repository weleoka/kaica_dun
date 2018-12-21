package kaica_dun.util;

public class Util {

    public static boolean IsEmptyString(String s) {
        if (s == null || s.trim().isEmpty())
            return true;
        return false;
    }

    public static boolean getBoolValue(String value){
        if(value == null) return false;
        if(value.toLowerCase().equals("false")) return false;
        return true;
    }

    /**
     * Sleep the program for x number of miliseconds.
     *
     * @param mSeconds
     */
    public static void sleeper(int mSeconds)
    {
        try {
            Thread.sleep(mSeconds);

        } catch (InterruptedException e) {
            System.out.println("Sleep function has been aborted.");
        }
    }
}
