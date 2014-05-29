package com.systemware.contentintegrator.app;

import android.app.Activity;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
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
import android.widget.Toast;

import java.lang.reflect.Method;
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
    Boolean logonResult = false;

    static Context aContext;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Dialog login = new Dialog(this);
        login.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        // Create login Dialog
        setContentView(R.layout.activity_main);
        login.setContentView(R.layout.login_dialog);
        setaContext(MainActivity.this);
        // Set GUI of login screen
        final EditText hostname = (EditText) login.findViewById(R.id.hostname);
        final EditText domain = (EditText) login.findViewById(R.id.domain);
        final EditText port = (EditText) login.findViewById(R.id.port);
        final EditText username = (EditText) login.findViewById(R.id.username);
        final EditText password = (EditText) login.findViewById(R.id.password);
        final Button cancel = (Button) login.findViewById(R.id.cancel_button);
        final Button loginButton = (Button) login.findViewById(R.id.login_button);

        // Make dialog box visible.
        login.show();
        //Closes app if they try to back out of dialog
        login.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                finish();
            }
        });
        //Listener for login button
        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                Log.d("Message", "Login button clicked");
                loginlogoff liloobj = new loginlogoff(MainActivity.this);//passed in context of this activity
                XmlParser xmlpobj = new XmlParser();
                liloobj.setHostname(hostname.getText().toString());
                liloobj.setDomain(domain.getText().toString());
                liloobj.setPortnumber(Integer.parseInt(port.getText().toString()));
                liloobj.setUsername(username.getText().toString());
                liloobj.setPassword(password.getText().toString());
                try {
                    login();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
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
            Log.d("Message", "login() task status:" + reqobj.getStatus());
            reqobj.execute();
            Log.d("Message", "login() task running...");
        }

    }//end of login()

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
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
