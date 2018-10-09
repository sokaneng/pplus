package io.fireant.pplus.views.inventory.product;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fireant.pplus.R;
import io.fireant.pplus.database.AppDatabase;
import io.fireant.pplus.database.dto.ProductDetailQuery;
import io.fireant.pplus.database.dto.StockQuery;
import io.fireant.pplus.database.tables.entities.Stock;
import io.fireant.pplus.views.stock.StockAddAct;
import io.fireant.pplus.views.stock.StockViewImportAct;

/**
 * Created by engsokan on 8/13/18.
 */

public class ProductDetailAct extends AppCompatActivity {

    @BindView(R.id.tv_id)
    TextView mTvId;

    @BindView(R.id.tv_name)
    TextView mTvName;

    @BindView(R.id.tv_category)
    TextView mTvCategory;

    @BindView(R.id.tv_code)
    TextView mTvCode;

    @BindView(R.id.tv_price)
    TextView mTvPrice;

    @BindView(R.id.tv_create_date)
    TextView mTvCreateDate;

    @BindView(R.id.tv_update_date)
    TextView mTvUpdateDate;

    @BindView(R.id.ln_update_date)
    LinearLayout mLnUpdateDate;

    @BindView(R.id.tv_quantity)
    TextView mTvQuantity;

    @BindView(R.id.tv_import_date)
    TextView mTvImportDate;

    @BindView(R.id.ln_about_stock)
    LinearLayout mLnAboutStock;

    private AppDatabase mDb;
    private String productId;
    private String stockId;
    private static final int PRODUCT_UPDATE_ACTIVITY_CODE = 101;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_product_detail);
        ButterKnife.bind(this);
        mDb = AppDatabase.getDatabase(getApplicationContext());

        Intent intent = getIntent();
        productId = intent.getStringExtra("PRODUCT_ID");

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (productId != null) {
            loadProductDetail();
        }
    }

    private void loadProductDetail() {
        ProductDetailQuery productDetailQuery = mDb.productDao().findProductDetailById(productId);
        if (loadProductDetail(productDetailQuery)) {
            Stock stock = mDb.stockDao().findAvailableStockByProductId(productDetailQuery.id);
            loadStockDetail(stock);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.product_detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_refresh) {
            if (productId != null) {
                Toast.makeText(getApplicationContext(), R.string.refreshing, Toast.LENGTH_SHORT).show();
                loadProductDetail();
            }
        } else {
            onBackPressed();
        }
        return true;
    }

    @OnClick(R.id.btn_update_product)
    void onBtnUpdateProduct() {
        Intent intent = new Intent(getApplicationContext(), ProductUpdateAct.class);
        intent.putExtra("PRODUCT_ID", productId);
        startActivityForResult(intent, PRODUCT_UPDATE_ACTIVITY_CODE);
    }

    @OnClick(R.id.btn_add_stock)
    void onBtnAddStock(){
        Intent intent = new Intent(getApplicationContext(), StockAddAct.class);
        intent.putExtra("PRODUCT_NAME", mTvName.getText().toString().trim());
        intent.putExtra("PRODUCT_ID", productId);
        startActivity(intent);
    }

    @OnClick(R.id.btn_view_import)
    void onBtnViewImport() {
        Intent intent = new Intent(getApplicationContext(), StockViewImportAct.class);
        intent.putExtra("STOCK_ID", stockId);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PRODUCT_UPDATE_ACTIVITY_CODE && resultCode == RESULT_OK && data != null) {
            if (productId != null) {
                loadProductDetail();
            }
        }
    }

    private boolean loadProductDetail(ProductDetailQuery productDetailQuery) {
        if (productDetailQuery != null) {
            mTvId.setText(productDetailQuery.id);
            mTvName.setText(productDetailQuery.productName);
            mTvCategory.setText(productDetailQuery.categoryName);
            mTvCode.setText(productDetailQuery.code);
            mTvPrice.setText(productDetailQuery.pricePerUnit + " " + productDetailQuery.currencyCode);
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
            mTvCreateDate.setText(format.format(productDetailQuery.createDate));
            if (productDetailQuery.updateDate != null) {
                mTvUpdateDate.setText(format.format(productDetailQuery.updateDate));
                mLnUpdateDate.setVisibility(View.VISIBLE);
            } else {
                mLnUpdateDate.setVisibility(View.GONE);
            }
            return true;
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.no_detail_product), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void loadStockDetail(Stock stock) {
        if (stock != null) {
            stockId = stock.id;
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
            mTvQuantity.setText(String.valueOf(stock.totalQuantity));
            mTvImportDate.setText(format.format(stock.updateDate));
            mLnAboutStock.setVisibility(View.VISIBLE);
        } else {
            mLnAboutStock.setVisibility(View.GONE);
        }
    }

}
