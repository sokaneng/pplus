package io.fireant.pplus.views.inventory.category;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import io.fireant.pplus.R;
import io.fireant.pplus.common.PPlusDialog;
import io.fireant.pplus.database.AppDatabase;
import io.fireant.pplus.database.tables.Category;
import io.fireant.pplus.views.inventory.category.adapter.SelectMainCategoryAdapter;

/**
 * Created by engsokan on 8/9/18.
 */

public class CategoryAddAct extends AppCompatActivity {

    @BindView(R.id.cb_issubcat)
    CheckBox mCbIsSubCat;

    @BindView(R.id.tv_category)
    TextView mTvCategory;

    @BindView(R.id.ed_category_name)
    EditText mEdCategoryName;

    private String mainCatId;
    private AppDatabase mDb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_category_add);
        ButterKnife.bind(this);
        mDb = AppDatabase.getDatabase(getApplicationContext());
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
        } else {
            mTvCategory.setText(R.string.none);
            mainCatId = null;
        }

    }

    @OnClick(R.id.btn_create)
    void onBtnCreateClicked() {
        String categoryName = mEdCategoryName.getText().toString().trim();
        if (categoryName != null && !categoryName.isEmpty()) {
            Category category = new Category();
            UUID uuid = UUID.randomUUID();
            String id = uuid.toString();
            category.id = id;
            category.categoryName = categoryName;

            if (mainCatId != null) {
                category.mainCatId = mainCatId;
            }
            category.createDate = new Date();
            category.status = 1;

            mDb.categoryDao().insertCategory(category);
            new PPlusDialog(CategoryAddAct.this, new PPlusDialog.PPlusDialogListener() {
                @Override
                public void onPositiveClicked() {
                    mainCatId = null;
                    mEdCategoryName.setText("");
                    mCbIsSubCat.setChecked(false);
                }

                @Override
                public void onNegativeClicked() {
                    onBackPressed();
                }
            }).confirmSuccessDialog(
                    getString(R.string.category_has_been_created),
                    getString(R.string.add_more),
                    getString(R.string.back)
            );

        } else {
            Toast.makeText(getApplicationContext(), R.string.please_input_category_name, Toast.LENGTH_SHORT).show();
        }

    }

    private void dialogSelectCategory() {

        List<Category> mainCategoryList = mDb.categoryDao().loadAllMainCategory();

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_select_main_category);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);

        Button mBtnBack = dialog.findViewById(R.id.btn_back);
        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCbIsSubCat.setChecked(false);
                dialog.dismiss();
            }
        });

        RecyclerView mRecyclerView = dialog.findViewById(R.id.rc_main_category);
        SelectMainCategoryAdapter mAdapter = new SelectMainCategoryAdapter(mainCategoryList, new SelectMainCategoryAdapter.OnItemClickListener() {
            @Override
            public void onAddItemClick(Category category, int position) {
                mainCatId = category.id;
                mTvCategory.setText(category.categoryName);
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
