package io.fireant.pplus.views.inventory.category;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import io.fireant.pplus.R;
import io.fireant.pplus.views.inventory.category.adapter.SelectMainCategoryAdapter;

/**
 * Created by engsokan on 8/9/18.
 */

public class CategoryAddAct extends AppCompatActivity {

    @BindView(R.id.cb_issubcat)
    CheckBox mCbIsSubCat;

    @BindView(R.id.tv_category)
    TextView mTvCategory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_category_add);
        ButterKnife.bind(this);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    @OnCheckedChanged(R.id.cb_issubcat)
    void IsSubCatCheckChange() {
        if (mCbIsSubCat.isChecked()) {
            dialogSelectCategory();
        }else {
            mTvCategory.setText("None");
        }

    }

    private void dialogSelectCategory() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_select_main_category);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);

        Button mBtnBack = (Button) dialog.findViewById(R.id.btn_back);
        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCbIsSubCat.setChecked(false);
                dialog.dismiss();
            }
        });

        final String[] myDataset = {"Beer", "Food", "as", "asd", "asdad", "assdd"};
        RecyclerView mRecyclerView = (RecyclerView) dialog.findViewById(R.id.rc_main_category);
        SelectMainCategoryAdapter mAdapter = new SelectMainCategoryAdapter(myDataset, new SelectMainCategoryAdapter.OnItemClickListener() {
            @Override
            public void onAddItemClick(String content, int position) {
                mTvCategory.setText(content);
                dialog.dismiss();
            }
        });

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);

        dialog.show();
    }
}
