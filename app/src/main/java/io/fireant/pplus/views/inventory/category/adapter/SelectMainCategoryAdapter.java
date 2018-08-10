package io.fireant.pplus.views.inventory.category.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.fireant.pplus.R;

/**
 * Created by engsokan on 8/10/18.
 */

public class SelectMainCategoryAdapter extends RecyclerView.Adapter<SelectMainCategoryAdapter.MyViewHolder> {

    private String[] mDataset;
    private final OnItemClickListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name) TextView mTvName;
        @BindView(R.id.btn_add) Button mBtnAdd;


        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bind(final String content, final int position, final OnItemClickListener listener) {

            mBtnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onAddItemClick(content, position);
                }
            });

        }

    }

    public SelectMainCategoryAdapter(String[] myDataset, OnItemClickListener listener) {
        mDataset = myDataset;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_select_main_category, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.bind(mDataset[position], position, listener);
        holder.mTvName.setText(mDataset[position]);
    }


    @Override
    public int getItemCount() {
        return mDataset.length;
    }

    public interface OnItemClickListener {
        void onAddItemClick(String content, int position);
    }
}
