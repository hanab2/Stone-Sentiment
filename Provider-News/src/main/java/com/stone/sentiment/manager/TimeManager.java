package com.stone.sentiment.manager;

import java.time.LocalDateTime;

public interface TimeManager {

    void updateMinimumDayFloor(long timestamp);

    LocalDateTime getValidDayFloor(long timestamp);
}
