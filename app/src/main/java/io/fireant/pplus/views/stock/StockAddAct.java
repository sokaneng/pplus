package io.fireant.pplus.views.stock;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fireant.pplus.R;
import io.fireant.pplus.common.PPlusDialog;
import io.fireant.pplus.database.AppDatabase;
import io.fireant.pplus.database.dto.ProductDetailQuery;
import io.fireant.pplus.database.dto.StockQuery;
import io.fireant.pplus.database.tables.entities.Stock;
import io.fireant.pplus.database.tables.entities.StockImport;
import io.fireant.pplus.views.inventory.product.ProductDetailAct;
import io.fireant.pplus.views.inventory.product.ProductSelectionAct;

public class StockAddAct extends AppCompatActivity {

    @BindView(R.id.btn_plus)
    ImageView mImgPlus;

    @BindView(R.id.btn_minus)
    ImageView mImgMinus;

    @BindView(R.id.ed_quantity)
    EditText mEdQuantity;

    @BindView(R.id.tv_product_selected_text)
    TextView mTvProductSelected;

    private static final int PRODUCT_SELECTION_ACTIVITY_CODE = 100;
    private String productSelectedId;
    private AppDatabase mDb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_stock_add);
        ButterKnife.bind(this);
        mDb = AppDatabase.getDatabase(getApplicationContext());
        mEdQuantity.setSelection(mEdQuantity.getText().toString().trim().length());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    @OnClick(R.id.btn_plus)
    void onBtnPlusClicked() {
        String qtyStr = mEdQuantity.getText().toString().trim();
        if (qtyStr != null && !qtyStr.isEmpty()) {
            int qty = Integer.parseInt(qtyStr);
            qty++;
            qtyStr = String.valueOf(qty);
            mEdQuantity.setText(qtyStr);
            mEdQuantity.setSelection(qtyStr.length());
        } else {
            mEdQuantity.setText("0");
            onBtnPlusClicked();
        }
    }

    @OnClick(R.id.btn_minus)
    void onBtnMinusClicked() {
        String qtyStr = mEdQuantity.getText().toString().trim();
        if (qtyStr != null && !qtyStr.isEmpty()) {
            int qty = Integer.parseInt(qtyStr);
            if (qty > 0) {
                qty--;
                qtyStr = String.valueOf(qty);
                mEdQuantity.setText(qtyStr);
                mEdQuantity.setSelection(qtyStr.length());
            }
        }
    }

    @OnClick(R.id.ln_select_product)
    void onLnSelectProductClicked() {
        Intent intent = new Intent(getApplicationContext(), ProductSelectionAct.class);
        startActivityForResult(intent, PRODUCT_SELECTION_ACTIVITY_CODE);
    }

    @OnClick(R.id.btn_add)
    void onBtnAddClicked(){
        if(productSelectedId != null && !productSelectedId.isEmpty()){
            String qtyStr = mEdQuantity.getText().toString().trim();
            if (qtyStr != null && !qtyStr.isEmpty()) {
                int qty = Integer.parseInt(qtyStr);
                if (qty > 0) {

                    //Get stock id
                    Stock stock = mDb.stockDao().findStockByProductId(productSelectedId);

                    //Add stock import
                    StockImport stockImport = new StockImport();
                    UUID uuid = UUID.randomUUID();
                    String stockImportId = uuid.toString();
                    stockImport.id = stockImportId;
                    stockImport.stockId = stock.id;
                    stockImport.quantity = qty;
                    stockImport.importDate = new Date();
                    stockImport.status = 1;
                    mDb.stockImportDao().insertStockImport(stockImport);

                    //Update total stock
                    stock.totalQuantity = stock.totalQuantity + qty;
                    stock.updateDate = new Date();
                    stock.status = 1;
                    mDb.stockDao().updateStock(stock);

                    new PPlusDialog(this, new PPlusDialog.PPlusDialogListener() {
                        @Override
                        public void onPositiveClicked() {
                            productSelectedId = null;
                            mTvProductSelected.setText(getString(R.string.select_product));
                            mTvProductSelected.setTextColor(getResources().getColor(R.color.colorGrayDark));
                            mEdQuantity.setText("0");
                            mEdQuantity.setSelection(mEdQuantity.getText().toString().trim().length());
                        }

                        @Override
                        public void onNegativeClicked() {
                            onBackPressed();
                        }
                    }).confirmSuccessDialog(
                            getString(R.string.stock_has_been_added),
                            getString(R.string.add_more),
                            getString(R.string.back)
                    );
                }else{
                    Toast.makeText(getApplicationContext(), R.string.please_set_the_quantity, Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(getApplicationContext(), R.string.please_set_the_quantity, Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(getApplicationContext(), getString(R.string.please_select_product), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PRODUCT_SELECTION_ACTIVITY_CODE && resultCode == RESULT_OK && data != null) {
            String productName = data.getStringExtra("PRODUCT_NAME");
            productSelectedId = data.getStringExtra("PRODUCT_ID");
            mTvProductSelected.setText(productName);
            mTvProductSelected.setTextColor(getResources().getColor(R.color.colorBlack));
        }else {
            productSelectedId = null;
            mTvProductSelected.setText(getString(R.string.select_product));
            mTvProductSelected.setTextColor(getResources().getColor(R.color.colorGrayDark));
        }
    }
}
