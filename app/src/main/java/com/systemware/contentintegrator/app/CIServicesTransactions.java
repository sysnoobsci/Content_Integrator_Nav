package com.systemware.contentintegrator.app;

import android.content.Context;

/**
 * Created by adrian.meraz on 6/13/2014.
 * XML API core services
 */
public class CIServicesTransactions {
    Context mContext;
    QueryFormer qf = new QueryFormer();

    public CIServicesTransactions(Context mContext){
        this.mContext = mContext;
    }

    String targetCIQuery(){
        loginlogoff lilobj = new loginlogoff(mContext);
        String targetCIQuery = "http://" + lilobj.getHostname() + "." +
                lilobj.getDomain() + ":" + lilobj.getPortnumber() + "/ci";
        return targetCIQuery;
    }
    //about
    String aboutQuery(String opt){
        String aboutQuery = "?action=about" + qf.formQuery(opt) ;
        return targetCIQuery() + aboutQuery;
    }
    //addtopiclink
    String addtopiclinkQuery(String rid,String ptid,String ctid){
        String addtopiclinkQuery = "?action=addtopiclink" + qf.formQuery(rid,ptid,ctid);
        return targetCIQuery() + addtopiclinkQuery;
    }
    //checkintopic
    String checkintopicQuery(String tid,String createtplid,String updatetplid){
        String checkintopicQuery = "?action=checkintopic" + qf.formQuery(tid, createtplid, updatetplid);
        return targetCIQuery() + checkintopicQuery;
    }
    //checkouttopic
    String checkouttopicQuery(String tid){
        String checkouttopicQuery = "?action=checkouttopic" + qf.formQuery(tid);
        return targetCIQuery() + checkouttopicQuery;
    }
    //cixrule
    String cixruleQuery(String tplid){
        String cixruleQuery = "?action=cixrule" + qf.formQuery(tplid);
        return targetCIQuery() + cixruleQuery;
    }
    //combine
    String combineQuery(String tid,String opt,String inline){
        String combineQuery = "?action=combine" + qf.formQuery(tid, opt, inline);
        return targetCIQuery() + combineQuery;
    }
    //createtopic     ***LOOK AT THIS ONE AGAIN***
    String createtopicQuery(String tplid,String nvpairs,String detail){
        String createtopicQuery = "?action=createtopic" + qf.formQuery(tplid,nvpairs,detail);
        return targetCIQuery() + createtopicQuery;
    }
    //createwq
    String createwqQuery(String tid,String desc,String force){
        String createwqQuery = "?action=action=createwq" + qf.formQuery(tid,desc,force);
        return targetCIQuery() + createwqQuery;
    }
    //deletetopic
    String deletetopicQuery(String tid,String ruleset){
        String deletetopicQuery = "?action=deletetopic" + qf.formQuery(tid,ruleset);
        return targetCIQuery() + deletetopicQuery;
    }
    //deletewq
    String deletewqQuery(String otid,String xid,String oid){
        String deletewqQuery = "?action=deletewq" + qf.formQuery(otid,xid,oid);
        return targetCIQuery() + deletewqQuery;
    }
    //deliver     ***LOOK AT THIS ONE AGAIN***
    String deliverQuery(String tplid,String rtvtplid,String dlvtplid){
        String deliverQuery = "?action=deliver" + qf.formQuery(tplid,rtvtplid,dlvtplid);
        return targetCIQuery() + deliverQuery;
    }
    //download      ***LOOK AT THIS ONE AGAIN***
    String downloadQuery(String tid,String DSID,String xid,String wqid,String fmt,String maxseg,
            String offset,String opt,String axvs,String afpresources,String openfmtparm,
            String optimize){
        String downloadQuery = "?action=download" + qf.formQuery(tid,DSID,xid,wqid,fmt,maxseg,
            offset,opt,axvs,afpresources,openfmtparm,optimize);
        return targetCIQuery() + downloadQuery;
    }
    //export
    String exportQuery(String tplid){
        String exportQuery = "?action=export" + qf.formQuery(tplid);
        return targetCIQuery() + exportQuery;
    }
    //generateflattopicDDL
    String generateflattopicDDLQuery(String dbtype,String camid,String otid,String allotids,
                                String topicdefxml,String schema,String dbname,String inline){
        String generateflattopicDDLQuery = "?action=generateflattopicDDL" + qf.formQuery(dbtype,camid,otid,
                allotids,topicdefxml,schema,dbname,inline);
        return targetCIQuery() + generateflattopicDDLQuery;
    }
    //import    ***LOOK AT THIS AGAIN***
    String importQuery(String tplid,String serverfilename,String csvskipheader,String skipheader){
        String importQuery = "?action=import" + qf.formQuery(tplid,serverfilename,csvskipheader,skipheader);
        return targetCIQuery() + importQuery;
    }
    //listtemplate
    String listtemplateQuery(String opt, String tid){
        String listtemplateQuery = "?action=listtemplate" + qf.formQuery(opt,tid);
        return targetCIQuery() + listtemplateQuery;
    }
    //listwq
    String listwqQuery(String otid,String xid){
        String listwqQuery = "?action=listwq" + qf.formQuery(otid,xid);
        return targetCIQuery() + listwqQuery;
    }
    //logoff
    String logoffQuery(){
        String logoffQuery = "?action=logoff";
        return logoffQuery;
    }
    //logon
    String logonQuery(String user,String password,String newpwd){
        String logonQuery = "?action=logon" + qf.formQuery(user,password,newpwd);
        return targetCIQuery() + logonQuery;
    }
    //ping
    String pingQuery(){
        String pingQuery = "?action=ping";
        return targetCIQuery() + pingQuery;
    }
    //readtopic
    String readtopicQuery(String tid,String opt) {
        String readtopicQuery = "?action=readtopic" + qf.formQuery(tid, opt);
        return targetCIQuery() + readtopicQuery;
    }
    //readuserattribute       ***LOOK AT THIS AGAIN***
    String readuserattributeQuery(String name, String list, String attr){
        String readuserattributeQuery = "?action=readuserattribute" + qf.formQuery(name,list,attr);
        return targetCIQuery() + readuserattributeQuery;
    }
    //restore
    String restoreQuery(String DSID,String tid, String xid, String opt){
        String restoreQuery = "?action=restore" + qf.formQuery(DSID, tid, xid, opt);
        return targetCIQuery() + restoreQuery;
    }
    //retrieve  ***LOOK AT THIS AGAIN***
    String retrieveQuery(String mode, String tid, String DSID, String xid, String tplid,String fmt,
                         String combtype,String maxseg,String offset,String axvs,String label,
                         String inline,String tq,String sln){
        String retrieveQuery = "?action=retrieve" + qf.formQuery();
        return targetCIQuery() + retrieveQuery;
    }
    //runsavedsearch
    String runsavedsearchQuery(String query,String gridout,String maxseg,String offset){
        String runsavedsearchQuery = "?action=runsavedsearch" + qf.formQuery(query,gridout,maxseg,offset);
        return targetCIQuery() + runsavedsearchQuery;
    }
    //savesearch
    String savesearchQuery(String label, String tplid, String nvpair){
        String savesearchQuery = "?action=ssavesearch" + qf.formQuery(label,tplid,nvpair);
        return targetCIQuery() + savesearchQuery;
    }
    //saveuserattribute
    String saveuserattributeQuery(String name, String value, String encrypt,String list,
                                  String attrsuffix,String valuesuffix,String encryptsuffix){
        String saveuserattributeQuery = "?action=saveuserattribute" + qf.formQuery(name,value,encrypt,
                list,attrsuffix,valuesuffix,encryptsuffix);
        return targetCIQuery() + saveuserattributeQuery;
    }
    //search
    String searchQuery(String tplid,String nvpairs,String any,String gridout,String maxseg,
                       String wq, String offset, String orderby,String savehistory,String xreport,
                       String dlvtplid,String tid){
        String searchQuery = "?action=search" + qf.formQuery(tplid,nvpairs,any,gridout,maxseg,wq,
                offset,orderby,savehistory,xreport,dlvtplid,tid);
        return targetCIQuery() + searchQuery;
    }
    //updatetopic
    String updatetopicQuery(String tplid,String tid,String nvpairs, String detail){
        String updatetopicQuery = "?action=updatetopic" + qf.formQuery(tplid,tid,nvpairs,detail);
        return targetCIQuery() + updatetopicQuery;
    }

}
