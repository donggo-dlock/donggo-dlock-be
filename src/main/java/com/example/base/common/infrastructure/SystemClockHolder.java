package com.example.base.common.infrastructure;

import com.example.base.common.service.port.ClockHolder;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component
public class SystemClockHolder implements ClockHolder {
    @Override
    public long millis() {
        return Clock.systemUTC().millis();
    }

    @Override
    public String date() {
        // 현재 UTC 시간의 밀리세컨드 값을 얻습니다.
        long millis = Clock.systemUTC().millis();

        // 밀리세컨드 값을 Instant 객체로 변환합니다.
        Instant instant = Instant.ofEpochMilli(millis);

        // 날짜 형식을 정의합니다. "YY-MM-DD"가 아닌 "yyyy-MM-dd"를 사용해야 합니다.
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(ZoneId.of("UTC"));

        // Instant를 포맷된 문자열로 변환합니다.
        return formatter.format(instant);
    }


}
