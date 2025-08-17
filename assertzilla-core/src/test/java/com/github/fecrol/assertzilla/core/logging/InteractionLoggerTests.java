package com.github.fecrol.assertzilla.core.logging;

import com.github.fecrol.assertzilla.core.annotations.Log;
import com.github.fecrol.assertzilla.core.interactions.Interaction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("When logging interactions with the InteractionLogger class")
public class InteractionLoggerTests {

    @Test
    @DisplayName("Log annotation value without placeholders should be logged successfully")
    void itShouldLogMessageForInteractionWithoutPlaceholders() {
        // Given
        Interaction interactionWithoutPlaceholders = new Interaction() {
            @Override
            @Log("Message with no placeholders")
            public void perform() {
            }
        };
        Logger logger = mock(Logger.class);
        InteractionLogger interactionLogger = new InteractionLogger(logger);
        // When
        interactionLogger.log(interactionWithoutPlaceholders);
        // Then
        verify(logger).info("Message with no placeholders");
    }

    @Test
    @DisplayName("""
            Log annotation values with placeholders matching a field in the interaction instance should be logged
            successfully with placeholders being replaced by the matching field value
            """)
    void itShouldLogMessageWithPlaceholderBeingReplacedByMatchingFiledFromInteractionInstance() {
        // Given
        Interaction interactionWithPlaceholders = new Interaction() {
            private final String placeholder = "I was replaced!";

            @Override
            @Log("Message with placeholder value: ${placeholder}")
            public void perform() {
            }
        };
        Logger logger = mock(Logger.class);
        InteractionLogger interactionLogger = new InteractionLogger(logger);
        // When
        interactionLogger.log(interactionWithPlaceholders);
        // Then
        verify(logger).info("Message with placeholder value: I was replaced!");
    }

    @Test
    @DisplayName("""
            Log annotation value with placeholder not matching any field in the interaction instance should be logged
            successfully with placeholders not being replaced
            """)
    void itShouldLogMessageWithPlaceholderNotHavingMatchingFieldInInteractionInstance() {
        // Given
        Interaction interactionWithPlaceholders = new Interaction() {
            @Override
            @Log("Message with placeholder value: ${placeholder}")
            public void perform() {
            }
        };
        Logger logger = mock(Logger.class);
        InteractionLogger interactionLogger = new InteractionLogger(logger);
        // When
        interactionLogger.log(interactionWithPlaceholders);
        // Then
        verify(logger).info("Message with placeholder value: ${placeholder}");
    }

    @Test
    @DisplayName("Nothing should be logged for interactions without Log annotation being present")
    void itShouldNotCallLoggerInfoMethodForInteractionsWithoutLogAnnotation() {
        // Given
        Interaction interactionWithoutLogAnnotation = new Interaction() {
            @Override
            public void perform() {
            }
        };
        Logger logger = mock(Logger.class);
        InteractionLogger interactionLogger = new InteractionLogger(logger);
        // When
        interactionLogger.log(interactionWithoutLogAnnotation);
        // Then
        verifyNoInteractions(logger);
    }
}
