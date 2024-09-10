package college.pb.petfriends.model.event;

import college.pb.petfriends.model.entity.Order;

public class OrderCreatedEvent extends BaseEvent<Order> {

    public OrderCreatedEvent(Order data) {
        super(data);
    }
}
