package com.systemware.contentintegrator.queries;

import android.content.Context;

import com.systemware.contentintegrator.app.QueryFormer;
import com.systemware.contentintegrator.app.loginlogoff;

/**
 * Created by adrian.meraz on 6/10/2014.
 * XML API services for content servers
 */
public class CIContentServer {
    Context mContext;
    QueryFormer qf = new QueryFormer();
    //default variable values
    public CIContentServer(Context mContext){
        this.mContext = mContext;
    }

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
    //listglobalindex
    String listglobalindexQuery(String opt,String xid,String gix,String xvn,String xv,
                                String xve, String sdate, String edate, String max, String res,
                                String title,String alternate, String order){
        String listgixQuery = "?action=listgix" + qf.formQuery(opt,xid,gix,xvn,xv,xve,sdate,edate,max,res,title,alternate,order);
        return targetCIQuery() + listgixQuery;
    }
    //listindex
    String listindexQuery(String opt, String xid, String DSID, String xname){
        String listgixQuery = "?action=listgix" + qf.formQuery(opt,xid,DSID,xname);
        return targetCIQuery() + listgixQuery;
    }
    //listnode
    String listNodeQuery(){
        String listNodeQuery = "?action=listnode";
        return targetCIQuery() + listNodeQuery;
    }
    //listversion
    String listversionQuery(String res, String xid, String maxseg, String offset, String sdate){
        String listversionQuery = "?action=listversion" + qf.formQuery();
        return targetCIQuery() + listversionQuery;
    }


}
