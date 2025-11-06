package br.com.joao.service;

import br.com.joao.controller.dto.BookingResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

import java.io.UncheckedIOException;

@ApplicationScoped
public class BookingExpirationService {

    private static final int expirationCheckSeconds = 10; // 15 minutes

    private final String queueUrl;
    private final SqsClient sqsClient;
    private final ObjectMapper objectMapper;

    public BookingExpirationService(@ConfigProperty(name = "queue.check-booking-pending-state.url") String queueUrl,
                                    SqsClient sqsClient,
                                    ObjectMapper objectMapper) {
        this.queueUrl = queueUrl;
        this.sqsClient = sqsClient;
        this.objectMapper = objectMapper;
    }

    public void scheduleExpirationCheck(Long bookingId) {

        try {
            var dto = new BookingResponseDto(bookingId);
            var body = objectMapper.writeValueAsString(dto);

            sqsClient.sendMessage(SendMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .delaySeconds(expirationCheckSeconds)
                    .messageBody(body)
                    .build()
            );
        } catch (JsonProcessingException e) {
            throw new UncheckedIOException(e);
        }
    }
}
