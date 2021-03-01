package com.stone.sentiment.manager;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class DefaultTimeManager implements TimeManager {

    private final AtomicLong minimumDayFloor = new AtomicLong(
            LocalDateTime.now().minusDays(30).toInstant(ZoneOffset.of("+8")).toEpochMilli()
    );

    @Override
    public void updateMinimumDayFloor(long timestamp) {
        this.minimumDayFloor.getAndSet(timestamp);
    }

    @Override
    public LocalDateTime getValidDayFloor(long timestamp) {
        long validTimestamp = Math.max(minimumDayFloor.get(), timestamp);
        return LocalDateTime
                .ofInstant(
                        Instant.ofEpochMilli(validTimestamp),
                        ZoneId.systemDefault()
                );
    }
}
