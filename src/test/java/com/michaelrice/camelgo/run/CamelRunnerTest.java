package com.michaelrice.camelgo.run;

import junit.framework.Assert;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;

/**
 * Created by mrice on 4/27/15.
 */
public class CamelRunnerTest {

    @Test
    public void printWelcome() {
        CamelRunner camelRunner = new CamelRunner();
        camelRunner.printWelcomeMessage();
    }

    @Test
    public void printUsageHint() {
        CamelRunner camelRunner = new CamelRunner();
        camelRunner.printUsageHint();
    }

    @Test
    public void printExitInformation() {
        CamelRunner camelRunner = new CamelRunner();
        camelRunner.printExitInstructions();
    }

    @Test
    public void validateClassesAllClassesAvail() {
        CamelRunner camelRunner = new CamelRunner();
        assertTrue(camelRunner.validateActivationClasses("com.michaelrice.camelgo.run.MockTest"));
    }

    @Test
    public void activateClasses() {
        CamelRunner camelRunner = new CamelRunner();
        camelRunner.activateClasses("com.michaelrice.camelgo.run.MockTest");
    }

    @Test
    public void startCamel() {
        CamelRunner camelRunner = new CamelRunner();
        camelRunner.activateClasses("com.michaelrice.camelgo.run.MockTest");
        camelRunner.startCamel();
    }

}
