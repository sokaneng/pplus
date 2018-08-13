package io.fireant.pplus.views.inventory.product.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.fireant.pplus.R;
import io.fireant.pplus.database.tables.entities.Product;

/**
 * Created by engsokan on 8/10/18.
 */

public class ProductSelectionAdapter extends RecyclerView.Adapter<ProductSelectionAdapter.MyViewHolder> {

    private List<Product> productList;
    private final OnItemClickListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        TextView mTvName;

        @BindView(R.id.tv_no)
        TextView mTvNo;

        @BindView(R.id.tv_code)
        TextView mTvCode;

        @BindView(R.id.tv_currency_type)
        TextView mTvCurrencyType;

        @BindView(R.id.tv_price)
        TextView mTvPrice;

        @BindView(R.id.ln_item)
        LinearLayout mLnItem;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bind(final Product product, final int position, final OnItemClickListener listener) {

            mLnItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(product, position);
                }
            });

        }
    }

    public ProductSelectionAdapter(List<Product> productList, OnItemClickListener listener) {
        this.productList = productList;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_product_selection, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Product product = productList.get(position);
        int no = position + 1;
        holder.bind(product, position, listener);
        holder.mTvNo.setText(String.valueOf(no));
        holder.mTvName.setText(product.productName);
        holder.mTvCode.setText(product.code);
        holder.mTvCurrencyType.setText(product.currencyCode);
        holder.mTvPrice.setText(String.valueOf(product.pricePerUnit));
    }


    @Override
    public int getItemCount() {
        return productList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Product product, int position);
    }
}