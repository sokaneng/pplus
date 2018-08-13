package io.fireant.pplus.database.tables.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import io.fireant.pplus.database.dto.ProductDetailQuery;
import io.fireant.pplus.database.tables.entities.Product;
import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

/**
 * Created by engsokan on 8/12/18.
 */

@Dao
public interface ProductDao {

    @Insert(onConflict = IGNORE)
    void insertProduct(Product product);

    @Delete
    void deleteProduct(Product product);

    @Query("select * from product WHERE status = 1")
    List<Product> loadAllProduct();

    @Query("select * from product where status = 1 AND productName like :filterStr or code like :filterStr")
    List<Product> findByProductNameOrCode(String filterStr);

    @Query("select Product.id, Product.productName, Category.categoryName AS cat_name, Product.code, " +
            "Currency.currencyCode, Product.pricePerUnit, Product.createDate, SUM(Stock.quantity) " +
            "AS quantity, Stock.createDate AS stock_create_date FROM Product " +
            "INNER JOIN Category ON Product.cat_id = Category.id " +
            "INNER JOIN Currency ON Currency.currencyCode = Product.currency_code " +
            "INNER JOIN Stock ON Stock.pro_id = Product.id WHERE Product.status = 1 AND Stock.status = 1 " +
            "AND Product.id = :productId GROUP BY Product.productName")
    ProductDetailQuery findProductDetailById(String productId);

}
