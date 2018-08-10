package io.fireant.pplus.views.sale;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.fireant.pplus.R;
import io.fireant.pplus.dto.ProductDTO;
import io.fireant.pplus.dto.ProductSaleDTO;
import io.fireant.pplus.views.sale.adapter.ProductSaleCardAdapter;
import io.fireant.pplus.views.stock.adapter.StockCardAdapter;

public class SaleAct extends Fragment {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private ProductSaleCardAdapter mAdapter;
    private List<ProductSaleDTO> productSaleDTOList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_sale, container, false);
        ButterKnife.bind(this, rootView);

        ProductSaleDTO productSaleDTO = new ProductSaleDTO();
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId("090933");
        productDTO.setName("T-Shirt");
        productSaleDTO.setProduct(productDTO);
        productSaleDTO.setQty(2);
        productSaleDTOList.add(productSaleDTO);

        productSaleDTO = new ProductSaleDTO();
        productDTO = new ProductDTO();
        productDTO.setId("090933");
        productDTO.setName("Beg");
        productSaleDTO.setProduct(productDTO);
        productSaleDTO.setQty(2);
        productSaleDTOList.add(productSaleDTO);

        mAdapter = new ProductSaleCardAdapter(productSaleDTOList, new ProductSaleCardAdapter.OnItemClickListener() {

            @Override
            public void onDeleteItemClick(ProductSaleDTO item, int position) {
                productSaleDTOList.remove(position);
                mAdapter.notifyDataSetChanged();
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    public void loadFragment() {
    }
}
