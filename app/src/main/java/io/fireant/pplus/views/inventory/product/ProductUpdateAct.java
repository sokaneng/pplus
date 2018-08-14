package io.fireant.pplus.views.inventory.product;

import android.app.Dialog;
import android.content.Intent;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fireant.pplus.R;
import io.fireant.pplus.common.Constants;
import io.fireant.pplus.common.PPlusDialog;
import io.fireant.pplus.database.AppDatabase;
import io.fireant.pplus.database.dto.ProductDetailQuery;
import io.fireant.pplus.database.tables.entities.Category;
import io.fireant.pplus.database.tables.entities.Currency;
import io.fireant.pplus.database.tables.entities.Product;
import io.fireant.pplus.views.inventory.category.adapter.SelectMainCategoryAdapter;

public class ProductUpdateAct extends AppCompatActivity {

    @BindView(R.id.ed_product_name)
    EditText mEdProductName;

    @BindView(R.id.ed_product_code)
    EditText mEdProductCode;

    @BindView(R.id.ed_product_price)
    EditText mEdProductPrice;

    @BindView(R.id.tv_category_selected_text)
    TextView mTvCategorySelectedText;

    @BindView(R.id.spinner_currency)
    MaterialSpinner mSpCurrency;

    private AppDatabase mDb;
    private String productId;
    private String catId;
    private List<Currency> currencyList = new ArrayList<>();
    private ProductDetailQuery productDetailQuery;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_product_update);
        ButterKnife.bind(this);
        mDb = AppDatabase.getDatabase(getApplicationContext());

        Intent intent = getIntent();
        productId = intent.getStringExtra("PRODUCT_ID");
        if (productId != null) {
            productDetailQuery = mDb.productDao().findProductDetailById(productId);
            loadProductDetail();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    @OnClick(R.id.ln_select_category)
    void onSelectCategoryClicked() {
        dialogSelectCategory();
    }

    @OnClick(R.id.btn_update_product)
    void onBtnUpdateProductClicked() {
        String productName = mEdProductName.getText().toString().trim();
        if (productName != null && !productName.isEmpty()) {
            String productCode = mEdProductCode.getText().toString().trim();
            if (productCode != null && !productCode.isEmpty()) {
                String productPrice = mEdProductPrice.getText().toString().trim();
                if(productPrice != null && !productPrice.isEmpty()){
                    if (catId != null && !catId.isEmpty()) {
                        Product product = new Product();
                        product.id = productDetailQuery.id;
                        product.productName = productName;
                        product.catId = catId;
                        product.code = productCode;
                        product.pricePerUnit = Double.parseDouble(productPrice);
                        product.currencyCode = currencyList.get(mSpCurrency.getSelectedIndex()).currencyCode;
                        product.createDate = productDetailQuery.createDate;
                        product.updateDate = new Date();
                        product.status = 1;

                        mDb.productDao().updateProduct(product);
                        new PPlusDialog(ProductUpdateAct.this, new PPlusDialog.PPlusDialogListener() {
                            @Override
                            public void onPositiveClicked() {
                                Intent output = new Intent();
                                setResult(RESULT_OK, output);
                                finish();
                            }

                            @Override
                            public void onNegativeClicked() {
                                onBackPressed();
                            }
                        }).confirmSuccessUpdateDialog(
                                getString(R.string.update_product_success),
                                getString(R.string.back)
                        );
                    } else {
                        Toast.makeText(getApplicationContext(), R.string.please_select_category, Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(), R.string.please_set_the_price, Toast.LENGTH_SHORT).show();
                    mEdProductPrice.requestFocus();
                }

            } else {
                Toast.makeText(getApplicationContext(), R.string.please_input_product_code, Toast.LENGTH_SHORT).show();
                mEdProductCode.requestFocus();
            }
        } else {
            Toast.makeText(getApplicationContext(), R.string.please_input_product_name, Toast.LENGTH_SHORT).show();
            mEdProductName.requestFocus();
        }
    }

    private void dialogSelectCategory() {

        List<Category> mainCategoryList = mDb.categoryDao().loadAllCategoryByIsMainCategoryStatus(Constants.HAS_NO_CHILD);

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
                catId = null;
                mTvCategorySelectedText.setText(R.string.select_category);
                mTvCategorySelectedText.setTextColor(getResources().getColor(R.color.colorGrayDark));
                dialog.dismiss();
            }
        });

        RecyclerView mRecyclerView = dialog.findViewById(R.id.rc_main_category);
        SelectMainCategoryAdapter mAdapter = new SelectMainCategoryAdapter(mainCategoryList, new SelectMainCategoryAdapter.OnItemClickListener() {
            @Override
            public void onAddItemClick(Category category, int position) {
                catId = category.id;
                mTvCategorySelectedText.setText(category.categoryName);
                mTvCategorySelectedText.setTextColor(getResources().getColor(R.color.colorBlack));
                dialog.dismiss();
            }
        });

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);

        dialog.show();
    }

    private boolean loadProductDetail() {
        if (productDetailQuery != null) {
            mEdProductName.setText(productDetailQuery.productName);
            mTvCategorySelectedText.setText(productDetailQuery.categoryName);
            mTvCategorySelectedText.setTextColor(getResources().getColor(R.color.colorBlack));
            mEdProductCode.setText(productDetailQuery.code);
            mEdProductPrice.setText(String.valueOf(productDetailQuery.pricePerUnit));
            loadCurrencyCode(productDetailQuery.currencyCode);
            catId = productDetailQuery.categoryId;
            mEdProductName.requestFocus();
            mEdProductName.setSelection(mEdProductName.getText().toString().length());
            return true;
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.no_detail_product), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void loadCurrencyCode(String currencyCode) {
        currencyList = mDb.currencyDao().loadAllCurrency();
        List<String> codes = new ArrayList<>();
        int index = 0;
        for (int i = 0; i <currencyList.size(); i ++) {
            Currency currency = currencyList.get(i);
            codes.add(currency.currencyCode);
            if(currencyCode.equals(currency.currencyCode)){
                index = i;
            }
        }
        mSpCurrency.setItems(codes);
        mSpCurrency.setSelectedIndex(index);
    }
}
