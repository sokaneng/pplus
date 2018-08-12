package io.fireant.pplus.database.tables;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;
import java.util.Date;
import io.fireant.pplus.database.utility.DateConverter;

/**
 * Created by engsokan on 8/11/18.
 */

@Entity(foreignKeys = {
        @ForeignKey(entity = Category.class,
                parentColumns = "id",
                childColumns = "cat_id")})

@TypeConverters(DateConverter.class)
public class Product {

    @PrimaryKey
    @NonNull
    public String id;

    public String productName;

    @ColumnInfo(name = "cat_id")
    public String catId;

    public String code;

    public Date createDate;

    public int status;
}
