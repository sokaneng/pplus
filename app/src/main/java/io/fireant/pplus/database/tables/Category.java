package io.fireant.pplus.database.tables;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;
import java.util.Date;

import io.fireant.pplus.database.utility.DateConverter;

/**
 * Created by engsokan on 8/11/18.
 */

@Entity
@TypeConverters(DateConverter.class)
public class Category {

    @PrimaryKey
    @NonNull
    public String id;

    public String categoryName;

    public String mainCatId;

    public Date createDate;

    public int status;
}
