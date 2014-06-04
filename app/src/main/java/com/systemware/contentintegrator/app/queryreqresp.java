package com.systemware.contentintegrator.app;

/**
 * Created by john.williams on 5/27/2014.
 */
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by adrian.meraz on 5/20/2014.
 */
public class queryreqresp{

    static String result = "No result";

    static HttpClient httpclient = new DefaultHttpClient();
    static HttpPost httppost = new HttpPost("http://www.yoursite.com/");

    private static String query;
    private static String className;
    private static Context mContext;


    public static String getResult() {
        return result;
    }

    public static void setResult(String result) {
        queryreqresp.result = result;
    }

    public static String getQuery() {
        return query;
    }

    public static void setQuery(String query) {
        queryreqresp.query = query;
    }

    public static String getClassName() {
        return className;
    }

    public static void setClassName(String className) {
        queryreqresp.className = className;
    }

    public static Context getActContext() {
        return mContext;
    }

    public static void setActContext(Context mContext) {
        queryreqresp.mContext = mContext;
    }

    protected static void eraseQueryResults(){
        setResult("No result");
    }

    protected static class ReqTask extends AsyncTask<String, Void, String> {

        protected ReqTask(String query, String className, Context context){
            setQuery(query);
            setClassName(className);
            Log.d("Variable", "ReqTask context value: " + context);
            setActContext(context);
            Log.d("Variable", "ReqTask getActContext() value: " + getActContext());
        }

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            //dialog = ProgressDialog.show(getActivityContext(),"Logging in...","Please Wait",false);
        }

        @Override
        protected String doInBackground(String... args) {
            XmlParser xmlobj = new XmlParser();
            StringBuilder total = new StringBuilder();
            try {
                HttpPost httptemp = new HttpPost(getQuery());//form http req string and assign to httppost
                Log.d("Variable", getClassName() + ".java - httpstringcreate() result: " + getQuery());
                httppost = httptemp;
                // Execute HTTP Post Request

                HttpResponse response = httpclient.execute(httppost);

                HttpEntity ht = response.getEntity();

                BufferedHttpEntity buf = new BufferedHttpEntity(ht);

                InputStream is = buf.getContent();

                BufferedReader r = new BufferedReader(new InputStreamReader(is));

                //StringBuilder total = new StringBuilder();
                String line;
                while ((line = r.readLine()) != null) {
                    total.append(line);
                }
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Log.d("Variable", "Caller " + getClassName() + ".java - total.toString() result: " + total.toString());
            if (!xmlobj.isXMLformat(total.toString())) {
                Log.e("XMLFormatError", getClassName() + ".java - XML is malformed");
            }
            Log.d("Message", "Right before reqTask returns...");
            return total.toString();
        }

        protected void onPostExecute(String result) {
            Log.d("Variable", "Task Executed from " + this.getClass().getName() + " + result: " + result);
            setResult(result);//store the result
        }
    }//end of ReqTask
}
