package com.example.base.mock;

import com.example.base.common.service.port.ClockHolder;
import lombok.Builder;

@Builder
public class TestClockHolder implements ClockHolder {

    public long millis;

    public TestClockHolder(long millis) {
        this.millis = millis;
    }

    @Override
    public long millis() {
        return millis;
    }

    @Override
    public String date() {
        return null;
    }

    @Override
    public String dateTime(Long millis) {
        return "2024-01-23 00:00";
    }
}
