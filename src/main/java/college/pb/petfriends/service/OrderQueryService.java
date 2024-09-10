package college.pb.petfriends.service;

import college.pb.petfriends.model.entity.Order;
import college.pb.petfriends.model.event.OrderCreatedEvent;
import college.pb.petfriends.repository.OrderRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderQueryService {

    private final EventStore eventStore;
    private final OrderRepository orderRepository;

    public List<Order> getOrderCreatedEvents() {
        log.info("Fetching all OrderCreatedEvent events");
        return eventStore.readEvents(Order.class.getSimpleName())
            .asStream()
            .filter(event -> event.getPayload() instanceof OrderCreatedEvent)
            .map(event -> ((OrderCreatedEvent) event.getPayload()).getData())
            .toList();
    }

    public Optional<Order> getOrderById(Long orderId) {
        log.info("Fetching Order by id: {}", orderId);
        return orderRepository.findById(orderId);
    }


}
