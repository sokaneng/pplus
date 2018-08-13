package io.fireant.pplus.views.inventory.category.adapter;

import android.content.Context;
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
import io.fireant.pplus.database.tables.entities.Category;

/**
 * Created by engsokan on 8/10/18.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {

    private List<Category> categoryList;
    private final OnItemClickListener listener;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        TextView mTvName;

        @BindView(R.id.tv_no)
        TextView mTvNo;

        @BindView(R.id.tv_type)
        TextView mTvType;

        @BindView(R.id.img_delete)
        ImageView mImgDelete;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bind(final Category category, final int position, final OnItemClickListener listener) {

            mImgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onDeleteItemClick(category, position);
                }
            });

        }

    }

    public CategoryAdapter(Context context, List<Category> categoryList, OnItemClickListener listener) {
        this.categoryList = categoryList;
        this.listener = listener;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_category, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Category category = categoryList.get(position);
        holder.bind(category, position, listener);
        int no = position + 1;
        holder.mTvNo.setText(String.valueOf(no));
        holder.mTvName.setText(category.categoryName);

        //Check type
        String type;
        if (category.mainCatId == null) {
            type = context.getString(R.string.main_category);
        } else {
            type = context.getString(R.string.sub_category);
        }
        holder.mTvType.setText(type);
    }


    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public interface OnItemClickListener {
        void onDeleteItemClick(Category category, int position);
    }

}
