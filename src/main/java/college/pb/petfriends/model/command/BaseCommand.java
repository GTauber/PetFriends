package college.pb.petfriends.model.command;

import java.util.UUID;
import lombok.Builder.Default;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@SuperBuilder
public abstract class BaseCommand<T> {

    @TargetAggregateIdentifier
    @Default
    private String uuid = UUID.randomUUID().toString();
    private T data;

    protected BaseCommand(T data) {
        this.data = data;
    }
}
