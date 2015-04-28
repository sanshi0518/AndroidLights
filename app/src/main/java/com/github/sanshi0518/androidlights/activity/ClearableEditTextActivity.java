package com.github.sanshi0518.androidlights.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.github.sanshi0518.androidlights.R;
import com.github.sanshi0518.androidlights.widget.ClearableEditText;

public class ClearableEditTextActivity extends Activity {

    private ClearableEditText mClearableEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clearable_edit_text);

        mClearableEditText = (ClearableEditText) findViewById(R.id.clearableEditText);
        mClearableEditText.setTextChangeListener(new ClearableEditText.TextChangedListener() {
            @Override
            public void afterTextChanged(String text) {
                Toast.makeText(ClearableEditTextActivity.this, "text changed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
