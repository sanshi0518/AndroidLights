package com.github.sanshi0518.androidlights.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.github.sanshi0518.androidlights.R;
import com.github.sanshi0518.androidlights.widget.IOSStyleDialog;

public class IOSStyleDialogActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iosstyle_dialog);

        Button singleOption = (Button) findViewById(R.id.singleOption);
        Button doubleOptions = (Button) findViewById(R.id.doubleOptions);

        singleOption.setOnClickListener(this);
        doubleOptions.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        IOSStyleDialog.Builder builder;
        switch (v.getId()) {
            case R.id.singleOption:
                builder = new IOSStyleDialog.Builder(this);
                builder.setTitle(R.string.prompt);
                builder.setMessage("用户信息异动，请重新登录");
                builder.setPositiveButton(R.string.iknow, null);
                builder.create().show();
                break;

            case R.id.doubleOptions:
                builder = new IOSStyleDialog.Builder(this);
                builder.setTitle(R.string.prompt);
                builder.setMessage("用户信息异动，请重新登录");
                builder.setNegativeButton(R.string.cancel, null);
                builder.setPositiveButton(R.string.confirm, null);
                builder.create().show();
        }
    }
}
