package com.systemware.contentintegrator.app;

import android.app.Activity;

import android.app.ActionBar;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import java.util.Iterator;
import java.util.List;
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
    Dialog loginDialog = null;
    Context maContext = MainActivity.this;
    ArrayList<String> logonXmlTextTags;
    Bundle bundle1 = new Bundle();
    final static private int LOGIN_TIMEOUT = 500;//time in milliseconds for login attempt to timeout
    final static private int REQUEST_TIMEOUT = 500;

    private Boolean first_open = true;//keeps track of if the app is opening for the first time to show the home screen

    ProgressDialog progress;

    public ArrayList<String> getLogonXmlTextTags() {
        return logonXmlTextTags;
    }

    public void setLogonXmlTextTags(ArrayList<String> logonXmlTextTags) {
        this.logonXmlTextTags = logonXmlTextTags;
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

                progress = ProgressDialog.show(maContext, "Logging in...", "Please Wait", true);

                new Thread(new Runnable() {
                    public void run() {
                        final ReqTask reqobj = new ReqTask(liloobj.httpstringcreate(),//send login query to CI via asynctask
                                this.getClass().getName(), maContext);
                        try {
                            reqobj.execute().get(LOGIN_TIMEOUT, TimeUnit.MILLISECONDS);
                            XmlParser xobj3 = new XmlParser();
                            xobj3.parseXMLfunc(reqobj.getResult());
                            setLogonXmlTextTags(xobj3.getTextTag());
                            Bundle bundle1 = new Bundle();
                            bundle1.putStringArrayList("xmllogon",getLogonXmlTextTags());
                            //fragment.setArguments
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (TimeoutException e) {
                            ToastMessageTask tmtask = new ToastMessageTask(maContext,"Logon attempt timed out.");
                            tmtask.execute();
                            e.printStackTrace();
                        } catch (XmlPullParserException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progress.dismiss();
                                loginlogoff lobj = new loginlogoff(maContext);
                                lobj.isLoginSuccessful(reqobj);//check if login was successful
                                lobj.logonMessage(reqobj);//show status of login
                                if(lobj.getLogin_successful()){//if login is true,dismiss login screen
                                    loginDialog.dismiss();
                                }
                            }
                        });//end of UiThread
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
        Log.d("Message","aboutDialog() called");
        loginlogoff lobj = new loginlogoff(maContext);
        final String targetCIQuery = "http://" + lobj.getHostname() + "." +
                lobj.getDomain() + ":" + lobj.getPortnumber() + "/ci";
        final String aboutQuery = "?action=about";
        final String aboutQueryFeatures = "?action=about&opt=features";
        final Dialog aboutDialog = new Dialog(this);
        final List<String> listFeature = new ArrayList<String>();
        aboutDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Create about dialog
        aboutDialog.setContentView(R.layout.about_dialog);

        final TextView ciservername = (TextView) aboutDialog.findViewById(R.id.ci_servername_placeholder);
        final TextView civersion = (TextView) aboutDialog.findViewById(R.id.ci_version_placeholder);
        final TextView cinodename = (TextView) aboutDialog.findViewById(R.id.ci_nodename_placeholder);

        final ListView featureList = (ListView)aboutDialog.findViewById(R.id.features_list);

        new Thread(new Runnable() {
            public void run() {
                final XmlParser xobj = new XmlParser();
                final XmlParser xobj2 = new XmlParser();

                ReqTask reqobj = new ReqTask(targetCIQuery + aboutQuery,//query about CI information(not features)
                        this.getClass().getName(), maContext);
                ReqTask reqobj2 = new ReqTask(targetCIQuery + aboutQueryFeatures,//start a new query for CI features
                        this.getClass().getName(), maContext);
                try {
                    reqobj.execute().get(REQUEST_TIMEOUT,TimeUnit.MILLISECONDS);//wait for reqobj to finish before continuing
                    Log.d("Message", "reqobj task ran...");
                    Log.d("Variable", "reqobj.getResult()" + reqobj.getResult());
                    xobj.parseXMLfunc(reqobj.getResult());//parse result from query
                    reqobj2.execute().get(REQUEST_TIMEOUT,TimeUnit.MILLISECONDS);//wait for reqobj2 to finish before continuing
                    Log.d("Message","reqobj2 task ran...");
                    Log.d("Variable", "reqobj2.getResult()" + reqobj2.getResult());
                    xobj2.parseXMLfunc(reqobj2.getResult());
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
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("Message", "UI thread running...");
                        ArrayList<String> listOfTextTags;
                        listOfTextTags = xobj.getTextTag();
                        ciservername.setText(listOfTextTags.get(3));
                        civersion.setText(listOfTextTags.get(4));
                        cinodename.setText(listOfTextTags.get(5));

                        ArrayList<String> listOfTextTags2;
                        listOfTextTags2 = xobj2.getTextTag();
                        Iterator<String> it = listOfTextTags2.iterator();
                        while(it.hasNext())
                        {
                            if(!it.next().equals("0")){//avoid grabbing the return codes
                                listFeature.add(it.next());
                            }
                        }
                        ArrayAdapter adapter = new ArrayAdapter<String>(MainActivity.this,R.layout.list_item, listFeature);
                        featureList.setAdapter(adapter);
                        aboutDialog.show();
                    }
                });//end of UiThread
            }
        }).start();

    }//end of about dialog

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        Log.d("Variable", "Value of argument position: " + position);
        Fragment fragment = new Home_Fragment();
        if(getFirst_open()){//if first time opening app, show home screen fragment
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


