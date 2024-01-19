package com.example.base.reportable.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Reportable {
    private Long id;
    private String userInformation;
    private String password;
    private String content;
    private ActiveStatus status;
    private Long createdAt;
}
