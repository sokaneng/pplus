package io.fireant.pplus.database.dto;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.TypeConverters;
import java.util.Date;
import io.fireant.pplus.database.utility.DateConverter;

/**
 * Created by engsokan on 8/12/18.
 */

public class StockQuery {

    public String id;

    public int quantity;

    @TypeConverters(DateConverter.class)
    @ColumnInfo(name = "createDate")
    public Date importDate;

    public String productName;

    public String code;
}
