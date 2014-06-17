package com.systemware.contentintegrator.queries;

import android.content.Context;

import com.systemware.contentintegrator.app.QueryFormer;
import com.systemware.contentintegrator.app.loginlogoff;

/**
 * Created by adrian.meraz on 6/17/2014.
 * XML API services for script recording
 */
public class CIScriptRecording {
    Context mContext;
    QueryFormer qf = new QueryFormer();

    public CIScriptRecording(Context mContext){
        this.mContext = mContext;
    }

    String targetCIQuery(){
        loginlogoff lilobj = new loginlogoff(mContext);
        String targetCIQuery = "http://" + lilobj.getHostname() + "." +
                lilobj.getDomain() + ":" + lilobj.getPortnumber() + "/ci";
        return targetCIQuery;
    }
    //recordopen
    String recordopenQuery(String recordtxn,String filename){
        String recordopenQuery = "?action=recordopen" + qf.formQuery(recordtxn,filename);
        return targetCIQuery() + recordopenQuery;
    }
    //recordsave
    String recordsaveQuery(String recordtxn,String filename,String filedesc){
        String recordsaveQuery = "?action=recordsave" + qf.formQuery(recordtxn,filename,filedesc);
        return targetCIQuery() + recordsaveQuery;
    }
    //runscript
    String runscriptQuery(String script, String scriptid, String tid){
        String runscriptQuery = "?action=runscript" + qf.formQuery(script,scriptid,tid);
        return targetCIQuery() + runscriptQuery;
    }
    //startrecording
    String startrecordingQuery(){
        String startrecordingQuery = "?action=startrecording";
        return targetCIQuery() + startrecordingQuery;
    }
    //stoprecording
    String stoprecordingQuery(){
        String stoprecordingQuery = "?action=stoprecording";
        return targetCIQuery() + stoprecordingQuery;
    }



}
