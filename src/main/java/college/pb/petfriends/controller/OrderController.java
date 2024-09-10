package college.pb.petfriends.controller;

import college.pb.petfriends.model.entity.Order;
import college.pb.petfriends.service.OrderCommandService;
import college.pb.petfriends.service.OrderQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Order Commands", description = "API para operações de comando em pedidos")
public class OrderController {

    private final OrderCommandService orderCommandService;
    private final OrderQueryService orderQueryService;


    @PostMapping
    @Operation(summary = "Criar um novo pedido", description = "Cria um novo pedido com os itens especificados")
    public CompletableFuture<ResponseEntity<String>> createOrder(@RequestBody Order order) {
        return orderCommandService.createOrder(order)
            .thenApply(ResponseEntity::ok);
    }

    @GetMapping
    @Operation(summary = "Listar todos os pedidos", description = "Retorna uma lista de todos os pedidos")
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderQueryService.getOrderCreatedEvents());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pedido por ID", description = "Retorna um pedido específico com base no ID fornecido")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        return orderQueryService.getOrderById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

}
