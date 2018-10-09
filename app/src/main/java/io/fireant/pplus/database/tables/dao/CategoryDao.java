package io.fireant.pplus.database.tables.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import java.util.List;
import io.fireant.pplus.database.tables.entities.Category;
import static android.arch.persistence.room.OnConflictStrategy.IGNORE;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by engsokan on 8/11/18.
 */

@Dao
public interface CategoryDao {

    @Insert(onConflict = IGNORE)
    void insertCategory(Category category);

    @Query("select * from category WHERE status = 1")
    List<Category> loadAllCategories();

    @Query("select * from category where mainCatId is null and status = 1")
    List<Category> loadAllMainCategory();

    @Query("select * from category where hasChild =:status and status = 1")
    List<Category> loadAllCategoryByIsMainCategoryStatus(int status);

    @Query("select * from category where categoryName like :filterStr and status = 1")
    List<Category> findByCategoryName(String filterStr);

    @Query("select * from category where mainCatId =:catId and status = 1")
    List<Category> findByCategoryIdHasChild(String catId);

    @Update(onConflict = REPLACE)
    void updateCategory(Category category);

}
