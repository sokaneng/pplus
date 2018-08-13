package io.fireant.pplus.database.dto;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.TypeConverters;

import java.util.Date;

import io.fireant.pplus.database.utility.DateConverter;

/**
 * Created by engsokan on 8/12/18.
 */

public class ProductDetailQuery {

    public String id;

    public String productName;

    @ColumnInfo(name = "cat_name")
    public String categoryName;

    public String code;

    @ColumnInfo(name = "currencyCode")
    public String currencyCode;

    public Double pricePerUnit;

    @TypeConverters(DateConverter.class)
    public Date createDate;

    public int quantity;

    @ColumnInfo(name = "stock_create_date")
    @TypeConverters(DateConverter.class)
    public Date importDate;
}
