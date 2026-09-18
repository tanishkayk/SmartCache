package smartcache;

import java.time.LocalDateTime;

public class Logger {

    public static void info(String message) {

        System.out.println(
                "[" + LocalDateTime.now() + "] INFO: "
                        + message
        );
    }

    public static void error(String message) {

        System.err.println(
                "[" + LocalDateTime.now() + "] ERROR: "
                        + message
        );
    }
}