package dk.mk.bee.config;

public class Logger {

    /** Prints/logs warning. Will always be logged. */
    public static void warnLn(String msg) {
        System.out.println(msg);
    }

    /** Prints/logs and performs newline */
    public static void logLn(String msg) {
        System.out.println(msg);
    }

    /** Prints/logs newline */
    public static void logLn() {
        System.out.println();
    }

    /** Prints/logs (no newline) */
    public static void log(String msg) {
        System.out.print(msg);
    }

    /** Prints/logs if debugging is enabled */
    public static void debugLn(String msg) {
        if (ApplicationConfig.DEBUGGING) {
            System.out.println(msg);
        }
    }

    /** Prints/logs newline */
    public static void debugLn() {
        if (ApplicationConfig.DEBUGGING) {
            System.out.println();
        }
    }

    /** Prints/logs if debugging is enabled (no newline) */
    public static void debug(String msg) {
        if (ApplicationConfig.DEBUGGING) {
            System.out.print(msg);
        }
    }
}
