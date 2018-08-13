package io.fireant.pplus.views.inventory.product;

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
import io.fireant.pplus.database.tables.entities.Category;
import io.fireant.pplus.database.tables.entities.Currency;
import io.fireant.pplus.database.tables.entities.Product;
import io.fireant.pplus.views.inventory.category.adapter.SelectMainCategoryAdapter;

/**
 * Created by engsokan on 8/9/18.
 */

public class ProductAddAct extends AppCompatActivity {

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
    private String catId;
    List<Currency> currencyList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_product_add);
        ButterKnife.bind(this);
        mDb = AppDatabase.getDatabase(getApplicationContext());

        loadCurrencyCode();
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

    @OnClick(R.id.btn_create)
    void onBtnCreateClicked() {
        String productName = mEdProductName.getText().toString().trim();
        if (productName != null && !productName.isEmpty()) {
            String productCode = mEdProductCode.getText().toString().trim();
            if (productCode != null && !productCode.isEmpty()) {
                String productPrice = mEdProductPrice.getText().toString().trim();
                if(productPrice != null && !productPrice.isEmpty()){
                    if (catId != null && !catId.isEmpty()) {
                        Product product = new Product();
                        UUID uuid = UUID.randomUUID();
                        String id = uuid.toString();
                        product.id = id;
                        product.productName = productName;
                        product.catId = catId;
                        product.code = productCode;
                        product.pricePerUnit = Double.parseDouble(productPrice);
                        product.currencyCode = currencyList.get(mSpCurrency.getSelectedIndex()).currencyCode;
                        product.createDate = new Date();
                        product.status = 1;

                        mDb.productDao().insertProduct(product);
                        new PPlusDialog(ProductAddAct.this, new PPlusDialog.PPlusDialogListener() {
                            @Override
                            public void onPositiveClicked() {
                                mEdProductName.setText("");
                                mEdProductCode.setText("");
                                mTvCategorySelectedText.setText(R.string.select_category);
                                mTvCategorySelectedText.setTextColor(getResources().getColor(R.color.colorGrayDark));
                                catId = null;
                            }

                            @Override
                            public void onNegativeClicked() {
                                onBackPressed();
                            }
                        }).confirmSuccessDialog(
                                getString(R.string.product_has_been_created),
                                getString(R.string.add_more),
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

    private void loadCurrencyCode() {
        currencyList = mDb.currencyDao().loadAllCurrency();
        List<String> codes = new ArrayList<>();
        for (Currency currency : currencyList) {
            codes.add(currency.currencyCode);
        }
        mSpCurrency.setItems(codes);
    }
}
