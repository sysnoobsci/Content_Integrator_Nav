package com.systemware.contentintegrator.app;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by adrian.meraz on 6/3/2014.
 */
public class Home_Fragment extends Fragment {


    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater .inflate(R.layout.home_fragment, container, false);
        //TextView greeting = (TextView) getView().findViewById(R.id.textView2);
        //List<String> logonXmlTextTags = getActivity().getLogonXmlTextTags();

        return rootView;
    }
}