package com.systemware.contentintegrator.app;

/**
 * Created by john.williams on 5/27/2014.
 */
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
public class queryreqresp {

    static String result;

    static HttpClient httpclient = new DefaultHttpClient();
    static HttpPost httppost = new HttpPost("http://www.yoursite.com/");

    public static String getResult() {
        return result;
    }

    public static void setResult(String result) {
        queryreqresp.result = result;
    }

    protected static class ReqTask extends AsyncTask<String, Void, String> {
        static String query;
        static String className;
        protected ReqTask(String query, String className){
            setQuery(query);
            setClassName(className);
        }

        public static String getQuery() {
            return query;
        }

        public static void setQuery(String query) {
            ReqTask.query = query;
        }

        public static String getClassName() {
            return className;
        }

        public static void setClassName(String className) {
            ReqTask.className = className;
        }

        @Override
        protected String doInBackground(String... args) {
            XmlParser xmlobj = new XmlParser();
            StringBuilder total = new StringBuilder();
            try {
                HttpPost httptemp = new HttpPost(getQuery());//form http req string and assign to httppost
                Log.d("Variable", "loginlogoff.java - httpstringcreate() result: " + getQuery());
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
            return total.toString();

        }
        protected void onPostExecute(String result) {//check if resulting response is a well-formed XML file
            Log.d("Variable", "Task Executed from" + this.getClass().getName() + " + result: " + result);
            setResult(result);//store the result
        }
    }
}
