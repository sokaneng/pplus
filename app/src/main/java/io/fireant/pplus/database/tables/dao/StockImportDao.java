package io.fireant.pplus.database.tables.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import java.util.List;
import io.fireant.pplus.database.tables.entities.StockImport;
import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface StockImportDao {

    @Insert(onConflict = IGNORE)
    void insertStockImport(StockImport stockImport);

    @Query("select * from stockimport where status = 1 and stock_id = :stockId order by importDate desc")
    List<StockImport> loadStockImportByStockId(String stockId);
}
