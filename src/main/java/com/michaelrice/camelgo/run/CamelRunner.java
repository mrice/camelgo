package com.michaelrice.camelgo.run;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mrice on 4/27/15.
 */
public class CamelRunner {

    private Logger logger = LoggerFactory.getLogger(CamelRunner.class);

    Map<String, RouteBuilder> preparedRoutes = new HashMap<String, RouteBuilder>();

    public static int main(String... args) {
        CamelRunner camelRunner = new CamelRunner();
        return camelRunner.go();
    }

    public int go(String... args) {
        printWelcomeMessage();

        if (args == null || args.length == 0) {
            printUsageHint();
        } else {
            if (validateActivationClasses(args)) {
                activateClasses();
                startCamel();
            } else {
                return -1;
            }
        }

        // run until camel until we get interrupted
        while (true) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                logger.info("interrupted, exiting...");
                return 0;
            }
        }
    }

    void startCamel() {
        CamelContext camelContext = new DefaultCamelContext();
        for (Map.Entry<String, RouteBuilder> entry : preparedRoutes.entrySet()) {
            try {
                camelContext.addRoutes(entry.getValue());
            } catch (Exception e) {
                e.printStackTrace(); //TODO
            }
        }
        try {
            camelContext.start();
        } catch (Exception e) {
            e.printStackTrace(); //TODO
        }
    }

    void activateClasses(String... classes) {

        //TODO revisit this for a better method, add error handling, and make sure it's actually a camelgo class
        for (String className : classes) {
            Class camelgoClass = null;
            try {
                camelgoClass = Class.forName(className);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Camelgo instance = null;
            try {
                instance = (Camelgo)camelgoClass.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            preparedRoutes.put(className, instance.prepare());
        }
    }

    void printWelcomeMessage() {
        logger.info("com.michaelrice.camelgo.run.Camelgo - runs Camel routes without OSGi");
    }

    void printUsageHint() {
        logger.info("usage: camelgo package.class ...");
    }

    boolean validateActivationClasses(String... classes) {
        for (String className : classes) {
            try {
                Class.forName(className);
            } catch (ClassNotFoundException e) {
                logger.error(String.format("error: could not find class named: %s", className));
                printUsageHint();
                return false;
            }
        }
        return true;
    }

    void printExitInstructions() {
        logger.info("routes are running; ctrl+c to exit");
    }
}
