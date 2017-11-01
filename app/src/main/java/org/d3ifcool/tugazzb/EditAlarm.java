package org.d3ifcool.tugazzb;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toolbar;

/**
 * Created by prase on 30/10/2017.
 */

public class EditAlarm extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_alarm);

        FirstFragment firstFragment = new FirstFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.firstLayout,firstFragment,firstFragment.getTag())
                .commit();

        SecondFragment secondFragment = new SecondFragment();
        manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.secondLayout,secondFragment,secondFragment.getTag())
                .commit();


    }
}
