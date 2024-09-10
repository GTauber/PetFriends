package college.pb.petfriends.service;

import college.pb.petfriends.model.command.CreateOrderCommand;
import college.pb.petfriends.model.entity.Order;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderCommandService {

    private final CommandGateway commandGateway;

    public CompletableFuture<String> createOrder(Order order) {
        log.info("OrderCommandService.createOrder: {}", order);
        return commandGateway.send(new CreateOrderCommand(order));
    }

}
