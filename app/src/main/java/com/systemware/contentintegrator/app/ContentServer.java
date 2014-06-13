package com.systemware.contentintegrator.app;

import android.content.Context;
/**
 * Created by adrian.meraz on 6/10/2014.
 * XML API services for content servers
 */
public class ContentServer {
    Context mContext;
    QueryFormer qf = new QueryFormer();
    //default variable values
    public ContentServer(Context mContext){
        this.mContext = mContext;
    }

//all variables that can go into queries



    //Content Server API queries

    String targetCIQuery(){
        loginlogoff lilobj = new loginlogoff(mContext);
        String targetCIQuery = "http://" + lilobj.getHostname() + "." +
                lilobj.getDomain() + ":" + lilobj.getPortnumber() + "/ci";
        return targetCIQuery;
    }
    //deleteversion
    String deleteDSIDQuery(String DSID,String xid, String res){

        String delverQuery = "?action=deleteversion" + qf.formQuery(DSID,xid,res);
        return targetCIQuery() + delverQuery;
    }
    String deleteVersionQuery(String version,String xid, String res){
        String delverQuery = "?action=deleteversion" + qf.formQuery(version,xid,res);
        return targetCIQuery() + delverQuery;
    }
    //listdirectory
    String listdirQuery(String res, String xid, String maxseg, String offset){
        String listDirQuery = "?action=listdirectory" + qf.formQuery(res,xid,maxseg,offset);
        return targetCIQuery() + listDirQuery;
    }
    String listglobalindexQuery(String opt,String xid,String gix,String xvn,String xv,
                                String xve, String sdate, String edate, String max, String res,
                                String title,String alternate, String order){
        String listgixQuery = "?action=listgix" + qf.formQuery(opt,xid,gix,xvn,xv,xve,sdate,edate,max,res,title,alternate,order);
        return targetCIQuery() + listgixQuery;
    }
    //listnode
    String listNodeQuery(){
        String listNodeQuery = "?action=listnode";
        return targetCIQuery() + listNodeQuery;
    }


}
