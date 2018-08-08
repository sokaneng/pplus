package io.fireant.pplus.views.main.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import io.fireant.pplus.views.stock.StockAct;
import io.fireant.pplus.views.dashboard.DashboardAct;
import io.fireant.pplus.views.inventory.InventoryAct;
import io.fireant.pplus.views.sale.SaleAct;

public class FragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {

    private Context mContext;

    public FragmentPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    // This determines the fragment for each tab
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new DashboardAct();
        } else if (position == 1) {
            return new SaleAct();
        } else if (position == 2) {
            return new StockAct();
        } else {
            return new InventoryAct();
        }
    }

    // This determines the number of tabs
    @Override
    public int getCount() {
        return 4;
    }

}
