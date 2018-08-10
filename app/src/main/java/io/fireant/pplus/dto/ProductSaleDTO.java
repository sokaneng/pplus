package io.fireant.pplus.dto;

/**
 * Created by engsokan on 8/9/18.
 */

public class ProductSaleDTO {
    private ProductDTO product;
    private int qty;
    private double total;

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
