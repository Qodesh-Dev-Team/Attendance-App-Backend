package com.qcc.hallow.model;

public record EmailRequest(String sender, String to, String subject, String html_body) {

}
