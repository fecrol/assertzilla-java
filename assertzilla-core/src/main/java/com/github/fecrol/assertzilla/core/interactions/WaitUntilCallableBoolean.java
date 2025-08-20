package com.github.fecrol.assertzilla.core.interactions;

import org.awaitility.Awaitility;

import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.concurrent.Callable;

import static java.time.temporal.ChronoUnit.*;

public class WaitUntilCallableBoolean implements Interaction {

    private Callable<Boolean> condition;
    private Duration waitTimeout;
    private Duration pollInterval;

    private WaitUntilCallableBoolean(Callable<Boolean> condition) {
        this.condition = condition;
        this.waitTimeout = Duration.of(5, SECONDS);
        this.pollInterval = Duration.of(100, MILLIS);
    }

    public static WaitUntilCallableBoolean condition(Callable<Boolean> condition) {
        return new WaitUntilCallableBoolean(condition);
    }

    public WaitUntilCallableBoolean waitingNoLongerThan(long amount, TemporalUnit timeUnit) {
        this.waitTimeout = Duration.of(amount, timeUnit);
        return this;
    }

    public WaitUntilCallableBoolean pollingEvery(long amount, TemporalUnit timeUnit) {
        this.pollInterval = Duration.of(amount, timeUnit);
        return this;
    }

    public WaitUntilCallableBoolean orComplainWith(Exception exception) {
        return this;
    }

    @Override
    public void perform() {
        Awaitility.await().atMost(waitTimeout).pollInterval(pollInterval).until(condition);
    }
}
