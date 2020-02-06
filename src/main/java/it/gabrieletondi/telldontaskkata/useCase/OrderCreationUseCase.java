package it.gabrieletondi.telldontaskkata.useCase;

import it.gabrieletondi.telldontaskkata.domain.Order;
import it.gabrieletondi.telldontaskkata.domain.OrderStatus;
import it.gabrieletondi.telldontaskkata.domain.Product;
import it.gabrieletondi.telldontaskkata.repository.OrderRepository;
import it.gabrieletondi.telldontaskkata.repository.ProductCatalog;

import java.math.BigDecimal;
import java.util.ArrayList;

import static java.math.BigDecimal.valueOf;

public class OrderCreationUseCase {
    private final OrderRepository orderRepository;
    private final ProductCatalog productCatalog;

    public OrderCreationUseCase(OrderRepository orderRepository, ProductCatalog productCatalog) {
        this.orderRepository = orderRepository;
        this.productCatalog = productCatalog;
    }

    public void run(SellItemsRequest request) {
        Order order = new Order(OrderStatus.CREATED, new ArrayList<>(), "EUR", new BigDecimal("0.00"), new BigDecimal("0.00"));

        for (SellItemRequest itemRequest : request.getRequests()) {
            Product product = productCatalog.getByName(itemRequest.getProductName());

            if (product == null) {
                throw new UnknownProductException();
            } else {
                final BigDecimal unitaryTax = product.getUnitaryTax();
                final BigDecimal unitaryTaxedAmount = product.getUnitaryTaxedAmount(unitaryTax);
                final BigDecimal taxedAmount = itemRequest.getTaxedAmount(unitaryTaxedAmount);
                final BigDecimal taxAmount = itemRequest.getTaxAmount(unitaryTax);

                order.addOrderItem(itemRequest, product, taxedAmount, taxAmount);
            }
        }

        orderRepository.save(order);
    }

}
