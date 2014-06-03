package com.systemware.contentintegrator.app;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;



/**
 * Created by adrian.meraz on 5/16/2014.
 */


public class loginlogoff {

    XmlParser xmlobj = new XmlParser("");

    Context mContext;

    public loginlogoff(Context mContext) {
        this.mContext = mContext;
    }

    private static String hostname = "gardner";
    private static String domain = "usa.systemware.com";
    private static int portnumber = 34544;
    private static String username = "admin";
    private static String password = "admin";
    private static String LogonRes = "";
    private static Boolean connection_state = false;
    private static Boolean login_successful = false;


    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public static String getDomain() {
        return domain;
    }

    public static void setDomain(String domain) {
        loginlogoff.domain = domain;
    }

    public int getPortnumber() {
        return portnumber;
    }

    public void setPortnumber(int portnumber) {
        this.portnumber = portnumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static String getLogonRes() {
        return LogonRes;
    }

    public static void setLogonRes(String logonRes) {
        LogonRes = logonRes;
    }

    public static Boolean getConnection_state() {
        return connection_state;
    }

    public static void setConnection_state(Boolean connection_state) {
        loginlogoff.connection_state = connection_state;
    }

    public static Boolean getLogin_successful() {
        return login_successful;
    }

    public static void setLogin_successful(Boolean login_successful) {
        loginlogoff.login_successful = login_successful;
    }


    protected String httplogonreq() {
        String httpstr = "?action=logon&user=" + getUsername() + "&password=" + getPassword();
        return httpstr;
    }

    protected String httpstringcreate() {
        String httpreq = "http://" + getHostname() + "." + getDomain() + ":" + getPortnumber() + "/ci" + httplogonreq();
        return httpreq;
    }

    protected void isLoginSuccessful() {
        queryreqresp qobj = new queryreqresp();
        setLogonRes(qobj.getResult());//get result from query
        if (getLogonRes().contains("<rc>0</rc><xrc>0</xrc><xsrc>0</xsrc>")) {
            setLogin_successful(true);
        } else {
            setLogin_successful(false);
        }
    }

    private void isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        setConnection_state(activeNetworkInfo != null && activeNetworkInfo.isConnected());
        Log.d("Message", "loginlogoff.java - getConnection_state(): " + getConnection_state());
        //return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
