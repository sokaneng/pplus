package io.fireant.pplus.database.tables.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import java.util.List;
import io.fireant.pplus.database.tables.entities.Stock;
import io.fireant.pplus.database.dto.StockQuery;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

/**
 * Created by engsokan on 8/12/18.
 */

@Dao
public interface StockDao {

    @Insert(onConflict = IGNORE)
    void insertStock(Stock stock);

    @Query("select Stock.id, SUM(Stock.quantity) AS quantity, Stock.createDate, Product.id AS productId, Product.productName, Product.code FROM Stock " +
            "INNER JOIN Product ON Stock.pro_id = Product.id WHERE Stock.status = 1 AND Product.status = 1 GROUP BY Product.productName")
    List<StockQuery> loadAllStock();

    @Query("select Stock.id, SUM(Stock.quantity) AS quantity, Stock.createDate, Product.id AS productId, Product.productName, Product.code FROM Stock " +
            "INNER JOIN Product ON Stock.pro_id = Product.id WHERE Stock.status = 1 " +
            "AND Product.status = 1 AND Product.productName LIKE :filterStr or Product.code like :filterStr GROUP BY Product.productName")
    List<StockQuery> findByProductNameOrCode(String filterStr);

}
