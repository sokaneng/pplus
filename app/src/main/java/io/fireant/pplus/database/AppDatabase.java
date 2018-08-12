package io.fireant.pplus.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import io.fireant.pplus.database.tables.Category;
import io.fireant.pplus.database.tables.Product;
import io.fireant.pplus.database.tables.dao.CategoryDao;

/**
 * Created by engsokan on 8/11/18.
 */

@Database(entities = {Category.class, Product.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase db;

    public abstract CategoryDao categoryDao();

    public static AppDatabase getDatabase(Context context) {
        if (db == null) {
            db = Room.databaseBuilder(context,
                    AppDatabase.class, "pplus").allowMainThreadQueries().build();
        }
        return db;
    }
}
