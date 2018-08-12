package io.fireant.pplus.views.inventory.category;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.fireant.pplus.R;
import io.fireant.pplus.common.PPlusDialog;
import io.fireant.pplus.database.AppDatabase;
import io.fireant.pplus.database.tables.Category;
import io.fireant.pplus.views.inventory.category.adapter.CategoryAdapter;

/**
 * Created by engsokan on 8/9/18.
 */

public class CategoryAct extends AppCompatActivity {

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

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (loadCategory()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        });

        mAdapter = new CategoryAdapter(categoryList, new CategoryAdapter.OnItemClickListener() {
            @Override
            public void onDeleteItemClick(final Category category, final int position) {
                new PPlusDialog(CategoryAct.this, new PPlusDialog.PPlusDialogListener() {
                    @Override
                    public void onPositiveClicked() {
                        mDb.categoryDao().deleteCategory(category);
                        categoryList.remove(position);
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onNegativeClicked() {

                    }
                }).confirmDeleteDialog(
                        "Do you want to delete?",
                        "YES",
                        "CANCEL");

            }
        });
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);

        loadCategory();
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
}
