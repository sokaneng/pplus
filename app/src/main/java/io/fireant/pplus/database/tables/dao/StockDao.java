package io.fireant.pplus.database.tables.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import java.util.List;
import io.fireant.pplus.database.tables.Stock;
import io.fireant.pplus.database.tables.StockQuery;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

/**
 * Created by engsokan on 8/12/18.
 */

@Dao
public interface StockDao {

    @Insert(onConflict = IGNORE)
    void insertStock(Stock stock);

    @Query("select Stock.id, Stock.quantity, Stock.createDate, Product.productName, Product.code FROM Stock " +
            "INNER JOIN Product ON Stock.pro_id = Product.id WHERE Stock.status = 1 AND Product.status = 1")
    LiveData<List<StockQuery>> loadAllStock();

}
