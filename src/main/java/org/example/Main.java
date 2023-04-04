package org.example;

import org.apache.commons.daemon.Daemon;
import org.apache.commons.daemon.DaemonContext;
import org.example.cls.Engine;

import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Launch the Engine from a variety of sources, either through a main() or invoked through
 * Apache Daemon.
 */
public class Main implements Daemon {
    private static final Logger log = Logger.getLogger("Main");
    private static Engine engine = null;

    private static Main mainInstance = new Main();

    /**
     * The Java entry point.
     * @param args Command line arguments, all ignored.
     */
    public static void main(String[] args) {
        // the main routine is only here so I can also run the app from the command line
        mainInstance.initialize();

        Scanner sc = new Scanner(System.in);
        // wait until receive stop command from keyboard
        System.out.printf("Enter 'stop' to halt: ");
        while(!sc.nextLine().toLowerCase().equals("stop"));

        if (!engine.isStopped()) {
            mainInstance.terminate();
        }

    }

    public static void windowsStart(String args[]) {
        log.info("windowsStart called");
        mainInstance.initialize();
        while (!engine.isStopped()) {
            // don't return until stopped
            synchronized(mainInstance) {
                try {
                    mainInstance.wait(5000);  // wait 5 sec and check if stopped
                }
                catch(InterruptedException ie){
                    System.out.println("InterruptedException");
                }
            }
        }
    }

    public static void windowsStop(String args[]) {
        log.info("windowsStop called");
        mainInstance.terminate();
        synchronized(mainInstance) {
            // stop the start loop
            mainInstance.notify();
        }
    }

    // Implementing the Daemon interface is not required for Windows but is for Linux
    @Override
    public void init(DaemonContext arg0) throws Exception {
        log.info("Daemon init");
    }

    @Override
    public void start() {
        log.info("Daemon start");
        initialize();
    }

    @Override
    public void stop() {
        log.info("Daemon stop");
        terminate();
    }

    @Override
    public void destroy() {
        log.info("Daemon destroy");
    }

    /**
     * Do the work of starting the engine
     */
    private void initialize() {
        if (engine == null) {
            log.info("Starting the Engine");
            log.info("... spawn threads etc");
            engine = new Engine();
        }
    }

    /**
     * Cleanly stop the engine.
     */
    public void terminate() {
        if (engine != null) {
            log.info("Stopping the Engine");
            engine.stop();
            log.info("Engine stopped");
        }
    }
}