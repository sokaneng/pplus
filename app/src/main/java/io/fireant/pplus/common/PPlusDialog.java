package io.fireant.pplus.common;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import io.fireant.pplus.R;

/**
 * Created by engsokan on 8/12/18.
 */

public class PPlusDialog{

    private Context context;
    private PPlusDialogListener listener;

    public PPlusDialog(Context context, PPlusDialogListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void confirmDeleteDialog(String message, String positiveText, String negativeText){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);

        ImageView mImgIcon = dialog.findViewById(R.id.img_icon);
        mImgIcon.setImageDrawable(context.getDrawable(R.drawable.delete_icon));

        TextView mTvTitle = dialog.findViewById(R.id.tv_title);
        mTvTitle.setText(R.string.confirmation);

        TextView mTvMsg = dialog.findViewById(R.id.tv_message);
        mTvMsg.setText(message);

        Button btnPositive = dialog.findViewById(R.id.btn_positive);
        btnPositive.setText(positiveText);
        btnPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onPositiveClicked();
                dialog.dismiss();
            }
        });

        Button btnNegative = dialog.findViewById(R.id.btn_negative);
        btnNegative.setText(negativeText);
        btnNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    public void warningDialog(String message){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);

        ImageView mImgIcon = dialog.findViewById(R.id.img_icon);
        mImgIcon.setImageDrawable(context.getDrawable(R.drawable.delete_icon));

        TextView mTvTitle = dialog.findViewById(R.id.tv_title);
        mTvTitle.setText(R.string.confirmation);

        TextView mTvMsg = dialog.findViewById(R.id.tv_message);
        mTvMsg.setText(message);

        Button btnPositive = dialog.findViewById(R.id.btn_positive);
        btnPositive.setText(R.string.got_it);
        btnPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Button btnNegative = dialog.findViewById(R.id.btn_negative);
        btnNegative.setVisibility(View.GONE);

        dialog.show();

    }

    public void confirmSuccessDialog(String message, String positiveText, String negativeText){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);

        ImageView mImgIcon = dialog.findViewById(R.id.img_icon);
        mImgIcon.setImageDrawable(context.getDrawable(R.drawable.success_icon));

        TextView mTvTitle = dialog.findViewById(R.id.tv_title);
        mTvTitle.setText(R.string.congratulation);

        TextView mTvMsg = dialog.findViewById(R.id.tv_message);
        mTvMsg.setText(message);

        Button btnPositive = dialog.findViewById(R.id.btn_positive);
        btnPositive.setText(positiveText);
        btnPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onPositiveClicked();
                dialog.dismiss();
            }
        });

        Button btnNegative = dialog.findViewById(R.id.btn_negative);
        btnNegative.setText(negativeText);
        btnNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onNegativeClicked();
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    public void confirmSuccessUpdateDialog(String message, String positiveText){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);

        ImageView mImgIcon = dialog.findViewById(R.id.img_icon);
        mImgIcon.setImageDrawable(context.getDrawable(R.drawable.success_icon));

        TextView mTvTitle = dialog.findViewById(R.id.tv_title);
        mTvTitle.setText(R.string.congratulation);

        TextView mTvMsg = dialog.findViewById(R.id.tv_message);
        mTvMsg.setText(message);

        Button btnPositive = dialog.findViewById(R.id.btn_positive);
        btnPositive.setText(positiveText);
        btnPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onPositiveClicked();
                dialog.dismiss();
            }
        });

        Button btnNegative = dialog.findViewById(R.id.btn_negative);
        btnNegative.setVisibility(View.GONE);

        dialog.show();

    }

    public interface PPlusDialogListener {
        void onPositiveClicked();
        void onNegativeClicked();
    }
}
