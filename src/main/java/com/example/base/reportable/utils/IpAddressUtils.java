package com.example.base.reportable.utils;

import com.example.base.common.service.port.ClockHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IpAddressUtils {

    private final ClockHolder clockHolder;

    public String from(final String ipAddress) {
        return new StringBuilder()
                .append(ipAddress)
                .append(":")
                .append(clockHolder.date())
                .toString();
    }
}
