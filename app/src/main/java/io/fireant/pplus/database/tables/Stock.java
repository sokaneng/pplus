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
 * Created by engsokan on 8/12/18.
 */

@Entity(foreignKeys = {
        @ForeignKey(entity = Product.class,parentColumns = "id", childColumns = "pro_id"),
        @ForeignKey(entity = Currency.class, parentColumns = "id", childColumns = "currency_id")})

@TypeConverters(DateConverter.class)
public class Stock {

    @PrimaryKey
    @NonNull
    public String id;

    @ColumnInfo(name = "pro_id")
    public String proId;

    public int quantity;

    @ColumnInfo(name = "currency_id")
    public String currencyId;

    public Double pricePerUnit;

    public Date createDate;
}
