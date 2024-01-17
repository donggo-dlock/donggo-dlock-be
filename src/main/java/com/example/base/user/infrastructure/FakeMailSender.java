package com.example.base.user.infrastructure;


import com.example.base.user.service.port.MailSender;
import org.springframework.stereotype.Component;

@Component
public class FakeMailSender implements MailSender {

    public String email;
    public String title;
    public String content;

    @Override
    public void send(String email, String title, String content) {
        this.email = email;
        this.title = title;
        this.content = content;
    }
}
