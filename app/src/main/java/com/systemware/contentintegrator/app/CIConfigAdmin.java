package com.systemware.contentintegrator.app;

import android.content.Context;

/**
 * Created by adrian.meraz on 6/13/2014.
 */
public class CIConfigAdmin {
    Context mContext;
    QueryFormer qf = new QueryFormer();

    public CIConfigAdmin(Context mContext){
        this.mContext = mContext;
    }

    String targetCIQuery(){
        loginlogoff lilobj = new loginlogoff(mContext);
        String targetCIQuery = "http://" + lilobj.getHostname() + "." +
                lilobj.getDomain() + ":" + lilobj.getPortnumber() + "/ci";
        return targetCIQuery;
    }
    //applyappconfig
    String applyappconfigQuery(String appfile){
        String applyappconfigQuery = "?action=applyappconfig" + qf.formQuery(appfile);
        return targetCIQuery() + applyappconfigQuery;
    }
    //applyrule
    String applyruleQuery(String ruleset,String encode){
        String applyruleQuery = "?action=applyrule" + qf.formQuery(ruleset,encode);
        return targetCIQuery() + applyruleQuery;
    }
    //audit
    String auditQuery(String opt,String fmt,String max,String sdate){
        String auditQuery = "?action=audit" + qf.formQuery(opt,fmt,max,sdate);
        return targetCIQuery() + auditQuery;
    }
    //deleteappconfig
    String deleteappconfigQuery(String appfile){
        String deleteappconfigQuery = "?action=deleteappconfig" + qf.formQuery(appfile);
        return targetCIQuery() + deleteappconfigQuery;
    }
    //deleterule
    String deleteruleQuery(String ruleset){
        String deleteruleQuery = "?action=deleterule" + qf.formQuery(ruleset);
        return targetCIQuery() + deleteruleQuery;
    }
    //listappconfig
    String listappconfigQuery(){
        String listappconfigQuery = "?action=listappconfig";
        return targetCIQuery() + listappconfigQuery;
    }
    //listrule
    String listruleQuery(){
        String listruleQuery = "?action=listrule";
        return targetCIQuery() + listruleQuery;
    }
    //listtopicschema
    String listtopicschemaQuery(String filter){
        String listtopicschemaQuery = "?action=listtopicschema" + qf.formQuery(filter);
        return  targetCIQuery() + listtopicschemaQuery;
    }
    //readappconfig
    String readappconfigQuery(String appfile){
        String readappconfigQuery = "?action=readappconfig" + qf.formQuery(appfile);
        return targetCIQuery() + readappconfigQuery;
    }
    //readappdata
    String readappdataQuery(String datafile,String app){
        String readappdataQuery = "?action=readappdata" + qf.formQuery(datafile,app);
        return targetCIQuery() + readappdataQuery;
    }
    //readrule
    String readruleQuery(String ruleset,String encode){
        String readruleQuery = "?action=readrule" + qf.formQuery(ruleset,encode);
        return targetCIQuery() + readruleQuery;
    }
    //saveappconfig
    String saveappconfigQuery(String appfile,String appxml){
        String saveappconfigQuery = "?action=saveappconfig" + qf.formQuery(appfile,appxml);
        return targetCIQuery() + saveappconfigQuery;
    }
    //saverule
    String saveruleQuery(String ruleset,String rulexml,String encode){
        String saveruleQuery = "?action=saverule" + qf.formQuery(ruleset,rulexml,encode);
        return targetCIQuery() + saveruleQuery;
    }













}
