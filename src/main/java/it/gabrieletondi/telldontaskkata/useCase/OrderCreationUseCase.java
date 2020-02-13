package it.gabrieletondi.telldontaskkata.useCase;

import it.gabrieletondi.telldontaskkata.domain.Order;
import it.gabrieletondi.telldontaskkata.domain.OrderStatus;
import it.gabrieletondi.telldontaskkata.domain.Product;
import it.gabrieletondi.telldontaskkata.repository.OrderRepository;
import it.gabrieletondi.telldontaskkata.repository.ProductCatalog;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.math.BigDecimal.valueOf;

public class OrderCreationUseCase {
    private final OrderRepository orderRepository;
    private final ProductCatalog productCatalog;

    public OrderCreationUseCase(OrderRepository orderRepository, ProductCatalog productCatalog) {
        this.orderRepository = orderRepository;
        this.productCatalog = productCatalog;
    }

    public void run(List<SellItemRequest> soldItems) {
        Order order = new Order(OrderStatus.CREATED, new ArrayList<>(), "EUR", new BigDecimal("0.00"), new BigDecimal("0.00"));

        for (SellItemRequest item : soldItems) {
            Product product = productCatalog.getByName(item.getProductName());

            if (product == null) {
                throw new UnknownProductException();
            } else {
                final BigDecimal taxedAmount = item.getTaxedAmount(product.getUnitTaxedAmount());
                final BigDecimal taxAmount = item.getTaxAmount(product.getUnitTax());

                order.addOrderItem(item, product, taxedAmount, taxAmount);
            }
        }

        orderRepository.save(order);
    }

}
