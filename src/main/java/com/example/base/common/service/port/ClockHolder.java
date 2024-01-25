package com.example.base.common.service.port;

public interface ClockHolder {
    long millis();
    String date();
    String dateTime(Long millis);
}
