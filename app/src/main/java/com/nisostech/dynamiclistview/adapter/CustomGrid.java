package com.nisostech.dynamiclistview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nisostech.dynamiclistview.R;
import com.nisostech.dynamiclistview.vos.ActivityScreenModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CustomGrid extends BaseAdapter  {
    private List<ActivityScreenModel> activityScreenModels;
    private Context mContext;
    private LayoutInflater inflater;

    public CustomGrid(Context c, List<ActivityScreenModel> list) {
        mContext = c;
        this.activityScreenModels=new ArrayList<ActivityScreenModel>();
        this.activityScreenModels=list;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public void updatetListData(List<ActivityScreenModel> data) {
        this.activityScreenModels.addAll(data);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return activityScreenModels.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid=convertView;
        ViewHolder holder;

        if (grid == null) {
            inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            grid = inflater.inflate(R.layout.grid_single, null);
            holder = new ViewHolder();
            holder.textView = (TextView) grid.findViewById(R.id.text);
            holder.imageView = (ImageView) grid.findViewById(R.id.imageView);
            grid.setTag(holder);
        } else {
            holder = (ViewHolder) grid.getTag();
        }

        holder.textView.setText(activityScreenModels.get(position).getText());
        Picasso.with(mContext) //
                .load(activityScreenModels.get(position).getImageurl()) //
                .placeholder(R.drawable.placeholder) //
                .error(R.drawable.placeholder) //
                .fit()
                .into(holder.imageView);
        return grid;
    }
  private class ViewHolder {

         TextView textView;

         ImageView imageView;
    }
}