package dev.abelab.timestamp.annotation;

import java.lang.annotation.*;

import org.junit.jupiter.api.Tag;

/**
 * Unit Test Interface
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Tag("UnitTest")
@Inherited
public @interface UnitTest {
}
