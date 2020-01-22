package com.eventnotifierlibgdx.event.internal;

import com.eventnotifierlibgdx.event.api.Event;
import com.eventnotifierlibgdx.event.api.EventNotifier;
import com.eventnotifierlibgdx.event.api.EventNotifierInitializer;
import com.eventnotifierlibgdx.event.api.HandlesEvent;
import com.eventnotifierlibgdx.event.api.InjectEventNotifier;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EventNotifierInitializerImpl implements EventNotifierInitializer
{
    @Override
    public void initialize(final Object... objects)
    {
        final Map<String, List<EventHandler>> eventHandlerRegistry = new HashMap<>();
        final EventNotifier eventNotifier = new EventNotifierImpl(eventHandlerRegistry);

        final EventHandlerFactory eventHandlerFactory = new EventHandlerFactory();

        for (final Object object : objects)
        {
            for (final Method method : object.getClass().getMethods())
            {
                Arrays
                    .stream(method.getAnnotations())
                    .filter(annotation -> annotation instanceof HandlesEvent)
                    .map(annotation -> ((HandlesEvent) annotation).value())
                    .forEach(registeredEventName ->
                    {
                        final EventHandler eventHandler;

                        if (method.getParameterCount() == 0)
                        {
                            eventHandler = eventHandlerFactory.create(event -> method.invoke(object));
                        }
                        else
                        {
                            eventHandler = eventHandlerFactory.create(event -> method.invoke(object, event));
                        }

                        if (eventHandlerRegistry.containsKey(registeredEventName))
                        {
                            eventHandlerRegistry.get(registeredEventName).add(eventHandler);
                        }
                        else
                        {
                            final List<EventHandler> eventHandlers = new ArrayList<>();
                            eventHandlers.add(eventHandler);
                            eventHandlerRegistry.put(registeredEventName, eventHandlers);
                        }
                    });
            }

            for (final Field field : object.getClass().getDeclaredFields())
            {
                final boolean fieldIsMarkedAsInjectableEventNotifier = Arrays
                    .stream(field.getAnnotations())
                    .anyMatch(annotation -> annotation instanceof InjectEventNotifier);

                if (fieldIsMarkedAsInjectableEventNotifier)
                {
                    field.setAccessible(true);

                    try
                    {
                        field.set(object, eventNotifier);
                    }
                    catch (final IllegalAccessException e)
                    {
                        throw new RuntimeException(
                            "Event notifier initialization failed. Could not set event notifier field in class "
                                + object.getClass().getSimpleName(), e);
                    }
                }
            }
        }
    }

    private static class EventHandlerFactory
    {
        EventHandler create(
            final MethodInvoker methodInvoker)
        {
            return event ->
            {
                try
                {
                    methodInvoker.invokeMethod(event);
                }
                catch (final IllegalAccessException | InvocationTargetException e)
                {
                    throw new RuntimeException("Exception occurred while handling event.", e);
                }
            };
        }
    }

    @FunctionalInterface
    private interface MethodInvoker
    {
        void invokeMethod(Event event) throws IllegalAccessException, InvocationTargetException;
    }
}
