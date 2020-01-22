package com.eventnotifierlibgdx.event.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates methods which should be used for event handling.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HandlesEvent
{
    /**
     * The name of the event which should be handled by this method.
     */
    String value() default "";
}
