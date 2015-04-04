package com.nisostech.dynamiclistview.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.GridView;

import com.nisostech.dynamiclistview.Interface.OnAsynTaskCompleted;
import com.nisostech.dynamiclistview.Interface.OnTaskCompleted;
import com.nisostech.dynamiclistview.R;
import com.nisostech.dynamiclistview.adapter.CustomGrid;
import com.nisostech.dynamiclistview.networkcalls.AsyncListLoader;
import com.nisostech.dynamiclistview.networkcalls.VolleyJsonRequest;
import com.nisostech.dynamiclistview.vos.ActivityScreenModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity implements OnTaskCompleted,OnAsynTaskCompleted {

    List<ActivityScreenModel> activityScreenModels;
  private GridView grid;
    private VolleyJsonRequest volleyJsonRequest;
    private Context context;
    private CustomGrid customGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        grid = (GridView) findViewById(R.id.grid);
        context=this;
        volleyJsonRequest = new VolleyJsonRequest(MainActivity.this, context);
        volleyJsonRequest.makeJsonObjReqPost("http://192.168.1.2/api/getalldata?limit=20&offset=0");
        grid.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem == 0) {
                    // check if we reached the top or bottom of the list
                    View v = grid.getChildAt(0);
                    int offset = (v == null) ? 0 : v.getTop();
                    if (offset == 0) {
                        // reached the top:
                        return;
                    }
                } else if (totalItemCount - visibleItemCount == firstVisibleItem) {
                    View v = grid.getChildAt(totalItemCount - 1);
                    int offset = (v == null) ? 0 : v.getTop();
                    if (offset == 0) {
                        // reached the bottom:

                            new AsyncListLoader("http://192.168.1.2/api/getalldata?limit=40&offset=0",MainActivity.this)
                                    .execute();

                        return;
                    }
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTaskCompleted(JSONObject result) {
        try {
            setAdapter(result,false);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(String result) {

    }

    private void setAdapter(JSONObject result,boolean isUpdated) throws JSONException {

        JSONObject jObjectResult = result.getJSONObject("resp");
        JSONArray jArray = jObjectResult.getJSONArray("data");
        ActivityScreenModel activityScreenModel;
        activityScreenModels=new ArrayList<ActivityScreenModel>();
        for (int i = 0; i < jArray.length(); i++) {
            activityScreenModel=new ActivityScreenModel();
            activityScreenModel.setText(jArray.getJSONObject(i).getString("name"));
            activityScreenModel.setImageurl(jArray.getJSONObject(i).getString("imageUrl"));
            activityScreenModels.add(activityScreenModel);
        }
        if(!isUpdated) {
            customGrid = new CustomGrid(context, activityScreenModels);
            grid.setAdapter(customGrid);
        }else{
            customGrid.updatetListData(activityScreenModels);
            customGrid.notifyDataSetChanged();
        }
    }

    @Override
    public void afterSuccess(String result) {
        try {
            setAdapter(new JSONObject(result),true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterFailure(String result) {

    }
}
