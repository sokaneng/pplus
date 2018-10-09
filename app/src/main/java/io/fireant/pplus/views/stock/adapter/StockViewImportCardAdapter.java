package io.fireant.pplus.views.stock.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.fireant.pplus.R;
import io.fireant.pplus.database.tables.entities.StockImport;

/**
 * Created by engsokan on 11/29/17.
 */

public class StockViewImportCardAdapter extends RecyclerView.Adapter<StockViewImportCardAdapter.MyViewHolder> {

    private List<StockImport> stockImportQueryList;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_qty) TextView mQty;
        @BindView(R.id.tv_import_date) TextView mTvImportDate;
        @BindView(R.id.tv_no) TextView mTvNo;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public StockViewImportCardAdapter(List<StockImport> stockImportQueryList) {
        this.stockImportQueryList = stockImportQueryList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_stock_view_import_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        StockImport stockImport = stockImportQueryList.get(position);
        int no = position + 1;
        holder.mTvNo.setText(String.valueOf(no));
        holder.mQty.setText(String.valueOf(stockImport.quantity));
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        holder.mTvImportDate.setText(format.format(stockImport.importDate));
    }

    @Override
    public int getItemCount() {
        return stockImportQueryList.size();
    }
}