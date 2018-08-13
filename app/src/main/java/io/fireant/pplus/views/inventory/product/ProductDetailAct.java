package io.fireant.pplus.views.inventory.product;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.fireant.pplus.R;
import io.fireant.pplus.database.AppDatabase;
import io.fireant.pplus.database.dto.ProductDetailQuery;

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

    @BindView(R.id.tv_quantity)
    TextView mTvQuantity;

    @BindView(R.id.tv_import_date)
    TextView mTvImportDate;

    private AppDatabase mDb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_product_detail);
        ButterKnife.bind(this);
        mDb = AppDatabase.getDatabase(getApplicationContext());

        Intent intent = getIntent();
        String productId = intent.getStringExtra("PRODUCT_ID");
        if (productId != null) {
            ProductDetailQuery productDetailQuery = mDb.productDao().findProductDetailById(productId);
            initView(productDetailQuery);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    private void initView(ProductDetailQuery productDetailQuery) {
        mTvId.setText(productDetailQuery.id);
        mTvName.setText(productDetailQuery.productName);
        mTvCategory.setText(productDetailQuery.categoryName);
        mTvCode.setText(productDetailQuery.code);
        mTvPrice.setText(productDetailQuery.pricePerUnit + " " + productDetailQuery.currencyCode);
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        mTvCreateDate.setText(format.format(productDetailQuery.createDate));
        mTvQuantity.setText(String.valueOf(productDetailQuery.quantity));
        mTvImportDate.setText(format.format(productDetailQuery.importDate));
    }
}
