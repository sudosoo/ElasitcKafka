package com.sudosoo.takeItEasy.domain.repository;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class EmitterRepositoryImpl implements EmitterRepository{
    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();
    private final Map<String, Object> eventCache = new ConcurrentHashMap<>();

    @Override
    public SseEmitter save(String receiverName, SseEmitter sseEmitter) {
        emitters.put(receiverName, sseEmitter);
        return sseEmitter;
    }

    @Override
    public void saveEventCache(String receiverName, Object event) {
        eventCache.put(receiverName, event);
    }

    @Override
    public Map<String, SseEmitter> findAllEmitterStartWithByMemberId(String memberName) {
        return emitters.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(memberName))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public Map<String, Object> findAllEventCacheStartWithByMemberId(String memberId) {
        return eventCache.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(memberId))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public void deleteByEmitterCreatedTimeWithMemberName(String EmitterCreatedTimeWithMemberName) {
        emitters.remove(EmitterCreatedTimeWithMemberName);
    }

    @Override
    public void deleteAllEmitterStartWithId(String memberId) {
        emitters.forEach(
                (key, emitter) -> {
                    if (key.startsWith(memberId)) {
                        emitters.remove(key);
                    }
                }
        );
    }

    @Override
    public void deleteAllEventCacheStartWithId(String memberId) {
        eventCache.forEach(
                (key, emitter) -> {
                    if (key.startsWith(memberId)) {
                        eventCache.remove(key);
                    }
                }
        );
    }
}
