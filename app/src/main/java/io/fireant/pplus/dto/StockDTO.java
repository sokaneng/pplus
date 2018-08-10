package io.fireant.pplus.dto;

/**
 * Created by engsokan on 8/9/18.
 */

public class StockDTO {
    private ProductDTO product;
    private int amountInStock;

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public int getAmountInStock() {
        return amountInStock;
    }

    public void setAmountInStock(int amountInStock) {
        this.amountInStock = amountInStock;
    }
}
