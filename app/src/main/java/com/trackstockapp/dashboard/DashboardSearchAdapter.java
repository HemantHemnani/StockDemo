package com.trackstockapp.dashboard;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trackstockapp.R;
import com.trackstockapp.databinding.ItemDashboardSearchItemBinding;

import java.util.ArrayList;

public class DashboardSearchAdapter extends RecyclerView.Adapter<DashboardSearchAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<StockSearchBean.Datum> mList;
    private stockItemSelectListener mListener;
    public DashboardSearchAdapter(Context context , stockItemSelectListener listener)
    {
        this.mContext =context;
        this.mListener = listener;
    }

    public void setList(ArrayList<StockSearchBean.Datum> list)
    {
        this.mList=new ArrayList<>();
        this.mList = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemDashboardSearchItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext) ,
                R.layout.item_dashboard_search_item , viewGroup,false);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        StockSearchBean.Datum data = mList.get(i);
        viewHolder.binding.tvName.setText(data.getName());


        viewHolder.binding.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.selectStockItem(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
    ItemDashboardSearchItemBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding= DataBindingUtil.bind(itemView);
        }
    }

}
