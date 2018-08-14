package io.fireant.pplus.views.stock;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fireant.pplus.R;
import io.fireant.pplus.database.AppDatabase;
import io.fireant.pplus.database.dto.StockQuery;
import io.fireant.pplus.views.inventory.product.ProductDetailAct;
import io.fireant.pplus.views.stock.adapter.StockCardAdapter;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class StockAct extends Fragment implements MaterialSearchBar.OnSearchActionListener, TextWatcher {

    @BindView(R.id.searchBar)
    MaterialSearchBar mSearchBar;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private StockCardAdapter mAdapter;
    private List<StockQuery> stockQueryList = new ArrayList<>();
    private AppDatabase mDb;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_stock, container, false);
        ButterKnife.bind(this, rootView);
        mDb = AppDatabase.getDatabase(getActivity());
        mSearchBar.setOnSearchActionListener(this);
        mSearchBar.addTextChangeListener(this);
        mSearchBar.clearFocus();
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (loadStock()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        });

        mAdapter = new StockCardAdapter(stockQueryList, new StockCardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String productId) {
                Intent intent = new Intent(getActivity(), ProductDetailAct.class);
                intent.putExtra("PRODUCT_ID", productId);
                startActivity(intent);
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        loadStock();

        return rootView;
    }

    public boolean loadStock() {
        stockQueryList.clear();
        stockQueryList.addAll(mDb.stockDao().loadAllStock());
        mAdapter.notifyDataSetChanged();
        return true;
    }

    @Override
    public void onSearchStateChanged(boolean enabled) {

    }

    @Override
    public void onSearchConfirmed(CharSequence text) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
        filterStock(text.toString());
    }

    @Override
    public void onButtonClicked(int buttonCode) {
        switch (buttonCode) {
            case MaterialSearchBar.BUTTON_BACK:
                mSearchBar.disableSearch();
                break;
        }
    }

    private void filterStock(String filterStr) {
        stockQueryList.clear();
        stockQueryList.addAll(mDb.stockDao().findByProductNameOrCode("%" + filterStr + "%"));
        mAdapter.notifyDataSetChanged();
    }

    public void reloadStock(){
        if(mSearchBar.getText().trim().isEmpty()){
            loadStock();
        }
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
            loadStock();
        }
    }

    @OnClick(R.id.btn_add_stock)
    void onBtnAddStockClicked(){
        if(!mSearchBar.getText().trim().isEmpty()){
            mSearchBar.disableSearch();
        }
        startActivity(new Intent(getActivity(), StockAddAct.class));
    }
}
