package io.fireant.pplus.database.tables.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import java.util.List;
import io.fireant.pplus.database.tables.entities.Currency;
import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

/**
 * Created by engsokan on 8/12/18.
 */

@Dao
public interface CurrencyDao {

    @Insert(onConflict = IGNORE)
    void insertCurrency(Currency currency);

    @Delete
    void deleteCurrency(Currency currency);

    @Query("select * from currency")
    List<Currency> loadAllCurrency();
}
