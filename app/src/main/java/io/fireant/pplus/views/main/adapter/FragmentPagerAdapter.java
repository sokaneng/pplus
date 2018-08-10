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
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = Fragment.instantiate(mContext, DashboardAct.class.getName());
                break;
            case 1:
                fragment = Fragment.instantiate(mContext, SaleAct.class.getName());
                break;
            case 2:
                fragment = Fragment.instantiate(mContext, StockAct.class.getName());
                break;
            case 3:
                fragment = Fragment.instantiate(mContext, InventoryAct.class.getName());
                break;
        }
        return fragment;
    }

    // This determines the number of tabs
    @Override
    public int getCount() {
        return 4;
    }


}
