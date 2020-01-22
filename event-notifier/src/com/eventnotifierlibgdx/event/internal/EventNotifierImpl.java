package com.eventnotifierlibgdx.event.internal;

import com.eventnotifierlibgdx.event.api.Event;
import com.eventnotifierlibgdx.event.api.EventNotifier;

import java.util.List;
import java.util.Map;

class EventNotifierImpl implements EventNotifier
{
    private final Map<String, List<EventHandler>> eventHandlerRegistry;

    public EventNotifierImpl(
        final Map<String, List<EventHandler>> eventHandlerRegistry)
    {
        this.eventHandlerRegistry = eventHandlerRegistry;
    }

    @Override
    public void notifyEvent(
        final Event event)
    {
        final String eventName = event.getEventName();

        if (eventHandlerRegistry.containsKey(eventName))
        {
            eventHandlerRegistry
                .get(eventName)
                .forEach(eventHandler -> eventHandler.handleEvent(event));
        }
    }
}
