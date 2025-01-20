package com.qcc.hallow.service;

import com.qcc.hallow.model.EmailRequest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@PropertySource("classpath:application.properties")
@Service
public class EmailService {

    private final String API_KEY;
    private static final String SENDER_EMAIL = "no-reply@qcc.com";

    private final WebClient webClient;

    public EmailService(WebClient.Builder webClientBuilder, Environment env) {
        String API_URL = env.getProperty("smtp2go_api_url");
        this.API_KEY = env.getProperty("smtp2go_api_key");
        if (API_URL == null) {
            throw new IllegalStateException("smtp2go_api_url must be configured");
        }
        this.webClient = webClientBuilder.baseUrl(API_URL).build();
    }

    public void sendEmail(String to, String subject, String htmlBody) {
        EmailRequest emailRequest = new EmailRequest(SENDER_EMAIL, to, subject, htmlBody);

        webClient.post()
                .header("Content-Type", "application/json")
                .header("X-Smtp2go-Api-Key", API_KEY)
                .bodyValue(emailRequest)
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(error -> {
                    throw new RuntimeException("Failed to send email", error);
                })
                .subscribe();
    }

}
