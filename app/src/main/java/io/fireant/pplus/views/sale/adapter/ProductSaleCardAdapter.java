package io.fireant.pplus.views.sale.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.fireant.pplus.R;
import io.fireant.pplus.dto.ProductSaleDTO;

/**
 * Created by engsokan on 11/29/17.
 */

public class ProductSaleCardAdapter extends RecyclerView.Adapter<ProductSaleCardAdapter.MyViewHolder> {

    private List<ProductSaleDTO> productSaleDTOList;
    private final OnItemClickListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name) TextView mTvName;
        @BindView(R.id.tv_id) TextView mTvId;
        @BindView(R.id.tv_qty) TextView mQty;
        @BindView(R.id.tv_no) TextView mTvNo;
        @BindView(R.id.img_delete) ImageView mImgDelete;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bind(final ProductSaleDTO item, final int position, final OnItemClickListener listener) {

            mImgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onDeleteItemClick(item, position);
                }
            });

        }

    }


    public ProductSaleCardAdapter(List<ProductSaleDTO> productSaleDTOList,
                                  OnItemClickListener listener) {
        this.productSaleDTOList = productSaleDTOList;
        this.listener = listener;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_product_sale_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        ProductSaleDTO model = productSaleDTOList.get(position);
        int no = position + 1;
        holder.mTvNo.setText(String.valueOf(no));
        holder.mTvName.setText(model.getProduct().getName());
        holder.mTvId.setText(model.getProduct().getId());
        holder.mQty.setText(String.valueOf(model.getQty()));
        holder.bind(productSaleDTOList.get(position), position, listener);
    }

    @Override
    public int getItemCount() {
        return productSaleDTOList.size();
    }

    public interface OnItemClickListener {
        void onDeleteItemClick(ProductSaleDTO item, int position);
    }
}