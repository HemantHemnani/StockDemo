package com.trackstockapp.dashboard;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.trackstockapp.AppApplication;
import com.trackstockapp.DialogUtil;
import com.trackstockapp.KeyboardUtil;
import com.trackstockapp.R;
import com.trackstockapp.databinding.ActivityDashboardBinding;
import com.trackstockapp.monthlydata.MonthlyActivity;
import com.trackstockapp.server.ApiBuilderSingleton;
import com.trackstockapp.server.ApiInterface;
import com.trackstockapp.server.CallBackResponse;
import com.trackstockapp.server.GetBaseRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;

public class DashBoardActivity extends AppCompatActivity implements stockItemSelectListener {

    private Context mContext;
    private ActivityDashboardBinding mBinding;
    private LinearLayoutManager mLayoutManager;
    private DashboardSearchAdapter mSearchAdapter;
    SearchView mSearchView;
    private EditText mSearchEt;
    private ApiInterface mApiInterface;
    private GetBaseRequest mGetBaseRequest;
    private StockSearchBean mStockSearchBean;
    private ArrayList<StockSearchBean.Datum> mStockSearchList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard);
        getControls();
    }

    private void getControls() {
        mContext = DashBoardActivity.this;
        mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mBinding.rvSearchList.setLayoutManager(mLayoutManager);
        mSearchAdapter = new DashboardSearchAdapter(mContext , this);
        mSearchView = mBinding.searchView;
        mSearchEt = mSearchView.findViewById(R.id.search_src_text);
        searchData();

    }


    /*
    * search through keyword/symbol
    * */
    private void callSearchAPI(String searchTxt) {
        KeyboardUtil.hideSoftKeyboard(this);
        mSearchAdapter=new DashboardSearchAdapter(mContext , (stockItemSelectListener) this);
        mStockSearchList = new ArrayList<>();
        mApiInterface = ApiBuilderSingleton.getInstance();
        mGetBaseRequest = new GetBaseRequest(mContext);
        Call call = mApiInterface.searchStockAPI(searchTxt);
        mGetBaseRequest.setBaseRequest(call, new CallBackResponse() {
            @Override
            public void getResponse(Object response) {
                showLoaderOnRequest(false);
                Log.e("rr", "rrrrr :::: " + response);
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(jsonObject.has("symbols_requested")) {
                    mStockSearchBean = new Gson().fromJson(response.toString(), StockSearchBean.class);
                    if (mStockSearchBean.getSymbolsReturned() > 0) {
                        mStockSearchList = (ArrayList<StockSearchBean.Datum>) mStockSearchBean.getData();
                        setListData();
                    }
                }else{
                    Toast.makeText(mContext, ""+jsonObject.optString("Message",""), Toast.LENGTH_SHORT).show();

                    mSearchAdapter.setList(mStockSearchList);
                    mBinding.rvSearchList.setAdapter(mSearchAdapter);
                }
            }

            @Override
            public void getError(Object response, String error) {
                showLoaderOnRequest(false);
                Toast.makeText(mContext, ""+error.toString(), Toast.LENGTH_SHORT).show();
            }
        }, false);


    }


    private void setListData() {
        mSearchAdapter=new DashboardSearchAdapter(mContext , this);
        mSearchAdapter.setList(mStockSearchList);
        mBinding.rvSearchList.setAdapter(mSearchAdapter);
    }


    private Handler mHandler = new Handler();

    private void searchData() {
        mBinding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (mTextChangeEnable && !TextUtils.isEmpty(newText)) {
                    mHandler.removeCallbacksAndMessages(null);
                    mHandler.postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            showLoaderOnRequest(true);
                            callSearchAPI(newText.toLowerCase());
                        }
                    }, 500);

                }
                return true;
            }
        });
    }

    private boolean mTextChangeEnable = true;

    public void setTextChangeEnable(boolean enable) {
        this.mTextChangeEnable = enable;
    }


    Dialog mShowNetworkDialog;

    public void showLoaderOnRequest(boolean isShowLoader) {
        if (isShowLoader && mShowNetworkDialog == null) {
            mShowNetworkDialog = DialogUtil.showLoader(mContext);
        } else if (isShowLoader && !mShowNetworkDialog.isShowing()) {
            mShowNetworkDialog = null;
            mShowNetworkDialog = DialogUtil.showLoader(mContext);
        } else {
            if (mShowNetworkDialog != null) {
                DialogUtil.hideLoader(mShowNetworkDialog);
                mShowNetworkDialog = null;
            }
        }
    }

    /*
    * manage selection
    * */
    @Override
    public void selectStockItem(int position) {
        Intent intent = new Intent(mContext , MonthlyActivity.class);
        intent.putExtra("selected-stock-item" , mStockSearchList.get(position) );
        startActivity(intent);
    }
}
