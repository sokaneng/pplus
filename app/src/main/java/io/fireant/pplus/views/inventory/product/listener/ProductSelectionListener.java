package io.fireant.pplus.views.inventory.product.listener;

import io.fireant.pplus.database.tables.entities.Product;

public interface ProductSelectionListener {
    void selectedProduct(Product product);
}
