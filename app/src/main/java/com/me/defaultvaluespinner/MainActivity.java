package com.me.defaultvaluespinner;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.me.defaultvaluespinner.view.DefaultValueSpinner;

import java.util.ArrayList;


public class MainActivity extends Activity implements DefaultValueSpinner.onSpinnerItemSelectedListener {

    private DefaultValueSpinner mSpinner;
    private ArrayList<String> mDatalist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSpinner = (DefaultValueSpinner) findViewById(R.id.default_value_spinner);
        mDatalist = new ArrayList<String>();
        mDatalist.add("item 1");
        mDatalist.add("item 2");
        mDatalist.add("item 3");
        initSpinner();
    }

    private void initSpinner() {

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, mDatalist);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(dataAdapter);
        mSpinner.setOnItemSelectedListener(this);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this, mDatalist.get(position), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
