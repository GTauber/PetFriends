package college.pb.petfriends.model.event;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder.Default;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@SuperBuilder
public abstract class BaseEvent<T> {


    @TargetAggregateIdentifier
    @Default
    private String uuid = UUID.randomUUID().toString();
    private T data;
    private LocalDateTime timestamp;

    protected BaseEvent(T data) {
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }

}
