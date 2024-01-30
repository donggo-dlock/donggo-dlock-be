package com.example.base.common.infrastructure;

public class Constant {
    public static final String REQUEST_MESSAGE_FORMAT = "Request: {} uri=[{}]";
    public static final String REQUEST_BODY_MESSAGE_FORMAT = "Request: {} uri=[{}] content-type=[{}] payload={}";
    public static final String RESPONSE_MESSAGE_FORMAT = "Response Payload: {}";

    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";
    public static final String ZONE_ID = "Asia/Seoul";

    private Constant() {
        throw new UnsupportedOperationException("Cannot instantiate class Constant");
    }
}
