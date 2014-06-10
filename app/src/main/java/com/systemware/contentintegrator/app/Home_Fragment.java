package com.systemware.contentintegrator.app;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adrian.meraz on 6/3/2014.
 */
public class Home_Fragment extends Fragment {
    static View rootView;

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        rootView = inflater .inflate(R.layout.home_fragment, container, false);
        return rootView;
    }
    public static void setText(String text){
        TextView textView = (TextView) rootView.findViewById(R.id.textView2);
        String month = text.substring(4,5);
        String day = text.substring(6,7);
        String year = text.substring(0,3);
        textView.setText(month + "-" + day + "-" + year);
    }
}