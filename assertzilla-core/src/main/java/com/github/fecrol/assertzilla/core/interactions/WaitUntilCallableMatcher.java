package com.github.fecrol.assertzilla.core.interactions;

import org.awaitility.Awaitility;
import org.hamcrest.Matcher;

import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.concurrent.Callable;

import static java.time.temporal.ChronoUnit.MILLIS;
import static java.time.temporal.ChronoUnit.SECONDS;

public class WaitUntilCallableMatcher<T> implements Interaction {

    private Callable<T> condition;
    private Matcher<? super T> matcher;
    private Duration waitTimeout;
    private Duration pollInterval;

    private WaitUntilCallableMatcher(Callable<T> condition, Matcher<? super T> matcher) {
        this.condition = condition;
        this.matcher = matcher;
        this.waitTimeout = Duration.of(5, SECONDS);
        this.pollInterval = Duration.of(100, MILLIS);
    }

    public static <T> WaitUntilCallableMatcher<T> condition(Callable<T> condition, Matcher<? super T> matcher) {
        return new WaitUntilCallableMatcher<>(condition, matcher);
    }

    public WaitUntilCallableMatcher<T> waitingNoLongerThan(long amount, TemporalUnit timeUnit) {
        this.waitTimeout = Duration.of(amount, timeUnit);
        return this;
    }

    public WaitUntilCallableMatcher<T> pollingEvery(long amount, TemporalUnit timeUnit) {
        this.pollInterval = Duration.of(amount, timeUnit);
        return this;
    }

    public WaitUntilCallableMatcher<T> orComplainWith(Exception exception) {
        return this;
    }

    @Override
    public void perform() {
        Awaitility.await().atMost(waitTimeout).pollInterval(pollInterval).until(condition, matcher);
    }
}
