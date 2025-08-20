package com.github.fecrol.assertzilla.selenium.junit.extensions;

import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(AssertzillaJunitSeleniumExtension.class)
public @interface AssertzillaSeleniumTest {
}
