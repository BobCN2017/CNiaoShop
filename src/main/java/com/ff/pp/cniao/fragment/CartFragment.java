package com.ff.pp.cniao.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ff.pp.cniao.PayChanelActivity;
import com.ff.pp.cniao.adapter.BaseAdapter;
import com.ff.pp.cniao.adapter.CartAdapter;
import com.ff.pp.cniao.bean.Ware;
import com.ff.pp.cniao.bean.WareChange;
import com.ff.pp.cniao.bean.WareInCart;
import com.ff.pp.cniao.tools.Constants;
import com.ff.pp.cniao.tools.WareInCartProvider;
import com.ff.pp.myapplication2.R;
import com.ff.pp.cniao.view.DividerDecoration;

import org.greenrobot.eventbus.Subscribe;

import java.util.Iterator;
import java.util.List;

/**
 * Created by PP on 2017/3/20.
 */

public class CartFragment extends BaseFragment {

    private static final String TAG = "CartFragment";
    private static final int STATE_EDIT = 1;
    private static final int STATE_FINISH = 2;
    private static final int REQUEST_CODE = 100;

    private RecyclerView mRecyclerView;
    private CartAdapter mAdapter;
    private List<WareInCart> mData;
    private WareInCartProvider mProvider;
    private TextView mTotalCost;
    private Button mEditBtn, mPayBtn, mBottomDeleteBtn;
    private CheckBox mAllCheck;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, null);
        mProvider =WareInCartProvider.getInstance();

        initCartRecyclerView(view);
        initBottomWidget(view);
        countAndSetTotalCost();
        return view;
    }

    private void initCartRecyclerView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_cart);

        mData = mProvider.getAll();

        mAdapter = new CartAdapter(getContext(), mData);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerDecoration());

        mAdapter.setOnWareStateChangedListener(new CartAdapter.OnWareStateChangedListener() {
            @Override
            public void wareNumberChanged(WareInCart ware, int count) {
                ware.setCount(count);
                mProvider.update(ware);
                countAndSetTotalCost();

            }

            @Override
            public void wareSelectedChanged(WareInCart ware, boolean selected) {

            }
        });
        mAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                WareInCart ware = mData.get(position);
                boolean status = !ware.isChecked();
                ware.setChecked(status);
                mProvider.update(ware);
                countAndSetTotalCost();
                setAllCheckBoxState(status);
                mAdapter.notifyItemChanged(position);
            }
        });

    }

    private void initBottomWidget(View view) {
        mAllCheck = (CheckBox) view.findViewById(R.id.checkbox_pay);
        mTotalCost = (TextView) view.findViewById(R.id.textView_total_cost);
        mPayBtn = (Button) view.findViewById(R.id.btn_to_pay);
        mBottomDeleteBtn = (Button) view.findViewById(R.id.btn_delete_cart);

        mEditBtn = (Button) view.findViewById(R.id.button_edit);
        editCart();
        mAllCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setAllWareChecked(mAllCheck.isChecked());
            }
        });
        if (mProvider.isAllSelected()) {
            mAllCheck.setChecked(true);
        }

        mPayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResultAfterCheckLogin(
                        new Intent(getContext(), PayChanelActivity.class), REQUEST_CODE);

            }
        });
    }

    private void setAllWareChecked(boolean status) {
        mProvider.setAllSelected(status);
        mAdapter.notifyDataSetChanged();
        countAndSetTotalCost();
    }

    private void setAllCheckBoxState(boolean itemStatus) {
        if (!itemStatus) mAllCheck.setChecked(false);
        if (itemStatus && mProvider.isAllSelected()) {
            mAllCheck.setChecked(true);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e(TAG, "onActivityResult: ");
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                if (data!=null && (data.getSerializableExtra(Constants.KEY_WARE_PAYED_LIST)) != null) {
                    List<WareInCart> list = (List<WareInCart>) data.getSerializableExtra
                            (Constants.KEY_WARE_PAYED_LIST);
                    removeWarePaid(list);
                }
            }
        }
    }

    private void removeWarePaid(List<WareInCart> list) {
        for (WareInCart ware : list) {
            for (WareInCart inWare : mData) {
                if (ware.getId() == inWare.getId()) {
                    mData.remove(inWare);
                    break;
                }
            }
        }
        mAdapter.notifyDataSetChanged();
        countAndSetTotalCost();
    }

    private void editCart() {
        mEditBtn.setTag(STATE_FINISH);
        mEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int state = (int) v.getTag();
                if (state == STATE_EDIT) {
                    convertToFinish();
                } else {
                    convertToEdit();
                }
            }
        });

        mBottomDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Iterator<WareInCart> iterator = mData.iterator();
                while (iterator.hasNext()) {
                    WareInCart ware = iterator.next();
                    int postion = mData.indexOf(ware);
                    if (ware.isChecked()) {
//                        mProvider.delete(ware);
                        iterator.remove();
                        mAdapter.notifyItemRemoved(postion);
                    }
                }
//                mAdapter.notifyDataSetChanged();
            }
        });
    }

    private void convertToEdit() {
        mPayBtn.setVisibility(View.GONE);
        mTotalCost.setVisibility(View.GONE);
        mBottomDeleteBtn.setVisibility(View.VISIBLE);

        mEditBtn.setTag(STATE_EDIT);

        setAllWareChecked(false);
        setAllCheckBoxState(false);
         mEditBtn.setText("完成");
    }

    private void convertToFinish() {
        mPayBtn.setVisibility(View.VISIBLE);
        mTotalCost.setVisibility(View.VISIBLE);
        mBottomDeleteBtn.setVisibility(View.GONE);

        mEditBtn.setTag(STATE_FINISH);

        setAllWareChecked(true);
        setAllCheckBoxState(true);
        mEditBtn.setText("编辑");
    }

    private void countAndSetTotalCost() {
        Double total =mProvider.countTotal();
        mTotalCost.setText("合计：¥" + total);
    }


}
