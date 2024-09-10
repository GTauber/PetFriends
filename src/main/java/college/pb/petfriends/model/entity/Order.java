package college.pb.petfriends.model.entity;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

import college.pb.petfriends.enums.OrderStatus;
import college.pb.petfriends.model.command.CreateOrderCommand;
import college.pb.petfriends.model.event.OrderCreatedEvent;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Aggregate
@Table(name = "orders")
@Entity
public class Order {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Default
    @AggregateIdentifier
    private String uuid = UUID.randomUUID().toString();
    private String customerId;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private Set<OrderItem> items;
    @Default
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.CREATED;
    private Double totalAmount;

    public double getTotalAmount(Order order) {
        return order.getItems().stream()
            .mapToDouble(item -> item.getPrice() * item.getQuantity())
            .sum();
    }

    @CommandHandler
    public Order(CreateOrderCommand command) {
        var order = command.getData();
        order.setTotalAmount(getTotalAmount(order));
        apply(new OrderCreatedEvent(order));
    }

    @EventSourcingHandler
    public void on(OrderCreatedEvent event) {
        var order = event.getData();
        this.uuid = order.getUuid();
        this.customerId = order.getCustomerId();
        this.items = order.getItems();
        this.status = order.getStatus();
        this.totalAmount = order.getTotalAmount();
    }

}
