package com.example.base.common.infrastructure;

import com.example.base.common.service.port.ClockHolder;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component
public class SystemClockHolder implements ClockHolder {

    private Clock clock;

    @PostConstruct
    public void init() {
        this.clock = Clock.system(ZoneId.of(Constant.ZONE_ID));
    }
    @Override
    public long millis() {
        return clock.millis();
    }

    @Override
    public String date() {
        return transferToDateFormat(Constant.DATE_FORMAT, millis());
    }

    @Override
    public String dateTime(Long millis) {
        return transferToDateFormat(Constant.DATE_TIME_FORMAT, millis);
    }

    private String transferToDateFormat(String dateFormat, Long millis) {

        // 밀리세컨드 값을 Instant 객체로 변환합니다.
        Instant instant = Instant.ofEpochMilli(millis);

        // 날짜 형식을 정의합니다.
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat).withZone(ZoneId.of(Constant.ZONE_ID));

        // Instant를 포맷된 문자열로 변환합니다.
        return formatter.format(instant);
    }
}
