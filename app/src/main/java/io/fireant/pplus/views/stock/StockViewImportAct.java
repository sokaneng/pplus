package io.fireant.pplus.views.stock;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.fireant.pplus.R;
import io.fireant.pplus.database.AppDatabase;
import io.fireant.pplus.database.tables.entities.StockImport;
import io.fireant.pplus.views.stock.adapter.StockViewImportCardAdapter;

public class StockViewImportAct extends AppCompatActivity {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private StockViewImportCardAdapter mAdapter;
    private AppDatabase mDb;
    private List<StockImport> stockImportList = new ArrayList<>();;
    private String stockId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_stock_view_import);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        stockId = intent.getStringExtra("STOCK_ID");

        mDb = AppDatabase.getDatabase(this);
        mAdapter = new StockViewImportCardAdapter(stockImportList);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (loadStockImport()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        });

        if (stockId != null) {
            loadStockImport();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    private boolean loadStockImport(){
        stockImportList.clear();
        stockImportList.addAll(mDb.stockImportDao().loadStockImportByStockId(stockId));
        mAdapter.notifyDataSetChanged();
        return true;
    }
}
