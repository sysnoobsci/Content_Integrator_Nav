package com.systemware.contentintegrator.app;

import android.content.Context;

/**
 * Created by adrian.meraz on 6/10/2014.
 */
public class ContentServer {
    Context mContext;
    public ContentServer(Context mContext){
        ContentServer.this.mContext = mContext;
    }
    String targetCIQuery(){
        loginlogoff lilobj = new loginlogoff(mContext);
        String targetCIQuery = "http://" + lilobj.getHostname() + "." +
                lilobj.getDomain() + ":" + lilobj.getPortnumber() + "/ci";
        return targetCIQuery;
    }
    /*String deleteVersionDSIDQuery(String DSID,Integer xid, ){

    }*/
    String listNodeQuery(){
        String listNodeQuery = "?action=listnode";
        return targetCIQuery() + listNodeQuery;
    }
}
