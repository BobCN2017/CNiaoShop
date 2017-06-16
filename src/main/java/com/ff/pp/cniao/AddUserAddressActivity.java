package com.ff.pp.cniao;

import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.OptionsPickerView.OnOptionsSelectListener;
import com.ff.pp.cniao.Application.MyApplication;
import com.ff.pp.cniao.adapter.AddresseeAdapter;
import com.ff.pp.cniao.bean.Addressee;
import com.ff.pp.cniao.bean.BaseMessage;
import com.ff.pp.cniao.provinceCityDistrict.XmlParserHandler;
import com.ff.pp.cniao.provinceCityDistrict.model.CityModel;
import com.ff.pp.cniao.provinceCityDistrict.model.DistrictModel;
import com.ff.pp.cniao.provinceCityDistrict.model.ProvinceModel;
import com.ff.pp.cniao.tools.Constants;
import com.ff.pp.cniao.tools.OkHttpHelper;
import com.ff.pp.cniao.tools.SpotsDialogCallBack;
import com.ff.pp.cniao.tools.T;
import com.ff.pp.cniao.view.DividerDecoration;
import com.ff.pp.cniao.view.ErasableEditText;
import com.ff.pp.cniao.view.ThreePositionToolbar;
import com.ff.pp.myapplication2.R;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import okhttp3.Response;

public class AddUserAddressActivity extends BaseActivity
        implements AddresseeAdapter.OnEditAndDeleteAndSetDefaultClickListener {
    private static final String TAG = "AddUserAddressActivity";
    private XmlParserHandler xmlParserHandler;
    private List<ProvinceModel> mProvinces;
    private List<ArrayList<String>> mCities = new ArrayList<>();
    private List<ArrayList<ArrayList<String>>> mDistricts = new ArrayList<>();

    private OptionsPickerView mCityPicker;

    private RecyclerView mRecyclerView;
    private AddresseeAdapter mAdapter;
    private List<Addressee> mData;

    private ErasableEditText mAddressee, mUserPhone, mProvinceCity, mAvenueAddress;

    private Addressee mEditAddressee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user_address);
        initToolbar();
        initProvinceCityPicker();
        initRecyclerView();
        initEditText();
        requestAddresseesFromServer();
    }

    private void initEditText() {
        mAddressee = (ErasableEditText) findViewById(R.id.editTextClear_user_name);
        mUserPhone = (ErasableEditText) findViewById(R.id.editTextClear_user_phone);
        mProvinceCity = (ErasableEditText) findViewById(R.id.editTextClear_address_province);
        mAvenueAddress = (ErasableEditText) findViewById(R.id.editTextClear_address_avenue);

        mProvinceCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCityPicker.show();
            }
        });
    }

    private void initRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_user_address);
        mData = new ArrayList<>();
        mAdapter = new AddresseeAdapter(this, mData);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerDecoration());
        mAdapter.setOnEditAndDeleteButtonClickListener(this);
    }

    private void initToolbar() {
        ThreePositionToolbar toolbar = (ThreePositionToolbar) findViewById(R.id.toolbar_add_address);
        toolbar.setTitle("收货地址");

        toolbar.setRightButtonIcon(R.drawable.actionbar_add_icon);
        toolbar.setOnRightButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOrHideAddAddressView();
            }
        });
    }

    private void showOrHideAddAddressView() {
        LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout_add_address);
        if (layout.getVisibility() == View.GONE) {
            layout.setVisibility(View.VISIBLE);
        } else {
            layout.setVisibility(View.GONE);
        }
    }

    private void showAddAddressView() {
        LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout_add_address);
        layout.setVisibility(View.VISIBLE);

    }

    private void initProvinceCityPicker() {

        initProvinceCityDistrictData();
        constructThreeLevelList();

        OnOptionsSelectListener selectListener = new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = mProvinces.get(options1).getName()
                        + mCities.get(options1).get(options2)
                        + mDistricts.get(options1).get(options2).get(options3);
                if (mProvinceCity != null)
                    mProvinceCity.setText(tx);
            }
        };

        mCityPicker = new OptionsPickerView.Builder(this, selectListener)
                .setTitleText("城市选择")
                .setContentTextSize(20)//设置滚轮文字大小
                .setDividerColor(Color.GREEN)//设置分割线的颜色
                .setSelectOptions(0, 1)//默认选中项
                .setBgColor(Color.BLACK)
                .setTitleBgColor(Color.DKGRAY)
                .setTitleColor(Color.LTGRAY)
                .setCancelColor(Color.YELLOW)
                .setSubmitColor(Color.YELLOW)
                .setTextColorCenter(Color.LTGRAY)
                .isCenterLabel(true) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setLabels("", "", "")
                .build();

        mCityPicker.setPicker(mProvinces, mCities, mDistricts);//三级选择器*/

    }

    private void initProvinceCityDistrictData() {

        AssetManager assets = getAssets();
        try {
            InputStream inputStream = assets.open("province_data.xml");
            xmlParserHandler = new XmlParserHandler();
            SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
            saxParser.parse(inputStream, xmlParserHandler);
            inputStream.close();
            mProvinces = xmlParserHandler.getProvinceList();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }


    private void constructThreeLevelList() {
        if (mProvinces == null) return;
        for (ProvinceModel p : mProvinces) {
            List<CityModel> cities = p.getCiteList();
            ArrayList<String> cityStrings = new ArrayList<>();
            ArrayList<ArrayList<String>> districtsOfOneCity = new ArrayList<>();
            for (CityModel city : cities) {
                cityStrings.add(city.getName());

                List<DistrictModel> districts = city.getDistrictList();
                ArrayList<String> districtStrings = new ArrayList<>();

                for (DistrictModel district : districts) {
                    districtStrings.add(district.getName());
                }

                districtsOfOneCity.add(districtStrings);
            }
            mDistricts.add(districtsOfOneCity);
            mCities.add(cityStrings);
        }
    }

    public void addAddressee(View view) {
        if (InputIsEmpty()) return;
        if (mEditAddressee == null) {
            Addressee addressee = getAddresseeFromEditText();
            mData.add(addressee);
            mAdapter.notifyItemInserted(mData.size());
            createAddresseeToServer(addressee);
        } else {
            updateAddressee();
        }
        ClearEditText();
    }

    private void updateAddressee() {

        mEditAddressee.setName(mAddressee.getText().toString());
        mEditAddressee.setPhone(mUserPhone.getText().toString());
        mEditAddressee.setAddress(mProvinceCity.getText().toString() +
                mAvenueAddress.getText().toString());

        mAdapter.notifyDataSetChanged();
        updateAddresseeToServer(mEditAddressee);
        mEditAddressee = null;
    }

    private void updateAddresseeToServer(Addressee addressee) {
        String url = Constants.ADDRESS_UPDATE_URL;
        OkHttpHelper.getInstance().post(url, getPostParams(addressee), new SpotsDialogCallBack<BaseMessage>(this) {
            @Override
            public void onSuccess(Response response, BaseMessage message) {
                if (message.getStatus() == 1) {
                    Log.e(TAG, "onSuccess: 更新用户地址成功");
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

    @NonNull
    private Addressee getAddresseeFromEditText() {
        Addressee addressee = new Addressee();
        addressee.setName(mAddressee.getText().toString());
        addressee.setPhone(mUserPhone.getText().toString());
        addressee.setAddress(mProvinceCity.getText().toString() +
                mAvenueAddress.getText().toString());
        addressee.setUserId(getUserId());
        return addressee;
    }

    private void ClearEditText() {
        mAddressee.setText("");
        mUserPhone.setText("");
        mProvinceCity.setText("");
        mAvenueAddress.setText("");
    }

    private boolean InputIsEmpty() {
        if (TextUtils.isEmpty(mAddressee.getText().toString())) {
            T.showTips("收货人姓名不能为空");
            return true;
        }
        if (TextUtils.isEmpty(mUserPhone.getText().toString())) {
            T.showTips("收货人电话不能为空");
            return true;
        }
        if (TextUtils.isEmpty(mProvinceCity.getText().toString())) {
            T.showTips("收货人省市区不能为空");
            return true;
        }
        if (TextUtils.isEmpty(mAvenueAddress.getText().toString())) {
            T.showTips("收货人详细地址不能为空");
            return true;
        }
        return false;
    }

    private void createAddresseeToServer(Addressee addressee) {

        OkHttpHelper.getInstance().post(Constants.ADDRESS_CREATE_URL, getPostParams(addressee),
                new SpotsDialogCallBack<BaseMessage>(this) {
                    @Override
                    public void onSuccess(Response response, BaseMessage message) {
                        if (message.getStatus() == 1) {
                            Log.e(TAG, "onSuccess: 创建用户地址成功");
                        }
                    }

                    @Override
                    public void onError(Response response, int code, Exception e) {

                    }
                });
    }

    private Map<String, String> getPostParams(Addressee addressee) {
        Map<String, String> params = new HashMap<>();
        params.put("id", addressee.getId() + "");
        params.put("user_id", addressee.getUserId() + "");
        params.put("consignee", addressee.getName());
        params.put("phone", addressee.getPhone());
        params.put("addr", addressee.getAddress());
        params.put("zip_code", "");
        params.put("is_default", addressee.isDefaultAddress() + "");
        return params;
    }

    private void requestAddresseesFromServer() {
        String url = Constants.ADDRESS_REQUEST_URL + "?user_id=" + getUserId();
        OkHttpHelper.getInstance().get(url, new SpotsDialogCallBack<List<Addressee>>(this) {
            @Override
            public void onSuccess(Response response, List<Addressee> addressees) {
                mData.clear();
                Collections.sort(addressees);
                mData.addAll(addressees);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

    private void deleteAddresseeInServer(String addresseeId) {
        Map<String, String> params = new HashMap<>();
        params.put("id", addresseeId);

        OkHttpHelper.getInstance().post(Constants.ADDRESS_DELETE_URL,
                params, new SpotsDialogCallBack<BaseMessage>(this) {
                    @Override
                    public void onSuccess(Response response, BaseMessage message) {
                        if (message.getStatus() == 1) {
                            Log.e(TAG, "onSuccess: 删除用户地址成功");
                        }
                    }

                    @Override
                    public void onError(Response response, int code, Exception e) {

                    }
                });
    }

    private int getUserId() {
        return MyApplication.getInstance().getUser().getUserInfo().getId();
    }

    private String getProvinceCityDistrictFromAddress(String address) {
        StringBuilder stringBuilder = new StringBuilder();
        for (ProvinceModel province : mProvinces) {
            if (address.contains(province.getName())) {
                stringBuilder.append(province.getName());
                List<CityModel> cities = province.getCiteList();
                for (CityModel city : cities) {
                    if (address.contains(city.getName())) {
                        stringBuilder.append(city.getName());
                        List<DistrictModel> districts = city.getDistrictList();
                        for (DistrictModel district : districts) {
                            if (address.contains(district.getName())) {
                                stringBuilder.append(district.getName());
                                break;
                            }
                        }
                        break;
                    }
                }
                break;
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public void onEdit(Addressee addressee) {
        showAddAddressView();
        String provinceCityDistrict = getProvinceCityDistrictFromAddress(addressee.getAddress());
        String others = addressee.getAddress().substring(provinceCityDistrict.length());
        mUserPhone.setText(addressee.getPhone());
        mAddressee.setText(addressee.getName());
        mProvinceCity.setText(provinceCityDistrict);
        mAvenueAddress.setText(others);
        mEditAddressee = addressee;
    }

    @Override
    public void onDelete(Addressee addressee) {
        deleteAddresseeInServer(addressee.getId().toString());
    }

    @Override
    public void onSetDefault(Addressee addressee) {
        updateAddresseeToServer(addressee);
    }
}
