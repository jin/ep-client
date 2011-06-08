/*
 * EasyparkServerApp.java
 */

package easyparkserver;

import java.io.IOException;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class EasyparkServerApp extends SingleFrameApplication {

    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
        show(new EasyparkServerView(this));
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of EasyparkServerApp
     */
    public static EasyparkServerApp getApplication() {
        return Application.getInstance(EasyparkServerApp.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) throws IOException {
        launch(EasyparkServerApp.class, args);
    }// end main

    void systemExit() {
        System.out.println("Application closing now...");
        System.exit(1);
    }//end closeConnection()
}
