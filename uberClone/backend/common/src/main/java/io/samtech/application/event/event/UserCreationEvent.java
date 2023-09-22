package io.samtech.application.event.event;

import io.samtech.entity.rdb.User;
import lombok.Getter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

import java.io.Serial;


@Getter
@ToString
public class UserCreationEvent extends ApplicationEvent {
    @Serial
    private static final long serialVersionUID = -8053143381029977953L;
    private final User user;
    public UserCreationEvent(Object source, final User user) {
        super(source);
        this.user = user;
    }

}
