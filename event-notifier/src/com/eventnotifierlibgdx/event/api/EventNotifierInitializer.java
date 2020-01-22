package com.eventnotifierlibgdx.event.api;

/**
 * Initializes event notifiers.
 */
public interface EventNotifierInitializer
{
    /**
     * Initializes an event notifier. This event notifier will notify all of the event handling methods marked
     * with @HandlesEvent in the given objects. A reference to the event notifier is injected into all of the
     * EventNotifier fields marked with @InjectEventNotifier in the given objects.
     */
    void initialize(Object... objects);
}
