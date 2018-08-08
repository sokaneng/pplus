package io.fireant.pplus.views.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.fireant.pplus.R;
import io.fireant.pplus.views.main.adapter.FragmentPagerAdapter;

public class MainAct extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.main_vp)
    ViewPager mViewPager;

    @BindView(R.id.main_tap)
    TabLayout mTab;

    private ActionBar actionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        ButterKnife.bind(MainAct.this);
        actionBar = getSupportActionBar();

        FragmentPagerAdapter pagerAdapter = new FragmentPagerAdapter(this, getSupportFragmentManager());
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setOnPageChangeListener(this);
        mTab.setupWithViewPager(mViewPager);
        setUpTabIcon();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.dashboard_menu, menu);
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
//        switch (position) {
//            case 0:
//                actionBar.setTitle(R.string.dashboard);
//                break;
//            case 1:
//                actionBar.setTitle(R.string.sale);
//                break;
//            case 2:
//                actionBar.setTitle(R.string.stock);
//                break;
//            case 3:
//                actionBar.setTitle(R.string.inventory);
//                break;
//        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
