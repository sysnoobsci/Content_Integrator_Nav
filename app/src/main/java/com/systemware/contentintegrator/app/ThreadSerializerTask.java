package com.systemware.contentintegrator.app;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by adrian.meraz on 6/4/2014.
 * For this class, you pass in an arrayList of Threads and it will serialize the execution
 * of the threads
 */
public class ThreadSerializerTask extends AsyncTask<String, String, String> {

    List<Thread> threadsList = new ArrayList<Thread>();
    int threadcount = 0;

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

        //Iterator<Thread> it = threadsList.iterator();
        /*while(it.hasNext())
        {
            Log.d("Message", "Thread " + threadcount + " has started");
            it.next().start();
            try {
                it.next().join();
                Log.d("Message", "Thread " + threadcount + " has joined");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            threadcount++;
        }*/
        int j = 0;
        while(getThreadsList().size() > j){
            getThreadsList().get(j).start();
            Log.d("Message", "Thread " + threadcount + " has started");
            try {
                getThreadsList().get(j).join();
                Log.d("Message", "Thread " + threadcount + " has joined");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            j++;
            threadcount++;
        }
        return "DONE";
    }

    protected void OnProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }
    // This is executed in the context of the main GUI thread
    protected void onPostExecute(String result){
        Log.d("Message", "ThreadSerializerTask status: " + result);
        Log.d("Message", "Threads ran: " + threadcount + " of " + getThreadsList().size());
    }
}