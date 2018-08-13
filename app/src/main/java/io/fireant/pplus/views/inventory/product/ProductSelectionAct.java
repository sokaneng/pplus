package io.fireant.pplus.views.inventory.product;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.fireant.pplus.R;
import io.fireant.pplus.database.AppDatabase;
import io.fireant.pplus.database.tables.entities.Product;
import io.fireant.pplus.views.inventory.product.adapter.ProductSelectionAdapter;
import io.fireant.pplus.views.inventory.product.listener.ProductSelectionListener;

public class ProductSelectionAct extends AppCompatActivity implements MaterialSearchBar.OnSearchActionListener, TextWatcher {

    @BindView(R.id.searchBar)
    MaterialSearchBar mSearchBar;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private ProductSelectionAdapter mAdapter;
    private List<Product> productList = new ArrayList<>();
    private AppDatabase mDb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_product_selection);
        ButterKnife.bind(this);

        mDb = AppDatabase.getDatabase(getApplicationContext());
        mSearchBar.setOnSearchActionListener(this);
        mSearchBar.addTextChangeListener(this);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (loadProduct()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        });
        mAdapter = new ProductSelectionAdapter(productList, new ProductSelectionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Product product, int position) {
                new ProductSelectionListener() {
                    @Override
                    public void selectedProduct(Product product) {

                    }
                }.selectedProduct(product);
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    private boolean loadProduct() {
        productList.clear();
        productList.addAll(mDb.productDao().loadAllProduct());
        mAdapter.notifyDataSetChanged();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadProduct();
    }

    @Override
    public void onSearchStateChanged(boolean enabled) {

    }

    @Override
    public void onSearchConfirmed(CharSequence text) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        filterProduct(text.toString());
    }

    @Override
    public void onButtonClicked(int buttonCode) {
        switch (buttonCode) {
            case MaterialSearchBar.BUTTON_BACK:
                mSearchBar.disableSearch();
                break;
        }
    }

    private void filterProduct(String filterStr) {
        productList.clear();
        productList.addAll(mDb.productDao().findByProductNameOrCode("%" + filterStr + "%"));
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (mSearchBar.getText().isEmpty()) {
            loadProduct();
        }
    }
}
