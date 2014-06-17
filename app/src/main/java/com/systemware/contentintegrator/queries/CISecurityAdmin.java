package com.systemware.contentintegrator.queries;

import android.content.Context;

import com.systemware.contentintegrator.app.QueryFormer;
import com.systemware.contentintegrator.app.loginlogoff;

/**
 * Created by adrian.meraz on 6/16/2014.
 * XML API services for security administration
 */
public class CISecurityAdmin {
    Context mContext;
    QueryFormer qf = new QueryFormer();

    public CISecurityAdmin(Context mContext){
        this.mContext = mContext;
    }

    String targetCIQuery(){
        loginlogoff lilobj = new loginlogoff(mContext);
        String targetCIQuery = "http://" + lilobj.getHostname() + "." +
                lilobj.getDomain() + ":" + lilobj.getPortnumber() + "/ci";
        return targetCIQuery;
    }

    //cancelsession
    String cancelsessionQuery(String cancelsid){
        String cancelsessionQuery = "?action=cancelsession" + qf.formQuery(cancelsid);
        return targetCIQuery() + cancelsessionQuery;
    }
    //deleteconnectprofile
    String deleteconnectprofileQuery(String id){
        String deleteconnectprofileQuery = "?action=deleteconnectprofile" + qf.formQuery(id);
        return targetCIQuery() + deleteconnectprofileQuery;
    }
    //deletecsfilterrule
    String deletecsfilterruleQuery(String gid,String uid,String id){
        String deletecsfilterruleQuery = "?action=deletecsfilterrule" + qf.formQuery(gid, uid, id);
        return targetCIQuery() + deletecsfilterruleQuery;
    }
    //deletecsresrule
    String deletecsresruleQuery(String gid,String uid,String id){
        String deletecsresruleQuery = "?action=deletecsresrule" + qf.formQuery(gid,uid,id);
        return targetCIQuery() + deletecsresruleQuery;
    }
    //deletefilter
    String deletefilterQuery(String id) {
        String deletefilterQuery = "?action=deletefilter" + qf.formQuery(id);
        return targetCIQuery() + deletefilterQuery;
    }
    //deletegroup
    String deletegroupQuery(String id){
        String deletegroupQuery = "?action=deletegroup" + qf.formQuery(id);
        return targetCIQuery() + deletegroupQuery;
    }
    //deletenode
    String deletenodeQuery(String id){
        String deletenodeQuery = "?action=deletenode" + qf.formQuery(id);
        return targetCIQuery() + deletenodeQuery;
    }
    //deletepermission
    String deletepermissionQuery(String id){
        String deletepermissionQuery = "?action=deletepermission" + qf.formQuery(id);
        return targetCIQuery() + deletepermissionQuery;
    }
    //deleterole
    String deleteroleQuery(String id){
        String deleteroleQuery = "?action=deleterole" + qf.formQuery(id);
        return targetCIQuery() + deleteroleQuery;
    }
    //deleteuser
    String deleteuserQuery(String id){
        String deleteuserQuery = "?action=deleteuser" + qf.formQuery(id);
        return targetCIQuery() + deleteuserQuery;
    }
    //exportsecurityentity
    String exportsecurityentityQuery(String withdependencies,String ckey,String inline,String types,
                                     String nmasktype,String minidtype,String maxidtype,String minappidtype,
                                     String maxappidtype){
        String exportsecurityentityQuery = "?action=exportsecurityentity" + qf.formQuery(withdependencies,
                ckey,inline,types,nmasktype,minidtype,maxidtype,minappidtype,maxappidtype);
        return targetCIQuery() + exportsecurityentityQuery;
    }
    //importcertificate
    String importcertificateQuery(String opt,String sslcontext,String host,String port){
        String importcertificate = "?action=importcertificate" + qf.formQuery(opt,sslcontext,host,port);
        return targetCIQuery() + importcertificate;
    }
    //importsecurityentity
    String importsecurityentityQuery(String file,String ckey,String allowupdate){
        String importsecurityentityQuery = "?action=importsecurityentity" + qf.formQuery(file,ckey,allowupdate);
        return targetCIQuery() + importsecurityentityQuery;
    }
    //listavailablegroups
    String listavailablegroupsQuery(String nmask,String maxseg,String offset,String minid,String maxid){
        String listavailablegroupsQuery = "?action=listavailablegroups" + qf.formQuery(nmask,maxseg,offset,
                minid,maxid);
        return targetCIQuery() + listavailablegroupsQuery;
    }
    //listfilter
    String listfilterQuery(String maxseg,String offset,String minid,String maxid){
        String listfilterQuery = "?action=listfilter" + qf.formQuery(maxseg,offset,minid,maxid);
        return targetCIQuery() + listfilterQuery;
    }
    //listgroup
    String listgroupQuery(String nmask,String maxseg,String offset,String minid,String maxid,String detail){
        String listgroupQuery = "?action=listgroup" + qf.formQuery(nmask,maxseg,offset,minid,maxid,detail);
        return targetCIQuery() + listgroupQuery;
    }
    //listnode
    String listnodeQuery(String nmask,String maxseg,String offset){
        String listnodeQuery = "?action=listnode" + qf.formQuery(nmask,maxseg,offset);
        return targetCIQuery() + listnodeQuery;
    }
    //listpermission
    String listpermissionQuery(String nmask,String maxseg,String offset,String minid,String maxid){
        String listpermissionQuery = "?action=listpermission" + qf.formQuery(nmask,maxseg,offset,minid,maxid);
        return targetCIQuery() + listpermissionQuery;
    }
    //listrole
    String listroleQuery(String nmask,String maxseg,String offset, String minid,String maxid,String detail){
        String listroleQuery = "?action=listrole" + qf.formQuery(nmask,maxseg,offset,minid,maxid,detail);
        return targetCIQuery() + listroleQuery;
    }
    //listsession
    String listsessionQuery(String offset,String max,String ascend,String uid){
        String listsessionQuery = "?action=listsession" + qf.formQuery(offset,max,ascend,uid);
        return targetCIQuery() + listsessionQuery;
    }
    //listuser
    String listuserQuery(String nmask,String maxseg, String offset,String minid,String maxid,String gid,
                         String rid,String detail){
        String listuserQuery = "?action=listuser" + qf.formQuery(nmask,maxseg,offset,minid,maxid,gid,rid,detail);
        return targetCIQuery() + listuserQuery;
    }
    //saveconnectprofile
    String saveconnectprofileQuery(String giduid,String xid,String account,String secid,String user,
                                   String pwd,String encode){
        String saveconnectprofileQuery = "?action=saveconnectprofile" + qf.formQuery(giduid,xid,account,
                secid,user,pwd,encode);
        return targetCIQuery() + saveconnectprofileQuery;
    }
    //savecsfilterrule
    String savecsfilterruleQuery(String gid,String uid,String id, String seq,String xids,String desc,
                                 String resmask,String resmasknull,String xvn,String xvc,String xv,
                                 String xvnull,String xvto,String ixacs){
        String savecsfilterruleQuery = "?action=savecsfilterrule" + qf.formQuery(gid,uid,id,seq,xids,desc,
                resmask,resmasknull,xvn,xvc,xv,xvnull,xvto,ixacs);
        return targetCIQuery() + savecsfilterruleQuery;
    }
    String savecsresruleQuery(String gid,String uid,String id,String seq,String xids,String desc,
                              String resmask, String rtype,String acs){
        String savecsresruleQuery = "?action=savecsresrule" + qf.formQuery(gid,uid,id,seq,xids,
                desc,resmask,rtype,acs);
        return targetCIQuery() + savecsresruleQuery;
    }
    String savefilterQuery(String id,String name,String desc,String cmxfilter,String cxsfilter,
                           String filter,String otid,String mask,String opt){
        String savefilterQuery = "?action=savefilter" + qf.formQuery(id,name,desc,cmxfilter,cxsfilter,
                filter,otid,mask,opt);
        return targetCIQuery() + savefilterQuery;
    }
    //savegroup
    String savegroupQuery(String id,String name,String desc,String appid,String filters,String rids,
                     String ownerid,String opt,String sessionlimit){
        String savegroupQuery = "?action=savegroup" + qf.formQuery(id,name,desc,appid,filters,rids,
                ownerid,opt,sessionlimit);
        return targetCIQuery() + savegroupQuery;
    }
    //savenode
    String savenodeQuery(String id, String name,String desc,String account,String secid,
                         String csuser,String pwd,String encode,String host,String port){
        String savenodeQuery = "?action=savenode" + qf.formQuery(id,name,desc,account,secid,csuser,
                pwd,encode,host,port);
        return targetCIQuery() + savenodeQuery;
    }
    //savepermission
    String savepermissionQuery(String id,String name,String desc,String appid,String ownerid,String opt){
        String savepermissionQuery = "?action=savepermission" + qf.formQuery(id,name,desc,appid,ownerid,opt);
        return targetCIQuery() + savepermissionQuery;
    }
    //saverole
    String saveroleQuery(String id,String name,String desc,String appid,String pids,String permissions,
                         String rids,String ownerid,String opt){
        String saveroleQuery = "?action=saverole" + qf.formQuery(id,name,desc,appid,pids,permissions,
                rids,ownerid,opt);
        return targetCIQuery() + saveroleQuery;
    }
    //saveuser
    String saveuserQuery(String id,String name,String desc,String pwd,String encode,String email,
                         String status,String gids,String rids,String permissions,String grpadmlist,
                         String ownerid,String locale,String opt,String sessionlimit,String csuser,
                         String cssecid,String cslocdata,String cssecinfo,String csaccount){
        String saveuserQuery = "?action=saveuser" + qf.formQuery(id,name,desc,pwd,encode,email,
                status,gids,rids,permissions,grpadmlist,ownerid,locale,opt,sessionlimit,csuser,
                cssecid,cslocdata,cssecinfo,csaccount);
        return targetCIQuery() + saveuserQuery;
    }
    //synccsuserprofile
    String synccsuserprofileQuery(String syncaction,String targetxids,String excludexids,
                                  String ignoreerrors,String usecssessioncache,String nmask,
                                  String minid,String maxid,String amask){
        String synccsuserprofileQuery = "?action=synccsuserprofile" + qf.formQuery(syncaction,targetxids,
                excludexids,ignoreerrors,usecssessioncache,nmask,minid,maxid,amask);
        return targetCIQuery() + synccsuserprofileQuery;
    }
    //syncexternaluser
    String syncexternaluserQuery(String username){
        String syncexternaluserQuery = "?action=syncexternaluser" + qf.formQuery(username);
        return targetCIQuery() + syncexternaluserQuery;
    }













}
