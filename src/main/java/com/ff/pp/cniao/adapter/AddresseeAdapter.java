package com.ff.pp.cniao.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.CheckBox;

import com.ff.pp.cniao.bean.Addressee;
import com.ff.pp.cniao.tools.T;
import com.ff.pp.myapplication2.R;

import java.util.List;

/**
 * Created by PP on 2017/4/21.
 */

public class AddresseeAdapter extends BaseAdapter<Addressee> {
    private CheckBox mCheckBox;
    private OnEditAndDeleteAndSetDefaultClickListener mListener;
    private Context mContext;

    public AddresseeAdapter(Context context, List<Addressee> list) {
        super(context, list, R.layout.user_address_item);
        mContext=context;
    }

    @Override
    public void bindData(final BaseViewHolder holder, final Addressee addressee) {
        holder.getTextView(R.id.textView_user_name).setText(addressee.getName());
        holder.getTextView(R.id.textView_phone).setText(addressee.getPhone());
        holder.getTextView(R.id.textView_user_address).setText(addressee.getAddress());

        holder.getButton(R.id.button_edit_address).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mListener != null)
                    mListener.onEdit(mData.get(holder.getLayoutPosition()));
            }
        });
        holder.getButton(R.id.button_delete_address).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(mContext,holder);
            }
        });

        mCheckBox = holder.getCheckBox(R.id.checkBox);
        mCheckBox.setChecked(addressee.isDefaultAddress());
        mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox) v;
                if (checkBox.isChecked()) {
                    resetAllAddresseeNotDefault();
                    addressee.setDefaultAddress(((CheckBox) v).isChecked());
                    if (mListener != null) {
                        mListener.onSetDefault(addressee);
                    }
                    notifyDataSetChanged();
                }
            }
        });

    }

    private void resetAllAddresseeNotDefault() {
        for (Addressee addressee : mData) {
            addressee.setDefaultAddress(false);
        }
    }

    public void setOnEditAndDeleteButtonClickListener(OnEditAndDeleteAndSetDefaultClickListener listener) {
        mListener = listener;
    }

    public void showDialog(Context context, final BaseViewHolder holder) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("删除收件人").setMessage("确定删除该收件人？")
        ;

        builder.setCancelable(true).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (mListener != null)
                    mListener.onDelete(mData.get(holder.getLayoutPosition()));

                mData.remove(holder.getLayoutPosition());
                notifyItemRemoved(holder.getLayoutPosition());
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();
    }

    public interface OnEditAndDeleteAndSetDefaultClickListener {
        void onEdit(Addressee addressee);

        void onDelete(Addressee addressee);

        void onSetDefault(Addressee addressee);
    }
}
