package com.github.fecrol.assertzilla.core.interactions;

import org.hamcrest.Matcher;

import java.util.concurrent.Callable;

public class Wait {

    public static WaitUntilCallableBoolean until(Callable<Boolean> condition) {
        return WaitUntilCallableBoolean.condition(condition);
    }

    public static <T> WaitUntilCallableMatcher<T> until(Callable<T> condition, Matcher<? super T> matcher) {
        return WaitUntilCallableMatcher.condition(condition, matcher);
    }
}
