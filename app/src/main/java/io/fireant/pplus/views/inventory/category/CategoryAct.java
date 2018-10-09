package io.fireant.pplus.views.inventory.category;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;

import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.fireant.pplus.R;
import io.fireant.pplus.common.PPlusDialog;
import io.fireant.pplus.database.AppDatabase;
import io.fireant.pplus.database.tables.entities.Category;
import io.fireant.pplus.database.tables.entities.Product;
import io.fireant.pplus.views.inventory.category.adapter.CategoryAdapter;

/**
 * Created by engsokan on 8/9/18.
 */

public class CategoryAct extends AppCompatActivity implements MaterialSearchBar.OnSearchActionListener, TextWatcher {

    @BindView(R.id.searchBar)
    MaterialSearchBar mSearchBar;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private CategoryAdapter mAdapter;
    private List<Category> categoryList = new ArrayList<>();
    private AppDatabase mDb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_category);
        ButterKnife.bind(this);

        mDb = AppDatabase.getDatabase(getApplicationContext());

        mSearchBar.setOnSearchActionListener(this);
        mSearchBar.addTextChangeListener(this);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (loadCategory()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        });

        mAdapter = new CategoryAdapter(CategoryAct.this, categoryList, new CategoryAdapter.OnItemClickListener() {
            @Override
            public void onDeleteItemClick(final Category category, final int position) {
                List<Category> categories = mDb.categoryDao().findByCategoryIdHasChild(category.id);
                if (categories == null || categories.isEmpty()) {
                    Product product = mDb.productDao().findProductByCategoryId(category.id);
                    if (product == null) {
                        new PPlusDialog(CategoryAct.this, new PPlusDialog.PPlusDialogListener() {
                            @Override
                            public void onPositiveClicked() {
                                category.status = 0;
                                mDb.categoryDao().updateCategory(category);
                                categoryList.remove(position);
                                mAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onNegativeClicked() {

                            }
                        }).confirmDeleteDialog(
                                getString(R.string.do_you_want_to_delete),
                                getString(R.string.yes),
                                getString(R.string.cancel));
                    } else {
                        String msg = getString(R.string.sorry_you_cannot_delete_cat) + "\n" + getString(R.string.there_is_some_product_belong);
                        new PPlusDialog(CategoryAct.this, new PPlusDialog.PPlusDialogListener() {
                            @Override
                            public void onPositiveClicked() {

                            }

                            @Override
                            public void onNegativeClicked() {

                            }
                        }).warningDialog(msg);
                    }
                }else {
                    String msg = getString(R.string.sorry_you_cannot_delete_cat) + "\n" + getString(R.string.there_is_some_category_belong);
                    new PPlusDialog(CategoryAct.this, new PPlusDialog.PPlusDialogListener() {
                        @Override
                        public void onPositiveClicked() {

                        }

                        @Override
                        public void onNegativeClicked() {

                        }
                    }).warningDialog(msg);
                }

            }
        });
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            startActivity(new Intent(getApplicationContext(), CategoryAddAct.class));
        } else {
            onBackPressed();
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.category_menu, menu);
        return true;
    }

    private boolean loadCategory() {
        categoryList.clear();
        categoryList.addAll(mDb.categoryDao().loadAllCategories());
        mAdapter.notifyDataSetChanged();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadCategory();
    }

    @Override
    public void onSearchStateChanged(boolean enabled) {

    }

    @Override
    public void onSearchConfirmed(CharSequence text) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        filterCategory(text.toString());
    }

    @Override
    public void onButtonClicked(int buttonCode) {
        switch (buttonCode) {
            case MaterialSearchBar.BUTTON_BACK:
                mSearchBar.disableSearch();
                break;
        }
    }

    private void filterCategory(String filterStr) {
        categoryList.clear();
        categoryList.addAll(mDb.categoryDao().findByCategoryName("%" + filterStr + "%"));
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (mSearchBar.getText().isEmpty()) {
            loadCategory();
        }
    }
}
