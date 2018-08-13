package io.fireant.pplus.views.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.fireant.pplus.R;
import io.fireant.pplus.database.AppDatabase;
import io.fireant.pplus.database.utility.DatabaseInitializer;
import io.fireant.pplus.views.dashboard.DashboardAct;
import io.fireant.pplus.views.inventory.InventoryAct;
import io.fireant.pplus.views.main.adapter.FragmentPagerAdapter;
import io.fireant.pplus.views.sale.SaleAct;
import io.fireant.pplus.views.stock.StockAct;

public class MainAct extends AppCompatActivity{

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
        mTab.setupWithViewPager(mViewPager);
        setUpTabIcon();

        //Initialize database data
        AppDatabase appDatabase = AppDatabase.getDatabase(this);
        DatabaseInitializer.populateAsync(appDatabase);

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
    protected void onResume() {
        super.onResume();
        if (mViewPager.getCurrentItem() == 2) {
            List<Fragment> fragments = getSupportFragmentManager().getFragments();
            if (fragments != null) {
                for (Fragment fragment : fragments) {
                    if (fragment instanceof StockAct) {
                        ((StockAct) fragment).reloadStock();
                        break;
                    }
                }
            }
        }
    }
}
