package it.gabrieletondi.telldontaskkata.useCase;

import it.gabrieletondi.telldontaskkata.domain.Product;

import java.math.BigDecimal;

import static java.math.RoundingMode.HALF_UP;

public class SellItemRequest {
    private int quantity;
    private String productName;
    private Product product;

    public SellItemRequest(String productName, int quantity) {

        this.productName = productName;
        this.quantity = quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProduct(Product product){

        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getProductName() {
        return productName;
    }

    BigDecimal getTaxedAmount(BigDecimal unitaryTaxedAmount) {
        return unitaryTaxedAmount.multiply(BigDecimal.valueOf(quantity)).setScale(2, HALF_UP);
    }

    BigDecimal getTaxAmount(BigDecimal unitaryTax) {
        return unitaryTax.multiply(BigDecimal.valueOf(getQuantity()));
    }
}
