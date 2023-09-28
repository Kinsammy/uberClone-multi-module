package io.samtech.application.event.publisher;

import io.samtech.application.event.event.UserCreationEvent;
import io.samtech.entity.rdb.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class UserEventPublisher {
    private final ApplicationEventPublisher eventPublisher;

    public void publishVerificationEvent(final User user){
      log.info("Publishing custom event...");
      final UserCreationEvent verifyEmail = new UserCreationEvent(this, user);
      eventPublisher.publishEvent(verifyEmail);
    }
}