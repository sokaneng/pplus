package io.fireant.pplus.views.stock;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import io.fireant.pplus.dto.ProductDTO;
import io.fireant.pplus.dto.StockDTO;
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
    private List<StockDTO> stockDTOList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_stock, container, false);
        ButterKnife.bind(this, rootView);

        mSearchBar.setOnSearchActionListener(this);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        mAdapter = new StockCardAdapter(stockDTOList);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    public void loadFragment() {
        new LoadStock().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
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

    private class LoadStock extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            stockDTOList.clear();

            StockDTO stockDTO = new StockDTO();
            stockDTO.setAmountInStock(20);
            ProductDTO productDTO = new ProductDTO();
            productDTO.setName("T-Shirt");
            productDTO.setId("0000001");
            stockDTO.setProduct(productDTO);
            stockDTOList.add(stockDTO);

            stockDTO = new StockDTO();
            stockDTO.setAmountInStock(17);
            productDTO = new ProductDTO();
            productDTO.setName("Bag");
            productDTO.setId("0000002");
            stockDTO.setProduct(productDTO);
            stockDTOList.add(stockDTO);

            stockDTO = new StockDTO();
            stockDTO.setAmountInStock(17);
            productDTO = new ProductDTO();
            productDTO.setName("Bag");
            productDTO.setId("0000002");
            stockDTO.setProduct(productDTO);
            stockDTOList.add(stockDTO);

            stockDTO = new StockDTO();
            stockDTO.setAmountInStock(17);
            productDTO = new ProductDTO();
            productDTO.setName("Bag");
            productDTO.setId("0000002");
            stockDTO.setProduct(productDTO);
            stockDTOList.add(stockDTO);

            stockDTO = new StockDTO();
            stockDTO.setAmountInStock(17);
            productDTO = new ProductDTO();
            productDTO.setName("Bag");
            productDTO.setId("0000002");
            stockDTO.setProduct(productDTO);
            stockDTOList.add(stockDTO);

            stockDTO = new StockDTO();
            stockDTO.setAmountInStock(17);
            productDTO = new ProductDTO();
            productDTO.setName("Bag");
            productDTO.setId("0000002");
            stockDTO.setProduct(productDTO);
            stockDTOList.add(stockDTO);

            stockDTO = new StockDTO();
            stockDTO.setAmountInStock(17);
            productDTO = new ProductDTO();
            productDTO.setName("Bag");
            productDTO.setId("0000002");
            stockDTO.setProduct(productDTO);
            stockDTOList.add(stockDTO);

            stockDTO = new StockDTO();
            stockDTO.setAmountInStock(17);
            productDTO = new ProductDTO();
            productDTO.setName("Bag");
            productDTO.setId("0000002");
            stockDTO.setProduct(productDTO);
            stockDTOList.add(stockDTO);

            stockDTO = new StockDTO();
            stockDTO.setAmountInStock(17);
            productDTO = new ProductDTO();
            productDTO.setName("Bag");
            productDTO.setId("0000002");
            stockDTO.setProduct(productDTO);
            stockDTOList.add(stockDTO);

            stockDTO = new StockDTO();
            stockDTO.setAmountInStock(17);
            productDTO = new ProductDTO();
            productDTO.setName("Bag");
            productDTO.setId("0000002");
            stockDTO.setProduct(productDTO);
            stockDTOList.add(stockDTO);


            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
            mAdapter.notifyDataSetChanged();
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }
}
