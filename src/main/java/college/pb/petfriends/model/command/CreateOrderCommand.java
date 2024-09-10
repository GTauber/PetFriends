package college.pb.petfriends.model.command;

import college.pb.petfriends.model.entity.Order;

public class CreateOrderCommand extends BaseCommand<Order> {

    public CreateOrderCommand(Order data) {
        super(data);
    }
}
