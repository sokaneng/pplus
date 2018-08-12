package io.fireant.pplus.views.inventory.product.adapter;

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
import io.fireant.pplus.database.tables.Category;
import io.fireant.pplus.database.tables.Product;

/**
 * Created by engsokan on 8/10/18.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    private List<Product> productList;
    private final OnItemClickListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        TextView mTvName;

        @BindView(R.id.tv_no)
        TextView mTvNo;

        @BindView(R.id.tv_code)
        TextView mTvCode;

        @BindView(R.id.img_delete)
        ImageView mImgDelete;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bind(final Product product, final int position, final OnItemClickListener listener) {

            mImgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onDeleteItemClick(product, position);
                }
            });

        }

    }

    public ProductAdapter(List<Product> productList, OnItemClickListener listener) {
        this.productList = productList;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_product, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.bind(product, position, listener);
        int no = position + 1;
        holder.mTvNo.setText(String.valueOf(no));
        holder.mTvName.setText(product.productName);
        holder.mTvCode.setText(product.code);

    }


    @Override
    public int getItemCount() {
        return productList.size();
    }

    public interface OnItemClickListener {
        void onDeleteItemClick(Product product, int position);
    }
}
