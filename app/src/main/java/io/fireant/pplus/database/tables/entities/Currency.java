package io.fireant.pplus.database.tables.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by engsokan on 8/12/18.
 */

@Entity
public class Currency {

    @PrimaryKey
    @NonNull
    public String id;

    public String currencyCode;
}
