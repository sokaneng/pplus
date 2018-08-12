package io.fireant.pplus.views.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.fireant.pplus.R;
import io.fireant.pplus.database.AppDatabase;
import io.fireant.pplus.database.tables.Category;
import io.fireant.pplus.database.utility.DatabaseInitializer;
import io.fireant.pplus.views.dashboard.DashboardAct;
import io.fireant.pplus.views.inventory.InventoryAct;
import io.fireant.pplus.views.main.adapter.FragmentPagerAdapter;
import io.fireant.pplus.views.sale.SaleAct;
import io.fireant.pplus.views.stock.StockAct;

public class MainAct extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.main_vp)
    ViewPager mViewPager;

    @BindView(R.id.main_tap)
    TabLayout mTab;

    FragmentPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        ButterKnife.bind(MainAct.this);

        pagerAdapter = new FragmentPagerAdapter(this, getSupportFragmentManager());
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setOnPageChangeListener(this);
        mTab.setupWithViewPager(mViewPager);
        setUpTabIcon();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    private void setUpTabIcon() {
        mTab.getTabAt(0).setIcon(R.drawable.ic_view_dashboard_black_24dp);
        mTab.getTabAt(1).setIcon(R.drawable.ic_cart_outline_black_24dp);
        mTab.getTabAt(2).setIcon(R.drawable.ic_buffer_black_24dp);
        mTab.getTabAt(3).setIcon(R.drawable.ic_briefcase_plus_black_24dp);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        switch (position) {
            case 0:
                if (fragments != null) {
                    for (Fragment fragment : fragments) {
                        if (fragment instanceof DashboardAct) {
                            ((DashboardAct) fragment).loadFragment();
                            break;
                        }
                    }
                }
                break;
            case 1:
                if (fragments != null) {
                    for (Fragment fragment : fragments) {
                        if (fragment instanceof SaleAct) {
                            ((SaleAct) fragment).loadFragment();
                            break;
                        }
                    }
                }
                break;
            case 2:
                if (fragments != null) {
                    for (Fragment fragment : fragments) {
                        if (fragment instanceof StockAct) {
                            ((StockAct) fragment).loadFragment();
                            break;
                        }
                    }
                }
                break;
            case 3:
                if (fragments != null) {
                    for (Fragment fragment : fragments) {
                        if (fragment instanceof InventoryAct) {
                            ((InventoryAct) fragment).loadFragment();
                            break;
                        }
                    }
                }
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}
