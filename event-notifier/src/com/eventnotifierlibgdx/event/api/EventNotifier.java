package com.eventnotifierlibgdx.event.api;

/**
 * Notifies event handlers of events.
 */
public interface EventNotifier
{
    /**
     * Notify all registered event handlers of the given event.
     */
    void notifyEvent(Event event);
}
