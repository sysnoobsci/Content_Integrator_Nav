package com.systemware.contentintegrator.app;

import android.app.Activity;

import android.app.ActionBar;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class MainActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    final static int LOGIN_THREAD_WAIT_TIME = 50;
    final static int ABOUT_THREAD_WAIT_TIME = 50;
    Dialog loginDialog = null;
    Context maContext = MainActivity.this;

    String loginQueryResult = "noresult";

    private Boolean first_open = true;//keeps track of if the app is opening for the first time to show the home screen
    ProgressDialog progress;

    public String getLoginQueryResult() {
        return loginQueryResult;
    }

    public void setLoginQueryResult(String loginQueryResult) {
        this.loginQueryResult = loginQueryResult;
    }

    public Boolean getFirst_open() {
        return first_open;
    }

    public void setFirst_open(Boolean first_open) {
        this.first_open = first_open;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //final Dialog loginDialog = new Dialog(this);
        loginDialog = new Dialog(this);
        loginDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        // Create loginDialog Dialog
        setContentView(R.layout.activity_main);
        loginDialog.setContentView(R.layout.login_dialog);
        //navigation drawer stuff
        mNavigationDrawerFragment=(NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle=getTitle();
        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout)findViewById(R.id.drawer_layout)
        );
        // Set GUI of loginDialog screen
        final EditText hostname = (EditText) loginDialog.findViewById(R.id.hostname);
        final EditText domain = (EditText) loginDialog.findViewById(R.id.domain);
        final EditText port = (EditText) loginDialog.findViewById(R.id.port);
        final EditText username = (EditText) loginDialog.findViewById(R.id.username);
        final EditText password = (EditText) loginDialog.findViewById(R.id.password);
        final Button cancel = (Button) loginDialog.findViewById(R.id.cancel_button);
        final Button loginButton = (Button) loginDialog.findViewById(R.id.login_button);

        // Make dialog box visible.
        loginDialog.show();
        //Closes app if they try to back out of dialog
        loginDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                finish();
            }
        });
        //Listener for loginDialog button
        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                Log.d("Message", "Login button clicked");
                final loginlogoff liloobj = new loginlogoff(maContext);//passed in context of this activity
                liloobj.setHostname(hostname.getText().toString());
                liloobj.setDomain(domain.getText().toString());
                liloobj.setPortnumber(Integer.parseInt(port.getText().toString()));
                liloobj.setUsername(username.getText().toString());
                liloobj.setPassword(password.getText().toString());
                try {
                    liloobj.login();
                    progress = ProgressDialog.show(maContext, "Logging in...", "Please Wait", true);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                new Thread(new Runnable() {

                    public void run() {
                        int count = 0;//keeps track of how many loops have been done, total time waiting is approx count times thread sleep time
                        try {
                            while(queryreqresp.getResult().equals("No result")&&count<10){//waiting for a result to appear from login()
                                Log.d("Message", "Waiting for result from attempted login... " + LOGIN_THREAD_WAIT_TIME*count + " ms");
                                Thread.sleep(LOGIN_THREAD_WAIT_TIME);
                                count++;
                            }
                            progress.dismiss();
                            if(count==10){//request timed out - no response
                                ToastMessageTask tstask = new ToastMessageTask(maContext,"Request timed out");
                                tstask.execute();
                            }
                            else {
                                liloobj.logonMessage();
                                Thread.sleep(2000);
                                if (liloobj.getLogin_successful()) {//if logon successful, do this
                                    setLoginQueryResult(liloobj.getLogonRes());
                                    loginDialog.dismiss();
                                }
                            }
                        }
                        catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

        //Listener for Cancel Button
        cancel.setOnClickListener(new View.OnClickListener(){
              @Override
              public void onClick (View v){
                  finish();
              }
        });

    }//end of oncreate

    public void aboutDialog() throws InterruptedException, ExecutionException, TimeoutException, IOException, XmlPullParserException {
        loginlogoff lobj = new loginlogoff(maContext);

        final String aboutInfoQuery = "http://" + lobj.getHostname() + "." +
                lobj.getDomain() + ":" + lobj.getPortnumber() + "/ci";
        final String aboutQuery = "?action=about";
        final String aboutQueryFeatures = "?action=about&opt=features";
        final Dialog aboutDialog = new Dialog(this);
        final List<String> listFeature = new ArrayList<String>();
        aboutDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Create loginDialog Dialog
        aboutDialog.setContentView(R.layout.about_dialog);

        final TextView ciservername = (TextView) aboutDialog.findViewById(R.id.ci_servername_placeholder);
        final TextView civersion = (TextView) aboutDialog.findViewById(R.id.ci_version_placeholder);
        final TextView cinodename = (TextView) aboutDialog.findViewById(R.id.ci_nodename_placeholder);

        final ListView featureList = (ListView)aboutDialog.findViewById(R.id.features_list);

        List<String> listOfTextTags;
        XmlParser xobj = new XmlParser();
        XmlParser xobj2 = new XmlParser();

        queryreqresp.ReqTask reqobj = new queryreqresp.ReqTask(aboutInfoQuery + aboutQuery,//query about CI information(not features)
                this.getClass().getName(), maContext);
        reqobj.execute();
        reqobj.get(500,TimeUnit.MILLISECONDS);
        xobj.parseXMLfunc(queryreqresp.getResult());
        listOfTextTags = xobj.getTextTag();
        ciservername.setText(listOfTextTags.get(3));
        civersion.setText(listOfTextTags.get(4));
        cinodename.setText(listOfTextTags.get(5));
        queryreqresp.ReqTask reqobj2 = new queryreqresp.ReqTask(aboutInfoQuery + aboutQueryFeatures,//start a new query for CI features
                this.getClass().getName(), maContext);
        reqobj2.execute();
        reqobj2.get(500,TimeUnit.MILLISECONDS);
        xobj2.parseXMLfunc(queryreqresp.getResult());
        List<String> listOfTextTags2;
        listOfTextTags2 = xobj2.getTextTag();
        Iterator<String> it = listOfTextTags2.iterator();
        while(it.hasNext())
        {
            if(!it.next().equals("0")){//avoid grabbing the return codes
                listFeature.add(it.next());
            }
        }
        /*final Thread cithread = new Thread(new Runnable() {
            public void run() {
                final queryreqresp.ReqTask reqobj = new queryreqresp.ReqTask(aboutInfoQuery + aboutQuery,//query about CI information(not features)
                        this.getClass().getName(), maContext);
                reqobj.execute();
                Log.d("Message", "ReqTask task running...");
                final XmlParser xobj = new XmlParser();
                int count = 0;//keeps track of how many loops have been done, total time waiting is approx count times thread sleep time
                    while(queryreqresp.getResult().equals("No result")&&count<10){//waiting for a result to appear from login()
                        Log.d("Message", "Waiting for result from about query... " + LOGIN_THREAD_WAIT_TIME * count + " ms");
                        try {
                            Thread.sleep(ABOUT_THREAD_WAIT_TIME);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                        count++;
                    }
                    if(count==20){
                        ToastMessageTask tmtask = new ToastMessageTask(maContext,"Error. About Request Timed Out\n"+
                        "Check connection to CI server");
                        tmtask.execute();
                    }
                    else{
                        try {
                            xobj.parseXMLfunc(queryreqresp.getResult());
                        } catch (XmlPullParserException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if(!xobj.isXMLformat(queryreqresp.getResult())){//if xml response is not able to be parsed, show a Toast Message saying so
                            ToastMessageTask tmtask = new ToastMessageTask(maContext, "Error. Server sent invalid info back\n" +
                                    "Check connection to CI server");
                            tmtask.execute();
                        }
                        else{
                            MainActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    List<String> listOfTextTags;
                                    listOfTextTags = xobj.getTextTag();
                                    ciservername.setText(listOfTextTags.get(3));
                                    civersion.setText(listOfTextTags.get(4));
                                    cinodename.setText(listOfTextTags.get(5));
                                }
                            });
                        }
                        Log.d("Message","About request results with xml parser applied: " + xobj.getXmlstring());
                        xobj.clearXMLString();
                    }
                }
        });

        final Thread cifeaturesthread = new Thread(new Runnable() {
            public void run() {
                queryreqresp.ReqTask reqobj2 = new queryreqresp.ReqTask(aboutInfoQuery + aboutQueryFeatures,//start a new query for CI features
                        this.getClass().getName(), maContext);
                reqobj2.execute();
                Log.d("Message", "ReqTask2 task running...");
                final XmlParser xobj = new XmlParser();
                int count = 0;//keeps track of how many loops have been done, total time waiting is approx count times thread sleep time
                while(queryreqresp.getResult().equals("No result")&&count<10){//waiting for a result to appear from login()
                    Log.d("Message", "Waiting for result from about query... " + LOGIN_THREAD_WAIT_TIME * count + " ms");
                    try {
                        Thread.sleep(ABOUT_THREAD_WAIT_TIME);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    count++;
                }
                if(count==20){
                    ToastMessageTask tmtask = new ToastMessageTask(maContext,"Error. About Request Timed Out\n"+
                            "Check connection to CI server");
                    tmtask.execute();
                }
                else{
                    try {
                        xobj.parseXMLfunc(queryreqresp.getResult());
                    } catch (XmlPullParserException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(!xobj.isXMLformat(queryreqresp.getResult())){//if xml response is not able to be parsed, show a Toast Message saying so
                        ToastMessageTask tmtask = new ToastMessageTask(maContext, "Error. Server sent invalid info back\n" +
                                "Check connection to CI server");
                        tmtask.execute();
                    }
                    else{
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                List<String> listOfTextTags;
                                listOfTextTags = xobj.getTextTag();
                                Iterator<String> it = listOfTextTags.iterator();
                                while(it.hasNext())
                                {
                                    if(!it.next().equals("0")){//avoid grabbing the return codes
                                        listFeature.add(it.next());
                                    }
                                }
                            }
                        });

                    }
                    Log.d("Message","About features request results with xml parser applied: " + xobj.getXmlstring());
                    xobj.clearXMLString();
                }
            }
        });

        List<Thread> threadslist = new ArrayList<Thread>();

        threadslist.add(cithread);
        threadslist.add(cifeaturesthread);
        ThreadSerializerTask tstask = new ThreadSerializerTask(threadslist);
        tstask.execute();
        */

        ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.list_item, listFeature);
        featureList.setAdapter(adapter);
        aboutDialog.show();
    }//end of about dialog

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        Log.d("Variable", "Value of argument position: " + position);
        Fragment fragment = new Home_Fragment();
        if(first_open){//if first time opening app, show home screen fragment
            position = -1;
            setFirst_open(false);
        }
        FragmentManager fragmentManager = getFragmentManager();
        switch(position) {
            case 0:
                fragment = new Find_Fragment();
                break;
            case 1:
                fragment = new Tools_Fragment();
                break;
            case 2:
                fragment = new Admin_Fragment();
                break;
            default:
                fragment = new Home_Fragment();
                break;
        }
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                //.replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section2);
                break;
            case 2:
                mTitle = getString(R.string.title_section3);
                break;
            case 3:
                mTitle = getString(R.string.title_section4);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        if(id == R.id.action_about){
            try {
                aboutDialog();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}//end of MainActivity


