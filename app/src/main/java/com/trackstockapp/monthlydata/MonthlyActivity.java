package com.trackstockapp.monthlydata;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;
import com.trackstockapp.DateUtil;
import com.trackstockapp.DialogUtil;
import com.trackstockapp.KeyboardUtil;
import com.trackstockapp.R;
import com.trackstockapp.dashboard.DashboardSearchAdapter;
import com.trackstockapp.dashboard.StockSearchBean;
import com.trackstockapp.dashboard.stockItemSelectListener;
import com.trackstockapp.databinding.ActivityMonthlyBinding;
import com.trackstockapp.server.ApiBuilderSingleton;
import com.trackstockapp.server.ApiInterface;
import com.trackstockapp.server.CallBackResponse;
import com.trackstockapp.server.GetBaseRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import retrofit2.Call;

public class MonthlyActivity extends AppCompatActivity {

    private ActivityMonthlyBinding mBinding;
    private Context mContext;
    private StockSearchBean.Datum mSelectedStockItem;
    private final String TAG = MonthlyActivity.class.getSimpleName();
    private String mStartDateStr = "", mEndDateStr = "";
    private HashMap<String, MonthlyStockBean> mHashMap;
    private ArrayList<String> mKeyList;
    private float mMaxYRange = 250;
    private int mMinYRange = 0;
    private int mMinXRange = 0;
    private int mMaxXRange = 25;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_monthly);
        getIntentData();
        getControls();

    }

    private void getIntentData() {
        if (getIntent().getParcelableExtra("selected-stock-item") != null) {
            mSelectedStockItem = getIntent().getParcelableExtra("selected-stock-item");
        }
    }

    private void getControls() {
        mContext = MonthlyActivity.this;
        mHashMap = new HashMap<>();
        mKeyList =new ArrayList<>();
        mStartDateStr = DateUtil.startDate();
        mEndDateStr = DateUtil.endDate();
        callMonthlyData();
    }

    /*
     * get list as per stock selected from dashboard
     * https://api.worldtradingdata.com/api/v1/history?symbol=AAPL&date_from=2019-07-01
     * &output=json&sort=newest&api_token=SZCUt2RLC1WqpPC4rpgpnOCqqSYlnWqzTVU10ahZwN5UUCiJFYA6piC0mYze
     * */
    private ApiInterface mApiInterface;
    private GetBaseRequest mGetBaseRequest;
    private StockSearchBean mStockSearchBean;
    private ArrayList<StockSearchBean.Datum> mStockSearchList;

    private void callMonthlyData() {
        KeyboardUtil.hideSoftKeyboard(this);
//        mSearchAdapter=new DashboardSearchAdapter(mContext , (stockItemSelectListener) this);
        mStockSearchList = new ArrayList<>();
        mApiInterface = ApiBuilderSingleton.getInstance();
        mGetBaseRequest = new GetBaseRequest(mContext);
        showLoaderOnRequest(true);
        Call call = mApiInterface.monthlyStockRecordAPI(mSelectedStockItem.getSymbol(), mStartDateStr, mEndDateStr);
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
                if (jsonObject.has("name")) {
                    manageResponse(response.toString());
                } else {
                    Toast.makeText(mContext, "" + jsonObject.optString("Message", ""), Toast.LENGTH_SHORT).show();
//                    mSearchAdapter.setList(mStockSearchList);
//                    mBinding.rvSearchList.setAdapter(mSearchAdapter);
                }
            }

            @Override
            public void getError(Object response, String error) {
                showLoaderOnRequest(false);
                Toast.makeText(mContext, "" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }, false);


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

    /***
     * handle response
     * *****/
    String mStockName = "";
    private JSONObject mResponseJson;

    private void manageResponse(String response) {
        try {
            mResponseJson = new JSONObject(response);

            mStockName = mResponseJson.optString("name", "");
            JSONObject historyJson = mResponseJson.getJSONObject("history");
            Iterator<String> iter = historyJson.keys();
            while (iter.hasNext()) {
                String key = iter.next();
                String temp1 = key;
                Log.e(TAG , "kkkkkkk::::::"+temp1);
                temp1 = temp1.substring(temp1.lastIndexOf("-")+1 ,temp1.length() );
                mKeyList.add(key);
                try {
                    JSONObject value = (JSONObject) historyJson.get(key);
                    MonthlyStockBean bean = new MonthlyStockBean(value.optString("open", ""),
                            value.optString("close", ""),
                            value.optString("high", ""),
                            value.optString("low", ""), value.optString("volume", "")
                    );
                    if(mMaxYRange<Double.parseDouble(bean.high))
                    {
                        mMaxYRange = (float) Double.parseDouble(bean.high);
                    }
                    mHashMap.put(key, bean);

                } catch (JSONException e) {
                    // Something went wrong!
                }
            }
//            String temp = mKeyList.get(0);
//            temp = temp.substring(temp.lastIndexOf("-")+1 ,temp.length() );

            mMaxXRange = mKeyList.size()-1;
            setChart();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /*
    * set line chart
    * */
    private void setChart()
    {
        mBinding.chart1.getDescription().setEnabled(false);
        // enable touch gestures
        mBinding.chart1.setTouchEnabled(true);
        mBinding.chart1.setDragDecelerationFrictionCoef(0.9f);
        // enable scaling and dragging
        mBinding.chart1.setDragEnabled(true);
        mBinding.chart1.setScaleEnabled(true);
        mBinding.chart1.setDrawGridBackground(false);
        mBinding.chart1.setHighlightPerDragEnabled(true);
        // if disabled, scaling can be done on x- and y-axis separately
        mBinding.chart1.setPinchZoom(true);
        // set an alternative background color
        mBinding.chart1.setBackgroundColor(Color.WHITE);


        setLineChat();
    }



    private void setLineChat() {
        setData();
        mBinding.chart1.animateX(2500);
        XAxis xAxis = mBinding.chart1.getXAxis();
        xAxis.setTextSize(10f);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setGranularity(1.0f);
        xAxis.setGranularityEnabled(true);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);

        xAxis.setAxisMinimum(mMinXRange);
        xAxis.setAxisMaximum(mMaxXRange);

//        xAxis.setValueFormatter(new IAxisValueFormatter() {
//            @Override
//            public String getFormattedValue(float value, AxisBase axis) {
////                return String.valueOf(str[(int)value]);
//            }
//        });

        mBinding.chart1.getXAxis().setValueFormatter(new IndexAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                Log.e(TAG , "valuevalue:::: "+value+"  size: "+mKeyList.size());
                    return ""+mKeyList.get((int) value);
            }
        });

        YAxis leftAxis = mBinding.chart1.getAxisLeft();
        leftAxis.setTextColor(ColorTemplate.getHoloBlue());
        Log.v(TAG, "mMaxYRangeeeeeee11 ::::::: " + mMaxYRange);
//        leftAxis.setAxisMaximum(2000f);
        leftAxis.setAxisMaximum(mMaxYRange);
        if (mMaxYRange < 100) {
            leftAxis.setGranularity((float)mMaxYRange);
        } else {
            leftAxis.setGranularity(100.0f);
        }
        leftAxis.setAxisMinimum(0f);

        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularityEnabled(true);
        Legend l = mBinding.chart1.getLegend();
        l.setEnabled(false);
        mBinding.chart1.getAxisRight().setEnabled(false);
        mBinding.chart1.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
    }

    int val = 0;


    LineDataSet set1;

    private void setData() {

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        ArrayList<String> arrayList = new ArrayList<>();


      /*  for (int i = mMinXRange; i <= mMaxXRange; i++) {
            String tempdate = mSelectedYearInt + "-" +checkDigit( mSelectedMonthInt )+ "-" + checkDigit(i);

            Log.v(TAG , "tempdate :: "+tempdate);

            if (mMonthHashMap.get(tempdate) != null) {
                Log.e(TAG , "mMonthHashMap ::::: "+mMonthHashMap.get(tempdate).get(0).amounttotal+"   tempdate : "+tempdate);
                yVals1.add(new Entry(i, (float)mMonthHashMap.get(tempdate).get(0).amounttotal));
            } else {
                yVals1.add(new Entry(i, 0));
            }
        }
*/

        /*for (int i = 0; i < mKeyList.size(); i++) {
            Log.e(TAG , "highhhh:: "+Float.parseFloat(mHashMap.get(mKeyList.get(i)).high));
                yVals1.add(new Entry(i, Float.parseFloat(mHashMap.get(mKeyList.get(i)).high)));
        }*/

        for (int i = 0; i < mMaxXRange; i++) {
            Log.e(TAG , "highhhh:: "+Float.parseFloat(mHashMap.get(mKeyList.get(i)).high));
            yVals1.add(new Entry(i, Float.parseFloat(mHashMap.get(mKeyList.get(i)).high)));
        }


        if (mBinding.chart1.getData() != null &&
                mBinding.chart1.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mBinding.chart1.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            mBinding.chart1.getData().notifyDataChanged();
            mBinding.chart1.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(yVals1, "DataSet 1");
            set1.setAxisDependency(YAxis.AxisDependency.LEFT);
            set1.setColor(ColorTemplate.getHoloBlue());
            set1.setCircleColor(Color.BLACK);
            set1.setLineWidth(2f);
            set1.setCircleRadius(3f);
            set1.setFillAlpha(65);
            set1.setFillColor(ColorTemplate.getHoloBlue());
            set1.setHighLightColor(Color.rgb(244, 117, 117));
            set1.setDrawCircleHole(false);

            LineData data = new LineData(set1);
            data.setValueTextColor(Color.BLACK);
            data.setValueTextSize(9f);
            mBinding.chart1.setData(data);


        }
    }

}
