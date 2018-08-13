package io.fireant.pplus.views.stock.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name) TextView mTvName;
        @BindView(R.id.tv_code) TextView mTvCode;
        @BindView(R.id.tv_qty) TextView mQty;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public StockCardAdapter(List<StockQuery> stockQueryList) {
        this.stockQueryList = stockQueryList;

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
    }

    @Override
    public int getItemCount() {
        return stockQueryList.size();
    }

}