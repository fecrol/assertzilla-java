package com.github.fecrol.assertzilla.core.interactions;

import com.github.fecrol.assertzilla.core.exceptions.TestFailedException;
import com.github.fecrol.assertzilla.core.logging.InteractionLogger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Attempt {

    private static final InteractionLogger interactionLogger;

    static {
        interactionLogger = new InteractionLogger(LoggerFactory.getLogger(InteractionLogger.class));
    }

    public static void to(Interaction... interactions) {
        List<Interaction> interactionList = List.of(interactions);
        boolean caughtThrowable = false;
        Throwable theCaughtThrowable = null;

        for(Interaction interaction : interactionList) {
            if(!caughtThrowable) {
                try {
                    interactionLogger.log(interaction);
                    interaction.perform();
                } catch (Exception | Error e) {
                    caughtThrowable = true;
                    theCaughtThrowable = e;
                }
            }
        }

        if(caughtThrowable) {
            throw new TestFailedException(theCaughtThrowable.getMessage());
        }
    }
}
