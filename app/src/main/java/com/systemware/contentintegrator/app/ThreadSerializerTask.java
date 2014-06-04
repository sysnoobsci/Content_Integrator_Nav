package com.systemware.contentintegrator.app;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.Iterator;
import java.util.List;

/**
 * Created by adrian.meraz on 6/4/2014.
 */
public class ThreadSerializerTask extends AsyncTask<String, String, String> {

    List<Thread> threadsList;

    public List<Thread> getThreadsList() {
        return threadsList;
    }

    public void setThreadsList(List<Thread> threadsList) {
        this.threadsList = threadsList;
    }

    protected ThreadSerializerTask(List<Thread> threadsList) {
        setThreadsList(threadsList);
    }
    @Override
    protected String doInBackground(String... params) {
        Iterator<Thread> it = threadsList.iterator();
        while(it.hasNext())
        {
            it.next().start();
            try {
                it.next().join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return "YES";
    }

    protected void OnProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }
    // This is executed in the context of the main GUI thread
    protected void onPostExecute(String result){

    }
}