package com.systemware.contentintegrator.app;

import android.content.Context;

/**
 * Created by adrian.meraz on 6/10/2014.
 * XML API services for content servers
 */
public class ContentServer {
    Context mContext;
    //default variable values
    private String _DSID = "";//DSID of a report
    private String _xid = "1";// Content Server id
    private String _res = "";// report path
    private String _version = "0";// report version
    private String _maxseg = "1";// maxseg
    private String _offset = "";// offset
    private String _opt = "";// gix option
    private String _gix = "";// gix group name
    private String _xvn = "";// gix variable name
    private String _xv = "";// gix variable value
    private String _xve = "";//gix variable value, ending value
    private String _sdate = "00000000";//optional document-version start date
    private String _edate = "00000000";//optional document-version end date
    private String _max = "1";//optional document-version end date
    private String _title = "";//optional restart point when “opt” is “version”
    // and “order” is “T” (document title)
    private String _alternate = "";//optional restart point when “opt” is “version”
    // and “order” is “A” (document alternate name)
    private String _order = "";//Optional sort order when “opt” is “version”
    //Values: T=document title, A=document alternate name,
    //        D=document date/time.
    //Default is in database sequence, which is not defined.



    public ContentServer(Context mContext){
        ContentServer.this.mContext = mContext;
    }

//all variables that can go into queries

    public ContentServer DSID(String _DSID){
        this._DSID = qVar(_DSID) + _DSID;
        return this;
    }
    public ContentServer xid(String _xid){
        this._xid = qVar(_xid) + _xid;
        return this;
    }
    public ContentServer res(String _res){
        this._res = qVar(_res) + _res;
        return this;
    }
    public ContentServer version(String _version){
        this._version = qVar(_version) + _version;
        return this;
    }
    public ContentServer maxseg(String _maxseg){
        this._maxseg = qVar(_maxseg) + _maxseg;
        return this;
    }
    public ContentServer offset(String _offset){
        this._offset = qVar(_offset) + _offset;
        return this;
    }
    public ContentServer opt(String _opt){
        this._opt = qVar(_opt) + _opt;
        return this;
    }
    public ContentServer gix(String _gix){
        this._gix = qVar(_gix) + _gix;
        return this;
    }
    public ContentServer xvn(String _xvn){
        this._xvn = qVar(_xvn) + _xvn;
        return this;
    }
    public ContentServer xv(String _xv){
        this._xv = qVar(_xv) + _xv;
        return this;
    }
    public ContentServer xve(String _xve){
        this._xve = qVar(_xve) + _xve;
        return this;
    }
    public ContentServer sdate(String _sdate){
        this._sdate = qVar(_sdate) + _sdate;
        return this;
    }
    public ContentServer edate(String _edate){
        this._edate = qVar(_edate) + _edate;
        return this;
    }
    public ContentServer max(String _max){
        this._max = qVar(_max) + _max;
        return this;
    }
    public ContentServer title(String _title){
        this._title = qVar(_title) + _title;
        return this;
    }
    public ContentServer alternate(String _alternate){
        this._alternate = qVar(_alternate) + _alternate;
        return this;
    }
    public ContentServer order(String _order){
        this._order = qVar(_order) + _order;
        return this;
    }

    //Content Server API queries
    String qVar(String var){//takes a query variable and forms it properly
        return "&" + var + "=";
    }
    String targetCIQuery(){
        loginlogoff lilobj = new loginlogoff(mContext);
        String targetCIQuery = "http://" + lilobj.getHostname() + "." +
                lilobj.getDomain() + ":" + lilobj.getPortnumber() + "/ci";
        return targetCIQuery;
    }
    //deleteversion
    String deleteDSIDQuery(String DSID,String xid, String res){
        ContentServer query1 = new ContentServer(mContext).DSID(DSID).xid(xid).res(res);
        String delverQuery = "?action=deleteversion" + query1._res + query1._xid + query1._DSID;
        return targetCIQuery() + delverQuery;
    }
    String deleteVersionQuery(String version,String xid, String res){
        ContentServer query1 = new ContentServer(mContext).xid(xid).res(res).version(version);
        String delverQuery = "?action=deleteversion" + query1._res + query1._xid + query1._version;
        return targetCIQuery() + delverQuery;
    }
    //listdirectory
    String listdirQuery(String res, String xid, String maxseg, String offset){
        ContentServer query1 = new ContentServer(mContext).xid(xid).res(res).maxseg(maxseg).offset(offset);
        String listDirQuery = "?action=listdirectory" + query1._res + query1._xid + query1._maxseg +
                query1._offset;
        return targetCIQuery() + listDirQuery;
    }
    String listglobalindexQuery(String opt,String xid,String gix,String xvn,String xv,
                                String xve, String sdate, String edate, String max, String res,
                                String title,String alternate, String order){
        ContentServer query1 = new ContentServer(mContext).opt(opt).xid(xid).gix(gix).xvn(xvn).xv(xv)
                .xve(xve).sdate(sdate).edate(edate).max(max).res(res).title(title).alternate(alternate).order(order);
        String listgixQuery = "?action=listgix" + query1._opt + query

    }
    //listnode
    String listNodeQuery(){
        String listNodeQuery = "?action=listnode";
        return targetCIQuery() + listNodeQuery;
    }


}
