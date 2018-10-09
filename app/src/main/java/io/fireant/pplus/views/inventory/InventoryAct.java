package io.fireant.pplus.views.inventory;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fireant.pplus.R;
import io.fireant.pplus.views.inventory.category.CategoryAct;
import io.fireant.pplus.views.inventory.product.ProductAct;

public class InventoryAct extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.layout_inventory, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @OnClick(R.id.btn_category_manage)
    void onBtnCategoryClicked(){
        startActivity(new Intent(getActivity(), CategoryAct.class));
    }

    @OnClick(R.id.btn_product_manage)
    void onBtnProductClicked(){
        startActivity(new Intent(getActivity(), ProductAct.class));
    }


}
