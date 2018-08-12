package io.fireant.pplus.database.tables.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import java.util.List;
import io.fireant.pplus.database.tables.Category;
import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

/**
 * Created by engsokan on 8/11/18.
 */

@Dao
public interface CategoryDao {

    @Insert(onConflict = IGNORE)
    void insertCategory(Category category);

    @Query("select * from category")
    List<Category> loadAllCategories();

    @Query("select * from category where mainCatId is null")
    List<Category> loadAllMainCategory();

    @Query("select * from category where mainCatId is not null")
    List<Category> loadAllSubCategory();

    @Query("select * from category where categoryName like :filterStr")
    List<Category> findByCategoryName(String filterStr);

    @Delete
    void deleteCategory(Category category);

}
