package com.eventnotifierlibgdx.event.internal;

import com.eventnotifierlibgdx.event.api.Event;

/**
 * Handles events.
 *
 * TODO: Make this public when other event handlers need to be registered, besides the event handlers created by the
 * event notifier initializer.
 */
interface EventHandler
{
    /**
     * Handle the given event.
     */
    void handleEvent(Event event);
}
