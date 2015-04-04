package com.nisostech.dynamiclistview.Interface;

/**
 * Created by Soham Banerjee
 */
public interface OnAsynTaskCompleted {
    void afterSuccess(String result);

    void afterFailure(String result);
}
