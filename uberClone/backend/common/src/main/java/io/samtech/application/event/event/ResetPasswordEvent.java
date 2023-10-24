package io.samtech.application.event.event;

import io.samtech.entity.models.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
@ToString
public class ResetPasswordEvent extends ApplicationEvent {
    private final User user;

    public ResetPasswordEvent(Object source, final User user){
        super(source);
        this.user = user;
    }
}
