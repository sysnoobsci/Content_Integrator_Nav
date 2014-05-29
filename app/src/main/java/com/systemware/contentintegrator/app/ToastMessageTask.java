package com.systemware.contentintegrator.app;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by adrian.meraz on 5/29/2014.
 */
// A class that will run Toast messages in the main GUI context
    class ToastMessageTask extends AsyncTask<String, String, String> {
    static String toastMessage;
    static Context mContext;

    protected ToastMessageTask(Context mContext,String toastMessage) {
        this.mContext=mContext;
        this.toastMessage=toastMessage;
    }
    @Override
    protected String doInBackground(String... params) {
        return toastMessage;
    }

    protected void OnProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }
    // This is executed in the context of the main GUI thread
    protected void onPostExecute(String result){
        Toast toast = Toast.makeText(mContext, result, Toast.LENGTH_SHORT);
        toast.show();
    }
}