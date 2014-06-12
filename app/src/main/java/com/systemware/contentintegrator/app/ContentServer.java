package com.systemware.contentintegrator.app;

import android.content.Context;

/**
 * Created by adrian.meraz on 6/10/2014.
 * XML API services for content servers
 */
public class ContentServer {
    Context mContext;
    private String _DSID = "";//default DSID of a report
    private int _xid = 0;//default Content Server id
    private String _res = "";//default report path
    private int _version = 0;//default report version

    public ContentServer(Context mContext){
        ContentServer.this.mContext = mContext;
    }


    public ContentServer DSID(String DSID){
        this._DSID = _DSID;
        return this;
    }
    public ContentServer xid(int xid){
        this._xid = _xid;
        return this;
    }
    public ContentServer res(String res){
        this._res = _res;
        return this;
    }
    public ContentServer version(int _version){
        this._version = _version;
        return this;
    }
    String targetCIQuery(){
        loginlogoff lilobj = new loginlogoff(mContext);
        String targetCIQuery = "http://" + lilobj.getHostname() + "." +
                lilobj.getDomain() + ":" + lilobj.getPortnumber() + "/ci";
        return targetCIQuery;
    }
    //deleteversion
    String deleteDSIDQuery(String DSID,int xid, String res){
        ContentServer query1 = new ContentServer(mContext).DSID(DSID).xid(xid).res(res);
        String delverQuery;
        if(res.equals(null) || res.isEmpty()){
            delverQuery = "?action=deleteversion&xid=" + xid + "&dsid=" + DSID;
            return delverQuery;
        }
        else {
            delverQuery = "?action=deleteversion&res=" + res + "&xid=" + xid + "&dsid=" + DSID;
            return targetCIQuery() + delverQuery;
        }
    }
    String deleteVersionQuery(int version,int xid, String res){
        String delverQuery;
        delverQuery = "?action=deleteversion&res=" + res + "&xid=" + xid + "&version=" + version;
        return targetCIQuery() + delverQuery;

    }
    //listdirectory
    String listdirQuery(String res, int xid, int maxseg, String offset){
        String listDirQuery = "?action=listdirectory&res=" + res + "&xid=" +
                xid + "&maxseg=" + maxseg + "&offset=" + offset;
        return listDirQuery;
    }
    //listnode
    String listNodeQuery(){
        String listNodeQuery = "?action=listnode";
        return targetCIQuery() + listNodeQuery;
    }


}
