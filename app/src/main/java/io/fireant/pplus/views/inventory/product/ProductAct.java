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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;

import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.fireant.pplus.R;
import io.fireant.pplus.common.PPlusDialog;
import io.fireant.pplus.database.AppDatabase;
import io.fireant.pplus.database.tables.entities.Product;
import io.fireant.pplus.views.inventory.product.adapter.ProductAdapter;

/**
 * Created by engsokan on 8/9/18.
 */

public class ProductAct extends AppCompatActivity implements MaterialSearchBar.OnSearchActionListener, TextWatcher {

    @BindView(R.id.searchBar)
    MaterialSearchBar mSearchBar;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private ProductAdapter mAdapter;
    private List<Product> productList = new ArrayList<>();
    private AppDatabase mDb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_product);
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

        mAdapter = new ProductAdapter(productList, new ProductAdapter.OnItemClickListener() {
            @Override
            public void onDeleteItemClick(final Product product, final int position) {
                new PPlusDialog(ProductAct.this, new PPlusDialog.PPlusDialogListener() {
                    @Override
                    public void onPositiveClicked() {
                        mDb.productDao().deleteProduct(product);
                        productList.remove(position);
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onNegativeClicked() {

                    }
                }).confirmDeleteDialog(
                        getResources().getString(R.string.do_you_want_to_delete),
                        getResources().getString(R.string.yes),
                        getResources().getString(R.string.cancel));

            }
        });
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            startActivity(new Intent(getApplicationContext(), ProductAddAct.class));
        } else {
            onBackPressed();
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.category_menu, menu);
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
