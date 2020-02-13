package it.gabrieletondi.telldontaskkata.domain;

import it.gabrieletondi.telldontaskkata.useCase.SellItemRequest;

import java.math.BigDecimal;
import java.util.List;

public class Order {
    private BigDecimal total;
    private String currency;
    private List<OrderItem> items;
    private BigDecimal tax;
    private OrderStatus status;
    private int id;

    public Order(OrderStatus status, List<OrderItem> items, String Currency, BigDecimal total, BigDecimal tax){

        this.status = status;
        this.items = items;
        currency = Currency;
        this.total = total;
        this.tax = tax;
    }

    public Order() {

    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getCurrency() {
        return currency;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void addItem(OrderItem item) {
        items.add(item);
    }

    public void addTaxAmountToTax(BigDecimal taxAmount) {
        tax = tax.add(taxAmount);
    }

    public BigDecimal addTaxedAmountToTotal(BigDecimal taxedAmount) {
        return total.add(taxedAmount);
    }

    public void addOrderItem(SellItemRequest itemRequest, Product product, BigDecimal taxedAmount, BigDecimal taxAmount) {
        final OrderItem orderItem = new OrderItem(product, itemRequest.getQuantity(), taxAmount, taxedAmount);

        addItem(orderItem);

        setTotal(addTaxedAmountToTotal(taxedAmount));
        addTaxAmountToTax(taxAmount);
    }
}
