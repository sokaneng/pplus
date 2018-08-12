package io.fireant.pplus.views.inventory.category.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.fireant.pplus.R;
import io.fireant.pplus.database.tables.Category;

/**
 * Created by engsokan on 8/10/18.
 */

public class SelectMainCategoryAdapter extends RecyclerView.Adapter<SelectMainCategoryAdapter.MyViewHolder> {

    private List<Category> mainCategoryList;
    private final OnItemClickListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name) TextView mTvName;
        @BindView(R.id.btn_add) Button mBtnAdd;


        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bind(final Category category, final int position, final OnItemClickListener listener) {

            mBtnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onAddItemClick(category, position);
                }
            });

        }

    }

    public SelectMainCategoryAdapter(List<Category> mainCategoryList, OnItemClickListener listener) {
        this.mainCategoryList = mainCategoryList;
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
        Category category = mainCategoryList.get(position);
        holder.bind(category, position, listener);
        holder.mTvName.setText(category.categoryName);
    }


    @Override
    public int getItemCount() {
        return mainCategoryList.size();
    }

    public interface OnItemClickListener {
        void onAddItemClick(Category category, int position);
    }
}
