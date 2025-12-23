package com.caliban.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class EventBus {
    private static final EventBus INSTANCE = new EventBus();
    private final Map<Class<?>, List<Consumer<Object>>> subscribers = new HashMap<>();

    private EventBus() {}

    public static EventBus getInstance() {
        return INSTANCE;
    }

    @SuppressWarnings("unchecked")
    public <T> void subscribe(Class<T> eventType, Consumer<T> subscriber) {
        subscribers.computeIfAbsent(eventType, k -> new ArrayList<>()).add((Consumer<Object>) subscriber);
    }

    public void publish(Object event) {
        if (event == null) {
            return;
        }
        subscribers.getOrDefault(event.getClass(), new ArrayList<>())
            .forEach(subscriber -> subscriber.accept(event));
    }
}
