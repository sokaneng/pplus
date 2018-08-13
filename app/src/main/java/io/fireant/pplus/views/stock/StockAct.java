package io.fireant.pplus.views.stock;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import io.fireant.pplus.R;
import io.fireant.pplus.database.AppDatabase;
import io.fireant.pplus.database.tables.StockQuery;
import io.fireant.pplus.views.stock.adapter.StockCardAdapter;
import static android.content.Context.INPUT_METHOD_SERVICE;

public class StockAct extends Fragment implements MaterialSearchBar.OnSearchActionListener {

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
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        mAdapter = new StockCardAdapter(stockQueryList);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    public void loadFragment() {
        LiveData<List<StockQuery>> listLiveData = mDb.stockDao().loadAllStock();
        stockQueryList = (List<StockQuery>) Transformations.map(listLiveData, new Function<List<StockQuery>, Object>() {
            @Override
            public Object apply(List<StockQuery> data) {
                return data;
            }
        });
    }

    @Override
    public void onSearchStateChanged(boolean enabled) {

    }

    @Override
    public void onSearchConfirmed(CharSequence text) {
        Toast.makeText(getActivity(), "Search " + text, Toast.LENGTH_SHORT).show();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }

    @Override
    public void onButtonClicked(int buttonCode) {
        switch (buttonCode) {
            case MaterialSearchBar.BUTTON_BACK:
                mSearchBar.disableSearch();
                break;
        }
    }
}
