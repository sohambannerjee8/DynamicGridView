package com.nisostech.dynamiclistview.Interface;

import org.json.JSONObject;

/**
 * Created by nisos-admin on 9/10/14.
 */
public interface OnTaskCompleted {
    void onTaskCompleted(JSONObject result);

    void onError(String result);
}
