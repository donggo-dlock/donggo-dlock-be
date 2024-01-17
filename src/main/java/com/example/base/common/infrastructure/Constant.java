package com.example.base.common.infrastructure;

public class Constant {
    public static final String REQUEST_MESSAGE_FORMAT = "Request: {} uri=[{}]";
    public static final String REQUEST_BODY_MESSAGE_FORMAT = "Request: {} uri=[{}] content-type=[{}] payload={}";
    public static final String RESPONSE_MESSAGE_FORMAT = "Response Payload: {}";

    private Constant() {
        throw new UnsupportedOperationException("Cannot instantiate class Constant");
    }
}
