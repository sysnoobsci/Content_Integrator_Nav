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

import java.util.concurrent.ExecutionException;
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

    String loginQueryResult = "noresult";
    Boolean logonResult = false;
    static Context aContext;
    private Boolean first_open = true;//keeps track of if the app is opening for the first time to show the home screen
    ProgressDialog progress;

    public String getLoginQueryResult() {
        return loginQueryResult;
    }

    public void setLoginQueryResult(String loginQueryResult) {
        this.loginQueryResult = loginQueryResult;
    }

    public Boolean getLogonResult() {
        return logonResult;
    }

    public void setLogonResult(Boolean logonResult) {
        this.logonResult = logonResult;
    }

    public static Context getaContext() {
        return aContext;
    }

    public static void setaContext(Context aContext) {
        MainActivity.aContext = aContext;
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
        setaContext(MainActivity.this);
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
                final loginlogoff liloobj = new loginlogoff(MainActivity.this);//passed in context of this activity
                liloobj.setHostname(hostname.getText().toString());
                liloobj.setDomain(domain.getText().toString());
                liloobj.setPortnumber(Integer.parseInt(port.getText().toString()));
                liloobj.setUsername(username.getText().toString());
                liloobj.setPassword(password.getText().toString());
                try {
                    login();
                    progress = ProgressDialog.show(aContext, "Logging in...", "Please Wait", true);
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
                            if(count==10){
                                ToastMessageTask tmtask = new ToastMessageTask(aContext,logonMessage() + " - Request Timed Out");
                                tmtask.execute();
                            }
                            else {
                                ToastMessageTask tmtask = new ToastMessageTask(aContext, logonMessage());
                                tmtask.execute();
                                Thread.sleep(2000);
                                if (getLogonResult()) {//if logon successful, do this
                                    setLoginQueryResult(liloobj.getLogonRes());
                                    loginDialog.dismiss();
                                    queryreqresp.eraseQueryResults();//set it back to "No result" to clean it
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

        mNavigationDrawerFragment=(NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);

        mTitle=getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout)findViewById(R.id.drawer_layout)
        );
    }//end of oncreate


    String logonMessage(){
        String toastMessage;
        loginlogoff liloobj = new loginlogoff(MainActivity.this);
        liloobj.isLoginSuccessful();
        setLogonResult(loginlogoff.getLogin_successful());
        if (!loginlogoff.getLogin_successful()) {
            toastMessage = "Logon Failed";
        }
        else {
            toastMessage = "Logon Successful";
        }
        return toastMessage;
    }

    public void login() throws InterruptedException, ExecutionException, TimeoutException, NoSuchMethodException {
        Log.d("Variable", "getApplicationContext() value:" + getApplicationContext());
        Log.d("Variable", "aContext value:" + aContext);
        loginlogoff liloobj = new loginlogoff(MainActivity.this);

        queryreqresp.ReqTask reqobj = new queryreqresp.ReqTask(liloobj.httpstringcreate(),
                this.getClass().getName(), getaContext());
        if (reqobj.getStatus().equals(AsyncTask.Status.PENDING)) {//if task has not executed yet, execute
            Log.d("Message", "loginDialog() task status:" + reqobj.getStatus());
            reqobj.execute();
            Log.d("Message", "loginDialog() task running...");
        }

    }//end of login()

    public void aboutDialog(){
        loginlogoff lobj = new loginlogoff(getApplicationContext());
        String aboutInfoQuery = "http://" + lobj.getHostname() + "." +
                lobj.getDomain() + ":" + lobj.getPortnumber() + "/ci";
        final String aboutQuery = "?action=about";
        final String aboutQueryFeatures = "?action=about&opt=features";
        final Dialog aboutDialog = new Dialog(this);
        String[] listFeature = {"I can do stuff", "Me too", "As can I", "can't stop", "won't stop"};
        aboutDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Create loginDialog Dialog
        aboutDialog.setContentView(R.layout.about_dialog);
        TextView ciservername = (TextView) aboutDialog.findViewById(R.id.ci_servername_placeholder);
        TextView civersion = (TextView) aboutDialog.findViewById(R.id.ci_version_placeholder);
        TextView cinodename = (TextView) aboutDialog.findViewById(R.id.ci_nodename_placeholder);
        ListView featureList = (ListView)aboutDialog.findViewById(R.id.features_list);

        queryreqresp.ReqTask reqobj = new queryreqresp.ReqTask(aboutInfoQuery + aboutQuery,
                this.getClass().getName(), getaContext());
        if (reqobj.getStatus().equals(AsyncTask.Status.PENDING)) {//if task has not executed yet, execute
            Log.d("Message", "ReqTask task status:" + reqobj.getStatus());
            reqobj.execute();
            Log.d("Message", "ReqTask task running...");
        }
        new Thread(new Runnable() {

            public void run() {
                int count = 0;//keeps track of how many loops have been done, total time waiting is approx count times thread sleep time

                    while(queryreqresp.getResult().equals("No result")&&count<10) {//waiting for a result to appear from login()
                        Log.d("Message", "Waiting for result from about query... " + LOGIN_THREAD_WAIT_TIME * count + " ms");
                        try {
                            Thread.sleep(ABOUT_THREAD_WAIT_TIME);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                        count++;
                    }
                    if(count==10){
                        ToastMessageTask tmtask = new ToastMessageTask(aContext,logonMessage() + "Error. About Request Timed Out\n"+
                        "Check connection to CI server");
                        tmtask.execute();
                    }
                    else{
                        /*if(xobj.isXMLformat(queryreqresp.getResult())){
                            ToastMessageTask tmtask = new ToastMessageTask(aContext,logonMessage() + "Error. Server sent invalid info back\n"+
                                    "Check connection to CI server");
                            tmtask.execute();
                        }*/
                        Log.d("Message","About request results: " + queryreqresp.getResult());
                    }
                }


        }).start();

        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.list_item, listFeature);

        featureList.setAdapter(adapter);
        aboutDialog.show();
    }



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
            aboutDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

/**
 * A placeholder fragment containing a simple view.
 */
public static class PlaceholderFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragment newInstance(int sectionNumber) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public PlaceholderFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }
}

}
