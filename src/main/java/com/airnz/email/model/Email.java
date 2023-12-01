package com.airnz.email.model;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public class Email extends BaseEmail implements EmailOnCreate {
    @NotNull(groups = {EmailOnCreate.class})
    private String sender;

    @NotNull(groups = {EmailOnCreate.class})
    private List<String> recipients;

    public Email() {
    }

    @Override
    public String getSender() {
        return sender;
    }

    @Override
    public void setSender(String sender) {
        this.sender = sender;
    }

    @Override
    public List<String> getRecipients() {
        return recipients;
    }

    @Override
    public void setRecipients(List<String> recipients) {
        this.recipients = recipients;
    }
}
