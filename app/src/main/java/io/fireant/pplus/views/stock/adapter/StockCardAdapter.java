package io.fireant.pplus.views.stock.adapter;

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
import io.fireant.pplus.database.dto.StockQuery;

/**
 * Created by engsokan on 11/29/17.
 */

public class StockCardAdapter extends RecyclerView.Adapter<StockCardAdapter.MyViewHolder> {

    private List<StockQuery> stockQueryList;
    private OnItemClickListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name) TextView mTvName;
        @BindView(R.id.tv_code) TextView mTvCode;
        @BindView(R.id.tv_qty) TextView mQty;
        @BindView(R.id.ln_item)
        LinearLayout mLnItem;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bind(final StockQuery product, final int position, final OnItemClickListener listener) {

            mLnItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(product.productId);
                }
            });

        }
    }

    public StockCardAdapter(List<StockQuery> stockQueryList, OnItemClickListener listener) {
        this.stockQueryList = stockQueryList;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_stock_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        StockQuery stock = stockQueryList.get(position);
        holder.mTvName.setText(stock.productName);
        holder.mTvCode.setText(stock.code);
        holder.mQty.setText(String.valueOf(stock.quantity));
        holder.bind(stock, position, listener);
    }

    @Override
    public int getItemCount() {
        return stockQueryList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(String productId);
    }
}