package com.eventnotifierlibgdx.event.api;

import java.util.Objects;

/**
 * Represents an event. This class can be extended if other event types are needed.
 */
public class Event
{
    private final String eventName;

    public Event(final String eventName)
    {
        this.eventName = eventName;
    }

    /**
     * @return the name of the event.
     */
    public String getEventName()
    {
        return eventName;
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Event event = (Event) o;
        return Objects.equals(eventName, event.eventName);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(eventName);
    }
}
