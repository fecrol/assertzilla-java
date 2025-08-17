package com.github.fecrol.assertzilla.core.logging;

import com.github.fecrol.assertzilla.core.annotations.Log;
import com.github.fecrol.assertzilla.core.helpers.ReflectionHelpers;
import com.github.fecrol.assertzilla.core.helpers.StringHelpers;
import com.github.fecrol.assertzilla.core.interactions.Interaction;
import org.slf4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class InteractionLogger {

    private final Logger LOGGER;

    public InteractionLogger(Logger logger) {
        LOGGER = logger;
    }

    public void log(Interaction interaction) {
        Class<? extends Interaction> interactionClass = interaction.getClass();
        Method performMethod = ReflectionHelpers.getMethod(interactionClass, "perform");
        List<Field> interactionClassFields = ReflectionHelpers.getDeclaredFields(interactionClass);

        if(performMethod.isAnnotationPresent(Log.class)) {
            String logAnnotationValue = ReflectionHelpers.getAnnotationValue(Log.class, performMethod);
            logAnnotationValue = StringHelpers.replacePlaceholdersIn(logAnnotationValue).withValuesFrom(interaction, interactionClassFields);
            LOGGER.info(logAnnotationValue);
        }
    }
}
