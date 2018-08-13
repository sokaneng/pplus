package io.fireant.pplus.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import io.fireant.pplus.database.tables.entities.Category;
import io.fireant.pplus.database.tables.entities.Currency;
import io.fireant.pplus.database.tables.entities.Product;
import io.fireant.pplus.database.tables.entities.Stock;
import io.fireant.pplus.database.tables.dao.CategoryDao;
import io.fireant.pplus.database.tables.dao.CurrencyDao;
import io.fireant.pplus.database.tables.dao.ProductDao;
import io.fireant.pplus.database.tables.dao.StockDao;

/**
 * Created by engsokan on 8/11/18.
 */

@Database(entities = {
        Category.class,
        Product.class,
        Currency.class,
        Stock.class}
        , version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase db;

    public abstract CategoryDao categoryDao();

    public abstract ProductDao productDao();

    public abstract CurrencyDao currencyDao();

    public abstract StockDao stockDao();

    public static AppDatabase getDatabase(Context context) {
        if (db == null) {
            db = Room.databaseBuilder(context,
                    AppDatabase.class, "pplus")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries().build();
        }
        return db;
    }
}
