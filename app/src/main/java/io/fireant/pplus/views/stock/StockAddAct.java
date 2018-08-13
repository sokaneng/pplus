package io.fireant.pplus.views.stock;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fireant.pplus.R;
import io.fireant.pplus.database.tables.entities.Product;
import io.fireant.pplus.views.inventory.product.ProductSelectionAct;
import io.fireant.pplus.views.inventory.product.listener.ProductSelectionListener;

public class StockAddAct extends AppCompatActivity implements ProductSelectionListener{

    @BindView(R.id.btn_plus)
    ImageView mImgPlus;

    @BindView(R.id.btn_minus)
    ImageView mImgMinus;

    @BindView(R.id.ed_quantity)
    EditText mEdQuantity;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_stock_add);
        ButterKnife.bind(this);
        mEdQuantity.setSelection(mEdQuantity.getText().toString().trim().length());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    @OnClick(R.id.btn_plus)
    void onBtnPlusClicked(){
        String qtyStr = mEdQuantity.getText().toString().trim();
        if(qtyStr != null && !qtyStr.isEmpty()){
            int qty = Integer.parseInt(qtyStr);
            qty++;
            qtyStr = String.valueOf(qty);
            mEdQuantity.setText(qtyStr);
            mEdQuantity.setSelection(qtyStr.length());
        }else{
            mEdQuantity.setText("0");
            onBtnPlusClicked();
        }
    }

    @OnClick(R.id.btn_minus)
    void onBtnMinusClicked(){
        String qtyStr = mEdQuantity.getText().toString().trim();
        if(qtyStr != null && !qtyStr.isEmpty()){
            int qty = Integer.parseInt(qtyStr);
            if(qty>0){
                qty--;
                qtyStr = String.valueOf(qty);
                mEdQuantity.setText(qtyStr);
                mEdQuantity.setSelection(qtyStr.length());
            }
        }
    }

    @OnClick(R.id.ln_select_product)
    void onBtnAddClicked(){
        startActivity(new Intent(getApplicationContext(), ProductSelectionAct.class));
    }

    @Override
    public void selectedProduct(Product product) {
        Toast.makeText(getApplicationContext(), product.productName + "", Toast.LENGTH_SHORT).show();
    }
}
